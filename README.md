# cand

### To build all

```
./mvn_build.sh
```

### To build a specific module

```shell
cd <dir>
mvn clean install
OR
mvn -f <module name>/pom.xml clean install
```

For example:

```shell
cd atm-machine
mvn clean install
OR
mvn -f atm-machine/pom.xml clean install
```
