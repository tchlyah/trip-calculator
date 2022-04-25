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
./gradlew run --args="src/main/resources/csv/taps1.csv trips.csv"
```

#### Distribution file

```bash
cd build/distributions
unzip unzip trip-calculator-shadow-*.zip
cd trip-calculator-shadow-*/bin/

./trip-calculator src/main/resources/csv/taps1.csv trips.csv
```
