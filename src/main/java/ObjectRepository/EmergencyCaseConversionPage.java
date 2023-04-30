package ObjectRepository;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import LibraryFiles.ReusableLibraryFile;

public class EmergencyCaseConversionPage extends ReusableLibraryFile{

	By pageHeader = By.xpath("//ol[@class='breadcrumb']/li[2]");
	By TIDNumber = By.xpath("//label[contains(text(),'TID :')]/following-sibling::div/input");
	By IsJanadharYes = By.id("have_Jan_Aadhar_1");
	By IsJanadharNo  = By.id("have_Jan_Aadhar_2");
	By IdentityType = By.id("ddlIdentityType");
	By SearchBeneficiary = By.xpath("//button[contains(text(),' Search Beneficiary')]");
	By UniqueIdNumber = By.id("txtuid");   
	By VerifyUsing = By.id("flexRadioDefault2");
	By ChooseFile_MOIC = By.xpath("(//input[@type='file'])[1]");
	By UploadBtn_MOIC = By.xpath("(//button[text()='Upload'])[1]");
	By UploadDialogMessage = By.xpath("//div[@class='swal2-html-container']");
	By DialogOkBtn = By.xpath("//div[@class='swal2-actions']/button[text()='OK']");
	By MobileForSMSAlert = By.id("txtmobile");
	By ChooseFile_Photo = By.xpath("(//input[@type='file'])[2]");
	By UploadBtn_Photo = By.xpath("(//button[text()='Upload'])[2]");
	By SubmitBtn = By.xpath("//div/button[text()='Submit']");
	By GeneratedDataOnDialog = By.xpath("//div[@class='swal2-html-container']");
	By SearchBtn = By.xpath("//button[contains(text(),'Search')]");
	By AllowCaptureBtn = By.xpath("//button[text()='Allow']");
	By CaptureBtn = By.xpath("//button[text()='Capture']");
	By ContactDetailsTab = By.xpath("//h2[@id='headingOne']/button");
	By EntitlementTypeValue = By.xpath("//label[contains(text(),'Entitlement Type')]/following-sibling::label");
	By ListofTIDsToConvert = By.xpath("//button[contains(text(),'List of TID')]");
	By GridPageHeader = By.id("GeneralExaminationFindingsLabel");
	By GridTIDColumn = By.xpath("//div[text()='TID']");
	By GridHospitalColumn = By.xpath("//div[text()='Hospital Name ']");
	By GridPatientColumn = By.xpath("//div[text()='Patient Name ']");
	By GridTimeColumn = By.xpath("//div[text()='Consumed Time (Days)']");
	By GridSelectColumn = By.xpath("//div[text()='Select']");
	By TIDListRows = By.xpath("//div[@id='Emergencytid_pop_up']//tbody/tr/td[1]");
	By UnIdentifiedRadio = By.xpath("//label[@for='Un-Identified']"); 
	By IdentifiedRadio = By.xpath("//label[@for='PatientIdentified']"); 
	By PatientDetailsTab = By.xpath("//button[text()=' Patient Details ']"); 
	By PatientName = By.xpath("//input[@name='txtPatientName']"); 
	By PatientAge = By.xpath("//input[@name='txtpatientAge']"); 
	By PatientGender = By.xpath("//select[@name='ddlGender']"); 

	public EmergencyCaseConversionPage(WebDriver driver) {
		this.driver = driver;
	}

	public String getPageHeader()	{
		waitUntillElementVisible(pageHeader);
		return driver.findElement(pageHeader).getText();
	}

	public void ClickOnSearch()	 {
		driver.findElement(SearchBtn).click();
	}

	public String getEntitlementTypeValue()	{
		return driver.findElement(EntitlementTypeValue).getText().trim();
	}

	public void ClickOnListOfTIDs()	{
		driver.findElement(ListofTIDsToConvert).click();
	}

	public void InputUniqueIDNumber(String value)	{
		WebElement element = driver.findElement(UniqueIdNumber);
		element.clear();
		element.sendKeys(value);
	}

	public void SetIdentityTypeDropdownTo(String value)	{
		WebElement element = driver.findElement(IdentityType);
		Select select = new Select(element);
		//select.selectByVisibleText(value);
		int index = 0;
		for (WebElement option : select.getOptions()) {
			if (option.getText().equalsIgnoreCase(value))
				break;
			index++;
		}
		select.selectByIndex(index);
	}

	public String GetIdentityTypeSelectedValue()	{
		WebElement element = driver.findElement(IdentityType);
		Select select = new Select(element);
		return select.getFirstSelectedOption().getText();
	}

	public void SelectJanadharRadio(String value) {
		if(value.equalsIgnoreCase("Yes"))
			driver.findElement(IsJanadharYes).click();
		else
			driver.findElement(IsJanadharNo).click();
	}

	public void InputTIDNumber(String value)	{
		driver.findElement(TIDNumber).sendKeys(value);
	}

	public void InputMobileNumForSelectedUser(String MobileNum) throws Exception {
		Assert.assertTrue(IsElementVisible(MobileForSMSAlert));
		WebElement element = driver.findElement(MobileForSMSAlert);
		element.clear();
		element.sendKeys(MobileNum);
	}

	public void ClickOnSearchBeneficiary()	{
		driver.findElement(SearchBeneficiary).click();
	}

	public void ClickOnMOIC() {
		driver.findElement(VerifyUsing).click();
	}

