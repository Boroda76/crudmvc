package samson.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import samson.exceptions.UserException;
import samson.model.Role;
import samson.model.User;
import samson.service.RoleService;
import samson.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;


@Controller
public class UserController{
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping({"/index", "/"})
    public String allUsers(Model model, HttpServletRequest request) throws UserException{
            model.addAttribute("users", userService.getAll());
            model.addAttribute("roleADMIN", roleService.findByName("ADMIN"));
            model.addAttribute("roleUSER", roleService.findByName("USER"));
        return "index";
    }

    @GetMapping("/admin/user/{id}")
    public String userPage(@PathVariable(name = "id") Long id, Model model){
        try{
            User user=userService.getById(id);
            model.addAttribute("user", user);
            return "user";
        } catch (UserException e){
            model.addAttribute("message", e.getMessage());
            model.addAttribute("error", e);
            return "error";
        }
    }

    @PostMapping("/admin/update")
    public ModelAndView updateUser(@ModelAttribute("user") User user, @RequestParam("role") String[] role, ModelAndView model) {
        List<Role> roles=new ArrayList<>();
        for(String s:role){
            roles.add(roleService.findByName(s));
        }
        user.setAuthorities(roles);
        String pass=user.getPassword();
        if (user.getId() != null) {
            try {
                if(!user.getPassword().isEmpty()){
                    String newPass=userService.encodePassword(user.getPassword());
                    user.setPassword(newPass);
                }
                userService.updateUser(user);
            } catch (UserException e) {
                model.setViewName("create");
                try {
                    model.addObject("userEdit", userService.getById(user.getId()));
                } catch (UserException ex) {
                    //Couldn't happened
                }
                model.addObject("message", e.getMessage());
                return model;
            }
        } else {
            try {
                userService.createUser(user);
            } catch (UserException e) {
                model.addObject("message", e.getMessage());
                model.setViewName("create");
                return model;
            }
        }
        return new ModelAndView("redirect:/index");
    }

    @GetMapping("/admin/delete")
    public ModelAndView deleteUser(@RequestParam(name = "id") Long id, ModelAndView model) {
        if (id == null) {
            model.addObject("message", "You should provide ID");
            model.setViewName("error");
            return model;
        }
        try {
            userService.delete(id);
            return new ModelAndView("redirect:/index");
        } catch (UserException e) {
            model.addObject("message", e.getMessage());
            model.addObject("error", e);
            model.setViewName("error");
            return model;
        }
    }
}
