/**
 * File Name: DriverType.java<br>
 * Nepton, Jean-francois<br>
 * Java Boot Camp Exercise<br>
 * Instructor: Jean-francois Nepton<br>
 * Created: Feb 19, 2016
 */
package com.sqa.jf.auto.config;

import java.util.*;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.ie.*;
import org.openqa.selenium.opera.*;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.safari.*;

/**
 * DriverType //ADDD (description of class)
 * <p>
 * //ADDD (description of core fields)
 * <p>
 * //ADDD (description of core methods)
 *
 * @author Nepton, Jean-francois
 * @version 1.0.0
 * @since 1.0
 */
public enum DriverType implements DriverSetup {
	FIREFOX {

		@Override
		public DesiredCapabilities getDesiredCapabilities() {
			DesiredCapabilities capabilities = DesiredCapabilities.firefox();
			return capabilities;
		}

		@Override
		public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
			return new FirefoxDriver(getDesiredCapabilities());
		}
	},
	CHROME {

		@Override
		public DesiredCapabilities getDesiredCapabilities() {
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			capabilities.setCapability("chrome.switches", Arrays.asList("--no-default-browser-check"));
			HashMap<String, String> chromePreferences = new HashMap<String, String>();
			chromePreferences.put("profile.password_manager_enabled", "false");
			capabilities.setCapability("chrome.prefs", chromePreferences);
			return capabilities;
		}

		@Override
		public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
			System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
			return new ChromeDriver(getDesiredCapabilities());
		}
	},
	IE {

		@Override
		public DesiredCapabilities getDesiredCapabilities() {
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			capabilities.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
			capabilities.setCapability("requireWindowFocus", true);
			return capabilities;
		}

		@Override
		public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
			return new InternetExplorerDriver(getDesiredCapabilities());
		}
	},
	SAFARI {

		@Override
		public DesiredCapabilities getDesiredCapabilities() {
			DesiredCapabilities capabilities = DesiredCapabilities.safari();
			capabilities.setCapability("safari.cleanSession", true);
			return capabilities;
		}

		@Override
		public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
			return new SafariDriver(getDesiredCapabilities());
		}
	},
	OPERA {

		@Override
		public DesiredCapabilities getDesiredCapabilities() {
			DesiredCapabilities capabilities = DesiredCapabilities.operaBlink();
			return capabilities;
		}

		@Override
		public WebDriver getWebDriverObject(DesiredCapabilities capabilities) {
			return new OperaDriver(getDesiredCapabilities());
		}
	};
}
