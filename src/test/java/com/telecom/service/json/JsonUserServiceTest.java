package com.telecom.service.json;

import com.telecom.service.AbstractUserServiceTest;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;

import java.nio.file.Files;
import java.nio.file.Paths;

import static com.telecom.UserTestData.*;

@ActiveProfiles("json")
public class JsonUserServiceTest extends AbstractUserServiceTest {

    @Value("${pathToFileFolder}")
    private String pathToFileFolder;

    @Before
    public void setUp() throws Exception {
        Files.deleteIfExists(Paths.get(pathToFileFolder + "com.telecom.model.Rule.json"));
        Files.deleteIfExists(Paths.get(pathToFileFolder + "com.telecom.model.User.json"));
        Files.deleteIfExists(Paths.get(pathToFileFolder + "com.telecom.model.Contact.json"));

        service.save(VANO);
        service.save(SERG);
    }
}