	public void UploadMOICDocument(String filePath) throws Exception	{
		WebElement chooseFile = driver.findElement(ChooseFile_MOIC);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', arguments[1])", chooseFile, "0");
		chooseFile.sendKeys(filePath);
		Thread.sleep(2000);
		waitUntillElementVisible(UploadBtn_MOIC);
		driver.findElement(UploadBtn_MOIC).click();
		Thread.sleep(6000);
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

	public void SelectedMemberInTableIs(String name) throws Exception	{
		String xpathBuilder = "//tbody/tr/td[contains(text(),'"+name+"')]/parent::tr/td[3]/input";
		driver.findElement(By.xpath(xpathBuilder)).click();
		Thread.sleep(2000);
		String xpathBuilder2 = "//tbody/tr/td[2][contains(text(),'"+name+"')]";
		Assert.assertTrue(IsElementPresent(By.xpath(xpathBuilder2)));	
	}

	public void captureLivePhotoAndUpload() throws Exception	{
		driver.findElement(AllowCaptureBtn).click();
		Thread.sleep(2000);
		driver.findElement(CaptureBtn).click();
		Thread.sleep(2000);
		driver.findElement(UploadBtn_Photo).click();
		Thread.sleep(8000);
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

	public void UploadPatientPhoto(String filePath) throws Exception	{
		WebElement chooseFile = driver.findElement(ChooseFile_Photo);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', arguments[1])", chooseFile, "0");
		chooseFile.sendKeys(filePath);
		Thread.sleep(2000);
		waitUntillElementVisible(UploadBtn_Photo);
		driver.findElement(UploadBtn_Photo).click();
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

	public void ClickOnSubmitBtn() {
		driver.findElement(SubmitBtn).click();
	}

	public boolean IsContactTabVisibe() throws Exception { 
		return IsElementPresent(ContactDetailsTab); 
	} 

	public InstantApprovalUploadPopup ClickOnSearchForInsantMode()	{
		driver.findElement(SearchBeneficiary).click();
		return new InstantApprovalUploadPopup(driver);
	}

	public String getPopupMessageAndClickOK() throws Exception	{
		Assert.assertTrue(IsElementPresent(GeneratedDataOnDialog));
		String popupMessage = getText(GeneratedDataOnDialog);
		driver.findElement(DialogOkBtn).click();
		return popupMessage;
	}

	public List<String> getConvertedTIDNumberNClickOk()	{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(GeneratedDataOnDialog));
		List<String> popupMessages = new ArrayList();
		String[] messagesSplit = getText(GeneratedDataOnDialog).split(":");
		popupMessages.add(messagesSplit[0].trim());
		popupMessages.add(messagesSplit[1].trim());
		driver.findElement(DialogOkBtn).click();
		return popupMessages;
	}


	public String getGridPageHeader()	{
		return driver.findElement(GridPageHeader).getText();
	}

	public boolean IsGridTIDColumnVisible()	{
		return driver.findElement(GridTIDColumn).isDisplayed();
	}

	public boolean IsGridHospitalColumnVisible()	{
		return driver.findElement(GridHospitalColumn).isDisplayed();
	}

	public boolean IsGridPateintColumnVisible()	{
		return driver.findElement(GridPatientColumn).isDisplayed();
	}

	public boolean IsGridTimeColumnVisible()	{
		return driver.findElement(GridTimeColumn).isDisplayed();
	}

	public boolean IsGridSelectColumnVisible()	{
		return driver.findElement(GridSelectColumn).isDisplayed();
	}

	public boolean IsJanadharSelected(String value) {
		if(value.equalsIgnoreCase("Yes"))
			return driver.findElement(IsJanadharYes).isSelected();
		else
			return driver.findElement(IsJanadharNo).isSelected();
	}

	public boolean ValidateAllTIDsInProperFormat()	{
		List<WebElement> tids =  driver.findElements(TIDListRows);
		boolean check = false;
		for(WebElement listoftids : tids)
		{
			String tidValue = listoftids.getText().trim();			
			if(Arrays.asList('G','P').contains(tidValue.charAt(0)) && tidValue.length()==14)
			{	check = true;	}
			else
			{	check = false;	
			break;
			}
		}
		return check;
	}

	public void SelectIdentifiedRadio(String value) { 
		if(value.equalsIgnoreCase("Un-Identified")) 
			driver.findElement(UnIdentifiedRadio).click(); 
		else 
			driver.findElement(IdentifiedRadio).click(); 
	} 

	public String PatientDetailsTab()	{ 
		return driver.findElement(PatientDetailsTab).getText().trim(); 
	} 

	public void SetGenderTypeDropdownTo(String value)	{ 
		WebElement element = driver.findElement(PatientGender); 
		Select select = new Select(element); 
		select.selectByVisibleText(value); 
	} 

	public String GetGenderTypeSelectedValue()	{ 
		WebElement element = driver.findElement(PatientGender); 
		Select select = new Select(element); 
		return select.getFirstSelectedOption().getText(); 
	} 

	public void InputPatientName(String value){   
		WebElement element = driver.findElement(PatientName); 
		element.clear(); 
		element.sendKeys(value);	    	         
	} 

	public String GetEnteredPatientName() { 
		WebElement element = driver.findElement(PatientName); 
		return element.getAttribute("value"); 
	} 

	public void InputPatientAge(String value){   
		WebElement element = driver.findElement(PatientAge); 
		element.clear(); 
		element.sendKeys(value);	    	         
	} 

	public String GetEnteredPatientAge() { 
		WebElement element = driver.findElement(PatientAge); 
		return element.getAttribute("value"); 
	} 
}
