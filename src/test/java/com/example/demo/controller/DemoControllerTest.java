package com.example.demo.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class DemoControllerTest {

    @Test
    public void testHello(){
        assertEquals("hello", new DemoController().sayHello());
    }

}