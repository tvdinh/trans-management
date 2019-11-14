## Content

The repository consists of

1. pom.xml: Maven build file
2. src/ : All the Java source/test code and resource files
3. executable/TransManagementApp.jar : compiled binary file, ready to use. But steps to build the app from sources code also provided
4. transLog/translog1.txt: A sample of transaction log file, same as the one given in the assignment

## Build

### System/Tool requirements

1. Java 8
2. Maven >= 3.3.3

### Step

#### Build the project with maven

```
mvn clean install
```

A final jar file, named "TransManagementApp.jar" produced in target/ (a built version is also available in /executable

## Run

The program expects exactly 4 inputs:
 1. path to transaction load file
 2. accountId of interest (e.g ACC334455, ACC778899)
 3. Start date (strictly in "dd/MM/yyyy HH:mm:ss" format)
 4. End date (strictly in "dd/MM/yyyy HH:mm:ss" format)

Run with the following command:

```
java -jar <path/to/TransManagementApp.jar> <path/to/trans_log_file> <accountId> <start_date> <end_date>
```

For example, in the root folder:

```
java -jar executable/TransManagementApp.jar transLog/translog1.txt ACC334455 '20/10/2018 12:00:00' '20/10/2018 19:00:00'
```

Output:

```
Relative balance for account ACC334455 from 20/10/2018 12:00:00 to 20/10/2018 19:00:00 is: -25.0
Number of transactions included is:1
```

## Design & Solution

The heart of the program is the class `TransactionManager`, most of the rest are only supporting classes (log parsing or date utils class). When the application starts, a `TransactionManager` is initialized with a logFile and load all the transactions into a transaction storage, the `TransactionManager` iterates through all the transaction in order of time and updates the status of the `Transaction`, i.e if the transaction is a REVERSAL transaction, it goes updating the status of the one being reversed (via a flag) to `reversed=true`

```java
    Transaction trans = resolver.fromLogEntry(line);
    transStorage.add(trans);
    if (isReversal(trans)) {
        reverse(trans.getRelatedTransaction());
    }
```    

Thus, when calculate the relative balance, the `TransactionManager` filters out all the `Transaction` that matches all the below:
1. Either accountId matches toAccount or fromAccount
2. Within the date range
3. Not reversed

```java
        return transStorage.stream()
                .filter(
                        t -> (t.getFromAccount().equalsIgnoreCase(accountId) || t.getToAccount().equalsIgnoreCase(accountId))
                        && (t.getCreatedDate().before(end) && t.getCreatedDate().after(start))
                        && (t.getTransType().equals(TransactionType.PAYMENT))
                        && (!t.isReversed()))
                .collect(Collectors.toList());
```

After obtaining all the relevant transaction, calculating the relative balance is trivial

```java
        for (Transaction transaction : matchedTrans) {
            if (transaction.getFromAccount().equalsIgnoreCase(accountId)) {
                relativeBalance -= transaction.getAmount();
            } else if(transaction.getToAccount().equalsIgnoreCase(accountId)) {
                relativeBalance += transaction.getAmount();
            } 
        }
```

## Test

Take a few use case

Data
```
TX10001, ACC334455, ACC778899, 20/10/2018 12:47:55, 25.00, PAYMENT
TX10002, ACC334455, ACC998877, 20/10/2018 17:33:43, 10.50, PAYMENT
TX10003, ACC998877, ACC778899, 20/10/2018 18:00:00, 5.00, PAYMENT
TX10004, ACC334455, ACC998877, 20/10/2018 19:45:00, 10.50, REVERSAL,TX10002
TX10005, ACC334455, ACC778899, 21/10/2018 09:30:00, 7.25, PAYMENT 
```

- Calculate relative balance for account ACC778899 from '20/10/2018 12:00:00' to '20/10/2018 19:00:00'

This includes TX10002 and TX10003, hence RB is 30.00

```
 java -jar executable/TransManagementApp.jar transLog/translog1.txt ACC778899 '20/10/2018 12:00:00' '20/10/2018 19:00:00'
```
Output:

```
Relative balance for account ACC778899 from 20/10/2018 12:00:00 to 20/10/2018 19:00:00 is: 30.0
Number of transactions included is:2
```

If extends the end to "21/10/2018 09:30:01", it should also includes TX10005, hence RB is 37.25

```
java -jar executable/TransManagementApp.jar transLog/translog1.txt ACC778899 '20/10/2018 12:00:00' '21/10/2018 09:30:01'
```

```
Relative balance for account ACC778899 from 20/10/2018 12:00:00 to 21/10/2018 09:30:01 is: 37.25
Number of transactions included is:3
```
