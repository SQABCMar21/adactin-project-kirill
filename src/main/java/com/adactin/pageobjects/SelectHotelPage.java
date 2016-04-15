package com.adactin.pageobjects;

import java.util.ArrayList;
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

	// Select a hotel error message
	@FindBy(how = How.ID, using = "reg_error")
	private WebElement selectError;

	public SelectHotelPage(WebDriver driver)
	{
		this.driver = driver;
		// driver.get("http://adactin.com/HotelApp/SelectHotel.php");
	}

	// html table representation of selected Hotel
	public boolean calcStayPrice()
	{
		List<WebElement> rows = this.htmlTable.findElements(By.tagName("tr"));
		boolean[] priceResults = new boolean[] {};
		boolean isCorrectPrice = true;

		for (int rnum = 1; rnum < rows.size(); rnum++)
		{
			List<WebElement> roomsCol = rows.get(rnum).findElements(By.tagName("td"));
			List<WebElement> daysCol = rows.get(rnum).findElements(By.tagName("td"));
			List<WebElement> priceCol = rows.get(rnum).findElements(By.tagName("td"));
			List<WebElement> totalCol = rows.get(rnum).findElements(By.tagName("td"));
			String rVal = "";
			String dVal = "";
			String pVal = "";
			String tVal = "";
			priceResults = new boolean[rows.size() - 1];

			for (int i = 3; i <= 3; i++)
			{
				// Store number of rooms value into String
				rVal = roomsCol.get(i).findElement(By.tagName("input")).getAttribute("value").toString();
				// Leave only numeric values
				rVal = rVal.replaceAll("[^0-9]", "");

				for (int j = 6; j <= 6; j++)
				{
					// Store number of days value into String
					dVal = daysCol.get(j).findElement(By.tagName("input")).getAttribute("value").toString();
					dVal = dVal.replaceAll("[^0-9]", "");
				}

				for (int k = 8; k <= 8; k++)
				{
					// Store price per night value into String
					pVal = priceCol.get(k).findElement(By.tagName("input")).getAttribute("value").toString();
					pVal = pVal.replaceAll("[^0-9]", "");
				}

				for (int l = 9; l <= 9; l++)
				{
					// Store total price(exc. GST) value into String
					tVal = priceCol.get(l).findElement(By.tagName("input")).getAttribute("value").toString();
					tVal = tVal.replaceAll("[^0-9]", "");

					// Calculate Hotel Stay price (price per night * no. of
					// days)and compare with Total price shown
					priceResults[rnum - 1] = calcTrueTotalPrice(rVal, dVal, pVal, tVal, rnum, l);
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

	// look up the match in selected hotel table
	public boolean calcTrueTotalPrice(String rVal, String dVal, String pVal, String tVal, int rnum, int l)
	{
		double rooms = Double.parseDouble(rVal);
		double days = Double.parseDouble(dVal);
		double price = Double.parseDouble(pVal);
		double total = Double.parseDouble(tVal);
		double trueTotal = rooms * days * price;

		if (trueTotal == total)
		{
			return true;
		}
		else
		{
			System.out.println("Fail: on row " + (rnum + 1) + ", column " + l
					+ " - Wrong Total Price(excl. GST) value. Was calculated as $" + trueTotal + ", but found $"
					+ total);
			return false;
		}
	}

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
					val = foundVal;
					break;
				}
			}
		}
		return val;
	}

	public void changeLocatorId()
	{
		int n = registerSelect();
		if (n >= 1)
		{
			int recs = numOfSelections();
			String c = String.valueOf(n);
			List<WebElement> rows = this.htmlTable.findElements(By.tagName("tr"));
			for (int rnum = 1; rnum < 1; rnum++)
			{
				if (n == 1 && recs == 1)
				{
					continue;
				}
				else if (n >= 1 && recs >= 2)
				{
					this.select = this.driver.findElement(By.id("radiobutton_" + c));
					this.selectLocation = this.driver.findElement(By.id("radiobutton_" + c));
					this.selectName = this.driver.findElement(By.id("radiobutton_" + c));
					this.selectNumRooms = this.driver.findElement(By.id("radiobutton_" + c));
					this.selectRoomType = this.driver.findElement(By.id("radiobutton_" + c));
					this.selectArrivalDate = this.driver.findElement(By.id("radiobutton_" + c));
					this.selectDepartureDate = this.driver.findElement(By.id("radiobutton_" + c));
					this.selectNumDays = this.driver.findElement(By.id("radiobutton_" + c));
					this.selectPricePerNight = this.driver.findElement(By.id("radiobutton_" + c));
					this.selectTotalPrice = this.driver.findElement(By.id("radiobutton_" + c));
				}
			}
		}
	}

	public String hotelInfo(String colName, String val)
	{
		if (colName.equalsIgnoreCase("select"))
		{
			val = cellVals(0, val);
		}

		else if (colName.equalsIgnoreCase("hotel name"))
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

	public BookHotelPage next()
	{
		changeLocatorId();
		this.continueButton.click();
		return PageFactory.initElements(this.driver, BookHotelPage.class);
	}

	public int numOfSelections()
	{
		int num = 0;
		List<WebElement> rows = this.htmlTable.findElements(By.tagName("tr"));
		return num = rows.size() - 1; // return the number of records found
	}

	public int registerSelect()
	{
		int sel = 0;
		int n = numOfSelections();
		List<WebElement> rows = this.htmlTable.findElements(By.tagName("tr"));
		for (int rnum = 1; rnum < rows.size(); rnum++)
		{
			List<WebElement> columns = rows.get(rnum).findElements(By.tagName("td"));
			for (int cnum = 1; cnum <= 1; cnum++)
			{
				if (n >= 1 && !this.select.isSelected())
				{
					continue;
				}
				else if (n >= 1 && this.select.isSelected())
				{
					sel = rnum;
					break;
				}
			}
		}
		return sel;
	}

	public List<String> selectedHotelVals(String... vals)
	{
		List<String> searchedVals = new ArrayList<String>();
		for (int i = 0; i < vals.length; i++)
		{
			if (vals[i].equalsIgnoreCase("hotel name"))
			{
				vals[i] = hotelInfo("hotel name", "");
				searchedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("location"))
			{
				vals[i] = hotelInfo("location", "");
				searchedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("rooms"))
			{
				vals[i] = hotelInfo("rooms", "");
				searchedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("arrival date"))
			{
				vals[i] = hotelInfo("arrival date", "");
				searchedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("departure date"))
			{
				vals[i] = hotelInfo("departure date", "");
				searchedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("number of days"))
			{
				vals[i] = hotelInfo("number of days", "");
				searchedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("rooms type"))
			{
				vals[i] = hotelInfo("rooms type", "");
				searchedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("price per night"))
			{
				vals[i] = hotelInfo("price per night", "");
				searchedVals.add(i, vals[i]);
			}
			else if (vals[i].equalsIgnoreCase("total price"))
			{
				vals[i] = hotelInfo("total price", "");
				searchedVals.add(i, vals[i]);
			}
		}
		return searchedVals;
	}

	public SelectHotelPage selectHotel()
	{
		changeLocatorId();
		this.select.click();
		return this;
	}
}
