package com.example.curso.springboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("admin"));
		//$2a$10$9frO/Tnkdl/6hSYwBs7aE.5A2ox.j8scgMH7e2NHSDllSlXmT6Xri - Admin Criptografado
		
	}
	
	

}
