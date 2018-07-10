[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![Build Status](https://travis-ci.org/Jezorko/sainsbury-serverside-test.svg?branch=master)](https://travis-ci.org/Jezorko/sainsbury-serverside-test)

## Sainsbury’s groceries website scrapping application

### Task specification

Task specification can be found [here](https://jsainsburyplc.github.io/serverside-test/).

## Building

### Running tests

To run all unit tests:

```bash
mvn clean test
```

### Packaging

To build an executable `.jar`:

```bash
mvn clean package
```

### Running

To run the `.jar`:

```bash
java -jar target/sainsbury-serverside-test-1.0-SNAPSHOT-jar-with-dependencies.jar
```