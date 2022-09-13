package com.ll.exam.RecipiaProject;

import com.ll.exam.RecipiaProject.user.UserRepository;
import com.ll.exam.RecipiaProject.user.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RecipiaProjectApplicationTests {
	@Autowired
	private UserService userService;

	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("샘플 유저 생성")
	void t1() {
		userService.create("admin", "1234", "admin@test.com");
	}

	public static void createSampleData(UserService userService) {
		userService.create("admin", "1234", "admin@test.com");
		userService.create("user1", "1234", "user1@test.com");
	}

	private void createSampleData() {
		createSampleData(userService);
	}

}
