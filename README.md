# Network collector (netcol)

This program collects and processes network config data (ifconfig, route, etc) from remote hosts

## Getting Started

### Prerequisites

For this project you need to install the next software:

```
java 1.8 or higher
gradle
docker
ssh client
```

### Installing

```
```

## Running

```
01. git clone https://github.com/avesat/netcol_collector.git
02. cd netcol_collector
03. ./gradlew  build 
04. docker build -t netcol_collector .
05. docker run --name netcol_collector -p 8086:8086 netcol_collector
06. docker run --name ubuntu_sshd rastasheep/ubuntu-sshd:14.04
07. docker run -it -p 3307:3306 --name mysql-srv -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=netcol-db mysql
```

### Docker
```
docker build -t netcol_collector .
docker run --name netcol_collector -p 8086:8086 netcol_collector
docker run --name ubuntu_sshd rastasheep/ubuntu-sshd:14.04
docker run -it -p 3307:3306 --name mysql-srv -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=netcol-db mysql
```

License
=======
Sourcerer is under the MIT license. See the [LICENSE](https://github.com/sourcerer-io/sourcerer-app/blob/develop/LICENSE.md) for more information.
