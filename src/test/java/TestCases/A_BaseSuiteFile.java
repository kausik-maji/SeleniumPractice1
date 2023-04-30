package TestCases;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import Configuration.TestStatistics;
import LibraryFiles.ReusableLibraryFile;


@Listeners(Configuration.TestListeners.class)
public class A_BaseSuiteFile extends ReusableLibraryFile{

	// Declare test class objects here
	BeneficiaryIdentificationFormTests obj_BeneficiaryIdentificationFormTests;
	EmergencyCaseConversionTests obj_EmergencyCaseConversionTests;

	@BeforeSuite(alwaysRun=true)
	public void beforeSuite() {	 
		System.out.println("========Test Suite Started===========");
	}

	@AfterSuite(alwaysRun=true)
	public void afterSuite() {
		System.out.println("========Test Suite Ends===========");
	} 

	@BeforeTest(alwaysRun=true)
	public void beforeTest() throws IOException
	{
		System.out.println("Configuring extend reports...");  
		browser = GetPropertyFileValue("Browser");
		DateFormat df = new SimpleDateFormat("ddMMyy_HHmmss");
		Date dateobj = new Date();
		String subString = df.format(dateobj);
		String WSpath = System.getProperty("user.dir");
		String ExtentFilePath = WSpath+"\\TestReports\\HtmlReport_"+subString+".html";

		ExtentHtmlReporter htmlReporter  = new ExtentHtmlReporter(ExtentFilePath);
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Environment","RSHAA");
		extent.setSystemInfo("Username","Junaid");
		extent.setSystemInfo("Browser",browser);
	}

	@BeforeClass(alwaysRun=true)
	//instantiate object here
	public void beforeClass() {
		System.out.println("instantiating Test classes....");
		obj_BeneficiaryIdentificationFormTests = new BeneficiaryIdentificationFormTests();
		obj_EmergencyCaseConversionTests = new EmergencyCaseConversionTests();
	}

	@BeforeMethod(alwaysRun=true)
	public void beforeMethod(Method testMethod) throws IOException, InterruptedException {
		System.out.println("Starting Test method....");
		OpenBrowserInstance();
		logger = extent.createTest(testMethod.getName(),testMethod.getAnnotation(Test.class).description());
		LaunchApplication();
		logger.log(Status.INFO, "Launched application in given browser");
	}

	@AfterMethod(alwaysRun=true)
	public void afterMethod(ITestResult result) throws Exception {
		System.out.println("Closing browser instance....");
		if(result.getStatus()==ITestResult.FAILURE){
			logger.log(Status.INFO, "Testcase failed is: " +result.getName());
			logger.log(Status.INFO, "failure is: " +result.getThrowable());
		}
		else if(result.getStatus()==ITestResult.SKIP){
			logger.log(Status.INFO, "Testcase skipped is: " +result.getName());
		}	  
		//TakeScreenShot(result);
		System.out.println("Closing driver instance");
		driver.close();
		logger.log(Status.INFO, "Closed browser instance for given test");
	}


	@AfterTest(alwaysRun=true)
	public void afterTest()
	{
		System.out.println("Closing extend report instance");
		TestStatistics.printSuiteStatistics();
		extent.flush();
	}

	/*Start of TestCases
	 * ==========================================================================
	 * ========================================================================== 
	 */


	@Test(enabled=true, groups= {"BIM"})
	public void TC01_ValidatePageAndFieldValidationForBeneficiaryForm() throws Exception	{
		logger.log(Status.INFO, "Test: TC01_ValidatePageAndFieldValidationForBeneficiaryForm started");
		obj_BeneficiaryIdentificationFormTests.ValidatePageAndFieldValidationForBeneficiaryForm();
	}

	@Test(enabled=true, groups= {"BIM"})
	public void TC02_FieldValidationOfBeneficiaryFormForNormalAdmissionType() throws Exception	{
		logger.log(Status.INFO, "Test: TC02_FieldValidationOfBeneficiaryFormForNormalAdmissionType started");
		obj_BeneficiaryIdentificationFormTests.FieldValidationOfBeneficiaryFormForNormalAdmissionType();
	}

