package service;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

import bo.Email;
import driver.WebDriverSingleton;
import logger.LoggerSingleton;
import page.DraftEmailPage;
import page.InboxListPage;
import page.NewEmailPage;
import util.RandomDataHelper;

public class EmailService {

	private WebDriver driver;
	private FolderService folderService;

	public EmailService() {
		super();
		this.driver = WebDriverSingleton.getWebDriverInstance();
		folderService = new FolderService();
	}

	public List<Email> createANumberOfEmailsWithRandomDataAndSendThem(int numberOfEmails){
		LoggerSingleton.getLogger().info(String.format("Create %s email(s) with random data", numberOfEmails));
		NewEmailPage newEmailPage = new NewEmailPage(driver);
		List<Email> emails = new ArrayList<>();
		for (int i=0; i<numberOfEmails; i++){
			Email email = createNewEmailAndFillInData();
			emails.add(email);
			newEmailPage.sendEmail();
		}
		return emails;
	}

	public Email createEmailWithRandomDataAndSaveToDrafts(){
		NewEmailPage newEmailPage = new NewEmailPage(driver);
		int numberEmailsInDrafts = folderService.getnumberEmailsInDrafts();
		LoggerSingleton.getLogger().info("Create email with random data");
		Email email = createNewEmailAndFillInData();
		newEmailPage.closeEmailAndSaveToDraft().toolbarComponent.openDraftsFolder().
		toolbarComponent.waitForChangeOfNumberOfEmailsInFolder(numberEmailsInDrafts+1);
		return email;
	}

	public String getActualEmailAdress(){
		String actualEmailAdress = new DraftEmailPage(driver).getEmailAdress();
		LoggerSingleton.getLogger().info(String.format("Actual email adress is: %s", actualEmailAdress));
		return actualEmailAdress;
	}

	public String getActualEmailSubject(){
		String actualEmailSubject = new DraftEmailPage(driver).getSubject();
		LoggerSingleton.getLogger().info(String.format("Actual email subject is: %s", actualEmailSubject));
		return actualEmailSubject;
	}

	public String getActualEmailBody(){
		String actualEmailBody = new DraftEmailPage(driver).getBody();
		LoggerSingleton.getLogger().info(String.format("Actual email body is: %s", actualEmailBody));
		return actualEmailBody;
	}

	private Email createNewEmailAndFillInData(){
		InboxListPage inboxListPage = new InboxListPage(driver);
		Email email = new Email.EmailBuilder(RandomDataHelper.getRandomEmailAdress()).
				subject(RandomDataHelper.getRandomEmailSubject()).
				body(RandomDataHelper.getRandomEmailBody()).build();
		inboxListPage.toolbarComponent.createNewEmail().fillInEmailAdress(email.getAdress()).
		fillInEmailSubject(email.getSubject()).fillInEmailBody(email.getBody());
		LoggerSingleton.getLogger().info(email.toString());
		return email;
	}


}
