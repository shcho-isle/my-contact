package com.telecom.service.json;

import com.telecom.model.Contact;
import com.telecom.service.AbstractContactServiceTest;
import com.telecom.service.UserService;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ActiveProfiles;

import java.nio.file.Files;
import java.nio.file.Paths;

import static com.telecom.ContactTestData.*;
import static com.telecom.UserTestData.*;

@ActiveProfiles("json")
public class JsonContactServiceTest extends AbstractContactServiceTest {

    @Value("${pathToFileFolder}")
    private String pathToFileFolder;

    @Autowired
    protected UserService userService;

    @Before
    public void setUp() throws Exception {
        Files.deleteIfExists(Paths.get(pathToFileFolder + "com.telecom.model.Rule.json"));
        Files.deleteIfExists(Paths.get(pathToFileFolder + "com.telecom.model.User.json"));
        Files.deleteIfExists(Paths.get(pathToFileFolder + "com.telecom.model.Contact.json"));

        userService.save(VANO);
        userService.save(SERG);

        service.save(new Contact(VANO_CONTACT1), VANO_ID);
        service.save(new Contact(VANO_CONTACT2), VANO_ID);
        service.save(new Contact(VANO_CONTACT3), VANO_ID);
        service.save(new Contact(VANO_CONTACT4), VANO_ID);
        service.save(new Contact(VANO_CONTACT5), VANO_ID);
        service.save(new Contact(VANO_CONTACT6), VANO_ID);
        service.save(new Contact(SERG_CONTACT1), SERG_ID);
        service.save(new Contact(SERG_CONTACT2), SERG_ID);
    }
}
