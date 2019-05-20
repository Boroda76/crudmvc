package samson.config;

import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import samson.exceptions.UserException;
import samson.model.Role;
import samson.model.User;
import samson.service.RoleService;
import samson.service.UserService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

@Component
public class TestData {
    @Autowired
    @Qualifier("userService")
    private UserDetailsService userDetailsService;

    @Autowired
    private RoleService roleService;

    public void initData() {
        UserService userService=(UserService)userDetailsService;
        if(roleService.findAll().size()<2) {
            roleService.addRole(new Role("ADMIN"));
            roleService.addRole(new Role("USER"));
        }
        List<Role> roles=roleService.findAll();
        HashSet<Role> role1=new HashSet<>();
        role1.add(roles.get(0));
        HashSet<Role> role2=new HashSet<>();
        role2.add(roles.get(1));
        try {
            userService.getByLogin("8020d4");
        } catch (UserException e){
            try{
                userService.createUser(new User(
                        0l,
                        "8020d4",
                        "asd@asd.asd",
                        "123",
                        true,
                        (int) (Math.random() * 85 + 10),
                        Math.random() * 85 + 45,
                        Math.random() * 85 + 120,
                        role1));
            }
            catch (UserException ex){
                ex.printStackTrace();
            }
        }

        Faker faker = new Faker(new Locale("en"));
        for (int i = 0; i < 100; i++) {
            try {
                userService.createUser(new User(
                        0l,
                        faker.funnyName().name() + i,
                        faker.internet().emailAddress(),
                        userService.encodePassword(i+"asd"),
                        i % 2 == 0,
                        (int) (Math.random() * 85 + 10),
                        Math.random() * 85 + 45,
                        Math.random() * 85 + 120,
                        i%2==0?role1:role2
                ));
            } catch (UserException e) {
                e.printStackTrace();
            }

        }
    }
}
