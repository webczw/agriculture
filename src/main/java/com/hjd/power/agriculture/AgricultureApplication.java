package com.hjd.power.agriculture;

import javax.sql.DataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;

import com.hjd.power.agriculture.utils.AESUtils;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@SpringBootApplication
@MapperScan("com.hjd.power.agriculture.dao")
public class AgricultureApplication {
	@Autowired
	private Environment env;

	public static void main(String[] args) {
		SpringApplication.run(AgricultureApplication.class, args);
	}

	@Bean(name = "dataSource")
	// @ConfigurationProperties(prefix = "spring.datasource")
	@Primary
	public DataSource dataSource() {
		// DriverManagerDataSource dataSource = new DriverManagerDataSource();
		// dataSource.setPassword("1Qaz2Wsx");
		DataSourceBuilder<?> factory = DataSourceBuilder.create();
		factory.username(env.getProperty("spring.datasource.username"));
		String password = env.getProperty("spring.datasource.password");
		password = AESUtils.decrypt(password, Constants.AES_KEY, Constants.AES_IV);
		factory.password(password);
		factory.driverClassName(env.getProperty("spring.datasource.driver-class-name"));
		factory.url(env.getProperty("spring.datasource.url"));
		return factory.build();
	}
}
