package com.sqa.jf.auto.demo;

import org.openqa.selenium.*;
import org.testng.annotations.*;

import com.sqa.jf.auto.*;

public class BasicTestWD extends DriverFactory {

	@Test
	public void testCheese() throws Exception {
		exampleGoogleTest("Cheese");
	}

	@Test
	public void testMilk() throws Exception {
		exampleGoogleTest("Milk");
	}

	private void exampleGoogleTest(final String searchString) throws Exception {
		WebDriver driver = DriverFactory.getDriver();
		driver.get("http://sfbay.craigslist.com");
		WebElement searchField = driver.findElement(By.id("query"));
		searchField.clear();
		searchField.sendKeys(searchString);
		System.out.println("Page Title:" + driver.getTitle());
		searchField.submit();
		Thread.sleep(5000);
	}

	// @DataProvider
	// public Object[][] items() {
	// Object[][] myData = new Object[][] { { "Cheese" }, { "Milk" }, { "Fruit"
	// }, { "Chocolcate" }, { "Wine" },
	// { "Champagne" } };
	// return myData;
	// }
}
