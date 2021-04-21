package com.obtk.test;

import com.obtk.auth.pub.mapper.MenuMapper;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;
import java.util.List;

public class Test1 {

    @Test
    public void test1(){
        //测试方法1
        ApplicationContext applicationContext=
                new ClassPathXmlApplicationContext("applicationContext.xml");
        MenuMapper menuMapper=(MenuMapper) applicationContext.getBean("menuMapper");
        List<Integer> ids= Arrays.asList(new Integer[]{1,2});
        menuMapper.findTopMenu(ids).forEach(menu -> System.out.println(menu.getTitle()));

    }

    @Test
    public void test2(){

        ApplicationContext applicationContext=
                new ClassPathXmlApplicationContext("applicationContext.xml");
        MenuMapper menuMapper=(MenuMapper) applicationContext.getBean("menuMapper");
        List<Integer> ids= Arrays.asList(new Integer[]{1,2});
        menuMapper.findChildMenu(ids,5).forEach(menu -> System.out.println(menu.getTitle()));

    }
}
