# cand

### To build all

```
./mvn_build.sh
```

### To build a specific module

```shell
mvn -f <module name>/pom.xml clean install
OR -- if using gradle
gradle clean build
OR
cd <dir>
mvn clean install
```

For example:

```shell
cd atm-machine
mvn clean install
OR
mvn -f atm-machine/pom.xml clean install
```
