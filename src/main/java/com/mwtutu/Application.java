package com.mwtutu;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages="com.mwtutu.mapper")
@ComponentScan(basePackages= {"com.mwtutu"})
@EnableAutoConfiguration(exclude={DruidDataSourceAutoConfigure.class})
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
