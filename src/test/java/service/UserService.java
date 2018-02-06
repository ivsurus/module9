package service;

import org.openqa.selenium.WebDriver;

import bo.User;
import core.driver.WebDriverSingleton;
import core.logger.LoggerSingleton;
import page.InboxListPage;
import page.LoginPage;
import page.component.ToolbarComponent;

public class UserService {

	private WebDriver driver;

	public UserService() {
		super();
		this.driver = WebDriverSingleton.getWebDriverInstance();
	}

	public void logOut(){
		new ToolbarComponent(driver).expandUserMenu().clickLogOutFromUserMenu();
	}

	public InboxListPage logIn(User user){
		return new LoginPage(driver).fillInUserLogin(user.getLogin()).
				fillInPassword(user.getPassword()).clickLoginButton();
	}

	public String getActualUserName(){
		String actualUserName = new ToolbarComponent(driver).getUserName();
		LoggerSingleton.getLogger().info(String.format("Actual user name is: %s", actualUserName));
		return actualUserName;
	}

}
