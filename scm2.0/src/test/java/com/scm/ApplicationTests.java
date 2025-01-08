package com.scm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.scm.Services.EmailService;

@SpringBootTest
class ApplicationTests {

	@Test
	void contextLoads() {
	}

   @Autowired
   private EmailService service;
   
    @Test
	void sendEmailTest() {
		service.sendEmail(
					
		            "mohit.karonde435@gmail.com",
		 		    "Congratulations !!! Mr.Mohit karonde for selecting in Google.",
				 	"you are wonderfull , keep working on your goal...");
	}

}
