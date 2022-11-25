# Task


## Task for Aspects (AOP) 

Write universal cache using aspect

Example of usage:
```
Map<List, Object> cache = new HashMap<>();
// class, method, argument
cache.put(Arrays.asList(UserDao.class, "selectByLogin", "mike"), new User(1, "mike", "", "", null));
cache.put(Arrays.asList(Product.class, "selectById", 123), new Product(123, "Paper"));
cache.put(Arrays.asList(Product.class, "selectById", 321), new Product(321, "Eggs"))

```