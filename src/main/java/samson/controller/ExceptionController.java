package samson.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice/*(basePackages = "samson")*/ //TODO why it doesnt works with fucking ugly boot for 404 (see securityConfig)?! ErrorAttributes !!! https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-web-applications.html#boot-features-error-handling
//@RestController
public class ExceptionController extends ResponseEntityExceptionHandler {
//TODO stil works, but not fine for other exceptions!!!
    @ExceptionHandler(Throwable.class)
    public ModelAndView handle(Exception e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("error", e);
        return modelAndView;
    }

}
