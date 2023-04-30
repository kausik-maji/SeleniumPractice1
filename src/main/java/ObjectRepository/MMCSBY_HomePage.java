package ObjectRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import LibraryFiles.ReusableLibraryFile;

public class MMCSBY_HomePage extends ReusableLibraryFile{

	// Element locators on homepage
	By pageHeader = By.xpath("//app-home/section[1]/div[1]");
	By tmsLink = By.xpath("//a[contains(text(),'TMS')]");
	By hemLink = By.xpath("//a[contains(text(),'HEM')]");
	By serviceRequestLink = By.xpath("//a[contains(text(),'Service Request')]");
	By registrationLink = By.xpath("//a[contains(text(),' Registration')]");
	By accessRequestLink = By.xpath("//a[contains(text(),' Access Request')]");
	
	// Intantiating browser and page header validation
	public MMCSBY_HomePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public TMS_PanelAndDashboard clickOnTMSLink() throws InterruptedException	{
		driver.findElement(tmsLink).click();
		Thread.sleep(5000);
		return new TMS_PanelAndDashboard(driver);
	}
	
}
