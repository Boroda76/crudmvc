package samson.controller;

import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import samson.exceptions.UserException;

import javax.servlet.http.HttpServletRequest;

/**
 * This is note for me in future xD
 * When you need a global custom exception handler in springboot app look here:
 * @link https://stackoverflow.com/questions/55101797/using-spring-boots-errorcontroller-and-springs-responseentityexceptionhandler
 * @link https://www.logicbig.com/tutorials/spring-framework/spring-boot/implementing-error-controller.html
 */

@ControllerAdvice("samson")
public class ExceptionController /*implements ErrorController*/ {
//    @RequestMapping("/error")

    @ExceptionHandler(Throwable.class)
    public Object handle(Exception e, HttpServletRequest request) {
        if(e instanceof UserException){
            JSONObject errorMessage=new JSONObject();
            errorMessage.appendField("message", e.getCause().getCause().getMessage());
            errorMessage.appendField("stackTrace", e.getStackTrace());
            return errorMessage;
        } else {
            ModelAndView modelAndView = new ModelAndView("error");
            modelAndView.addObject("message", e.getMessage());
            modelAndView.addObject("error", e);
            return modelAndView;
        }
    }

//    @Override
//    public String getErrorPath() {
//        return "/error";
//    }

}
