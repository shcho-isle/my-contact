package com.telecom.service.json;

import com.telecom.TestUtil;
import com.telecom.service.AbstractUserServiceTest;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("json")
public class JsonUserServiceTest extends AbstractUserServiceTest {

    @Value("${pathToFileFolder}")
    private String pathToFileFolder;

    @Before
    public void setUp() throws Exception {
        TestUtil.populateJsonDb(pathToFileFolder);
    }
}
