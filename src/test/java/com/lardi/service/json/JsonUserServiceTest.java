package com.lardi.service.json;

import com.lardi.service.AbstractUserServiceTest;
import com.lardi.util.ServiceUtils;
import org.junit.Before;
import org.springframework.test.context.ActiveProfiles;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import static com.lardi.UserTestData.*;

@ActiveProfiles("json")
public class JsonUserServiceTest extends AbstractUserServiceTest {

    @Before
    public void setUp() throws Exception {
        Properties properties = ServiceUtils.getProperties();
        Files.deleteIfExists(Paths.get(properties.getProperty("pathToFileFolder") + "com.lardi.model.Rule.json"));
        Files.deleteIfExists(Paths.get(properties.getProperty("pathToFileFolder") + "com.lardi.model.User.json"));
        Files.deleteIfExists(Paths.get(properties.getProperty("pathToFileFolder") + "com.lardi.model.Contact.json"));

        service.save(VANO);
        service.save(SERG);
    }
}
