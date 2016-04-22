package com.adactin.pageobjects;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BookedItineraryPage extends BookingConfirmPage
{
	private WebDriver driver;
	private WebDriverWait wait;
	private Alert alert;

	// Select Hotel HTML table locator
	@FindBy(how = How.CSS, using = "table[cellspacing='1'] > tbody")
	private WebElement biTable;

	// Select all checkbox locator
	@FindBy(how = How.ID, using = "check_all")
	private WebElement biCheckAll;

	// Search order id field locator
	@FindBy(how = How.CLASS_NAME, using = "input_search")
	private WebElement biSearchIdField;

	// Search id go button locator
	@FindBy(how = How.CLASS_NAME, using = "button_search")
	private WebElement biSearchIdGo;

	public BookedItineraryPage(WebDriver driver) throws Exception
	{
		super(driver);
		this.driver = driver;
		this.wait = new WebDriverWait(driver, 15);
	}

	public boolean changeCellVals()
	{
		this.biCheckAll.click();
		List<WebElement> rows = this.biTable.findElements(By.tagName("tr"));
		for (int rnum = 1; rnum < rows.size(); rnum++)
		{
			List<WebElement> columns = rows.get(rnum).findElements(By.tagName("td"));
			for (int cnum = 1; cnum <= columns.size(); cnum++)
			{
				columns.get(cnum).findElement(By.tagName("input")).clear();
				String changedVal = columns.get(cnum).findElement(By.tagName("input")).getAttribute("value").toString();
				if (changedVal.equals(""))
				{
					return false;
				}
				else
				{
					return true;
				}
			}
		}
		return true;
	}

	public boolean compareBookingWithItineraryVals(List<String> cmp, int col)
	{
		List<WebElement> rows = this.biTable.findElements(By.tagName("tr"));
		for (int rnum = 1; rnum < rows.size(); rnum++)
		{
			List<WebElement> columns = rows.get(rnum).findElements(By.tagName("td"));
			for (int cnum = col; cnum < columns.size(); cnum++)
			{
				String foundVal = columns.get(cnum).findElement(By.tagName("input")).getAttribute("value").toString();
				if (cmp.get(cnum - col).equalsIgnoreCase(foundVal))
				{
					continue;
				}
				else
				{
					System.out.println("verifyBookedItineraryValsSameAsBookConfirm test failure - Row: " + rnum
							+ " Column: " + cnum + " - found '" + foundVal + "'" + " but expected to be '"
							+ cmp.get(cnum - col) + "'");
					return false;
				}
			}
		}
		return true;
	}

	public int numOfRecs()
	{
		List<WebElement> recs = this.biTable.findElements(By.tagName("tr"));
		int n = recs.size() - 1;
		return n;
	}

	public boolean orderCancel()
	{
		// save the total number of recs before the order cancel;
		int recsNum = numOfRecs();

		List<String> orderVals = searchedIdVals();

		// Click "Cancel <id>" button
		this.driver.findElement(By.id("btn_id_" + orderVals.get(0).toString())).click();

		// switch to delete confirmation pop up
		// and press "ok"
		this.alert = this.driver.switchTo().alert();
		this.alert.accept();

		// wait for search result to reload and error message about deleted
		// order to be displayed
		this.wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("search_result_error")));

		// "The booking has been cancelled." validation error
		WebElement bookCancelError = this.driver.findElement(By.id("search_result_error"));

		// get the total number of records, based on cancelled order.
		int recsNewNum = numOfRecs();

		if (recsNewNum < recsNum && bookCancelError.isDisplayed())
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public int randNum()
	{
		// declare random number generator
		Random rand = new Random();
		// generate the random number within 1 - n,
		// where n is max number of records shown
		int n = numOfRecs();
		int randN = rand.nextInt((n - 1) + 1) + 1;
		return randN;
	}

	public List<String> searchedIdVals()
	{
		List<String> idVals = new ArrayList<String>();
		List<WebElement> rows = this.biTable.findElements(By.tagName("tr"));
		List<WebElement> columns = null;
		int randomNum = randNum();

		String id = "";

		for (int rnum = randomNum; rnum <= randomNum; rnum++)
		{
			columns = rows.get(rnum).findElements(By.tagName("td"));
			// Store the record id
			id = columns.get(1).findElement(By.tagName("input")).getAttribute("value").toString();
			for (int cnum = 0; cnum < columns.size(); cnum++)
			{
				String foundVal = columns.get(cnum).findElement(By.tagName("input")).getAttribute("value").toString();
				idVals.add(foundVal);
			}
		}
		this.biSearchIdField.sendKeys(id);
		this.biSearchIdGo.click();
		return idVals;
	}
}