	@Test(enabled=true, groups= {"BIM"})
	public void TC03_ValidateJanAadhaarIDAndAcknowledgementIDTypeSelectionForBeneficiaryForm() throws Exception	{
		logger.log(Status.INFO, "Test: TC03_ValidateJanAadhaarIDAndAcknowledgementIDTypeSelectionForBeneficiaryForm started");
		obj_BeneficiaryIdentificationFormTests.ValidateJanAadhaarIDAndAcknowledgementIDTypeSelectionForBeneficiaryForm();
	}

	@Test(enabled=true, groups= {"BIM"})
	public void TC04_ValidateNegativeIDTypeSelectionForBeneficiaryForm() throws Exception	{
		logger.log(Status.INFO, "Test: TC04_ValidateNegativeIDTypeSelectionForBeneficiaryForm started");
		obj_BeneficiaryIdentificationFormTests.ValidateNegativeIDTypeSelectionForBeneficiaryForm();
	}

	@Test(enabled=true, groups= {"BIM"})
	public void TC05_ValidateIdentityTypeSelectionForBeneficiaryForm() throws Exception	{
		logger.log(Status.INFO, "Test: TC05_ValidateIdentityTypeSelectionForBeneficiaryForm started");
		obj_BeneficiaryIdentificationFormTests.ValidateIdentityTypeSelectionForBeneficiaryForm();
	}

	@Test(enabled=true, groups= {"BIM"})
	public void TC06_ValidateNegativeUniquieIDTypeInputForBIForm() throws Exception	{
		logger.log(Status.INFO, "Test: TC06_ValidateNegativeUniquieIDTypeInputForBIForm started");
		obj_BeneficiaryIdentificationFormTests.ValidateNegativeUniquieIDTypeInputForBIForm();
	}

	@Test(enabled=true, groups= {"BIM"})
	public void TC07_ValidateBeneficiaryCategoryOfGivenJanaadhar() throws Exception	{
		logger.log(Status.INFO, "Test: TC07_ValidateBeneficiaryCategoryOfGivenJanaadhar started");
		obj_BeneficiaryIdentificationFormTests.ValidateBeneficiaryCategoryOfGivenJanaadhar();
	}

	@Test(enabled=true, groups= {"BIM"})
	public void TC08_ValidateInstantModePopupFieldsForDisenrolledJanaadharOnBIForm() throws Exception  {
		logger.log(Status.INFO, "Test: TC08_ValidateInstantModePopupFieldsForDisenrolledJanaadharOnBIForm started");
		obj_BeneficiaryIdentificationFormTests.ValidateInstantModePopupFieldsForDisenrolledJanaadharOnBIForm();
	}

	@Test(enabled=true, groups= {"BIM"})
	public void TC09_ValidateInstantModePopupUploadForDisenrolledJanaadharOnBIForm() throws Exception	{
		logger.log(Status.INFO, "Test: TC06_ValidateInstantModePopupUploadForDisenrolledJanaadharOnBIForm started");
		obj_BeneficiaryIdentificationFormTests.ValidateInstantModePopupUploadForDisenrolledJanaadharOnBIForm();
	}

	@Test(enabled=true, groups= {"BIM"})
	public void TC10_ValidateNormalTIDCreationUsingBeneficiaryForm() throws Exception	{
		logger.log(Status.INFO, "Test: TC10_ValidateNormalTIDCreationFromBeneficiaryForm started");
		obj_BeneficiaryIdentificationFormTests.ValidateNormalTIDCreationFromBeneficiaryForm();
	}

	@Test(enabled=true, groups= {"BIM"})
	public void TC11_ValidateSelectedMemberDetailsFromGivenMembersList() throws Exception	{
		logger.log(Status.INFO, "Test: TC11_ValidateSelectedMemberDetailsFromGivenMembersList started");
		obj_BeneficiaryIdentificationFormTests.ValidateSelectedMemberDetailsFromGivenMembersList();
	}

