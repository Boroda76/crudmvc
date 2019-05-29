package samson.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import samson.exceptions.UserException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

/**
 * This is note for me in future xD
 * When you need a global custom exception handler in springboot app look here:
 * @link https://stackoverflow.com/questions/55101797/using-spring-boots-errorcontroller-and-springs-responseentityexceptionhandler
 * @link https://www.logicbig.com/tutorials/spring-framework/spring-boot/implementing-error-controller.html
 */
@Controller
public class ExceptionController implements ErrorController {
    @RequestMapping("/error")
    @ExceptionHandler(Throwable.class)
    public Object handle(Exception e, HttpServletRequest request, HttpServletResponse response, Principal principal) {
        if(e instanceof UserException){
            return null;
        } else {
            request.getSession();
            ModelAndView modelAndView = new ModelAndView("error");
            modelAndView.addObject("message", e.getMessage());
            modelAndView.addObject("error", e);
            return modelAndView;
        }
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

}
