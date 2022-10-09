## Docker

### To build jar with all libs required
```shell
./gradlew bootJar
```

### To build docker container

```shell
docker build -t app . 
# aka
# docker build -t app -f Dockerfile
```

### To run docker container
```shell
docker run -p 8899:8080 -t app:latest
```

### To check docker
```shell
docker ps
```

### To run console in docker 
```shell
docker ps
docker exec -it <NAME|CONTAINER ID> /bin/sh
```

## Spring Docker

### to use Spring container builder
```shell
./gradlew bootBuildImage
```

### & run in docker
```shell
docker run -p 8899:8080 -t docker.io/library/stream-16-demo-app:0.0.1-SNAPSHOT 
```


## Minikube

Good doc starting point: https://minikube.sigs.k8s.io/docs/start/

0. Start
```shell
minikube start
```

1. Push image to minikube
```
eval $(minikube docker-env)
```

2. build using spring goal
```
./gradlew bootBuildImage
```

3. create deployment to kubectl
```shell
kubectl create deployment app --image=docker.io/library/stream-16-demo-app:0.0.1-SNAPSHOT 
```

4. create kubectl deployment app
```shell
kubectl expose deployment app --type=NodePort --port=8080
```
```shell
kubectl describe deployments.app app
```

```shell
kubectl get pods
```
```shell
kubectl port-forward <POD_NAME from kubectl get pods> 7777:8080
```

in next tab
```
curl localhost:7777/actuator/info
```

5. to stop minikube:
```shell
minikube stop
```

