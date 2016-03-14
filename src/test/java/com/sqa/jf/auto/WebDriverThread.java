/**
 * File Name: WedDriverThread.java<br>
 * Nepton, Jean-francois<br>
 * Java Boot Camp Exercise<br>
 * Instructor: Jean-francois Nepton<br>
 * Created: Feb 18, 2016
 */
package com.sqa.jf.auto;

import java.net.*;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.*;

import com.sqa.jf.auto.config.*;

import static com.sqa.jf.auto.config.DriverType.FIREFOX;
import static com.sqa.jf.auto.config.DriverType.valueOf;


/**
 * WedDriverThread //ADDD (description of class)
 * <p>
 * //ADDD (description of core fields)
 * <p>
 * //ADDD (description of core methods)
 *
 * @author Nepton, Jean-francois
 * @version 1.0.0
 * @since 1.0
 */
public class WebDriverThread {

	private WebDriver webdriver;

	private DriverType selectedDriverType;

	private final DriverType defaultDriverType = FIREFOX;

	private final String browser = System.getProperty("browser").toUpperCase();

	private final String operatingSystem = System.getProperty("os.name").toUpperCase();

	private final String systemArchitecture = System.getProperty("os.arch");

	public WebDriver getDriver() throws Exception {
		if (this.webdriver == null) {
			this.selectedDriverType = determineEffectiveDriverType();
			DesiredCapabilities capabilities = this.selectedDriverType.getDesiredCapabilities();
			instantiateWebDriver(capabilities);
		}
		return this.webdriver;
	}

	public void quitDriver() {
		if (this.webdriver != null) {
			this.webdriver.quit();
		}
	}

	/**
	 * @return
	 */
	private DriverType determineEffectiveDriverType() {
		DriverType driverType = defaultDriverType;
		try {
			driverType = valueOf(browser);
		} catch (IllegalArgumentException ignored) {
			System.err.println("Unknown driver specified, defaulting to '" + driverType + "'...");;
		} catch (NullPointerException ignored) {
			System.err.println("No driver specified, defaulting to '" + driverType + "'...");;
		}
		return driverType;
	}

	/**
	 * @param capabilities
	 */
	private void instantiateWebDriver(DesiredCapabilities capabilities) throws MalformedURLException {
		System.out.println(" ");
		System.out.println("Current Operating System: " + this.operatingSystem);
		System.out.println("Current Architecture System: " + this.systemArchitecture);
		System.out.println("Current Browser Selection: " + this.selectedDriverType);
		System.out.println(" ");
		this.webdriver = this.selectedDriverType.getWebDriverObject(capabilities);
	}
}