	@Test(enabled=true, groups= {"BIM"})
	public void TC12_ValidateNormalTIDCreateUsingLivePatientPhoto() throws Exception	{
		logger.log(Status.INFO, "Test: TC12_ValidateNormalTIDCreateUsingLivePatientPhoto started");
		obj_BeneficiaryIdentificationFormTests.ValidateNormalTIDCreateUsingLivePatientPhoto();
	}

	@Test(enabled=true, groups= {"BIM"})
	public void TC13_ValidateNegativeScenariosForUploadFucntionalityOnBIForm() throws Exception  {
		logger.log(Status.INFO, "Test: TC13_ValidateNegativeScenariosForUploadFucntionalityOnBIForm started");
		obj_BeneficiaryIdentificationFormTests.ValidateNegativeScenariosForUploadFucntionalityOnBIForm();
	}

	@Test(enabled=true, groups= {"BIM"})
	public void TC14_ValidateNormalTIDNAdmissionFormatOnSuccessPopup() throws Exception	{
		logger.log(Status.INFO, "Test: TC14_ValidateNormalTIDNAdmissionFormatOnSuccessPopup started");
		obj_BeneficiaryIdentificationFormTests.ValidateNormalTIDNAdmissionFormatOnSuccessPopup();
	}

	@Test(enabled=true)
	public void TC15_ValidateChangingSelectedMemberAfterUploadsOnBIForm() throws Exception	{
		logger.log(Status.INFO, "Test: TC15_ValidateChangingSelectedMemberAfterUploadsOnBIForm started");
		obj_BeneficiaryIdentificationFormTests.ValidateChangingSelectedMemberAfterUploadsOnBIForm();
	}

	@Test(enabled=true, groups= {"ECC"}) 
	public void TC16_ValidatePageAndFieldForEmergencyCaseOnBeneficiaryForm() throws Exception	{ 
		logger.log(Status.INFO, "Test: TC16_ValidatePageAndFieldForEmergencyCaseOnBeneficiaryForm started"); 
		obj_EmergencyCaseConversionTests.ValidatePageAndFieldForEmergencyCaseOnBeneficiaryForm(); 
	} 

	@Test(enabled=true, groups= {"ECC"}) 
	public void TC17_ValidateFieldForPatientIdentifiedByOnBIForm() throws Exception	{ 
		logger.log(Status.INFO, "Test: TC17_ValidateFieldForPatientIdentifiedByOnBIForm started"); 
		obj_EmergencyCaseConversionTests.ValidateFieldForPatientIdentifiedByOnBIForm(); 
	} 
	
	@Test(enabled=true, groups= {"ECC"}) 
	public void TC18_ValidateMLCCaseRadioFieldOnEmergencyMode() throws Exception	{ 
		logger.log(Status.INFO, "Test: TC18_ValidateMLCCaseRadioFieldOnEmergencyMode started"); 
		obj_EmergencyCaseConversionTests.ValidateMLCCaseRadioFieldOnEmergencyMode(); 
	} 
	
	@Test(enabled=true, groups= {"ECC"}) 
	public void TC19_ValidatePateintIdentifiedSectionOnBeneficiaryPage() throws Exception	{ 
		logger.log(Status.INFO, "Test: TC19_ValidatePateintIdentifiedSectionOnBeneficiaryPage started"); 
		obj_EmergencyCaseConversionTests.ValidatePateintIdentifiedSectionOnBeneficiaryPage(); 
	} 
	
	@Test(enabled=true, groups= {"ECC"}) 
	public void TC20_ValidateUnidentifiedPatientFieldsOnDetailsTabOfBIFormEmergencyMode() throws Exception	{ 
		logger.log(Status.INFO, "Test: TC20_ValidateUnidentifiedPatientFieldsOnDetailsTabOfBIFormEmergencyMode started"); 
		obj_EmergencyCaseConversionTests.ValidateUnidentifiedPatientFieldsOnDetailsTabOfBIFormEmergencyMode(); 
	} 
	
