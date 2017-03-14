package com.lardi.service.json;

import com.lardi.service.AbstractContactServiceTest;
import com.lardi.service.UserService;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;

import java.nio.file.Files;
import java.nio.file.Paths;

import static com.lardi.ContactTestData.*;
import static com.lardi.UserTestData.*;

@ActiveProfiles("json")
public class JsonContactServiceTest extends AbstractContactServiceTest {

    @Autowired
    private Environment env;

    @Autowired
    protected UserService userService;

    @Before
    public void setUp() throws Exception {
        Files.deleteIfExists(Paths.get(env.getProperty("pathToFileFolder") + "com.lardi.model.Rule.json"));
        Files.deleteIfExists(Paths.get(env.getProperty("pathToFileFolder") + "com.lardi.model.User.json"));
        Files.deleteIfExists(Paths.get(env.getProperty("pathToFileFolder") + "com.lardi.model.Contact.json"));

        userService.save(VANO);
        userService.save(SERG);

        service.save(VANO_CONTACT1, VANO_ID);
        service.save(VANO_CONTACT2, VANO_ID);
        service.save(VANO_CONTACT3, VANO_ID);
        service.save(VANO_CONTACT4, VANO_ID);
        service.save(VANO_CONTACT5, VANO_ID);
        service.save(VANO_CONTACT6, VANO_ID);
        service.save(SERG_CONTACT1, SERG_ID);
        service.save(SERG_CONTACT2, SERG_ID);
    }
}
