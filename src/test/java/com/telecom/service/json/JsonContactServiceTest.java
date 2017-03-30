package com.telecom.service.json;

import com.telecom.TestUtil;
import com.telecom.service.AbstractContactServiceTest;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("json")
public class JsonContactServiceTest extends AbstractContactServiceTest {

    @Value("${pathToFileFolder}")
    private String pathToFileFolder;

    @Before
    public void setUp() throws Exception {
        TestUtil.populateJsonDb(pathToFileFolder);
    }
}
