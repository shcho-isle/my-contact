package com.lardi.service.json;

import com.lardi.service.AbstractUserServiceTest;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import java.nio.file.Files;
import java.nio.file.Paths;

import static com.lardi.UserTestData.*;

@ActiveProfiles("json")
public class JsonUserServiceTest extends AbstractUserServiceTest {

    @Autowired
    private Environment env;

    @Before
    public void setUp() throws Exception {
        Files.deleteIfExists(Paths.get(env.getProperty("pathToFileFolder") + "com.lardi.model.Rule.json"));
        Files.deleteIfExists(Paths.get(env.getProperty("pathToFileFolder") + "com.lardi.model.User.json"));
        Files.deleteIfExists(Paths.get(env.getProperty("pathToFileFolder") + "com.lardi.model.Contact.json"));

        service.save(VANO);
        service.save(SERG);
    }
}
