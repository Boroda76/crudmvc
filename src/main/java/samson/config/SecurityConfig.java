package samson.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.csrf.CsrfFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("userService")
    private UserDetailsService userDetailsService;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
//                .csrf().disable()//TODO fow to make REST work with csrf?!
                .authorizeRequests()
                .anyRequest().authenticated()
                .antMatchers("/api/**").hasAuthority("ADMIN") //TODO fix antMathcers
                .and()
                .formLogin()
                .successHandler((HttpServletRequest var1, HttpServletResponse var2, Authentication var3) -> redirectStrategy.sendRedirect(var1, var2, "/index"))
//                .and().addFilter()
                .and()
                .addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
                .logout().logoutSuccessUrl("/logout").logoutSuccessUrl("/login")
                .and()
                .exceptionHandling().accessDeniedHandler((HttpServletRequest var1, HttpServletResponse var2, AccessDeniedException var3) -> {
            var1.setAttribute("message", var3.getMessage());
            var1.getRequestDispatcher("/error").forward(var1, var2);
        })
        ;
    }


}
