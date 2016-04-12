package com.adactin.pageobjects;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class SelectHotelPage
{
	private WebDriver driver;

	// Select Hotel HTML table locator
	@FindBy(how = How.CSS, using = ".login>tbody>tr>td>table")
	private WebElement htmlTable;

	// Select radiobutton locator
	@FindBy(how = How.ID, using = "radiobutton_0")
	private WebElement select;

	// Hotel location locator
	@FindBy(how = How.ID, using = "location_0")
	private WebElement selectLocation;

	// Hotel name locator
	@FindBy(how = How.ID, using = "hotel_name_0")
	private WebElement selectName;

	// Hotel room type locator
	@FindBy(how = How.ID, using = "room_type_0")
	private WebElement selectRoomType;

	// Number of rooms in Hotel locator
	@FindBy(how = How.ID, using = "rooms_0")
	private WebElement selectNumRooms;

	// Arrival date locator
	@FindBy(how = How.ID, using = "add_date_0")
	private WebElement selectArrivalDate;

	// Departure date locator
	@FindBy(how = How.ID, using = "dep_date_0")
	private WebElement selectDepartureDate;

	// No. of days locator
	@FindBy(how = How.ID, using = "no_days_0")
	private WebElement selectNumDays;

	// Price per night locator
	@FindBy(how = How.ID, using = "price_night_0")
	private WebElement selectPricePerNight;

	// Total price locator
	@FindBy(how = How.ID, using = "total_price_0")
	private WebElement selectTotalPrice;

	// "Continue" button locator
	@FindBy(how = How.ID, using = "continue")
	private WebElement continueButton;

	// "Cancel" button locator
	@FindBy(how = How.ID, using = "cancel")
	private WebElement cancelButton;

	public SelectHotelPage(WebDriver driver)
	{
		this.driver = driver;
		driver.get("http://adactin.com/HotelApp/SelectHotel.php");
		PageFactory.initElements(driver, this);
	}

	public boolean calcStayPrice()
	{
		List<WebElement> rows = this.htmlTable.findElements(By.tagName("tr"));
		boolean[] priceResults = new boolean[] {};
		boolean isCorrectPrice = true;

		for (int rnum = 1; rnum < rows.size(); rnum++)
		{
			List<WebElement> daysCol = rows.get(rnum).findElements(By.tagName("td"));
			List<WebElement> priceCol = rows.get(rnum).findElements(By.tagName("td"));
			List<WebElement> totalCol = rows.get(rnum).findElements(By.tagName("td"));
			String dVal = "";
			String pVal = "";
			String tVal = "";
			priceResults = new boolean[rows.size() - 1];

			for (int i = 6; i <= 6; i++)
			{
				// Store number of days value into String
				dVal = daysCol.get(i).findElement(By.tagName("input")).getAttribute("value").toString();
				// Leave only numeric values
				dVal = dVal.replaceAll("[^0-9]", "");

				for (int j = 8; j <= 8; j++)
				{
					// Store price per night value into String
					pVal = priceCol.get(j).findElement(By.tagName("input")).getAttribute("value").toString();
					pVal = pVal.replaceAll("[^0-9]", "");
				}

				for (int k = 9; k <= 9; k++)
				{
					// Store total price(exc. GST) value into String
					tVal = priceCol.get(k).findElement(By.tagName("input")).getAttribute("value").toString();
					tVal = tVal.replaceAll("[^0-9]", "");

					// Calculate Hotel Stay price (price per night * no. of
					// days)and compare with Total price shown
					priceResults[rnum - 1] = calcTrueTotalPrice(dVal, pVal, tVal, rnum, k);
				}
			}
		}

		// iterate through array of boolean values and return "false" if at
		// least one price mismatch found
		for (int i = 1; i < rows.size(); i++)
		{
			if (priceResults[i] != isCorrectPrice)
			{
				isCorrectPrice = false;
				break;
			}
		}
		return isCorrectPrice;
	}

	public boolean calcTrueTotalPrice(String dVal, String pVal, String tVal, int rnum, int k)
	{
		double days = Double.parseDouble(dVal);
		double price = Double.parseDouble(pVal);
		double total = Double.parseDouble(tVal);
		double trueTotal = days * price;

		if (trueTotal == total)
		{
			return true;
		}
		else
		{
			System.out.println("Fail: on row " + (rnum + 1) + ", column " + k
					+ " - Wrong Total Price(excl. GST) value. Was calculated as $" + trueTotal + ", but found $"
					+ total);
			return false;
		}
	}

	// html table representation of selected Hotel
	public String cellVals(int col, String val)
	{
		List<WebElement> rows = this.htmlTable.findElements(By.tagName("tr"));
		for (int rnum = 1; rnum < rows.size(); rnum++)
		{
			List<WebElement> columns = rows.get(rnum).findElements(By.tagName("td"));
			for (int cnum = col; cnum <= col; cnum++)
			{
				String foundVal = columns.get(cnum).findElement(By.tagName("input")).getAttribute("value").toString();
				if (val.equalsIgnoreCase(foundVal))
				{
					continue;
				}
				else
				{
					System.out.println("Fail: found  \"" + foundVal + "\" insted of \"" + val + "\" on row "
							+ (rnum + 1) + ", column " + cnum);
					val = foundVal;
					break;
				}
			}
		}
		return val;
	}

	// look up the match in selected hotel table
	public String hotelInfo(String colName, String val)
	{
		if (colName.equalsIgnoreCase("hotel name"))
		{
			val = cellVals(1, val);
		}
		else if (colName.equalsIgnoreCase("location"))
		{
			// this.selectLocation.getAttribute("value").toString();
			val = cellVals(2, val);
		}
		else if (colName.equalsIgnoreCase("rooms"))
		{
			val = cellVals(3, val);
		}
		else if (colName.equalsIgnoreCase("arrival date"))
		{
			val = cellVals(4, val);
		}
		else if (colName.equalsIgnoreCase("depature date"))
		{
			val = cellVals(5, val);
		}
		else if (colName.equalsIgnoreCase("number of days"))
		{
			val = cellVals(6, val);
		}
		else if (colName.equalsIgnoreCase("rooms type"))
		{
			val = cellVals(7, val);
		}
		else if (colName.equalsIgnoreCase("price per night"))
		{
			val = cellVals(8, val);
		}
		else if (colName.equalsIgnoreCase("total price"))
		{
			val = cellVals(9, val);
		}
		return val;
	}
}
