package com.telecom;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import com.telecom.model.User;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestUtil {
    public static ResultActions print(ResultActions action) throws UnsupportedEncodingException {
        System.out.println(getContent(action));
        return action;
    }

    public static String getContent(ResultActions action) throws UnsupportedEncodingException {
        return action.andReturn().getResponse().getContentAsString();
    }

//    public static void mockAuthorize(User user) {
//        SecurityContextHolder.getContext().setAuthentication(
//                new UsernamePasswordAuthenticationToken(new AuthorizedUser(user), null, user.getRoles()));
//    }

    public static RequestPostProcessor userHttpBasic(User user) {
        return SecurityMockMvcRequestPostProcessors.httpBasic(user.getLogin(), user.getPassword());
    }

    public static RequestPostProcessor userAuth(User user) {
        return SecurityMockMvcRequestPostProcessors.authentication(new UsernamePasswordAuthenticationToken(user.getLogin(), user.getPassword()));
    }

    public static void populateJsonDb(String pathToFileFolder) throws IOException {
        Path users = Paths.get(pathToFileFolder + "com.telecom.model.User.json");
        Path roles = Paths.get(pathToFileFolder + "com.telecom.model.Role.json");
        Path contacts = Paths.get(pathToFileFolder + "com.telecom.model.Contact.json");

        BufferedWriter usersWriter = Files.newBufferedWriter(users);
        BufferedWriter rolesWriter = Files.newBufferedWriter(roles);
        BufferedWriter contactsWriter = Files.newBufferedWriter(contacts);

        usersWriter.write(
                        "[{\"login\":\"vano\",\"password\":\"$2a$10$OQB5/ci7ajvO7r3FknsbGutElRAeMv7Pl.O.VBP0N/2mydO/Th4gC\",\"fullName\":\"Yakovenko Ivan Venediktovich\",\"id\":1}" +
                        ",{\"login\":\"serg\",\"password\":\"$2a$10$7LUptJ9VzHnViMx98rz.LuIrFJzzV.RXebw2xvvB.ESW6Hz6yrrqO\",\"fullName\":\"Rubinov Sergey Nikolaevich\",\"id\":2}]"
        );
        rolesWriter.write(
                        "[{\"name\":\"ROLE_ADMIN\",\"userId\":1,\"id\":1}" +
                        ",{\"name\":\"ROLE_ADMIN\",\"userId\":2,\"id\":2}]"
        );
        contactsWriter.write(
                        "[{\"lastName\":\"Dovbash\",\"firstName\":\"Sveta\",\"middleName\":\"Andriivna\",\"mobilePhone\":\"+380(66)1234567\",\"homePhone\":\"+380(44)1234567\",\"address\":\"Kyiv, Mechnikova str. 2\",\"email\":\"sveta@gmail.com\",\"userId\":1,\"id\":1}" +
                        ",{\"lastName\":\"Kushnir\",\"firstName\":\"Lena\",\"middleName\":\"Viktorivna\",\"mobilePhone\":\"+380(50)1234123\",\"homePhone\":\"+380(47)2661181\",\"address\":\"Cherkasy, Taraskova str. 16\",\"email\":\"lena@gmail.com\",\"userId\":1,\"id\":2}" +
                        ",{\"lastName\":\"Parasiuk\",\"firstName\":\"Sasha\",\"middleName\":\"Igorevych\",\"mobilePhone\":\"+380(97)9876543\",\"homePhone\":\"+380(44)0909098\",\"address\":\"Kyiv, Pushkinska str. 10\",\"email\":\"sasha@gmail.com\",\"userId\":1,\"id\":3}" +
                        ",{\"lastName\":\"Tiagnybok\",\"firstName\":\"Yulia\",\"middleName\":\"Volodymyrivna\",\"mobilePhone\":\"+380(67)8555855\",\"homePhone\":\"+380(47)2234567\",\"address\":\"Cherkasy, Rustavi str. 8\",\"email\":\"yulia@gmail.com\",\"userId\":1,\"id\":4}" +
                        ",{\"lastName\":\"Tsymbal\",\"firstName\":\"Vitia\",\"middleName\":\"Fedorovych\",\"mobilePhone\":\"+380(98)9234567\",\"homePhone\":\"+380(44)9234567\",\"address\":\"Kyiv, Sosninykh str. 38\",\"email\":\"vitia@gmail.com\",\"userId\":1,\"id\":5}" +
                        ",{\"lastName\":\"Tymoshenko\",\"firstName\":\"Grysha\",\"middleName\":\"Petrovych\",\"mobilePhone\":\"+380(93)1234555\",\"homePhone\":\"+380(47)2234555\",\"address\":\"Cherkasy, Gaidara str. 8\",\"email\":\"grysha@gmail.com\",\"userId\":1,\"id\":6}" +
                        ",{\"lastName\":\"Kukushkin\",\"firstName\":\"Dima\",\"middleName\":\"Grygorovych\",\"mobilePhone\":\"+380(63)3334567\",\"homePhone\":\"+380(44)3334567\",\"address\":\"Kyiv, Zhylanska str. 99\",\"email\":\"dima@gmail.com\",\"userId\":2,\"id\":7}" +
                        ",{\"lastName\":\"Kukushkina\",\"firstName\":\"Sveta\",\"middleName\":\"Andreevna\",\"mobilePhone\":\"+380(66)1234567\",\"homePhone\":\"+380(44)1234567\",\"address\":\"Kyiv, Mechnikova str. 2\",\"email\":\"sveta@gmail.com\",\"userId\":2,\"id\":8}]"
        );

        usersWriter.flush();
        rolesWriter.flush();
        contactsWriter.flush();

        usersWriter.close();
        rolesWriter.close();
        contactsWriter.close();
    }
}
