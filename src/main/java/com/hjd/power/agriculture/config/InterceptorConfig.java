package com.hjd.power.agriculture.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import com.hjd.power.agriculture.interceptor.AccessInterceptor;

@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AccessInterceptor()).addPathPatterns("/**");
	}
}
