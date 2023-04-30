package LibraryFiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;


public class ReusableLibraryFile {

	//Global variables goes here
	public static WebDriver driver;
	String path = System.getProperty("user.dir");
	String PropertiesFilePath = path + "\\GlobalSettings.properties";

	public static ExtentTest logger;
	public static ExtentReports extent;
	public static String browser;

	public void OpenBrowserInstance() throws IOException
	{
		System.out.println("Setting up driver by initializing browser instance");
		String WSpath = System.getProperty("user.dir");
		browser = GetPropertyFileValue("Browser");
		if (browser.equalsIgnoreCase("Chrome"))	{
			System.setProperty("webdriver.chrome.driver", WSpath+"\\resources\\chromedriver.exe");
			System.out.println("Opening chrome browser instance");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--remote-allow-origins=*");
			options.addArguments("--incognito");
			driver = new ChromeDriver(options);
		}
		else if (browser.equalsIgnoreCase("Firefox"))	{
			System.setProperty("webdriver.gecko.driver", WSpath+"\\resources\\geckodriver.exe");
			System.out.println("Opening firefox browser instance");
			FirefoxOptions options = new FirefoxOptions();
			options.addArguments("-private");
			options.setCapability("marionette", true);
			driver = new FirefoxDriver(options);	
		}
		driver.manage().window().maximize();	
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	}

	public String GetPropertyFileValue(String Key) throws IOException
	{
		Properties obj = new Properties();
		FileInputStream objfile = new FileInputStream(PropertiesFilePath);
		obj.load(objfile);
		String value = obj.getProperty(Key);
		return value;
	}
	
	public String GetPropertyFileValue(String dataFilePath, String Key) throws IOException
	{
		Properties obj = new Properties();
		FileInputStream objfile = new FileInputStream(dataFilePath);
		obj.load(objfile);
		String value = obj.getProperty(Key);
		return value;
	}

	public void LaunchApplication() throws IOException, InterruptedException
	{
		String Environment = GetPropertyFileValue("EnvironmentUrl");
		driver.get(Environment);
		Thread.sleep(2000);
	}

	public void ClickOnControl(By Element)
	{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(Element));
		driver.findElement(Element).click();
	}

	public void SendValueToInputControl(By Element, String value) throws InterruptedException
	{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(Element));
		WebElement control = driver.findElement(Element);
		control.clear();
		Thread.sleep(1000);
		control.sendKeys(value);
		Thread.sleep(1000);
	}
	
	public void waitUntillElementVisible(By element)	{
		WebDriverWait wait = new WebDriverWait(driver, 15);
		wait.until(ExpectedConditions.visibilityOfElementLocated(element));
	}

	public boolean IsElementVisible(By Element) throws InterruptedException
	{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(Element));
		WebElement control = driver.findElement(Element);
		return control.isDisplayed();
	}

	public boolean IsElementPresent(By Elements) throws InterruptedException
	{
		Thread.sleep(1000);
		List<WebElement> controls = driver.findElements(Elements);
		if (controls.size()<=0)
			return false;
		else
			return true;	
	}
	
	public void SelectDropDownByVisibleText(By Element, String visibleText)
	{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(Element));
		WebElement element = driver.findElement(Element);
		Select select = new Select(element);
		select.selectByVisibleText(visibleText);
	}

	public void TakeScreenShot(ITestResult result) throws Exception
	{
		String WSpath = System.getProperty("user.dir");
		String SnapPath = null;
		DateFormat df = new SimpleDateFormat("ddMMyy_HHmmss");
		Date dateobj = new Date();
		String subString = df.format(dateobj);
		File srcFile= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

		if(result.getStatus()==ITestResult.SUCCESS) {
			SnapPath = WSpath+"\\ScreenShots\\Passed\\";
			//File srcFile= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			File destFile = new File(SnapPath+"SnapAt_"+subString+".jpeg");
			FileUtils.copyFile(srcFile, destFile);
			logger.log(Status.PASS, "Test Execution completed successfully");
		}
		else {
			SnapPath = WSpath+"\\ScreenShots\\Failed_Skipped\\";
			//File srcFile= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			File destFile = new File(SnapPath+"SnapAt_"+subString+".jpeg");
			FileUtils.copyFile(srcFile, destFile);
			if(result.getStatus()==ITestResult.FAILURE)
				logger.log(Status.FAIL, "Screen shot captured below: "+logger.addScreenCaptureFromPath(destFile.getPath()));
			else
				logger.log(Status.SKIP, "Screen shot captured below: "+logger.addScreenCaptureFromPath(destFile.getPath()));
		}
	}


	public String getText(By Element)
	{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(Element));
		return driver.findElement(Element).getText().trim();	
	}

	public void ScrollDownToGivenPixels(int num) throws Exception	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,"+num+")");
		Thread.sleep(2000);
	}
	
	public void ScrollUpToGivenPixels(int num) throws Exception	{
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,-"+num+")");
		Thread.sleep(2000);
	}
	
	public void readExcelData(String filePath) throws IOException
	{
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook fileName = new XSSFWorkbook(fis);
		XSSFSheet sheetName = fileName.getSheetAt(0);
		int rowSize = sheetName.getLastRowNum();
		for(int rowNum=0;rowNum<rowSize;rowNum++)
		{
			System.out.print("Excel values are - ");
			Row row = sheetName.getRow(rowNum);
			for(int colNum = 0;colNum<row.getLastCellNum();colNum++)
			{
				String strValue=row.getCell(colNum).getStringCellValue();
				System.out.print("\t "+strValue+ "\t");
			}
			System.out.println();
		}
		fileName.close();
	}

	public void writeExcelData(String filePath,String value) throws IOException
	{
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook fileName = new XSSFWorkbook(fis);
		XSSFSheet sheetName = fileName.getSheetAt(0);
		int rowSize = sheetName.getLastRowNum();
		int columnSize= sheetName.getRow(0).getLastCellNum();
		for(int rowNum=1;rowNum<=rowSize;rowNum++)
		{
			Row row = sheetName.getRow(rowNum);
			for(int colNum=0;colNum <= columnSize;colNum++){
				if(colNum == (columnSize-1))
				{
					sheetName.getRow(rowNum).createCell(colNum).setCellValue(value);
					break;
				}

			}
		}
		System.out.println(" Simply printing");
		FileOutputStream fos = new FileOutputStream(file);
		fileName.write(fos);
		System.out.println("Closing the excel sheet");
		fileName.close();
	}

	// Row count in a sheet
	public int getRowCount(String excelPath,String sheetName) throws IOException
	{
		File file = new File(excelPath);
		FileInputStream fis = new FileInputStream(file);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		int index=workbook.getSheetIndex(sheetName);
		XSSFSheet sheet=workbook.getSheetAt(index);
		int numberOfRows = sheet.getLastRowNum()+1;
		workbook.close();
		return numberOfRows;
	}

}
