package nl.bvsit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


/**
 * Configuring ViewResolver using Java Configuration
 * <https://javarevisited.blogspot.com/2017/08/what-does-internalresourceviewresolver-do-in-spring-mvc.html#axzz73Rb6zk7Y>
 */
@Configuration
public class HelloWebConfig {
    @Bean
    public ViewResolver viewResolver() {  //Created bean will have this name!
        InternalResourceViewResolver viewResolve = new InternalResourceViewResolver();
        viewResolve.setPrefix("/WEB-INF/jsp/");
        viewResolve.setSuffix(".jsp");
        return viewResolve;
    }
}

