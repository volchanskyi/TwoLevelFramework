package com.automationpractice.tests;

import org.apache.commons.lang3.RandomStringUtils;

public class DataGenerator {

      String names = RandomStringUtils.randomAscii(20, 50);
      String names2 = RandomStringUtils.randomAlphanumeric(20, 50);
      String names3 = RandomStringUtils.randomNumeric(10, 20);
      String names4 = RandomStringUtils.randomGraph(10, 20);
    
    
    public void blb (String str) {
	System.out.println(names);
	System.out.println(names2);
	System.out.println(names3);
    }
    
}
