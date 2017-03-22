package com.telecom;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@TestPropertySource("/test.properties")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public abstract class ApplicationAbstractTest {
}