
### Build Maven
```
$ mvn clean install
```

### Build Docker Image
```
$ docker build -t service-registry:latest .
```

### Docker Run Container
```
$ docker run -dit --name service-registry -p 8761:8761 service-registry:latest
```

### SSH Container
```
$ docker exec -it service-registry bin/sh
```