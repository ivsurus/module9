package driver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;

import logger.LoggerSingleton;

//Singleton pattern
//Factory method pattern

public class WebDriverSingleton {

	private static WebDriver driver;

	private WebDriverSingleton() {
	}

	public static WebDriver getWebDriverInstance() {
		if (driver == null) {
			String browserName = getBrowserName();
			switch(browserName){
			case "chrome":
				driver = new ChromeDriverCreator().createDriver();
				break;
			case "edge":
				driver = new EdgeDriverCreator().createDriver();
				break;
			}
			driver = new CustomDriverDecorator(driver);
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		}
		return driver;
	}

	public static void tearDown(){
		driver.quit();
		driver = null;
	}

	private static String getBrowserName(){
		Properties properties = new Properties();
		try {
			InputStream input  = new FileInputStream("browser.properties");
			properties.load(input);
		} catch (IOException e) {
			LoggerSingleton.getLogger().error(e.getMessage());
		}
		LoggerSingleton.getLogger().info("Properties file is readed successfully");
		return properties.getProperty("browser");
	}
}
