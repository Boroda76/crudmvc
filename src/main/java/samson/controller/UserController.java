package samson.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import samson.exceptions.UserException;
import samson.model.User;
import samson.service.RoleService;
import samson.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;


@Controller
public class UserController{
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping({"/admin/users", "/admin/users/", "/admin/", "/", "/admin"})
    public String allUsers(Model model) {
            model.addAttribute("users", userService.getAll());
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

    @GetMapping("/admin/update")
    public ModelAndView updateOrCreate(@RequestParam(required = false, name = "id") Long id) {
        ModelAndView modelAndView = new ModelAndView("create");
        if (id != null) {
            try {
                User user = userService.getById(id);
                modelAndView.addObject("userEdit", user);
            } catch (UserException e) {
                modelAndView.addObject("message", e.getMessage());
            }
        }
        return modelAndView;
    }

    @PostMapping("/admin/update")
    public ModelAndView updateUser(@ModelAttribute("user") User user, @RequestParam("role") String role, ModelAndView model) {
        user.setAuthorities(roleService.findByName(role));
        if (user.getId() != null) {
            try {
                if(!user.getPassword().equals(userService.getById(user.getId()).getPassword())){
                    String pass=userService.encodePassword(user.getPassword());
                    user.setPassword(pass);
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
        return new ModelAndView("redirect:/admin");
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
            return new ModelAndView("redirect:users");
        } catch (UserException e) {
            model.addObject("message", e.getMessage());
            model.addObject("error", e);
            model.setViewName("error");
            return model;
        }
    }

    @GetMapping({"/user/", "/user"})
    public String user(HttpServletRequest request, Model model) {
        Principal principal =request.getUserPrincipal();
        User user=null;
        try {
            user = userService.getByLogin(principal.getName());
            model.addAttribute("user", user);
        } catch (UserException e) {
            model.addAttribute("message", e.getMessage());
        }
        return "user";
    }
    @GetMapping({"/error", "/error/"})
    public String error(){
        return "error";
    }
}
