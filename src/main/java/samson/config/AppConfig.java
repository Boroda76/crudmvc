package samson.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "samson")
public class AppConfig implements WebMvcConfigurer{

        @Bean
    public InternalResourceViewResolver resolver(){
            InternalResourceViewResolver resolver=new InternalResourceViewResolver();
            resolver.setViewClass(JstlView.class);
            resolver.setPrefix("/WEB-INF/views/");
            resolver.setSuffix(".jsp");
            return resolver;
        }

        @Bean(initMethod = "initData")
        public TestData initTestData(){
            return new TestData();
        }
@Override
public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/img/**").addResourceLocations("classpath:/img/");
}
}
