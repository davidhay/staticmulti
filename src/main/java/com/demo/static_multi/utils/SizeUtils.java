package com.demo.static_multi.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SizeUtils {
    
    public static int increase(int value) {
        log.info("increase called - not mocked - on thread [{}]", Thread.currentThread().getName());  
        return value * 2;
    }

}
