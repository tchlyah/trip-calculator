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

You can run the project using Gradle [run task](https://docs.gradle.org/current/userguide/application_plugin.html#sec:application_tasks) or using generated zip distribution file.

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
