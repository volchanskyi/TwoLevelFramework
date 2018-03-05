package com.automationpractice.tests;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.automationpractice.appmanager.HttpSession;
import com.automationpractice.model.MailMessage;

import ru.lanwen.verbalregex.VerbalExpression;

public class RegistrationTests extends TestBase {
    
    final private Logger logger = LoggerFactory.getLogger(TestBase.class);
    
    
    
    @BeforeMethod
//    public void startMailServer() {
//	APP.mail().start();
//    }
    private void beforeMethod(Method method, Object[] parameters) {
   	logger.debug("Start test " + method.getName() + " with params " + Arrays.asList(parameters));

       }

    @Test
    public void testRegistration() throws MessagingException, IOException {
	long now = System.currentTimeMillis();
	String email = "test" + now + "@automationpractice.com";
	String password = "testPWD001";
	HttpSession session = APP.newSession();
	assertTrue(session.signUp(email));
	assertTrue(session.register(email,
		"My account - My Store", "Ivan", "Test", password, "178 Meadowbrook Dr.", "San Francisco", "94132",
		"5", "4158962578"));

	// APP.registration().start("Ivan", "Test", "testPWD001", "178 Meadowbrook Dr.",
	// "San Francisco", "94132",
	// "California", "4159065768");
//	APP.mail().waitForMail(2, 1000);
//	List<MailMessage> mailMessages = APP.mail().waitForMail(2, 1000);
//	String confirmationLink = findConfirmationLink(mailMessages, email);
//	APP.registration().finish(confirmationLink, password);

    }

//    private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
//	MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
//	VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
//	return regex.getText(mailMessage.text);
//
//    }

    @AfterMethod(alwaysRun = true)
//    public void stopMailServer() {
//	APP.mail().stop();
//    }
    private void logTestStop(Method method, Object[] parameters) {
	logger.debug("Stop test " + method.getName());

    }

}
