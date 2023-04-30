package ObjectRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import LibraryFiles.ReusableLibraryFile;

public class InstantApprovalUploadPopup extends ReusableLibraryFile{

	By ChooseFile = By.xpath("(//input[@type='file'])[1]");
	By UploadBtn = By.xpath("//button[contains(text(),'Upload')]");
	By ContinueBtn = By.id("btnInstantApprovalFileUploadSubmit");
	By CancelBtn = By.xpath("//button[contains(text(),'Cancel')]");
	By ModalMsg = By.xpath("(//div[@class='modal-body']/div[1]/div[1]/label[1])[3]");
	By popupHeader = By.xpath("(//h5[@id='staticBackdropLabel'])[3]");
	By UploadDialogMessage = By.xpath("//div[@class='swal2-html-container']");
	By DialogOkBtn = By.xpath("//div[@class='swal2-actions']/button[text()='OK']");
	
	public InstantApprovalUploadPopup(WebDriver driver) {
		this.driver = driver;
	}
	
	public void clickOnUploadBtn()	{
		driver.findElement(UploadBtn).click();
	}
	
	public String getPopupMessage()	{
		return getText(ModalMsg);
	}
	
	public BeneficiaryIdentificationForm clickOnCancelBtn()	{
		driver.findElement(CancelBtn).click();
		return new BeneficiaryIdentificationForm(driver);
	}
	
	public BeneficiaryIdentificationForm clickOnContinueBtn()	{
		driver.findElement(ContinueBtn).click();
		return new BeneficiaryIdentificationForm(driver);
	}
	
	public EmergencyCaseConversionPage clickOnCancelBtnForEmergencyPage()	{    
		driver.findElement(CancelBtn).click(); 
		return new EmergencyCaseConversionPage(driver); 
	} 
	
	public EmergencyCaseConversionPage clickOnContinueBtnForEmergencyPage()	{
		driver.findElement(ContinueBtn).click();
		return new EmergencyCaseConversionPage(driver);
	}
	
	public boolean IsUploadBtnVisible() throws Exception	{
		return IsElementVisible(UploadBtn);
	}
	
	public boolean IsCancelBtnVisible() throws Exception	{
		return IsElementVisible(CancelBtn);
	}
	
	public boolean IsContinueBtnVisible() throws Exception	{
		return IsElementVisible(ContinueBtn);
	}
	
	public boolean IsUploadOptionVisible() throws Exception	{
		return IsElementVisible(ChooseFile);
	}
	
	public void UploadInstantApprovalDocument(String filePath) throws Exception	{		
		WebElement chooseFile = driver.findElement(ChooseFile);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', arguments[1])", chooseFile, "0");
		chooseFile.sendKeys(filePath);
		Thread.sleep(2000);
		driver.findElement(UploadBtn).click();
		Thread.sleep(5000);
		if (IsElementPresent(UploadDialogMessage)) {
			Assert.assertEquals(getText(UploadDialogMessage).trim(), "File uploaded successfully");
			driver.findElement(DialogOkBtn).click();			
		}
		else	{
		Thread.sleep(2000);
		Assert.assertEquals(getText(UploadDialogMessage).trim(), "File uploaded successfully");
		driver.findElement(DialogOkBtn).click();
		}
	}
}
