package ObjectRepository;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import com.aventstack.extentreports.Status;
import LibraryFiles.ReusableLibraryFile;

public class BeneficiaryIdentificationForm extends ReusableLibraryFile{

	By pageHeader = By.xpath("//ol[@class='breadcrumb']/li[2]");
	By AdmissionType = By.id("ddAdmissionType");
	By IsAccidentCaseYes = By.id("RTA_Accidental_Y");
	By IsAccidentCaseNo = By.id("RTA_Accidental_N");
	By IdentityType = By.id("ddlIdentityType");
	By IsJanadharYes = By.id("have_Jan_Aadhar_1");
	By IsJanadharNo = By.id("have_Jan_Aadhar_2");
	By UniquieIDNumber = By.id("txtuid");
	By SearchBeneficiary = By.xpath("//button[contains(text(),'Search Beneficiary')]");
	By VerifyUsing = By.id("flexRadioDefault2");
	By ChooseFile_MOIC = By.xpath("(//input[@type='file'])[1]");
	By UploadBtn_MOIC = By.xpath("(//button[text()='Upload'])[1]");
	By UploadDialogMessage = By.xpath("//div[@class='swal2-html-container']");
	By DialogOkBtn = By.xpath("//div[@class='swal2-actions']/button[text()='OK']");
	By MobileForSMSAlert = By.id("txtmobile");
	By ChooseFile_Photo = By.xpath("(//input[@type='file'])[2]");
	By UploadBtn_Photo = By.xpath("(//button[text()='Upload'])[2]");
	By SubmitBtn = By.xpath("//div/button[text()='Submit']");
	By GeneratedDataOnDialog = By.id("swal2-html-container");
	By ContactDetailsTab = By.xpath("//h2[@id='headingOne']/button");
	By EntitlementTypeValue = By.xpath("//label[contains(text(),'Entitlement Type')]/following-sibling::label");
	By AllowCaptureBtn = By.xpath("//button[text()='Allow']");
	By CaptureBtn = By.xpath("//button[text()='Capture']");
	By UnIdentifiedRadio = By.id("Un-Identified"); 
	By IdentifiedRadio = By.id("PatientIdentified"); 
	By PatientDetailsTab = By.xpath("//button[text()=' Patient Details ']"); 
	By PatientName = By.name("txtPatientName"); 
	By PatientAge = By.name("txtpatientAge"); 
	By PatientGender = By.name("ddlGender"); 
	By MLCYesRadio = By.id("MlcYes");
	By MLCNoRadio  = By.id("MlcNo");
	By MLCTypeDropdown = By.name("ddlMlcType");
	By NamePatientIdentified = By.name("txtidentifyingPersonName");
	By RelationWithParent = By.name("txtidentifyingPersonRelationship");
	By ContactNumber = By.name("txtidentifyingPersonContactNumber");
	
		
	public BeneficiaryIdentificationForm(WebDriver driver) {
		this.driver = driver;
	}
	
	public String getPageHeader()	{
		return driver.findElement(pageHeader).getText().trim();
	}
	
	public boolean IsContactTabVisibe() throws Exception {
		return IsElementPresent(ContactDetailsTab);
	}
	
	public String getEntitlementTypeValue()	{
		return driver.findElement(EntitlementTypeValue).getText().trim();
	}

	public void SetAdmissionTypeDropdownTo(String value)	{
		WebElement element = driver.findElement(AdmissionType);
		Select select = new Select(element);
		select.selectByVisibleText(value);
	}
	
	public String GetAdmissionTypeSelectedValue()	{
		WebElement element = driver.findElement(AdmissionType);
		Select select = new Select(element);
		 return select.getFirstSelectedOption().getText();
	}

	public void IsRTAAccidentCase(String value) {
		if(value.equalsIgnoreCase("Yes"))
			driver.findElement(IsAccidentCaseYes).click();
		else
			driver.findElement(IsAccidentCaseNo).click();
	}
	
