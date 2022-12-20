package net.golovach.eshop.inject;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContextHolder {

    private static final Map<String, ApplicationContext> pathToContextRepo = new HashMap<>();

    // Pass path not to have multiple Application Contexts but to satisfy requirements:
    //   1. have kind-of global variable in Java (this solution is not sexy as Java does not have global vars)
    //   2. reading value directly from web.xml here is against single responsibility principle
    // Sample vs: appContext-dao-aop-schema.xml
    //
    static synchronized ApplicationContext getClassPathXmlApplicationContext(String path) {
        if (!pathToContextRepo.containsKey(path)) {
            pathToContextRepo.put(path, new ClassPathXmlApplicationContext(path));
        }
        return pathToContextRepo.get(path);
    }

}
