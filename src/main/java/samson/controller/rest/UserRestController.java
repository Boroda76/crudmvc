package samson.controller.rest;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;
import samson.exceptions.UserException;
import samson.model.Role;
import samson.model.User;
import samson.service.RoleService;
import samson.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")

public class UserRestController {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public boolean delete(@PathVariable Long id) throws UserException {
        userService.delete(id);
        return true;

    }

    @RequestMapping("/roles")
    public List<Role> allRoles() {
        return roleService.findAll();
    }

    @RequestMapping("/{id}")
    public User getById(@PathVariable Long id) throws UserException {
        return userService.getById(id);
    }
    @RequestMapping("/byLogin/{login}")
    public User getByName(@PathVariable String login) throws UserException {
        return userService.getByLogin(login);
    }

    @RequestMapping("/all")
    public List<User> getAll() {
        return userService.getAll();
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public boolean create(@RequestBody User u) throws UserException {
        System.out.println("here"); //TODO validation for empty field
        userService.createUser(u);
        return true;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public boolean update(@RequestBody User u) throws UserException {
        userService.updateUser(u);
        return true;
    }
}
