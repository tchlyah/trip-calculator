# Trip Calculator

This project takes an input file in CSV format containing on/off taps, calculate trips made by customer with total charges per trip, then create an output file in CSV file containing calculated trips.

## Example

### Input file: taps.csv

| **ID** | **DateTimeUTC**     | **TapType** | **StopId** | **CompanyId** | **BusID** | **PAN**          |
| ------ | ------------------- | ----------- | ---------- | ------------- | --------- | ---------------- |
| **1**  | 22-01-2018 13:00:00 | ON          | Stop1      | Company1      | Bus37     | 5500005555555559 |
| **2**  | 22-01-2018 13:05:00 | OFF         | Stop2      | Company1      | Bus37     | 5500005555555559 |

### Output file: 

| **Started**         | **Finished**        | **DurationSecs** | **FromStopId** | **ToStopId** | **ChargeAmount** | **CompanyId** | **BusID** | **PAN**          | **Status** |
| ------------------- | ------------------- | ---------------- | -------------- | ------------ | ---------------- | ------------- | --------- | ---------------- | ---------- |
| 22-01-2018 13:00:00 | 22-01-2018 13:05:00 | 300              | Stop1          | Stop2        | 3.25             | Company1      | Bus37     | 5500005555555559 | COMPLETED  |

## Requirements

Tip Calculator application is built using Java 17. So in order to build the application and run the java application locally, you'll need a JDK 17 distributions:
* [Eclipse Temurin](https://adoptium.net/)
* [Zulu OpenJDK](https://www.azul.com/downloads/zulu-community/?package=jdk)
* [Adopt OpenJDK Hotspot](https://adoptopenjdk.net/): Adopt OpenJDK got moved to Eclipse Temurin and won't be updated anymore.
* [Adopt OpenJDK OpenJ9](https://adoptopenjdk.net/)
* [Liberica JDK](https://bell-sw.com/)
* [Microsoft OpenJDK](https://www.microsoft.com/openjdk)

## Usage

```bash
Usage: tripCalculator [-hV] <tapsFile> <tripsFile>
Calculate trips from taps
      <tapsFile>    CSV file containing taps.
      <tripsFile>   CSV destination file to save calculated trips.
  -h, --help        Show this help message and exit.
  -V, --version     Print version information and exit.
```

### Build

This project is built using [Gradle](https://gradle.org) 

```bash
./gradlew clean build
```

### Run

You can run the project using native executables, Gradle [run task](https://docs.gradle.org/current/userguide/application_plugin.html#sec:application_tasks) or using generated zip distribution file.

#### Native executable

Native executables are built using [GraalVM Native Image](https://www.graalvm.org/22.0/reference-manual/native-image/), you don't need to have an installed JDK to run them.

Go to [latest release](https://github.com/tchlyah/trip-calculator/releases), and download the right executable for your platform, then run it using:

```bash
./trip-calculator-[os] taps.csv trips.csv
```

#### Gradle

```bash
./gradlew run --args="src/test/resources/csv/taps1.csv trips.csv"
```

#### Distribution file

```bash
cd build/distributions
unzip unzip trip-calculator-shadow-*.zip
cd trip-calculator-shadow-*/bin/

./trip-calculator src/test/resources/csv/taps1.csv trips.csv
```

## Assumptions

The trip calculator algorithm assumes some points:

* Input files are well-formed and without any missing data: There is no data validation.
* Any tap in the file is assumed successful: There is no payment validation.
* A person can only use one PAN in a trip: The _person_ is identified by his _PAN_.
* A bus is identified by Bus ID and Company ID: same bus ID with different company ID is considered a different bus.
* The _first tap_ for a PAN in the same bus is always considered as _tap on_, the _second one_, if it happens, is  considered as _top off_: If a person forget to tap on when getting on the bus, but tap off when leaving the bus, it is considered as a tap on.
* Trip duration is not bounded: it can be completed on multiple days.
