package com.ll.exam.RecipiaProject;


import com.ll.exam.RecipiaProject.mypage.MyPageDto;
import com.ll.exam.RecipiaProject.mypage.MyPageRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RecipiaProjectApplicationTests {


	@Autowired
	private MyPageRepository myPageRepository;

	@Test
	void contextLoads() {
	}
	@Test
	@BeforeEach
	void Create_Sample_data() {

	}
	@Test
	void MyPage_id_NickName_Matching() {
		MyPageDto q = this.myPageRepository.findByNickName("seonggu");

		assertEquals(1, q.getId());
	}

}
