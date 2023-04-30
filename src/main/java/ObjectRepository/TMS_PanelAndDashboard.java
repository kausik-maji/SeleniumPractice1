package ObjectRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import LibraryFiles.ReusableLibraryFile;

public class TMS_PanelAndDashboard extends ReusableLibraryFile{
	
	By pageHeader = By.xpath("//div[@class='container-fluid']/div[1]//h2[1]");
	By BeneficiaryDropdown = By.xpath("//span[contains(text(),'Beneficiary Identification')]");
	By BeneficiaryForm = By.xpath("//a/p[contains(text(),'Beneficiary Identification')]");
	By EmergencyCaseConversion = By.xpath("//p[contains(text(),'Emergency Case')]");
	By PatientAdmitDropdown = By.xpath("//span[contains(text(),'Patient Admission Form')]");
	By AdmissionForm = By.xpath("//a/p[contains(text(),'Admission Form')]");

	public TMS_PanelAndDashboard(WebDriver driver) {
		this.driver = driver;
	}
	
	public BeneficiaryIdentificationForm clickOnBeneficiaryForm() throws InterruptedException		{
		driver.findElement(BeneficiaryDropdown).click();
		Thread.sleep(1000);
		driver.findElement(BeneficiaryForm).click();
		return new BeneficiaryIdentificationForm(driver);
	}
	
	public BeneficiaryIdentificationForm directClickOnBeneficiaryForm() throws InterruptedException		{
		driver.findElement(BeneficiaryForm).click();
		return new BeneficiaryIdentificationForm(driver);
	}
	
	public EmergencyCaseConversionPage clickOnEmergencyCaseConvert() throws Exception 	{
		driver.findElement(BeneficiaryDropdown).click();
		Thread.sleep(1000);
		driver.findElement(EmergencyCaseConversion).click();
		return new EmergencyCaseConversionPage(driver);
	}
	
	public EmergencyCaseConversionPage directClickOnEmergencyCaseConvert() throws Exception 	{
		driver.findElement(EmergencyCaseConversion).click();
		return new EmergencyCaseConversionPage(driver);
	}
	
	public PatientAdmissionForm clickOnPatientAdmissionForm() throws InterruptedException		{
		driver.findElement(PatientAdmitDropdown).click();
		Thread.sleep(1000);
		driver.findElement(AdmissionForm).click();
		return new PatientAdmissionForm(driver);
	}
}
