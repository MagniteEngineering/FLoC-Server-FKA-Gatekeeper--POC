# Gatekeeprid-endpoint and Chrome Extension

The Gatekeeper proposal can be found in https://github.com/MagniteEngineering/Gatekeeper

## Setting up Gatekeeprid-endpoint

### Build and run gatekeeper-endpoint locally

Setup mysql locally and use the [DB scripts](/gatekeeper-endpoint/src/main/resources/schema.sql)

Update application-local.properties

```
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
```

```
cd gatekeeper-endpoint
mvn clean install
./start.sh
```


