/**
 * File Name: DriverFactory.java<br>
 * Nepton, Jean-francois<br>
 * Java Boot Camp Exercise<br>
 * Instructor: Jean-francois Nepton<br>
 * Created: Feb 18, 2016
 */
package com.sqa.jf.auto;

import java.util.*;

import org.openqa.selenium.*;
import org.testng.annotations.*;

/**
 * DriverFactory //ADDD (description of class)
 * <p>
 * //ADDD (description of core fields)
 * <p>
 * //ADDD (description of core methods)
 *
 * @author Nepton, Jean-francois
 * @version 1.0.0
 * @since 1.0
 */
public class DriverFactory {

	private static List<WebDriverThread> webDriverThreadPool = Collections
			.synchronizedList(new ArrayList<WebDriverThread>());

	private static ThreadLocal<WebDriverThread> driverThread;

	// @AfterMethod
	public static void clearCookies() throws Exception {
		getDriver().manage().deleteAllCookies();
	}

	@AfterSuite
	public static void closeDriverObjects() {
		for (WebDriverThread webDriverThread : webDriverThreadPool) {
			webDriverThread.quitDriver();
		}
	}

	public static WebDriver getDriver() throws Exception {
		return driverThread.get().getDriver();
	}

	@BeforeSuite
	public static void instantiateDriverObect() {
		driverThread = new ThreadLocal<WebDriverThread>() {

			@Override
			protected WebDriverThread initialValue() {
				WebDriverThread webDriverThread = new WebDriverThread();
				webDriverThreadPool.add(webDriverThread);
				return webDriverThread;
			}
		};
	}
}
