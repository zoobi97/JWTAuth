package com.demo.jwtauthdemo;

import com.demo.jwtauthdemo.Filters.AuthFilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class JwtAuthDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(JwtAuthDemoApplication.class, args);
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> CorsFilter(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedHeader("*");
        source.registerCorsConfiguration("/**",configuration);
        filterRegistrationBean.setFilter(new CorsFilter(source));
        filterRegistrationBean.setOrder(0);
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<AuthFilter> filterRegistrationBean(){
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        AuthFilter filter = new AuthFilter();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/api/categories/*");
        return registrationBean;
    }

}