	public boolean RTAAccidentCaseSelected(String value) {
		if(value.equalsIgnoreCase("Yes"))
			return driver.findElement(IsAccidentCaseYes).isSelected();
		else
			return driver.findElement(IsAccidentCaseNo).isSelected();
	}
	
	public boolean IsJanadharSelected(String value) {
		if(value.equalsIgnoreCase("Yes"))
			return driver.findElement(IsJanadharYes).isSelected();
		else
			return driver.findElement(IsJanadharNo).isSelected();
	}
	
	public boolean IsIdentifiedSelected(String value) {
		if(value.equalsIgnoreCase("Identified"))
			return driver.findElement(IdentifiedRadio).isSelected();
		else 
			return driver.findElement(UnIdentifiedRadio).isSelected();
	}
	
	public void SelectMLCTypeDropdownTo(String value)	{ 
		WebElement element = driver.findElement(MLCTypeDropdown); 
		Select select = new Select(element); 
		select.selectByVisibleText(value); 
	} 
	
	public String GetMLCTypeSelectedValue()	{
		WebElement element = driver.findElement(MLCTypeDropdown);
		Select select = new Select(element);
		 return select.getFirstSelectedOption().getText();
	}	
	
	public List<String> GetAllMLCTypeDropdownOptions()	{
		WebElement element = driver.findElement(MLCTypeDropdown);
		Select select = new Select(element);
		List<WebElement> allOptions = select.getOptions();
		List<String> allOptionsString = new ArrayList();
		for (WebElement option:allOptions) {
			String optionText = option.getText().trim();
			allOptionsString.add(optionText);
			}
		return allOptionsString;
	}	
	
	public List<String> GetAllGenderDropdownOptions()	{
		WebElement element = driver.findElement(PatientGender);
		Select select = new Select(element);
		List<WebElement> allOptions = select.getOptions();
		List<String> allOptionsString = new ArrayList();
		for (WebElement option:allOptions) {
			String optionText = option.getText().trim();
			allOptionsString.add(optionText);
			}
		return allOptionsString;
	}
	
	public void SelectMLCRadio(String value) { 
		if(value.equalsIgnoreCase("Yes")) 
			driver.findElement(MLCYesRadio).click(); 
		else 
			driver.findElement(MLCNoRadio).click(); 
	} 
	
	public boolean IsMLCRadioSelected(String value) {
		if(value.equalsIgnoreCase("Yes"))
			return driver.findElement(MLCYesRadio).isSelected();
		else
			return driver.findElement(MLCNoRadio).isSelected();
	}
	