	@Test(enabled=true, groups= {"ECC"}) 
	public void TC21_ValidateTIDNAdmissionNumCreatedFormatForEmergencyMode() throws Exception	{ 
		logger.log(Status.INFO, "Test: TC21_ValidateTIDNAdmissionNumCreatedFormatForEmergencyMode started"); 
		obj_EmergencyCaseConversionTests.ValidateTIDNAdmissionNumCreatedFormatForEmergencyMode(); 
	} 

	@Test(enabled=true, groups= {"ECC"})
	public void TC22_ValidateListOfTIDsToConvertPopupsAndTheirFields() throws Exception  {
		logger.log(Status.INFO, "Test: TC22_ValidateListOfTIDsToConvertPopupsAndTheirFields started");
		obj_EmergencyCaseConversionTests.ValidateListOfTIDsToConvertPopupsAndTheirFields();
	}

	@Test(enabled=true, groups= {"ECC"})
	public void TC23_ValidateEmergencyFieldsOnBenenficiaryPage() throws Exception  {
		logger.log(Status.INFO, "Test: TC23_ValidateEmergencyFieldsOnBenenficiaryPage started");
		obj_EmergencyCaseConversionTests.ValidateEmergencyFieldsOnBenenficiaryPage();
	}

	@Test(enabled=true, groups= {"ECC"}) //need to work on
	public void TC24_ValidateJanAadhaarIDAndAcknowledgementIDTypeSelectionForECCForm() throws Exception {
		logger.log(Status.INFO, "Test: TC24_ValidateJanAadhaarIDAndAcknowledgementIDTypeSelectionForECCForm started");
		obj_EmergencyCaseConversionTests.ValidateJanAadhaarIDAndAcknowledgementIDTypeSelectionForECCForm();  
	}

	@Test(enabled=true, groups= {"ECC"})
	public void TC25_ValidateCategoryOfGivenJanaadharOnEmergencyConversionPage() throws Exception {
		logger.log(Status.INFO, "Test: TC25_ValidateCategoryOfGivenJanaadharOnEmergencyConversionPage started");
		obj_EmergencyCaseConversionTests.ValidateCategoryOfGivenJanaadharOnEmergencyConversionPage();	  
	}

	@Test(enabled=true, groups= {"ECC"}) 
	public void TC26_ValidateInstantModePopupFieldsForDisenrolledJanaadharOnECCPage() throws Exception	{ 
		logger.log(Status.INFO, "Test: TC26_ValidateInstantModePopupFieldsForDisenrolledJanaadharOnECCPage started"); 
		obj_EmergencyCaseConversionTests.ValidateInstantModePopupFieldsForDisenrolledJanaadharOnECCPage(); 
	} 

	@Test(enabled=true, groups= {"ECC"}) 
	public void TC27_ValidateInstantModePopupUploadForDisenrolledJanaadharOnECCPage() throws Exception	{ 
		logger.log(Status.INFO, "Test: TC27_ValidateInstantModePopupUploadForDisenrolledJanaadharOnECCPage started"); 
		obj_EmergencyCaseConversionTests.ValidateInstantModePopupUploadForDisenrolledJanaadharOnECCPage(); 
	} 

	@Test(enabled=true, groups= {"ECC"})
	public void TC28_ValidateEmergencyTIDCreationUsingCaseConversionForm() throws Exception  {
		logger.log(Status.INFO, "Test: TC28_ValidateEmergencyTIDCreationUsingCaseConversion started");
		obj_EmergencyCaseConversionTests.ValidateEmergencyTIDCreationUsingCaseConversion();
	}

	@Test(enabled=true, groups= {"ECC"})
	public void TC29_ValidateEmergencyTIDCreateUsingLivePatientPhoto() throws Exception  {
		logger.log(Status.INFO, "Test: TC29_ValidateEmergencyTIDCreateUsingLivePatientPhoto started");
		obj_EmergencyCaseConversionTests.ValidateEmergencyTIDCreateUsingLivePatientPhoto();
	}
}
