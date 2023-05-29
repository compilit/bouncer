# basic-functions

An assortment of convenience functions which can be useful in any sort of larger project.

### Guard functions

Say you have a method that could potentially throw an exception, and in that case you would want to
return either null or a default value, then this package would provide you with that functionality.
The API is quite self-explanatory. Here is a little example, however.

# Installation

Get this dependency with the latest version

```xml
    <dependency>
      <artifactId>functions</artifactId>
      <groupId>com.compilit</groupId>
    </dependency>
```

### usage

```java


class ExampleClass {

  private final Integer value;

  public byte[] getValueAsAString() {
    return MappingGuards.orNull(() -> value.byteValue()); //returns null when the value is null
  }

}
```

### Fuzzy matching

While there are quite a few libraries out there that can provide a form of fuzzy matching, this one
actually compares two values as a human being would. By which I mean, it cares about the amount of
actual matching characters and their position while looking for actual matching sequences
within the values. This means it can be the perfect library to compare words that could contain
typo's for example.

```java
class Example {

  @Test
  public void someMethodComparingTwoStrings(String one, String two) {
    assertThat(fuzzyMatches(value, otherValue, 75)).isTrue();
    //will return true if both values are at least 75 percent equal
  }

}
```

#### The way fuzzy matching is applied

First part:
It takes the characters of the first String and looks if all characters are present in the other
String. For each character that is not present in the other String, the relative percentual value
will be subtracted of max value, which starts at 100.

Second part:
It will append each character to a StringBuilder and each round check if the StringBuilder sequence
is present in the other String. If it is not present, the relative percentual value
will be also be subtracted of max value. The StringBuilder will then be reset.
