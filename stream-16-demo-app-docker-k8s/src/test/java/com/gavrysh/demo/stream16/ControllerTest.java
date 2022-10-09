package com.gavrysh.demo.stream16;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.gavrysh.demo.stream16.model.ExcelReadResult;
import com.gavrysh.demo.stream16.model.ExcelRecord;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@WebMvcTest(controllers = Controller.class)
public class ControllerTest {

    private static final String EXCEL_FILE = "cool-excel.xlsx";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ExcelReader excelReaderMock;

    @MockBean
    private RecordSender recordSenderMock;

    @MockBean
    private AwsS3Saver awsS3SaverMock;

    @Test
    void shouldAcceptFile() throws IOException, URISyntaxException {

        when(excelReaderMock.read(any())).thenReturn(new ExcelReadResult(
                List.of(new ExcelRecord("abc", "xyz")),
                42
        ));

        final URL resource = getClass().getClassLoader().getResource(EXCEL_FILE);
        final byte[] bytes = Files.readAllBytes(Paths.get(resource.toURI()));

        RestAssuredMockMvc.given()
                .mockMvc(mockMvc)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(Controller.EXCEL_MEDIA_TYPE)
                .multiPart("file", EXCEL_FILE, bytes)
                .when()
                .post("/excel")
                .then()
                .statusCode(HttpStatus.ACCEPTED.value())
                .body("uploadedRecordsCount", Matchers.equalTo(1))
                .body("failedCount", Matchers.equalTo(42));

        //verify(recordSenderMock).send(any());
        //verify(awsS3SaverMock).save(EXCEL_FILE, new byte[0]);

    }

}
