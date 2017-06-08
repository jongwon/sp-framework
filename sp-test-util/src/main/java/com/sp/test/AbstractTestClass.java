package com.sp.test;

import lombok.extern.slf4j.Slf4j;
import org.junit.*;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import javax.transaction.Transactional;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static java.lang.String.format;

/**
 * Created by jongwon on 2017. 6. 8..
 */
@Slf4j
public class AbstractTestClass {

    protected static Validator validator;

    @Rule
    public TestName testName = new TestName();

    @BeforeClass
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Before
    public void before() {
        log.info(">>>> {} 시작 ", testName.getMethodName());
    }

    @After
    public void after() {
        log.info("<<<< {} 끝", testName.getMethodName());
    }

    protected String randomNumGenerate(int digit){
        String id = ""+java.util.concurrent.ThreadLocalRandom
                .current()
                .nextLong((long)Math.pow(10, digit-1), (long)Math.pow(10, digit));
        log.info("{} random id generated", id);
        return id;
    }

}
