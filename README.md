# java-warnings

## What is it?

The idea behind this project crossed my mind when I was sitting in a cafe.
A Java program execution can either flow or be interrupted by an Exception. What if we must keep the program running but register potential issues, like slower than usual program execution or an undesirable branching in the flow?
It's like the program has executed, but with concern. The warning would be similar in properties to an Exception; however, instead of being thrown, it is registered in a static registry.
It will then be possible to query the registry for any registered warning for a given thread id.

## What does it look like?

Given:

```java
Map<String, Object> namedParams = new HashMap<>();
namedParams.put("toul", 100);
namedParams.put("3ard", 2);

Object[] unNamedParams = { "Un", "Deux", "Trois" };

collectedWarningIDs.add(WarningsRegister.registerWarning("es demasiado lento", namedParams, unNamedParams));
```

Note the `namedParams` and `unNamedParams` are optional. Invoking `toString()` on the warning registered above will produce this output:

```
Warning message: es demasiado lento.
ThreadID: 1, Warning ID: 3.
Named Parameters:
        toul:100
        3ard:2
Unnamed parameters:
        Un
        Deux
        Trois
Stack trace:
        test.SimpleWarningsDemo.doSomeWork4(SimpleWarningsDemo.java:84)
        test.SimpleWarningsDemo.<init>(SimpleWarningsDemo.java:44)
        test.SimpleWarningsDemo.main(SimpleWarningsDemo.java:89)
```

Also, note that the `Warning` class is immutable and that all the properties displayed above are reachable through the classes' `getter` methods.

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

Again this is more of a thought experiment, showing what we can do with the Java language.
