/**
 * File Name: DriverSetup.java<br>
 * Nepton, Jean-francois<br>
 * Java Boot Camp Exercise<br>
 * Instructor: Jean-francois Nepton<br>
 * Created: Feb 19, 2016
 */
package com.sqa.jf.auto.config;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.*;

/**
 * DriverSetup //ADDD (description of class)
 * <p>
 * //ADDD (description of core fields)
 * <p>
 * //ADDD (description of core methods)
 *
 * @author Nepton, Jean-francois
 * @version 1.0.0
 * @since 1.0
 */
public interface DriverSetup {

	DesiredCapabilities getDesiredCapabilities();

	WebDriver getWebDriverObject(DesiredCapabilities capabilities);
}
