package com.gavrysh.demo.stream16.integration;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gavrysh.demo.stream16.Controller;
import com.gavrysh.demo.stream16.model.ExcelRecord;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.EqualToJsonPattern;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.testcontainers.containers.wait.strategy.Wait.forHttp;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FullIntegrationTest {

    private static final Integer CONSUMER_PORT = 8080;

    @Container
    private final static GenericContainer<?> WIREMOCK_CONTAINER
            = new GenericContainer<>("rodolpheche/wiremock:latest")
            .waitingFor(forHttp("/__admin"))
            .withExposedPorts(CONSUMER_PORT);

    @Container
    private final static LocalStackContainer LOCAL_STACK_CONTAINER
            = new LocalStackContainer(
                    DockerImageName.parse("localstack/localstack"))
            .withServices(LocalStackContainer.Service.S3);

    @DynamicPropertySource
    static void wiremockProps(final DynamicPropertyRegistry registry) {
        registry.add("app.base-url", () ->
            "http://" + WIREMOCK_CONTAINER.getHost() + ":" + WIREMOCK_CONTAINER.getMappedPort(CONSUMER_PORT)
        );
    }

    @LocalServerPort
    int localPort;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private S3Client s3Client;

    @BeforeAll
    static void setupWiremock() {
        final Logger log = LoggerFactory.getLogger(FullIntegrationTest.class);
        log.info(WIREMOCK_CONTAINER.getLogs());

        final Integer mappedPort = WIREMOCK_CONTAINER.getMappedPort(CONSUMER_PORT);
        WireMock.configureFor(WIREMOCK_CONTAINER.getHost(), mappedPort);

        log.info("http://" + WIREMOCK_CONTAINER.getHost() + ":" + mappedPort);

    }

    @BeforeEach
    void configRestAssured() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = localPort;
    }

    @Test
    void testEverything() throws Exception {
        WireMock.stubFor(
                WireMock.post(WireMock.urlPathEqualTo("/records"))
                        .willReturn(
                                ResponseDefinitionBuilder.responseDefinition().withStatus(200).withBody("Yay!")
                        )
        );

        final URL resource = getClass().getClassLoader().getResource("cool-excel.xlsx");
        final byte[] bytes = Files.readAllBytes(Paths.get(resource.toURI()));

        RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(Controller.EXCEL_MEDIA_TYPE)
                .multiPart("file", "cool-excel.xlsx", bytes)
                .when()
                .post("/excel")
                .then()
                .statusCode(HttpStatus.ACCEPTED.value())
                .body("uploadedRecordsCount", Matchers.equalTo(3))
                .body("failedCount", Matchers.equalTo(2));

        WireMock.verify(
                WireMock.postRequestedFor(WireMock.urlPathEqualTo("/records"))
                        .withRequestBody(new EqualToJsonPattern(
                                objectMapper.writeValueAsString(
                                        List.of(
                                                new ExcelRecord("hello", "helloworld"),
                                                new ExcelRecord("Slava", "Ukraini"),
                                                new ExcelRecord("Geroyam", "Slava")
                                        )
                                ),
                                true,
                                false
                        ))
        );

        final ResponseInputStream<GetObjectResponse> response = s3Client.getObject(
                GetObjectRequest.builder()
                        .bucket("my-bucket")
                        .key("cool-excel.xlsx")
                        .build());

        assertThat(response.readAllBytes()).isEqualTo(bytes);
    }

    @TestConfiguration
    public static class ConfigForTest {
        @Bean
        @Primary
        public S3Client s3ClientForTest() {
            final var client = S3Client.builder()
                    .endpointOverride(LOCAL_STACK_CONTAINER.getEndpointOverride(LocalStackContainer.Service.S3))
                    .credentialsProvider(
                            StaticCredentialsProvider.create(
                                    AwsBasicCredentials.create(
                                            LOCAL_STACK_CONTAINER.getAccessKey(),
                                            LOCAL_STACK_CONTAINER.getSecretKey()))
                    )
                    .region(Region.of(LOCAL_STACK_CONTAINER.getRegion()))
                    .build();

            client.createBucket(
                    CreateBucketRequest.builder()
                            .bucket("my-bucket")
                            .createBucketConfiguration(
                                    CreateBucketConfiguration.builder().locationConstraint(LOCAL_STACK_CONTAINER.getRegion()).build()
                            )
                            .build());
            System.out.println("Creating bucket: ny-bucket");
            client.waiter().waitUntilBucketExists(
                    HeadBucketRequest.builder()
                            .bucket("my-bucket")
                            .build());
            System.out.println("my-bucket is ready.");

            return client;
        }
    }
}
