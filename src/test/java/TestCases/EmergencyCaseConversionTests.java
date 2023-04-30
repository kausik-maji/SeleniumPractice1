package TestCases;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import ObjectRepository.BeneficiaryIdentificationForm;
import ObjectRepository.EmergencyCaseConversionPage;
import ObjectRepository.InstantApprovalUploadPopup;
import ObjectRepository.MMCSBY_HomePage;
import ObjectRepository.TMS_PanelAndDashboard;

public class EmergencyCaseConversionTests extends A_BaseSuiteFile{
	String dataFilePath = System.getProperty("user.dir")+"\\TestData\\TestData.properties";

	public void ValidatePageAndFieldForEmergencyCaseOnBeneficiaryForm() throws Exception	{
		logger.log(Status.INFO, "Landed on MMCSBY Home page and navigating to TMS"); 
		MMCSBY_HomePage homePage = new MMCSBY_HomePage(driver); 
		TMS_PanelAndDashboard tmsPanel = homePage.clickOnTMSLink(); 
		logger.log(Status.INFO, "Creating emergency TID without any details"); 
		BeneficiaryIdentificationForm beneficiaryForm = tmsPanel.clickOnBeneficiaryForm(); 
		Assert.assertTrue(beneficiaryForm.getPageHeader().trim().contains("Beneficiary Identification Form")); 
		Assert.assertEquals(beneficiaryForm.GetAdmissionTypeSelectedValue().trim(), "NORMAL");
		Assert.assertEquals(beneficiaryForm.GetIdentityTypeSelectedValue().trim(), "Identity Type");

		beneficiaryForm.SetAdmissionTypeDropdownTo("EMERGENCY");
		Assert.assertEquals(beneficiaryForm.GetAdmissionTypeSelectedValue().trim(), "EMERGENCY");
		logger.log(Status.INFO, "Validation of Admission type dropdown completed");

		beneficiaryForm.IsRTAAccidentCase("No");
		Assert.assertTrue(beneficiaryForm.RTAAccidentCaseSelected("No"));
		beneficiaryForm.IsRTAAccidentCase("Yes");
		Assert.assertTrue(beneficiaryForm.RTAAccidentCaseSelected("Yes"));
		logger.log(Status.INFO, "Validation of RTA accident radio button completed");
	}

	public void ValidateFieldForPatientIdentifiedByOnBIForm() throws Exception{ 
		logger.log(Status.INFO, "Landed on MMCSBY Home page and navigating to TMS"); 
		MMCSBY_HomePage homePage = new MMCSBY_HomePage(driver); 
		TMS_PanelAndDashboard tmsPanel = homePage.clickOnTMSLink(); 
		logger.log(Status.INFO, "Creating emergency TID without any details"); 
		BeneficiaryIdentificationForm beneficiaryForm = tmsPanel.clickOnBeneficiaryForm(); 
		Assert.assertTrue(beneficiaryForm.getPageHeader().trim().contains("Beneficiary Identification Form")); 
		beneficiaryForm.SetAdmissionTypeDropdownTo("EMERGENCY"); 
		beneficiaryForm.IsRTAAccidentCase("No"); 
		Thread.sleep(2000); 
		beneficiaryForm.SelectIdentifiedRadio("Un-Identified"); 
		Assert.assertTrue(beneficiaryForm.RTAAccidentCaseSelected("Un-Identified")); 
		Thread.sleep(2000); 
		beneficiaryForm.SelectIdentifiedRadio("Identified"); 
		Assert.assertTrue(beneficiaryForm.RTAAccidentCaseSelected("Identified")); 
		Thread.sleep(2000); 
		Assert.assertEquals(beneficiaryForm.PatientDetailsTab().trim(), "Patient Details"); 
		Thread.sleep(2000); 
		beneficiaryForm.InputPatientName("1234"); 
		Assert.assertTrue(beneficiaryForm.GetEnteredPatientName().equals(""));   
		beneficiaryForm.InputPatientName("Test purpose"); 
		Assert.assertTrue(beneficiaryForm.GetEnteredPatientName().equals("Test purpose"));  
		Thread.sleep(2000); 
		beneficiaryForm.InputPatientAge("Twenty"); 
		Assert.assertTrue(beneficiaryForm.GetEnteredPatientAge().equals("")); 
		beneficiaryForm.InputPatientAge("34"); 
		Assert.assertTrue(beneficiaryForm.GetEnteredPatientAge().equals("34"));  
		logger.log(Status.INFO, "Validating user is able to select Gender from the dropdown"); 
		String[] arr = {"Male","Female","Other"}; 
		for(String type :arr) { 
			beneficiaryForm.SetGenderTypeDropdownTo(type); 
			Assert.assertEquals(beneficiaryForm.GetGenderTypeSelectedValue().trim(), type); 
		}			 
	} 

