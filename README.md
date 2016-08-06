# Route map

* app/ folder contains the front end
* server/ folder contains the dropwizard API

## Installation

### Prerequisite
* nodejs [Install](https://nodejs.org/en/download/)
* java
* mvn
* docker
* docker-compose [Install](https://github.com/docker/compose/releases)

### Web App
```sh
cd app/
npm i -g bower
bower install
```

### Java API
```sh
cd server
mvn clean && mvn package
```
### Running containers
From the root of project
```
docker-compose up
```