	public String GetUniqueIDNumberPlacholder() {
		return driver.findElement(UniquieIDNumber).getAttribute("placeholder");
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

	public void IsJanadharAvailable(String value) {
		if(value.equalsIgnoreCase("Yes"))
			driver.findElement(IsJanadharYes).click();
		else
			driver.findElement(IsJanadharNo).click();
	}

	public void InputUniqueIDNumber(String value)	{
		WebElement element = driver.findElement(UniquieIDNumber);
		element.clear();
		element.sendKeys(value);
	}

	public void ClickOnSearchBeneficiary()	{
		driver.findElement(SearchBeneficiary).click();
	}
	
	public InstantApprovalUploadPopup ClickOnSearchForInsantMode()	{
		driver.findElement(SearchBeneficiary).click();
		return new InstantApprovalUploadPopup(driver);
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
	
	public void UnSelectedMemberInTableIs(String name) throws Exception	{
		String xpathBuilder = "//tbody/tr/td[contains(text(),'"+name+"')]/parent::tr/td[3]/input";
		driver.findElement(By.xpath(xpathBuilder)).click();
		Thread.sleep(2000);
		String xpathBuilder2 = "//tbody/tr/td[2][contains(text(),'"+name+"')]";
		Assert.assertFalse(IsElementPresent(By.xpath(xpathBuilder2)));	
	}
	
	public List<String> getDetailsOfSelectedMember(String name) throws Exception	{
		By selectedMemberPhoto = By.xpath("//tbody/tr/td[2][contains(text(),'"+name+"')]/preceding-sibling::td/img");
		By selectedMemberAge = By.xpath("//tbody/tr/td[2][contains(text(),'"+name+"')]/following-sibling::td[1]");
		By selectedMemberGender = By.xpath("//tbody/tr/td[2][contains(text(),'"+name+"')]/following-sibling::td[2]");
		By selectedMemberUID = By.xpath("//tbody/tr/td[2][contains(text(),'"+name+"')]/following-sibling::td[4]");
		Assert.assertTrue(IsElementVisible(selectedMemberPhoto));
		List<String> memberDetails = new ArrayList();
		memberDetails.add(getText(selectedMemberAge));
		memberDetails.add(getText(selectedMemberGender));
		memberDetails.add(getText(selectedMemberUID));
		return memberDetails;
	}
	
	public void captureLivePhotoAndUpload() throws Exception	{
		driver.findElement(AllowCaptureBtn).click();
		Thread.sleep(2000);
		driver.findElement(CaptureBtn).click();
		Thread.sleep(2000);
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
	
	public void InputMobileNumForSelectedUser(String MobileNum) throws Exception {
		Assert.assertTrue(IsElementVisible(MobileForSMSAlert));
		WebElement element = driver.findElement(MobileForSMSAlert);
		element.clear();
		element.sendKeys(MobileNum);
	}
	
	public void UploadPatientPhoto(String filePath) throws Exception	{
		WebElement chooseFile = driver.findElement(ChooseFile_Photo);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', arguments[1])", chooseFile, "0");
		chooseFile.sendKeys(filePath);
		Thread.sleep(2000);
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
	
	public String getPopupMessageAndClickOK() throws Exception	{
		Assert.assertTrue(IsElementPresent(GeneratedDataOnDialog));
		String popupMessage = getText(GeneratedDataOnDialog);
		driver.findElement(DialogOkBtn).click();
		return popupMessage;
	}
	
	public String[] getMultiplePopupMessagesAndClickOK() throws Exception  {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(GeneratedDataOnDialog));
		String[] dataGenerated = getText(GeneratedDataOnDialog).split("\n");
		driver.findElement(DialogOkBtn).click();
		return dataGenerated;
	}
	
	public List<String> getTIDNumberNClickOk()	{
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(GeneratedDataOnDialog));
		List<String> popupMessages = new ArrayList();
		String[] messagesSplit = getText(GeneratedDataOnDialog).split(":");
		popupMessages.add(messagesSplit[0].trim().split("\\.")[0]);
		popupMessages.add(messagesSplit[1].trim().split(" ")[0]);
		popupMessages.add(messagesSplit[2].trim());
		driver.findElement(DialogOkBtn).click();
		return popupMessages;
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
	
	public void InputNameOfPatientIdentified(String value){   
		WebElement element = driver.findElement(NamePatientIdentified); 
		element.clear(); 
		element.sendKeys(value);	    	         
	}
	
	public String GetEnteredNameOfPatientIdentified() { 
		WebElement element = driver.findElement(NamePatientIdentified); 
		return element.getAttribute("value"); 
	}
	
	public void InputRelationWithPatient(String value){   
		WebElement element = driver.findElement(RelationWithParent); 
		element.clear(); 
		element.sendKeys(value);	    	         
	}
	
	public String GetEnteredRelationWithParent() { 
		WebElement element = driver.findElement(RelationWithParent); 
		return element.getAttribute("value"); 
	}
	
	public void InputContactNumber(String value){   
		WebElement element = driver.findElement(ContactNumber); 
		element.clear(); 
		element.sendKeys(value);	    	         
	}
	
	public String GetEnteredContactNum() { 
		WebElement element = driver.findElement(ContactNumber); 
		return element.getAttribute("value"); 
	}
	
}
