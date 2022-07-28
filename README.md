# java-warnings

## What is it?

Normal code execution would either complete or throw an exception in case of an error. What if the code executed normally but with a concern? Maybe the code had to try a few times, maybe a request took longer than usual? This is the idea behind Java Warnings. This is a thought experiment.

## How does it work?

Check the class `test.SimpleWarningsDemo.java` for a typical use case.\
Suppose that a code executed slower than usual. You would code it like:

```java
long warningId = 0L;
if (executionTime>usualTime) {
    warningId = WarningsRegister.registerWarning("Ouch!"),executionTime,usualTime);
}
```

To fetch the warning you can:

```java

Optional<Warning[]> warnings = WarningsRegister.getWarnings(Thread.currentThread().getId(),warningId);

```

Or (This will return all the warnings generated within the given thread id):

```java

Optional<Warning[]> warnings = WarningsRegister.getWarnings(Thread.currentThread().getId(),warningId);

```

More updates will follow soon.
