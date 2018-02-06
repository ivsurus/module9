package service;

import org.openqa.selenium.WebDriver;

import core.driver.WebDriverSingleton;
import core.logger.LoggerSingleton;
import page.DraftEmailPage;
import page.DraftsListPage;
import page.SentListPage;
import page.component.ToolbarComponent;

public class FolderService {

	private final static String DRAFTS = "Drafts";
	private final static String SENT = "Sent";
	private WebDriver driver;

	public FolderService() {
		super();
		this.driver = WebDriverSingleton.getWebDriverInstance();
	}

	public boolean isSentFolderEmpty(){
		boolean isSentFolderEmpty = new SentListPage(driver).isSentFolderEmpty();
		LoggerSingleton.getLogger().info(String.format("The 'Sent' folder is empty: %s", isSentFolderEmpty));
		return isSentFolderEmpty;
	}

	public void moveAllEmailsInSentIntoTrashAndHighlightSuccessMessage(){
		new SentListPage(driver).toolbarComponent.openSentFolder().selectAllEmailsByAdvanceActions().
		moveEmailsIntoTrashByDragAndDrop().highlightElementeAndTakeScreenshot();;
	}

	public int getnumberEmailsInDrafts(){
		int numberEmailsInDrafts = new DraftsListPage(driver).toolbarComponent.openDraftsFolder().toolbarComponent.
				getNumberOfEmailsInFolder(DRAFTS);
		LoggerSingleton.getLogger().info(String.format("The number of emails in 'Draft' is: %s", numberEmailsInDrafts));
		return numberEmailsInDrafts;
	}

	public int getnumberEmailsInSent(){
		int numberEmailsInSent = new SentListPage(driver).toolbarComponent.openSentFolder().toolbarComponent.
				getNumberOfEmailsInFolder(SENT);
		LoggerSingleton.getLogger().info(String.format("The number of emails in 'Sent' is: %s", numberEmailsInSent));
		return numberEmailsInSent;
	}

	public boolean isEmailPresentInDraftsList(String emailSubject){
		boolean isEmailPresentInDraftsList = new DraftsListPage(driver).isEmailPresentInDraftsList(emailSubject);
		LoggerSingleton.getLogger().info(String.format
				("The email with subject '%s' is present in 'Drafts' folder: %s", emailSubject, isEmailPresentInDraftsList));
		return isEmailPresentInDraftsList;
	}

	public void openEmailBySubject(String emailSubject){
		LoggerSingleton.getLogger().info(String.format
				("Open an email with subject: %s", emailSubject));
		new DraftsListPage(driver).openEmailBySubject(emailSubject);
	}

	public void sendEmailFromDraft(String emailSubject){
		int numberEmailsInDrafts = new DraftsListPage(driver).toolbarComponent.openDraftsFolder().toolbarComponent.
				getNumberOfEmailsInFolder(DRAFTS);
		openEmailBySubject(emailSubject);
		LoggerSingleton.getLogger().info("Send an email from 'Drafts'");
		new DraftEmailPage(driver).sendDraftEmail().toolbarComponent.openDraftsFolder().toolbarComponent.
		waitForChangeOfNumberOfEmailsInFolder(numberEmailsInDrafts-1);
	}

	public boolean isEmailPresentInSentList(String emailSubject){
		boolean isEmailPresentInSentList = new SentListPage(driver).isEmailPresentInSentList(emailSubject);
		LoggerSingleton.getLogger().info(String.format
				("The email with subject '%s' is present in 'Sent' folder: %s", emailSubject, isEmailPresentInSentList));
		return isEmailPresentInSentList;
	}

	public void openSentFolder(int expectedNumberOfEmails){
		LoggerSingleton.getLogger().info("Open 'Sent' folder");
		new ToolbarComponent(driver).openSentFolder().toolbarComponent.waitForChangeOfNumberOfEmailsInFolder(expectedNumberOfEmails);;
	}

}


















