package com.mac.drive.macDrive;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mac.drive.macDrive.mapper")
public class MacDriveApplication {

	public static void main(String[] args) {
		SpringApplication.run(MacDriveApplication.class, args);
	}

}
