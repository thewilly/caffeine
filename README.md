<!-- <img src="/docs/cup-coffe.png" align="right" />-->

# Welcome to caffeine!


|| **Architecture** | **Status** |
|:------:|:-:|:----------:|
|** Ubuntu Trusty 14.04**|x86_64|[![Build Status](https://travis-ci.org/thewilly/caffeine.svg?branch=master)](https://travis-ci.org/thewilly/caffeine)|

### What's caffeine?
Caffeine is a java cache library to increase the performance of web based services.

```java
GenericCache<String> cache = new GenericCacheImpl<String>();
cache.save( "sadf", "Hola me llamo thewilly", 2000, () -> { System.err.println( "Goodbie" ); } );

System.out.println(cache);
// Will print the sadf record;

String record = cacge.get( "sadf" ); // record = Hola me llamo thewilly

// Wait for 2000 ms.
// Will print Goodbie.

System.out.println(cache);
// Will print nothing as it is empty.

record = cacge.get( "sadf" ); // record = null
```
