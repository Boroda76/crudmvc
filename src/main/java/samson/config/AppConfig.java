package samson.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import samson.controller.ExceptionController;


@Configuration
public class AppConfig implements WebMvcConfigurer {

    //        @Bean
//    public InternalResourceViewResolver resolver(){
//            InternalResourceViewResolver resolver=new InternalResourceViewResolver();
//            resolver.setViewClass(JstlView.class);
//            resolver.setPrefix("/WEB-INF/views/");
//            resolver.setSuffix(".jsp");
//            return resolver;
//        }
    @Bean
    SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setSuffix(".html");
        resolver.setPrefix("classpath:/views/");
        resolver.setTemplateMode("HTML5");
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

    @Bean
    SpringTemplateEngine engine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolver(templateResolver());
        engine.addDialect(securityDialect());
        return engine;
    }

    @Bean
    ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setTemplateEngine(engine());
        return viewResolver;
    }


    @Bean
    public SpringSecurityDialect securityDialect() {
        return new SpringSecurityDialect();
    }


//        @Bean(initMethod = "initData")
//        public TestData initTestData(){
//            return new TestData();
//        }
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/img/");
        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/");
    }

}
