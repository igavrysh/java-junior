package com.gavrysh.demo.stream15demoapp;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

import java.time.LocalTime;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class Service {

    private final CacheManager cacheManager;

    @Cacheable(
            cacheNames = "ops",
            key = "'my-key:'+#name",
            //condition = "'hello'.equals(#name)"
            unless = "#result.startsWith('aaa')"
    )
    @SneakyThrows
    public String operation(final String name) {
        Thread.sleep(3000);

        return name + " - " + LocalTime.now().toString();
    }

    @CacheEvict(
            cacheNames = "ops",
            key = "'my-key:'+#name"
    )
    public String clean(final String name) {
        return null;
    }

    @CachePut(
            cacheNames = "ops",
            key = "'my-key:'+#name"
    )
    public String update(final String name) {
        Cache cache = cacheManager.getCache("ops");
        return "XoXoXo";
    }

    @Cacheable(
            cacheNames = "ops2",
            key = "'my-key:' + #name1 + ':' + #name2.length()"
    )
    @SneakyThrows
    public String operation2(final String name1, final String name2) {
        Thread.sleep(3000);

        return name1 + " - " + name2  + " - " + LocalTime.now().toString();
    }

}