	public void ValidateInstantModePopupFieldsForDisenrolledJanaadharOnECCPage() throws Exception { 
		logger.log(Status.INFO, "Landed on MMCSBY Home page and navigating to TMS"); 
		MMCSBY_HomePage homePage = new MMCSBY_HomePage(driver); 
		TMS_PanelAndDashboard tmsPanel = homePage.clickOnTMSLink(); 
		logger.log(Status.INFO, "Creating emergency TID without any details"); 
		BeneficiaryIdentificationForm beneficiaryForm = tmsPanel.clickOnBeneficiaryForm(); 
		Assert.assertTrue(beneficiaryForm.getPageHeader().trim().contains("Beneficiary Identification Form")); 		 
		beneficiaryForm.SetAdmissionTypeDropdownTo("EMERGENCY"); 
		beneficiaryForm.IsRTAAccidentCase("No"); 
		ScrollDownToGivenPixels(300); 
		beneficiaryForm.ClickOnSubmitBtn(); 
		Thread.sleep(6000); 
		List<String> DataGenerated = beneficiaryForm.getTIDNumberNClickOk(); 
		logger.log(Status.INFO, "Created TID: "+DataGenerated.get(1)); 
		String TIDNumber = DataGenerated.get(1).trim(); 		 

		logger.log(Status.INFO, "Starting emergency case conversion"); 
		EmergencyCaseConversionPage caseConversionPage = tmsPanel.clickOnEmergencyCaseConvert(); 
		Assert.assertTrue(caseConversionPage.getPageHeader().trim().contains("Emergency Case Conversion")); 		 
		caseConversionPage.InputTIDNumber(TIDNumber); 
		caseConversionPage.ClickOnSearch(); 
		caseConversionPage.SelectJanadharRadio("Yes"); 
		caseConversionPage.SetIdentityTypeDropdownTo("Jan Aadhar Id"); 
		caseConversionPage.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "Disenrolled_Janaadhaar")); 

		InstantApprovalUploadPopup instantModePopup = caseConversionPage.ClickOnSearchForInsantMode(); 
		Thread.sleep(6000); 
		logger.log(Status.INFO, "Validate all fields present on popup"); 
		Assert.assertTrue(instantModePopup.IsUploadOptionVisible()); 
		Assert.assertTrue(instantModePopup.IsUploadBtnVisible()); 
		Assert.assertTrue(instantModePopup.IsCancelBtnVisible()); 
		Assert.assertTrue(instantModePopup.IsContinueBtnVisible()); 
		String popupMessage = "Citizen is not enrolled with Chiranjeevi Yojana. In case of Emergency, a approval request form document can be uploaded and after approval from concerned District Collector or Govt. Hospital Nodal (in case of Govt. hospital) , citizen can avail benefits. To continue, Upload document, submit and then fill other details to create TID OR Press cancel to cancel creating TID in instant mode."; 
		Assert.assertEquals(instantModePopup.getPopupMessage(),popupMessage); 

		logger.log(Status.INFO, "Click cancel to close the search & move back to case conversion page"); 
		caseConversionPage = instantModePopup.clickOnCancelBtnForEmergencyPage(); 
		Thread.sleep(3000);		 
		Assert.assertEquals(caseConversionPage.getPageHeader(), "Emergency Case Conversion"); 
		Assert.assertFalse(caseConversionPage.IsContactTabVisibe()); 
	} 

	public void ValidateInstantModePopupUploadForDisenrolledJanaadharOnECCPage() throws Exception { 
		logger.log(Status.INFO, "Landed on MMCSBY Home page and navigating to TMS"); 
		MMCSBY_HomePage homePage = new MMCSBY_HomePage(driver); 
		TMS_PanelAndDashboard tmsPanel = homePage.clickOnTMSLink(); 

		logger.log(Status.INFO, "Creating emergency TID without any details"); 
		BeneficiaryIdentificationForm beneficiaryForm = tmsPanel.clickOnBeneficiaryForm(); 
		Assert.assertTrue(beneficiaryForm.getPageHeader().trim().contains("Beneficiary Identification Form")); 
		beneficiaryForm.SetAdmissionTypeDropdownTo("EMERGENCY"); 
		beneficiaryForm.IsRTAAccidentCase("No"); 
		ScrollDownToGivenPixels(300); 
		beneficiaryForm.ClickOnSubmitBtn(); 
		Thread.sleep(6000); 
		List<String> DataGenerated = beneficiaryForm.getTIDNumberNClickOk(); 
		logger.log(Status.INFO, "Created TID: "+DataGenerated.get(1)); 
		String TIDNumber = DataGenerated.get(1).trim(); 

		logger.log(Status.INFO, "Starting emergency case conversion"); 
		EmergencyCaseConversionPage caseConversionPage = tmsPanel.clickOnEmergencyCaseConvert(); 
		Assert.assertTrue(caseConversionPage.getPageHeader().trim().contains("Emergency Case Conversion"));  
		caseConversionPage.InputTIDNumber(TIDNumber); 
		caseConversionPage.ClickOnSearch(); 
		caseConversionPage.SelectJanadharRadio("Yes"); 
		caseConversionPage.SetIdentityTypeDropdownTo("Jan Aadhar Id"); 
		caseConversionPage.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "Disenrolled_Janaadhaar")); 
		InstantApprovalUploadPopup instantModePopup = caseConversionPage.ClickOnSearchForInsantMode(); 
		Thread.sleep(6000);		 

		logger.log(Status.INFO, "Upload instant approval document to continue"); 
		String InstantApprovalDoc= System.getProperty("user.dir") + "\\TestData\\SampleFile.pdf"; 
		Thread.sleep(2000); 
		instantModePopup.UploadInstantApprovalDocument(InstantApprovalDoc); 
		Thread.sleep(2000); 
		caseConversionPage = instantModePopup.clickOnContinueBtnForEmergencyPage(); 
		Thread.sleep(5000); 

		logger.log(Status.INFO, "Validating the details retreived after submission"); 
		Assert.assertTrue(caseConversionPage.IsContactTabVisibe());		 
		List<String> EntitlementList = Arrays.asList("OTR","Others"); 
		Assert.assertTrue(EntitlementList.contains(caseConversionPage.getEntitlementTypeValue())); 
	} 

	public void ValidateJanAadhaarIDAndAcknowledgementIDTypeSelectionForECCForm() throws Exception {
		logger.log(Status.INFO, "Landed on MMCSBY Home page and navigating to TMS");
		MMCSBY_HomePage homePage = new MMCSBY_HomePage(driver);
		TMS_PanelAndDashboard tmsPanel = homePage.clickOnTMSLink();		
		logger.log(Status.INFO, "Creating emergency TID without any details");
		BeneficiaryIdentificationForm beneficiaryForm = tmsPanel.clickOnBeneficiaryForm();
		Assert.assertTrue(beneficiaryForm.getPageHeader().trim().contains("Beneficiary Identification Form"));

		beneficiaryForm.SetAdmissionTypeDropdownTo("EMERGENCY");
		beneficiaryForm.IsRTAAccidentCase("No");
		ScrollDownToGivenPixels(300);
		beneficiaryForm.ClickOnSubmitBtn();
		Thread.sleep(6000);
		List<String> DataGenerated = beneficiaryForm.getTIDNumberNClickOk();
		logger.log(Status.INFO, "Created TID: "+DataGenerated.get(1));
		String TIDNumber = DataGenerated.get(1).trim();
		Thread.sleep(3000);

		logger.log(Status.INFO, "Starting emergency case conversion");
		EmergencyCaseConversionPage caseConversionPage = tmsPanel.clickOnEmergencyCaseConvert();	
		caseConversionPage.InputTIDNumber(TIDNumber);
		Thread.sleep(2000);
		caseConversionPage.ClickOnSearch(); 
		Thread.sleep(2000);
		logger.log(Status.INFO, "Validating user is able to select identity type as Jan Aadhaar Acknowledgement");
		caseConversionPage.SetIdentityTypeDropdownTo("Jan Aadhar Acknowledgement");	
		logger.log(Status.INFO, "Validating UIN accepts 15 digits for Jan Aadhaar Acknowledgement");
		caseConversionPage.InputUniqueIDNumber("1234");
		Thread.sleep(2000);
		caseConversionPage.ClickOnSearchBeneficiary();
		Thread.sleep(6000);
		Assert.assertEquals(caseConversionPage.getPopupMessageAndClickOK(), "Please Enter a Jan Aadhaar Acknowledgement (15 Digit)");

		logger.log(Status.INFO, "Validating UIN accepts valid id for Jan Aadhaar Acknowledgement");
		caseConversionPage.InputUniqueIDNumber("012345678912345");
		Thread.sleep(2000);
		caseConversionPage.ClickOnSearchBeneficiary();
		Thread.sleep(6000);
		Assert.assertTrue(caseConversionPage.getPopupMessageAndClickOK().contains("This Janaadhaar null is not registered in Chiranjeevi"));

		caseConversionPage.InputTIDNumber(TIDNumber);
		Thread.sleep(2000);
		caseConversionPage.ClickOnSearch(); 
		Thread.sleep(2000);
		/*logger.log(Status.INFO, "Validating UIN accept valid Jan Aadhaar Acknowledgement");
		caseConversionPage.SetIdentityTypeDropdownTo("Jan Aadhar Acknowledgement");
		caseConversionPage.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "JanaadharAck"));
		Thread.sleep(3000);
		caseConversionPage.ClickOnSearchBeneficiary();
		Thread.sleep(6000);	
		Assert.assertTrue(caseConversionPage.IsContactTabVisibe());*/

		caseConversionPage.SetIdentityTypeDropdownTo("Aadhar");		
		logger.log(Status.INFO, "Validating UIN accepts 12 digits for Aadhaar");
		caseConversionPage.InputUniqueIDNumber("1234");
		Thread.sleep(3000);
		caseConversionPage.ClickOnSearchBeneficiary();
		Thread.sleep(6000);
		Assert.assertEquals(caseConversionPage.getPopupMessageAndClickOK(), "Please Enter a valid Aadhaar ID (12 Digit)");

		logger.log(Status.INFO, "Validating UIN does not accept invalid Aadhaar");
		Thread.sleep(3000);
		caseConversionPage.InputUniqueIDNumber("012345678912");
		Thread.sleep(3000);
		caseConversionPage.ClickOnSearchBeneficiary();
		Thread.sleep(6000);
		Assert.assertEquals(caseConversionPage.getPopupMessageAndClickOK(), "Beneficiary data not found in Janaadhaar.");

		caseConversionPage.InputTIDNumber(TIDNumber);
		Thread.sleep(2000);
		caseConversionPage.ClickOnSearch(); 
		Thread.sleep(2000);
		logger.log(Status.INFO, "Validating UIN accept valid  Aadhaar");
		caseConversionPage.SetIdentityTypeDropdownTo("Aadhar");
		caseConversionPage.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "AadhaarNumber"));
		Thread.sleep(3000);
		caseConversionPage.ClickOnSearchBeneficiary();
		Thread.sleep(6000);
		Assert.assertTrue(caseConversionPage.IsContactTabVisibe());

		caseConversionPage.SetIdentityTypeDropdownTo("House Hold Id");	
		logger.log(Status.INFO, "Validating UIN accepts 24 digits for House Hold ID");
		caseConversionPage.InputUniqueIDNumber("1234");
		Thread.sleep(3000);
		caseConversionPage.ClickOnSearchBeneficiary();
		Thread.sleep(6000);
		Assert.assertEquals(caseConversionPage.getPopupMessageAndClickOK(), "Please Enter a valid  House Hold ID (24 Digit)");

		logger.log(Status.INFO, "Validating UIN does not accept invalid House Hold ID");
		Thread.sleep(3000);
		caseConversionPage.InputUniqueIDNumber("012345678901234567891234");
		Thread.sleep(3000);
		caseConversionPage.ClickOnSearchBeneficiary();
		Thread.sleep(6000);
		Assert.assertEquals(caseConversionPage.getPopupMessageAndClickOK(), "Data Not Found");

		caseConversionPage.InputTIDNumber(TIDNumber);
		Thread.sleep(2000);
		caseConversionPage.ClickOnSearch(); 
		Thread.sleep(2000);
		logger.log(Status.INFO, "Validating UIN accept valid House Hold ID");
		caseConversionPage.SetIdentityTypeDropdownTo("House Hold Id");
		caseConversionPage.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "AadhaarNumber"));
		Thread.sleep(3000);
		caseConversionPage.ClickOnSearchBeneficiary();
		Thread.sleep(6000);
		Assert.assertTrue(caseConversionPage.IsContactTabVisibe());

		caseConversionPage.SetIdentityTypeDropdownTo("Mobile Number");		
		logger.log(Status.INFO, "Validating UIN accepts 10 digits for Mobile Number");
		caseConversionPage.InputUniqueIDNumber("1234");
		Thread.sleep(3000);
		caseConversionPage.ClickOnSearchBeneficiary();
		Thread.sleep(6000);
		Assert.assertEquals(caseConversionPage.getPopupMessageAndClickOK(), "Please Enter a valid Mobile Number (10 Digit)");

		logger.log(Status.INFO, "Validating UIN does not accept invalid Mobile Number");
		Thread.sleep(3000);
		caseConversionPage.InputUniqueIDNumber("0123456789");
		Thread.sleep(3000);
		caseConversionPage.ClickOnSearchBeneficiary();
		Thread.sleep(6000);
		Assert.assertEquals(caseConversionPage.getPopupMessageAndClickOK(), "Data Not Found");

		caseConversionPage.InputTIDNumber(TIDNumber);
		Thread.sleep(2000);
		caseConversionPage.ClickOnSearch(); 
		Thread.sleep(2000);
		logger.log(Status.INFO, "Validating UIN accept valid Mobile Number");
		caseConversionPage.SetIdentityTypeDropdownTo("Mobile Number");
		caseConversionPage.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "MobileNumber"));
		Thread.sleep(3000);
		caseConversionPage.ClickOnSearchBeneficiary();
		Thread.sleep(6000);
		Assert.assertTrue(beneficiaryForm.IsContactTabVisibe());
	}

	public void ValidateCategoryOfGivenJanaadharOnEmergencyConversionPage() throws Exception {
		logger.log(Status.INFO, "Landed on MMCSBY Home page and navigating to TMS");
		MMCSBY_HomePage homePage = new MMCSBY_HomePage(driver);
		TMS_PanelAndDashboard tmsPanel = homePage.clickOnTMSLink();
		logger.log(Status.INFO, "Creating emergency TID without any details");
		BeneficiaryIdentificationForm beneficiaryForm = tmsPanel.clickOnBeneficiaryForm();
		Assert.assertTrue(beneficiaryForm.getPageHeader().trim().contains("Beneficiary Identification Form"));

		beneficiaryForm.SetAdmissionTypeDropdownTo("EMERGENCY");
		beneficiaryForm.IsRTAAccidentCase("No");
		ScrollDownToGivenPixels(300);
		beneficiaryForm.ClickOnSubmitBtn();
		Thread.sleep(6000);
		List<String> DataGenerated = beneficiaryForm.getTIDNumberNClickOk();
		logger.log(Status.INFO, "Created TID: "+DataGenerated.get(1));
		String TIDNumber = DataGenerated.get(1).trim();
		Thread.sleep(3000);

		logger.log(Status.INFO, "Starting emergency case conversion");
		EmergencyCaseConversionPage caseConversionPage = tmsPanel.clickOnEmergencyCaseConvert();
		caseConversionPage.InputTIDNumber(TIDNumber);
		Thread.sleep(2000);
		caseConversionPage.ClickOnSearch(); 
		Thread.sleep(2000);
		List<String> categoryList = Arrays.asList("NFSA", "SECC", "CNT", "OTR", "SMF", "CVD");
		caseConversionPage.SetIdentityTypeDropdownTo("Jan Aadhar Id");

		logger.log(Status.INFO, "Validating NFSA type Jan aadhaar");
		caseConversionPage.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "NFSA_Janaadhaar"));
		caseConversionPage.ClickOnSearchBeneficiary();
		Thread.sleep(8000);
		Assert.assertEquals(caseConversionPage.getEntitlementTypeValue(), "NFSA");
		Assert.assertTrue(categoryList.contains(caseConversionPage.getEntitlementTypeValue().trim()));

		logger.log(Status.INFO, "Validating SECC type Jan aadhaar");
		caseConversionPage.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "SECC_Janaadhaar"));
		caseConversionPage.ClickOnSearchBeneficiary();
		Thread.sleep(8000);
		Assert.assertEquals(caseConversionPage.getEntitlementTypeValue(), "SECC");
		Assert.assertTrue(categoryList.contains(caseConversionPage.getEntitlementTypeValue().trim()));

		logger.log(Status.INFO, "Validating SMF type Jan aadhaar");
		caseConversionPage.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "SMF_Janaadhaar"));
		caseConversionPage.ClickOnSearchBeneficiary();
		Thread.sleep(8000);
		Assert.assertEquals(caseConversionPage.getEntitlementTypeValue(), "SMF");
		Assert.assertTrue(categoryList.contains(caseConversionPage.getEntitlementTypeValue().trim()));

		logger.log(Status.INFO, "Validating Covid type Jan aadhaar");
		caseConversionPage.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "Covid_Janaadhaar"));
		caseConversionPage.ClickOnSearchBeneficiary();
		Thread.sleep(8000);
		Assert.assertEquals(caseConversionPage.getEntitlementTypeValue(), "CVD");
		Assert.assertTrue(categoryList.contains(caseConversionPage.getEntitlementTypeValue().trim()));

		logger.log(Status.INFO, "Validating Contractual type Jan aadhaar");
		caseConversionPage.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "Cnt_Janaadhaar"));
		caseConversionPage.ClickOnSearchBeneficiary();
		Thread.sleep(8000);
		Assert.assertEquals(caseConversionPage.getEntitlementTypeValue(), "CNT");
		Assert.assertTrue(categoryList.contains(caseConversionPage.getEntitlementTypeValue().trim()));

		logger.log(Status.INFO, "Validating Paid type Jan aadhaar");
		caseConversionPage.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "Paid_Janaadhaar"));
		caseConversionPage.ClickOnSearchBeneficiary();
		Thread.sleep(6000);
		Assert.assertEquals(caseConversionPage.getEntitlementTypeValue(), "OTR");
		Assert.assertTrue(categoryList.contains(caseConversionPage.getEntitlementTypeValue().trim()));

	}

	public void ValidateEmergencyFieldsOnBenenficiaryPage() throws Exception {
		logger.log(Status.INFO, "Landed on MMCSBY Home page and navigating to TMS");
		MMCSBY_HomePage homePage = new MMCSBY_HomePage(driver);
		TMS_PanelAndDashboard tmsPanel = homePage.clickOnTMSLink();		
		logger.log(Status.INFO, "Creating emergency TID without any details");
		BeneficiaryIdentificationForm beneficiaryForm = tmsPanel.clickOnBeneficiaryForm();
		Assert.assertTrue(beneficiaryForm.getPageHeader().trim().contains("Beneficiary Identification Form"));		
		beneficiaryForm.SetAdmissionTypeDropdownTo("EMERGENCY");
		beneficiaryForm.IsRTAAccidentCase("No");
		ScrollDownToGivenPixels(300);
		beneficiaryForm.ClickOnSubmitBtn();
		Thread.sleep(5000);
		List<String> DataGenerated = beneficiaryForm.getTIDNumberNClickOk();
		logger.log(Status.INFO, "Created TID: "+DataGenerated.get(1));
		String TIDNumber = DataGenerated.get(1).trim();

		logger.log(Status.INFO, "Starting emergency case conversion");
		EmergencyCaseConversionPage caseConversionPage = tmsPanel.clickOnEmergencyCaseConvert();
		Assert.assertTrue(caseConversionPage.getPageHeader().trim().contains("Emergency Case Conversion"));		
		caseConversionPage.InputTIDNumber(TIDNumber);
		caseConversionPage.ClickOnSearch();
		Thread.sleep(2000);
		caseConversionPage.SelectJanadharRadio("No");
		Assert.assertTrue(caseConversionPage.IsJanadharSelected("No"));
		caseConversionPage.SelectJanadharRadio("Yes");
		Assert.assertTrue(caseConversionPage.IsJanadharSelected("Yes"));
		logger.log(Status.INFO, "Validation of Is Janadhar Available radio button completed");

		Assert.assertEquals(caseConversionPage.GetIdentityTypeSelectedValue().trim(), "Identity Type");
		logger.log(Status.INFO, "Validation of Identity Type Selection from Dropdown completed");

		caseConversionPage.SetIdentityTypeDropdownTo("Jan Aadhar Id");
		Assert.assertEquals(caseConversionPage.GetIdentityTypeSelectedValue().trim(), "Jan Aadhar Id");
		logger.log(Status.INFO, "Validation of Jan Aadhar ID Selection from Dropdown completed");

		logger.log(Status.INFO, "Validating UIN accepts 10 digits for Jan Aadhaar ID");
		caseConversionPage.InputUniqueIDNumber("1234");
		Thread.sleep(2000);
		caseConversionPage.ClickOnSearchBeneficiary();
		Thread.sleep(3000);
		Assert.assertEquals(caseConversionPage.getPopupMessageAndClickOK(), "Please Enter a valid Jan Aadhaar (10 Digit)");

		caseConversionPage.SelectJanadharRadio("Yes");
		caseConversionPage.SetIdentityTypeDropdownTo("Jan Aadhar Id");		
		logger.log(Status.INFO, "Validating UIN accept valid Jan Aadhaar ID");
		caseConversionPage.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "JanadharNumber"));
		Thread.sleep(3000);
		caseConversionPage.ClickOnSearchBeneficiary();
		Thread.sleep(8000);
		Assert.assertTrue(caseConversionPage.IsContactTabVisibe());
	}

	public void ValidateListOfTIDsToConvertPopupsAndTheirFields() throws Exception {
		logger.log(Status.INFO, "Landed on MMCSBY Home page and navigating to TMS");
		MMCSBY_HomePage homePage = new MMCSBY_HomePage(driver);
		TMS_PanelAndDashboard tmsPanel = homePage.clickOnTMSLink();

		logger.log(Status.INFO, "Creating emergency TID without any details");
		BeneficiaryIdentificationForm beneficiaryForm = tmsPanel.clickOnBeneficiaryForm();
		Assert.assertTrue(beneficiaryForm.getPageHeader().trim().contains("Beneficiary Identification Form"));

		beneficiaryForm.SetAdmissionTypeDropdownTo("EMERGENCY");
		beneficiaryForm.IsRTAAccidentCase("No");
		ScrollDownToGivenPixels(300);
		beneficiaryForm.ClickOnSubmitBtn();
		Thread.sleep(6000);
		List<String> DataGenerated = beneficiaryForm.getTIDNumberNClickOk();
		logger.log(Status.INFO, "Created TID: "+DataGenerated.get(1));
		String TIDNumber = DataGenerated.get(1).trim();		
		Thread.sleep(3000);

		logger.log(Status.INFO, "Starting emergency case conversion");
		EmergencyCaseConversionPage caseConversionPage = tmsPanel.clickOnEmergencyCaseConvert();
		Assert.assertTrue(caseConversionPage.getPageHeader().trim().contains("Emergency Case Conversion"));		
		caseConversionPage.InputTIDNumber(TIDNumber);
		Thread.sleep(1000);
		caseConversionPage.ClickOnSearch();
		Thread.sleep(2000);
		caseConversionPage.ClickOnListOfTIDs();
		Thread.sleep(2000);

		logger.log(Status.INFO, "Validating popup header and All TID's formats");
		Assert.assertEquals(caseConversionPage.getGridPageHeader(), "Beneficiary Enrolled");		
		Thread.sleep(1000);		
		Assert.assertTrue(caseConversionPage.IsGridTIDColumnVisible());
		Assert.assertTrue(caseConversionPage.IsGridHospitalColumnVisible());
		Assert.assertTrue(caseConversionPage.IsGridPateintColumnVisible());
		Assert.assertTrue(caseConversionPage.IsGridTimeColumnVisible());
		Assert.assertTrue(caseConversionPage.IsGridSelectColumnVisible());	
		Thread.sleep(1000);		
		Assert.assertTrue(caseConversionPage.ValidateAllTIDsInProperFormat());
	}

	public void ValidateEmergencyTIDCreationUsingCaseConversion() throws Exception {
		logger.log(Status.INFO, "Landed on MMCSBY Home page and navigating to TMS");
		MMCSBY_HomePage homePage = new MMCSBY_HomePage(driver);
		TMS_PanelAndDashboard tmsPanel = homePage.clickOnTMSLink();

		logger.log(Status.INFO, "Creating emergency TID without any details");
		BeneficiaryIdentificationForm beneficiaryForm = tmsPanel.clickOnBeneficiaryForm();
		Assert.assertTrue(beneficiaryForm.getPageHeader().trim().contains("Beneficiary Identification Form"));

		beneficiaryForm.SetAdmissionTypeDropdownTo("EMERGENCY");
		beneficiaryForm.IsRTAAccidentCase("Yes");
		ScrollDownToGivenPixels(300);
		Thread.sleep(2000);
		beneficiaryForm.ClickOnSubmitBtn();
		Thread.sleep(10000);
		List<String> DataGenerated = beneficiaryForm.getTIDNumberNClickOk();
		logger.log(Status.INFO, "Created TID: "+DataGenerated.get(1));
		String TIDNumber = DataGenerated.get(1).trim();

		logger.log(Status.INFO, "Starting emergency case conversion");
		EmergencyCaseConversionPage caseConversionPage = tmsPanel.clickOnEmergencyCaseConvert();
		Thread.sleep(2000);
		Assert.assertTrue(caseConversionPage.getPageHeader().trim().contains("Emergency Case Conversion"));

		caseConversionPage.InputTIDNumber(TIDNumber);
		caseConversionPage.ClickOnSearch();
		Thread.sleep(2000);
		caseConversionPage.SelectJanadharRadio("Yes");
		caseConversionPage.SetIdentityTypeDropdownTo("JAN AADHAR ID");
		caseConversionPage.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "JanadharNumber"));
		caseConversionPage.ClickOnSearchBeneficiary();
		Thread.sleep(7000);
		ScrollDownToGivenPixels(600);

		logger.log(Status.INFO, "Uploading & selecting the patient details from given Janadhaar card");
		caseConversionPage.ClickOnMOIC();
		Thread.sleep(1000);
		String MOICFilePath = System.getProperty("user.dir") + "\\TestData\\SampleFile.pdf";
		caseConversionPage.UploadMOICDocument(MOICFilePath);
		Thread.sleep(2000);
		String PatientName = GetPropertyFileValue(dataFilePath, "PatientName");
		String MobileNo = GetPropertyFileValue(dataFilePath, "MobileNumber");
		caseConversionPage.SelectedMemberInTableIs(PatientName);
		beneficiaryForm.InputMobileNumForSelectedUser(MobileNo);

		logger.log(Status.INFO, "Uploading photo & submitting the form");
		ScrollDownToGivenPixels(500);
		String photoFilePath = System.getProperty("user.dir") + "\\TestData\\SamplePhoto.jpg";
		caseConversionPage.UploadPatientPhoto(photoFilePath);
		Thread.sleep(1000);
		caseConversionPage.ClickOnSubmitBtn();
		Thread.sleep(8000);
		List<String> messageGenerated = caseConversionPage.getConvertedTIDNumberNClickOk();
		Assert.assertTrue(messageGenerated.get(0).trim().equalsIgnoreCase("Case converted successfully for Tid"));
		Assert.assertTrue(messageGenerated.get(1).trim().equals(TIDNumber));
		logger.log(Status.INFO, "Created TID: "+messageGenerated.get(1));
	}

	public void ValidateEmergencyTIDCreateUsingLivePatientPhoto() throws Exception	{
		logger.log(Status.INFO, "Landed on MMCSBY Home page and navigating to TMS");
		MMCSBY_HomePage homePage = new MMCSBY_HomePage(driver);
		TMS_PanelAndDashboard tmsPanel = homePage.clickOnTMSLink();

		logger.log(Status.INFO, "Creating emergency TID without any details");
		BeneficiaryIdentificationForm beneficiaryForm = tmsPanel.clickOnBeneficiaryForm();
		Assert.assertTrue(beneficiaryForm.getPageHeader().trim().contains("Beneficiary Identification Form"));
		beneficiaryForm.SetAdmissionTypeDropdownTo("EMERGENCY");
		beneficiaryForm.IsRTAAccidentCase("Yes");
		ScrollDownToGivenPixels(300);
		Thread.sleep(2000);
		beneficiaryForm.ClickOnSubmitBtn();
		Thread.sleep(8000);
		List<String> popupMessages = beneficiaryForm.getTIDNumberNClickOk();
		logger.log(Status.INFO, "Created TID: "+popupMessages.get(1));
		Assert.assertEquals(popupMessages.get(0), "Beneficiary saved successfully");
		Assert.assertTrue(Arrays.asList('G','P').contains(popupMessages.get(1).charAt(0)));
		Assert.assertTrue(popupMessages.get(1).length()==14, "TID should be of 14 digit");
		Assert.assertTrue(popupMessages.get(2).length()==15, "Admission number should be of 15 digit");
		logger.log(Status.INFO, "Created Admission number "+popupMessages.get(2));
		String TIDNumber = popupMessages.get(1).trim();

		logger.log(Status.INFO, "Starting emergency case conversion");
		EmergencyCaseConversionPage caseConversionPage = tmsPanel.clickOnEmergencyCaseConvert();
		Thread.sleep(2000);
		Assert.assertTrue(caseConversionPage.getPageHeader().trim().contains("Emergency Case Conversion"));

		caseConversionPage.InputTIDNumber(TIDNumber);
		caseConversionPage.ClickOnSearch();
		Thread.sleep(2000);
		caseConversionPage.SelectJanadharRadio("Yes");
		caseConversionPage.SetIdentityTypeDropdownTo("JAN AADHAR ID");
		caseConversionPage.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "JanadharNumber"));
		caseConversionPage.ClickOnSearchBeneficiary();
		Thread.sleep(7000);
		ScrollDownToGivenPixels(600);

		logger.log(Status.INFO, "Uploading & selecting the patient details from given Janadhaar card");
		caseConversionPage.ClickOnMOIC();
		Thread.sleep(1000);
		String MOICFilePath = System.getProperty("user.dir") + "\\TestData\\SampleFile.pdf";
		caseConversionPage.UploadMOICDocument(MOICFilePath);
		Thread.sleep(2000);
		String PatientName = GetPropertyFileValue(dataFilePath, "PatientName");
		String MobileNo = GetPropertyFileValue(dataFilePath, "MobileNumber");
		caseConversionPage.SelectedMemberInTableIs(PatientName);
		caseConversionPage.InputMobileNumForSelectedUser(MobileNo);

		logger.log(Status.INFO, "Uploading photo & submitting the form");
		ScrollDownToGivenPixels(500);
		caseConversionPage.captureLivePhotoAndUpload();		
		Thread.sleep(2000);
		beneficiaryForm.ClickOnSubmitBtn();
		Thread.sleep(8000);
		List<String> messageGenerated = caseConversionPage.getConvertedTIDNumberNClickOk();
		Assert.assertTrue(messageGenerated.get(0).trim().equalsIgnoreCase("Case converted successfully for Tid"));
		Assert.assertTrue(messageGenerated.get(1).trim().equals(TIDNumber));
	}

	public void ValidateMLCCaseRadioFieldOnEmergencyMode() throws Exception	{
		logger.log(Status.INFO, "Landed on MMCSBY Home page and navigating to TMS"); 
		MMCSBY_HomePage homePage = new MMCSBY_HomePage(driver); 
		TMS_PanelAndDashboard tmsPanel = homePage.clickOnTMSLink(); 
		logger.log(Status.INFO, "Creating emergency TID without any details"); 
		BeneficiaryIdentificationForm beneficiaryForm = tmsPanel.clickOnBeneficiaryForm(); 
		Assert.assertTrue(beneficiaryForm.getPageHeader().trim().contains("Beneficiary Identification Form")); 
		beneficiaryForm.SetAdmissionTypeDropdownTo("EMERGENCY");
		Assert.assertEquals(beneficiaryForm.GetAdmissionTypeSelectedValue().trim(), "EMERGENCY");
		
		Assert.assertTrue(beneficiaryForm.IsMLCRadioSelected("No"));
		beneficiaryForm.SelectMLCRadio("Yes");
		Assert.assertTrue(beneficiaryForm.IsMLCRadioSelected("Yes"));
		logger.log(Status.INFO, "Validation of MLC case radio button completed");
		
		logger.log(Status.INFO, "Validating of MLC type dropdown field");
		List<String> expectedListOfMlcTypes = Arrays.asList("MLC Type","Assault And Battery, Including Domestic Violence And Child Abuse","Accidents Like Road Traffic Accidents (Rta), Industrial Accidents Etc.","Cases Of Trauma With Suspicion Of Foul Play","Electrical Injuries","Poisoning, Alcohol Intoxication","Undiagnosed Coma","Chemical Injuries","Burns And Scalds","Sexual Offences","Criminal Abortions","Attempted Suicide","Cases Of Asphyxia As A Result Of Hanging, Strangulation, Drowning","Snake Bite Or Animal Bite","Fire Arm Injuries","Drug Overdose","Drug Abuse");
		Assert.assertEquals(beneficiaryForm.GetMLCTypeSelectedValue().trim(), "MLC Type");
		beneficiaryForm.SelectMLCTypeDropdownTo("Electrical Injuries");
		Assert.assertEquals(beneficiaryForm.GetMLCTypeSelectedValue().trim(), "Electrical Injuries");
		Assert.assertTrue(expectedListOfMlcTypes.contains(beneficiaryForm.GetMLCTypeSelectedValue().trim()));
		beneficiaryForm.SelectMLCTypeDropdownTo("Fire Arm Injuries");
		Assert.assertEquals(beneficiaryForm.GetMLCTypeSelectedValue().trim(), "Fire Arm Injuries");
		Assert.assertTrue(expectedListOfMlcTypes.contains(beneficiaryForm.GetMLCTypeSelectedValue().trim()));
		
		logger.log(Status.INFO, "Validating the option in MLC type dropdown field");
		List<String> allOptionsInDropdown = beneficiaryForm.GetAllMLCTypeDropdownOptions();
		Collections.sort(expectedListOfMlcTypes);
		Collections.sort(allOptionsInDropdown);
		Assert.assertEquals(allOptionsInDropdown, expectedListOfMlcTypes);		
	}
	
	public void ValidatePateintIdentifiedSectionOnBeneficiaryPage() throws Exception	{
		logger.log(Status.INFO, "Landed on MMCSBY Home page and navigating to TMS"); 
		MMCSBY_HomePage homePage = new MMCSBY_HomePage(driver); 
		TMS_PanelAndDashboard tmsPanel = homePage.clickOnTMSLink(); 
		logger.log(Status.INFO, "Creating emergency TID without any details"); 
		BeneficiaryIdentificationForm beneficiaryForm = tmsPanel.clickOnBeneficiaryForm(); 
		Assert.assertTrue(beneficiaryForm.getPageHeader().trim().contains("Beneficiary Identification Form")); 
		beneficiaryForm.SetAdmissionTypeDropdownTo("EMERGENCY");
		Assert.assertEquals(beneficiaryForm.GetAdmissionTypeSelectedValue().trim(), "EMERGENCY");
		Thread.sleep(2000);
		
		Assert.assertTrue(beneficiaryForm.IsIdentifiedSelected("Un-Identified"));
		beneficiaryForm.SelectIdentifiedRadio("Identified");
		Assert.assertTrue(beneficiaryForm.IsIdentifiedSelected("Identified"));
		logger.log(Status.INFO, "Validation of Identified radio button completed");
		
		logger.log(Status.INFO, "Validate patient identified section's fields");
		beneficiaryForm.InputNameOfPatientIdentified("Alphabetic only");
		Assert.assertEquals(beneficiaryForm.GetEnteredNameOfPatientIdentified().trim(), "Alphabetic only");
		beneficiaryForm.InputNameOfPatientIdentified("12345678");
		Assert.assertEquals(beneficiaryForm.GetEnteredNameOfPatientIdentified().trim(), "");
		
		beneficiaryForm.InputRelationWithPatient("Alphabetic only");
		Assert.assertEquals(beneficiaryForm.GetEnteredRelationWithParent().trim(), "Alphabetic only");
		beneficiaryForm.InputRelationWithPatient("12345678");
		Assert.assertEquals(beneficiaryForm.GetEnteredRelationWithParent().trim(), "");
		
		beneficiaryForm.InputContactNumber("1234567890");
		Assert.assertEquals(beneficiaryForm.GetEnteredContactNum().trim(), "1234567890");
		beneficiaryForm.InputContactNumber("Numbers Only");
		Assert.assertEquals(beneficiaryForm.GetEnteredContactNum().trim(), "");
	}
	
	public void ValidateUnidentifiedPatientFieldsOnDetailsTabOfBIFormEmergencyMode() throws Exception	{
		logger.log(Status.INFO, "Landed on MMCSBY Home page and navigating to TMS"); 
		MMCSBY_HomePage homePage = new MMCSBY_HomePage(driver); 
		TMS_PanelAndDashboard tmsPanel = homePage.clickOnTMSLink(); 
		logger.log(Status.INFO, "Creating emergency TID without any details"); 
		BeneficiaryIdentificationForm beneficiaryForm = tmsPanel.clickOnBeneficiaryForm(); 
		Assert.assertTrue(beneficiaryForm.getPageHeader().trim().contains("Beneficiary Identification Form")); 
		beneficiaryForm.SetAdmissionTypeDropdownTo("EMERGENCY");
		Assert.assertEquals(beneficiaryForm.GetAdmissionTypeSelectedValue().trim(), "EMERGENCY");
		Thread.sleep(2000);	
		Assert.assertTrue(beneficiaryForm.IsIdentifiedSelected("Un-Identified"));
		
		logger.log(Status.INFO, "Validate patient details tab fields on page");
		beneficiaryForm.InputPatientName("Alphabetic only");
		Assert.assertEquals(beneficiaryForm.GetEnteredPatientName(), "Alphabetic only");
		beneficiaryForm.InputPatientName("12345678");
		Assert.assertEquals(beneficiaryForm.GetEnteredPatientName().trim(), "");
		
		beneficiaryForm.InputPatientAge("10");
		Assert.assertEquals(beneficiaryForm.GetEnteredPatientAge(), "10");
		beneficiaryForm.InputPatientAge("Alphabets only");
		Assert.assertEquals(beneficiaryForm.GetEnteredPatientAge().trim(), "");
		
		List<String> expectedGenderDropdownOptions = Arrays.asList("Select Gender","Male","Female","Other");
		Assert.assertEquals(beneficiaryForm.GetAllGenderDropdownOptions(), expectedGenderDropdownOptions);
	}
	
	public void ValidateTIDNAdmissionNumCreatedFormatForEmergencyMode() throws Exception	{
		logger.log(Status.INFO, "Landed on MMCSBY Home page and navigating to TMS");
		MMCSBY_HomePage homePage = new MMCSBY_HomePage(driver);
		TMS_PanelAndDashboard tmsPanel = homePage.clickOnTMSLink();
		logger.log(Status.INFO, "Creating emergency TID without any details");
		BeneficiaryIdentificationForm beneficiaryForm = tmsPanel.clickOnBeneficiaryForm();
		Assert.assertTrue(beneficiaryForm.getPageHeader().trim().contains("Beneficiary Identification Form"));
		beneficiaryForm.SetAdmissionTypeDropdownTo("EMERGENCY");
		beneficiaryForm.IsRTAAccidentCase("Yes");
		ScrollDownToGivenPixels(300);
		Thread.sleep(2000);
		beneficiaryForm.ClickOnSubmitBtn();
		Thread.sleep(8000);
		
		List<String> popupMessages = beneficiaryForm.getTIDNumberNClickOk();
		logger.log(Status.INFO, "Created TID: "+popupMessages.get(1));
		Assert.assertEquals(popupMessages.get(0), "Beneficiary saved successfully");
		Assert.assertTrue(Arrays.asList('G','P').contains(popupMessages.get(1).charAt(0)));
		Assert.assertTrue(popupMessages.get(1).length()==14, "TID should be of 14 digit");
		Assert.assertTrue(popupMessages.get(2).length()==15, "Admission number should be of 15 digit");
		Assert.assertTrue(StringUtils.isNumeric(popupMessages.get(2)));
		logger.log(Status.INFO, "Created Admission number "+popupMessages.get(2));
	}
}
