package service;

import org.openqa.selenium.WebDriver;

import bo.User;
import driver.WebDriverSingleton;
import loggersingleton.LoggerSingleton;
import page.InboxListPage;
import page.LoginPage;
import page.component.ToolbarComponent;

public class UserService {

	private final static String LOGIN = "module5testmailbox";
	private final static String PASSWORD = "qwerty12345";

	private WebDriver driver;

	private User user;

	private void setUpUser(String login, String password){
		user = new User(login, password);
	}

	public UserService() {
		super();
		this.driver = WebDriverSingleton.getWebDriverInstance();
		setUpUser(LOGIN, PASSWORD);
	}

	public void logOut(){
		new ToolbarComponent(driver).expandUserMenu().clickLogOutFromUserMenu();
	}

	public InboxListPage logIn(){
		return new LoginPage(driver).fillInUserLogin(user.getLogin()).
				fillInPassword(user.getPassword()).clickLoginButton();
	}

	public User getUser() {
		return user;
	}

	public String getActualUserName(){
		String actualUserName = new ToolbarComponent(driver).getUserName();
		LoggerSingleton.getLogger().info(String.format("Actual user name is: %s", actualUserName));
		return actualUserName;
	}

}
