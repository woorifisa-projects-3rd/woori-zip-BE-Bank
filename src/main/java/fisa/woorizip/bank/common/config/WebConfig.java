package fisa.woorizip.bank.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private static final String CLIENT_LOCALHOST = "http://localhost:3000";
    private static final String CLIENT_SECURE_LOCALHOST = "https://localhost:3000";

    private static final String CORS_ALLOWED_METHODS =
            "GET,POST,HEAD,PUT,PATCH,DELETE,TRACE,OPTIONS";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/woori-bank/**")
                .allowedMethods(CORS_ALLOWED_METHODS.split(","))
                .allowedOrigins(CLIENT_LOCALHOST, CLIENT_SECURE_LOCALHOST)
                .exposedHeaders(HttpHeaders.SET_COOKIE, HttpHeaders.LOCATION)
                .allowCredentials(true);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/")
                .setCachePeriod(3600)
                .resourceChain(true);
    }
}
