[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![Build Status](https://travis-ci.org/Jezorko/sainsbury-serverside-test.svg?branch=master)](https://travis-ci.org/Jezorko/sainsbury-serverside-test)

## Sainsbury’s groceries website scrapping application

### Task specification

Newest version of the task specification can be found [here](https://jsainsburyplc.github.io/serverside-test/).

Mirror captured at the time of solving this task (2018-07-11) can be found [here](task-description.pdf).

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

## Possible improvements

A few details were not included in the task specification so I went with the "line of lowest resistance".
It means that many aspects of the code included in this repository could be improved.

### Error handling

There is a multitude of errors that may or may not occur when parsing HTML.
I have tried my best to check for all the nulls so that the program will not fail even if data is absent.
The product details page is very inconsistent and I can imagine this scrapper crashing on any page I have not tested.

From my point of view, it is very helpful to have all the crash details when the crash happens.
Therefore I did not catch any exceptions—nothing gives more insight into a failure as well as stack traces do.

### Program arguments

No program arguments are supported (the URL is hardcoded in the main application class).

### Testing

HTML specification is enormous and there are probably many corner cases that I haven't tested against.
