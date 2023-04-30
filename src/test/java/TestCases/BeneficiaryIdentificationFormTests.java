package TestCases;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.testng.Assert;
import com.aventstack.extentreports.Status;
import ObjectRepository.BeneficiaryIdentificationForm;
import ObjectRepository.InstantApprovalUploadPopup;
import ObjectRepository.MMCSBY_HomePage;
import ObjectRepository.TMS_PanelAndDashboard;

public class BeneficiaryIdentificationFormTests extends A_BaseSuiteFile{
	
	String dataFilePath = System.getProperty("user.dir")+"\\TestData\\TestData.properties";
	
	public void ValidatePageAndFieldValidationForBeneficiaryForm() throws Exception	{
		logger.log(Status.INFO, "Opening Beneficiary form");
		BeneficiaryIdentificationForm beneficiaryForm = OpenBeneficiaryForm();	
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

	public void ValidateNegativeIDTypeSelectionForBeneficiaryForm() throws Exception	{
		logger.log(Status.INFO, "Opening Beneficiary form");
		BeneficiaryIdentificationForm beneficiaryForm = OpenBeneficiaryForm();
		
		beneficiaryForm.SetIdentityTypeDropdownTo("AADHAR");
		Assert.assertEquals(beneficiaryForm.GetIdentityTypeSelectedValue().trim(), "AADHAR");
		beneficiaryForm.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "JanadharNumber"));
		Thread.sleep(2000);
		beneficiaryForm.ClickOnSearchBeneficiary();
		Thread.sleep(2000);
		Assert.assertEquals(beneficiaryForm.getPopupMessageAndClickOK().trim(), "Please Enter a valid Aadhaar ID (12 Digit)");
		
		logger.log(Status.INFO, "Select House hold  with wrong Unique Id");
		beneficiaryForm.SetIdentityTypeDropdownTo("HOUSE HOLD ID");
		Assert.assertEquals(beneficiaryForm.GetIdentityTypeSelectedValue().trim(), "HOUSE HOLD ID");
		beneficiaryForm.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "JanadharNumber"));
		Thread.sleep(2000);
		beneficiaryForm.ClickOnSearchBeneficiary();
		Thread.sleep(1000);
		Assert.assertEquals(beneficiaryForm.getPopupMessageAndClickOK().trim(), "Please Enter a valid House Hold ID (24 Digit)");
		
		logger.log(Status.INFO, "Select Mobile number with wrong Unique Id");
		beneficiaryForm.SetIdentityTypeDropdownTo("MOBILE NUMBER");
		Assert.assertEquals(beneficiaryForm.GetIdentityTypeSelectedValue().trim(), "MOBILE NUMBER");
		beneficiaryForm.InputUniqueIDNumber("12345");
		Thread.sleep(2000);
		beneficiaryForm.ClickOnSearchBeneficiary();
		Thread.sleep(3000);
		Assert.assertEquals(beneficiaryForm.getPopupMessageAndClickOK().trim(), "Please Enter a valid Mobile Number (10 Digit)");
	}
	
	public void ValidateNegativeUniquieIDTypeInputForBIForm() throws Exception	{
		logger.log(Status.INFO, "Opening Beneficiary form");
		BeneficiaryIdentificationForm beneficiaryForm = OpenBeneficiaryForm();
		Assert.assertEquals(beneficiaryForm.GetIdentityTypeSelectedValue(), "Identity Type");		
		Thread.sleep(2000);
		beneficiaryForm.ClickOnSearchBeneficiary();
		Thread.sleep(2000);
		Assert.assertEquals(beneficiaryForm.getPopupMessageAndClickOK().trim(), "Please select Identity Type");
		
		beneficiaryForm.SetIdentityTypeDropdownTo("JAN AADHAR ID");
		Thread.sleep(2000);
		beneficiaryForm.ClickOnSearchBeneficiary();
		Thread.sleep(2000);
		Assert.assertEquals(beneficiaryForm.getPopupMessageAndClickOK().trim(), "Please Enter Unique Identifier Number");
		
		List<String> categoryList = Arrays.asList("NFSA", "SECC", "CNT", "OTR", "SMF", "COVID");
		logger.log(Status.INFO, "Validating given Jan aadhaar category is present in list");
		beneficiaryForm.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "NFSA_Janaadhaar"));
		beneficiaryForm.ClickOnSearchBeneficiary();
		Thread.sleep(4000);
		Assert.assertTrue(categoryList.contains(beneficiaryForm.getEntitlementTypeValue().trim()));
		
		logger.log(Status.INFO, "Validating given Jan aadhaar category is present in list");
		beneficiaryForm.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "SECC_Janaadhaar"));
		beneficiaryForm.ClickOnSearchBeneficiary();
		Thread.sleep(4000);
		Assert.assertTrue(categoryList.contains(beneficiaryForm.getEntitlementTypeValue().trim()));
	}
	
	public void ValidateIdentityTypeSelectionForBeneficiaryForm() throws Exception	{
		logger.log(Status.INFO, "Landed on MMCSBY Home page and navigating to TMS");
		MMCSBY_HomePage homePage = new MMCSBY_HomePage(driver);
		TMS_PanelAndDashboard tmsPanel = homePage.clickOnTMSLink();
		
		logger.log(Status.INFO, "Select Aadhaar with valid Unique Id");
		BeneficiaryIdentificationForm beneficiaryForm = tmsPanel.clickOnBeneficiaryForm();
		Assert.assertTrue(beneficiaryForm.getPageHeader().trim().contains("Beneficiary Identification Form"));
		
		beneficiaryForm.SetIdentityTypeDropdownTo("AADHAR");
		Assert.assertEquals(beneficiaryForm.GetIdentityTypeSelectedValue().trim(), "AADHAR");
		beneficiaryForm.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "AadhaarNumber"));
		Thread.sleep(2000);
		beneficiaryForm.ClickOnSearchBeneficiary();
		Thread.sleep(2000);
		Assert.assertTrue(beneficiaryForm.IsContactTabVisibe());
		
		logger.log(Status.INFO, "Select Mobile number with valid Unique Id");
		tmsPanel.directClickOnEmergencyCaseConvert();
		Thread.sleep(2000);
		beneficiaryForm = tmsPanel.directClickOnBeneficiaryForm();
		Thread.sleep(2000);
		beneficiaryForm.SetIdentityTypeDropdownTo("MOBILE NUMBER");
		Assert.assertEquals(beneficiaryForm.GetIdentityTypeSelectedValue().trim(), "MOBILE NUMBER");
		beneficiaryForm.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "MobileNumber2"));
		Thread.sleep(2000);
		beneficiaryForm.ClickOnSearchBeneficiary();
		Thread.sleep(2000);
		Assert.assertTrue(beneficiaryForm.IsContactTabVisibe());
		
		logger.log(Status.INFO, "Select House hold  with valid Unique Id");
		tmsPanel.directClickOnEmergencyCaseConvert();
		Thread.sleep(2000);
		beneficiaryForm = tmsPanel.directClickOnBeneficiaryForm();
		Thread.sleep(2000);
		beneficiaryForm.SetIdentityTypeDropdownTo("HOUSE HOLD ID");
		Assert.assertEquals(beneficiaryForm.GetIdentityTypeSelectedValue().trim(), "HOUSE HOLD ID");
		beneficiaryForm.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "HouseHoldId"));
		Thread.sleep(2000);
		beneficiaryForm.ClickOnSearchBeneficiary();
		Thread.sleep(1000);
		Assert.assertTrue(beneficiaryForm.IsContactTabVisibe());
	}
	
	public void FieldValidationOfBeneficiaryFormForNormalAdmissionType() throws Exception	{
		logger.log(Status.INFO, "Opening Beneficiary form");
		BeneficiaryIdentificationForm beneficiaryForm = OpenBeneficiaryForm();
		
		logger.log(Status.INFO, "Validating Admission Type is selected as NORMAL");				
		Assert.assertEquals(beneficiaryForm.GetAdmissionTypeSelectedValue().trim(), "NORMAL");		
		logger.log(Status.INFO, "Validating Do you have Jan Aadhaar ID default value");
		Assert.assertTrue(beneficiaryForm.IsJanadharSelected("Yes"));						
		beneficiaryForm.IsJanadharAvailable("No");
		Assert.assertTrue(beneficiaryForm.IsJanadharSelected("No"));
		beneficiaryForm.IsJanadharAvailable("Yes");
		logger.log(Status.INFO, "Validation of radio button Do you have Jan Aadhaar Id completed");
		
		logger.log(Status.INFO, "Validating user is able to select identity type from the dropdown");
		String[] arr = {"JAN AADHAR ID","JAN AADHAR ACKNOWLEDGEMENT","AADHAR","HOUSE HOLD ID","MOBILE NUMBER"};
		for(String type :arr) {
				beneficiaryForm.SetIdentityTypeDropdownTo(type);
				Assert.assertEquals(beneficiaryForm.GetIdentityTypeSelectedValue().trim(), type);
		}			
		logger.log(Status.INFO, "Validating the placeholder for Unique Identifier Number field");
		Assert.assertEquals(beneficiaryForm.GetUniqueIDNumberPlacholder(),"Unique Identifier Number");
	}
	
	public void ValidateJanAadhaarIDAndAcknowledgementIDTypeSelectionForBeneficiaryForm() throws Exception	{
		logger.log(Status.INFO, "Opening Beneficiary form");
		BeneficiaryIdentificationForm beneficiaryForm = OpenBeneficiaryForm();		
		logger.log(Status.INFO, "Validating Admission Type is selected as NORMAL");				
		Assert.assertEquals(beneficiaryForm.GetAdmissionTypeSelectedValue().trim(), "NORMAL");					
		beneficiaryForm.SetIdentityTypeDropdownTo("JAN AADHAR ID");
		Assert.assertTrue(beneficiaryForm.GetIdentityTypeSelectedValue().trim().equalsIgnoreCase("JAN AADHAR ID"));
		
		logger.log(Status.INFO, "Validating UIN accepts 10 digits for Jan Aadhaar ID");
		beneficiaryForm.InputUniqueIDNumber("1234");
		Thread.sleep(3000);
		beneficiaryForm.ClickOnSearchBeneficiary();
		Thread.sleep(3000);
		Assert.assertEquals(beneficiaryForm.getPopupMessageAndClickOK(), "Please Enter a valid Jan Aadhaar (10 Digit)");
		
		logger.log(Status.INFO, "Validating UIN does not accept invalid Jan Aadhaar ID");
		Thread.sleep(3000);
		beneficiaryForm.InputUniqueIDNumber("0123456789");
		Thread.sleep(1000);
		beneficiaryForm.ClickOnSearchBeneficiary();
		Thread.sleep(1000);
		Assert.assertEquals(beneficiaryForm.getPopupMessageAndClickOK(), "Data Not Found");
		
		logger.log(Status.INFO, "Validating UIN accept valid Jan Aadhaar ID");
		beneficiaryForm.SetIdentityTypeDropdownTo("JAN AADHAR ID");
		String dataFilePath = System.getProperty("user.dir") + "\\TestData\\TestData.properties";
		beneficiaryForm.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "JanadharNumber"));
		Thread.sleep(1000);
		beneficiaryForm.ClickOnSearchBeneficiary();
		Thread.sleep(5000);
		Assert.assertTrue(beneficiaryForm.IsContactTabVisibe());
				
		logger.log(Status.INFO, "Validating user is able to select identity type as Jan Aadhaar Acknowledgement");
		beneficiaryForm.SetIdentityTypeDropdownTo("JAN AADHAR ACKNOWLEDGEMENT");
		Assert.assertTrue(beneficiaryForm.GetIdentityTypeSelectedValue().trim().equalsIgnoreCase("Jan Aadhaar Acknowledgement"));
		
		logger.log(Status.INFO, "Validating UIN accepts 15 didgits for Jan Aadhaar Acknowledgement");
		beneficiaryForm.InputUniqueIDNumber("1234");
		Thread.sleep(1000);
		beneficiaryForm.ClickOnSearchBeneficiary();
		Thread.sleep(1000);
		Assert.assertEquals(beneficiaryForm.getPopupMessageAndClickOK(), "Please Enter a Jan Aadhaar Acknowledgement (15 Digit)");
		
		logger.log(Status.INFO, "Validating UIN accepts valid id for Jan Aadhaar Acknowledgement");
		beneficiaryForm.InputUniqueIDNumber("012345678912345");
		Thread.sleep(1000);
		beneficiaryForm.ClickOnSearchBeneficiary();
		Thread.sleep(1000);
		Assert.assertEquals(beneficiaryForm.getPopupMessageAndClickOK(), "Data Not Found");
		
		logger.log(Status.INFO, "Validating UIN accept valid Jan Aadhaar Acknowledgement");
		beneficiaryForm.SetIdentityTypeDropdownTo("JAN AADHAR ACKNOWLEDGEMENT");
		dataFilePath = System.getProperty("user.dir") + "\\TestData\\TestData.properties";
		beneficiaryForm.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "JanaadharAck"));
		Thread.sleep(1000);
		beneficiaryForm.ClickOnSearchBeneficiary();
		Thread.sleep(5000);	
		Assert.assertTrue(beneficiaryForm.IsContactTabVisibe());
	}
	
	public void ValidateBeneficiaryCategoryOfGivenJanaadhar() throws Exception	{
		logger.log(Status.INFO, "Opening Beneficiary form");
		BeneficiaryIdentificationForm beneficiaryForm = OpenBeneficiaryForm();
		beneficiaryForm.SetIdentityTypeDropdownTo("JAN AADHAR ID");
		
		logger.log(Status.INFO, "Validating NFSA type Jan aadhaar");
		beneficiaryForm.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "NFSA_Janaadhaar"));
		beneficiaryForm.ClickOnSearchBeneficiary();
		Thread.sleep(6000);
		Assert.assertEquals(beneficiaryForm.getEntitlementTypeValue(), "NFSA");
		
		logger.log(Status.INFO, "Validating SECC type Jan aadhaar");
		beneficiaryForm.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "SECC_Janaadhaar"));
		Thread.sleep(2000);
		beneficiaryForm.ClickOnSearchBeneficiary();
		Thread.sleep(8000);
		Assert.assertEquals(beneficiaryForm.getEntitlementTypeValue(), "SECC");
		
		logger.log(Status.INFO, "Validating SMF type Jan aadhaar");
		beneficiaryForm.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "SMF_Janaadhaar"));
		Thread.sleep(2000);
		beneficiaryForm.ClickOnSearchBeneficiary();
		Thread.sleep(8000);
		Assert.assertEquals(beneficiaryForm.getEntitlementTypeValue(), "SMF");
		
		logger.log(Status.INFO, "Validating Covid type Jan aadhaar");
		beneficiaryForm.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "Covid_Janaadhaar"));
		Thread.sleep(2000);
		beneficiaryForm.ClickOnSearchBeneficiary();
		Thread.sleep(8000);
		Assert.assertEquals(beneficiaryForm.getEntitlementTypeValue(), "CVD");
		
		logger.log(Status.INFO, "Validating Contractual type Jan aadhaar");
		beneficiaryForm.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "Cnt_Janaadhaar"));
		Thread.sleep(2000);
		beneficiaryForm.ClickOnSearchBeneficiary();
		Thread.sleep(8000);
		Assert.assertEquals(beneficiaryForm.getEntitlementTypeValue(), "CNT");
		
		logger.log(Status.INFO, "Validating Paid type Jan aadhaar");
		beneficiaryForm.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "Paid_Janaadhaar"));
		Thread.sleep(2000);
		beneficiaryForm.ClickOnSearchBeneficiary();
		Thread.sleep(8000);
		Assert.assertEquals(beneficiaryForm.getEntitlementTypeValue(), "OTR");
	}
		
	public void ValidateSelectedMemberDetailsFromGivenMembersList() throws Exception	{
		logger.log(Status.INFO, "Opening Beneficiary form");
		BeneficiaryIdentificationForm beneficiaryForm = OpenBeneficiaryForm();	
		beneficiaryForm.SetIdentityTypeDropdownTo("JAN AADHAR ID");
		beneficiaryForm.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "JanadharNumber"));
		beneficiaryForm.ClickOnSearchBeneficiary();
		Thread.sleep(6000);
		ScrollDownToGivenPixels(600);
		
		logger.log(Status.INFO, "Uploading & selecting the patient details from given Janadhaar card");
		beneficiaryForm.ClickOnMOIC();
		Thread.sleep(1000);
		String MOICFilePath = System.getProperty("user.dir") + "\\TestData\\SampleFile.pdf";
		beneficiaryForm.UploadMOICDocument(MOICFilePath);
		Thread.sleep(2000);
		ScrollDownToGivenPixels(400);
		
		logger.log(Status.INFO, "Getting details of selected member");
		String PatientName = GetPropertyFileValue(dataFilePath, "PatientName");
		String MobileNo = GetPropertyFileValue(dataFilePath, "MobileNumber");
		beneficiaryForm.SelectedMemberInTableIs(PatientName);
		List<String> memberDetails = beneficiaryForm.getDetailsOfSelectedMember(PatientName);
		logger.log(Status.INFO, "Age: "+memberDetails.get(0)+", Gender :"+memberDetails.get(1)+", Aadhaar :"+memberDetails.get(2));
		
		logger.log(Status.INFO, "Uploading photo & submitting the form without mobile number");
		ScrollDownToGivenPixels(500);
		String photoFilePath = System.getProperty("user.dir") + "\\TestData\\SamplePhoto.jpg";
		beneficiaryForm.UploadPatientPhoto(photoFilePath);
		Thread.sleep(1000);
		beneficiaryForm.ClickOnSubmitBtn();
		Thread.sleep(6000);		
		Assert.assertEquals(beneficiaryForm.getPopupMessageAndClickOK(), "Please enter Mobile no. for SMS Alert");
		
		logger.log(Status.INFO, "Submitting the form with <10 digit mobile number");
		Thread.sleep(2000);
		beneficiaryForm.InputMobileNumForSelectedUser("123");
		Thread.sleep(1000);
		beneficiaryForm.ClickOnSubmitBtn();
		Thread.sleep(3000);	
		Assert.assertEquals(beneficiaryForm.getPopupMessageAndClickOK(), "Please enter valid (10 digit) mobile no.");		
	}
	
	public void ValidateInstantModePopupFieldsForDisenrolledJanaadharOnBIForm() throws Exception {
		logger.log(Status.INFO, "Opening Beneficiary form");
		BeneficiaryIdentificationForm beneficiaryForm = OpenBeneficiaryForm();	
		beneficiaryForm.SetAdmissionTypeDropdownTo("NORMAL");
		beneficiaryForm.IsJanadharAvailable("Yes");
		
		logger.log(Status.INFO, "Search disenrolled janaadhaar to open instant mode popup");
		beneficiaryForm.SetIdentityTypeDropdownTo("JAN AADHAR ID");
		beneficiaryForm.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "Disenrolled_Janaadhaar"));
		Thread.sleep(2000);
		InstantApprovalUploadPopup instantModePopup = beneficiaryForm.ClickOnSearchForInsantMode();
		Thread.sleep(6000);
		logger.log(Status.INFO, "Validate all fields present on popup");
		Assert.assertTrue(instantModePopup.IsUploadOptionVisible());
		Assert.assertTrue(instantModePopup.IsUploadBtnVisible());
		Assert.assertTrue(instantModePopup.IsCancelBtnVisible());
		Assert.assertTrue(instantModePopup.IsContinueBtnVisible());
		String errorMessage = "Citizen is not enrolled with Chiranjeevi Yojana. In case of Emergency, a approval request form document can be uploaded and after approval from concerned District Collector or Govt. Hospital Nodal (in case of Govt. hospital) , citizen can avail benefits. To continue, Upload document, submit and then fill other details to create TID OR Press cancel to cancel creating TID in instant mode.";
		Assert.assertEquals(instantModePopup.getPopupMessage(),errorMessage);
		
		logger.log(Status.INFO, "Click cancel to close the search");
		beneficiaryForm = instantModePopup.clickOnCancelBtn();
		Thread.sleep(3000);		
		Assert.assertEquals(beneficiaryForm.getPageHeader(), "Beneficiary Identification Form");
		Assert.assertFalse(beneficiaryForm.IsContactTabVisibe());
	}
	
	public void ValidateInstantModePopupUploadForDisenrolledJanaadharOnBIForm() throws Exception {
		logger.log(Status.INFO, "Opening Beneficiary form");
		BeneficiaryIdentificationForm beneficiaryForm = OpenBeneficiaryForm();		
		beneficiaryForm.SetAdmissionTypeDropdownTo("NORMAL");
		beneficiaryForm.IsJanadharAvailable("Yes");
		
		logger.log(Status.INFO, "Search disenrolled janaadhaar to open instant mode popup");
		beneficiaryForm.SetIdentityTypeDropdownTo("JAN AADHAR ID");
		beneficiaryForm.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "Disenrolled_Janaadhaar"));
		Thread.sleep(2000);
		InstantApprovalUploadPopup instantModePopup = beneficiaryForm.ClickOnSearchForInsantMode();
		Thread.sleep(6000);
		logger.log(Status.INFO, "Upload instant approval document to continue");
		String InstantApprovalDoc= System.getProperty("user.dir") + "\\TestData\\SampleFile.pdf";
		instantModePopup.UploadInstantApprovalDocument(InstantApprovalDoc);
		Thread.sleep(2000);
		beneficiaryForm = instantModePopup.clickOnContinueBtn();
		Thread.sleep(3000);
		logger.log(Status.INFO, "Validating the details retreived after submission");
		Assert.assertTrue(beneficiaryForm.IsContactTabVisibe());		
		List<String> EntitlementList = Arrays.asList("OTR","Others");
		Assert.assertTrue(EntitlementList.contains(beneficiaryForm.getEntitlementTypeValue()));														
	}

	public void ValidateNegativeScenariosForUploadFucntionalityOnBIForm () throws Exception {
		logger.log(Status.INFO, "Opening Beneficiary form");
		BeneficiaryIdentificationForm beneficiaryForm = OpenBeneficiaryForm();				
		beneficiaryForm.SetAdmissionTypeDropdownTo("NORMAL");
		beneficiaryForm.IsJanadharAvailable("Yes");
		beneficiaryForm.SetIdentityTypeDropdownTo("JAN AADHAR ID");
		beneficiaryForm.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "JanadharNumber"));
		beneficiaryForm.ClickOnSearchBeneficiary();
		Thread.sleep(6000);
		ScrollDownToGivenPixels(600);
		
		logger.log(Status.INFO, "Selecting the patient details from given Janadhaar card");
		beneficiaryForm.ClickOnMOIC();
		Thread.sleep(1000);
		ScrollDownToGivenPixels(400);
		String PatientName = GetPropertyFileValue(dataFilePath, "PatientName");
		String MobileNo = GetPropertyFileValue(dataFilePath, "MobileNumber");
		beneficiaryForm.SelectedMemberInTableIs(PatientName);
		beneficiaryForm.InputMobileNumForSelectedUser(MobileNo);		
		ScrollDownToGivenPixels(500);
		beneficiaryForm.ClickOnSubmitBtn();
		Assert.assertEquals(beneficiaryForm.getPopupMessageAndClickOK(), "Please verify member.");
		Thread.sleep(2000);
		
		ScrollUpToGivenPixels(600);
		logger.log(Status.INFO, "Uploading & selecting the patient details from given Janadhaar card");
		beneficiaryForm.ClickOnMOIC();
		Thread.sleep(2000);
		String MOICFilePath = System.getProperty("user.dir") + "\\TestData\\SampleFile.pdf";
		beneficiaryForm.UploadMOICDocument(MOICFilePath);
		Thread.sleep(2000);
		ScrollDownToGivenPixels(900);
		beneficiaryForm.ClickOnSubmitBtn();
		Thread.sleep(2000);
		Assert.assertEquals(beneficiaryForm.getPopupMessageAndClickOK(), "Select Patient photo");
	}

	public List<String> ValidateNormalTIDCreationFromBeneficiaryForm() throws Exception {
		logger.log(Status.INFO, "Opening Beneficiary form");
		BeneficiaryIdentificationForm beneficiaryForm = OpenBeneficiaryForm();		
		beneficiaryForm.SetAdmissionTypeDropdownTo("NORMAL");
		beneficiaryForm.IsRTAAccidentCase("No");
		beneficiaryForm.IsJanadharAvailable("Yes");
		beneficiaryForm.SetIdentityTypeDropdownTo("JAN AADHAR ID");
		beneficiaryForm.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "JanadharNumber"));
		beneficiaryForm.ClickOnSearchBeneficiary();
		Thread.sleep(6000);
		ScrollDownToGivenPixels(600);
		
		logger.log(Status.INFO, "Uploading & selecting the patient details from given Janadhaar card");
		beneficiaryForm.ClickOnMOIC();
		Thread.sleep(2000);
		String MOICFilePath = System.getProperty("user.dir") + "\\TestData\\SampleFile.pdf";
		beneficiaryForm.UploadMOICDocument(MOICFilePath);
		Thread.sleep(10000);
		ScrollDownToGivenPixels(400);
		String PatientName = GetPropertyFileValue(dataFilePath, "PatientName");
		String MobileNo = GetPropertyFileValue(dataFilePath, "MobileNumber");
		beneficiaryForm.SelectedMemberInTableIs(PatientName);
		beneficiaryForm.InputMobileNumForSelectedUser(MobileNo);
		
		logger.log(Status.INFO, "Uploading photo & submitting the form");
		ScrollDownToGivenPixels(500);
		String photoFilePath = System.getProperty("user.dir") + "\\TestData\\SamplePhoto.jpg";
		beneficiaryForm.UploadPatientPhoto(photoFilePath);
		Thread.sleep(6000);
		beneficiaryForm.ClickOnSubmitBtn();
		Thread.sleep(6000);
		List<String> DataGenerated = beneficiaryForm.getTIDNumberNClickOk();
		logger.log(Status.INFO, "Created TID: "+DataGenerated.get(1));
		return DataGenerated;
	}

	public void ValidateNormalTIDCreateUsingLivePatientPhoto() throws Exception	{
		logger.log(Status.INFO, "Opening Beneficiary form");
		BeneficiaryIdentificationForm beneficiaryForm = OpenBeneficiaryForm();
		
		beneficiaryForm.SetAdmissionTypeDropdownTo("NORMAL");
		beneficiaryForm.IsRTAAccidentCase("No");
		beneficiaryForm.IsJanadharAvailable("Yes");
		beneficiaryForm.SetIdentityTypeDropdownTo("JAN AADHAR ID");
		beneficiaryForm.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "JanadharNumber"));
		beneficiaryForm.ClickOnSearchBeneficiary();
		Thread.sleep(6000);
		ScrollDownToGivenPixels(600);
		
		logger.log(Status.INFO, "Uploading & selecting the patient details from given Janadhaar card");
		beneficiaryForm.ClickOnMOIC();
		Thread.sleep(2000);
		String MOICFilePath = System.getProperty("user.dir") + "\\TestData\\SampleFile.pdf";
		beneficiaryForm.UploadMOICDocument(MOICFilePath);
		Thread.sleep(10000);
		ScrollDownToGivenPixels(400);
		String PatientName = GetPropertyFileValue(dataFilePath, "PatientName");
		String MobileNo = GetPropertyFileValue(dataFilePath, "MobileNumber");
		beneficiaryForm.SelectedMemberInTableIs(PatientName);
		beneficiaryForm.InputMobileNumForSelectedUser(MobileNo);
		
		logger.log(Status.INFO, "Uploading photo & submitting the form");
		ScrollDownToGivenPixels(500);
		beneficiaryForm.captureLivePhotoAndUpload();		
		Thread.sleep(2000);
		beneficiaryForm.ClickOnSubmitBtn();
		Thread.sleep(8000);
		List<String> DataGenerated = beneficiaryForm.getTIDNumberNClickOk();
		logger.log(Status.INFO, "Created TID: "+DataGenerated.get(1));
	}
	
	public void ValidateChangingSelectedMemberAfterUploadsOnBIForm() throws Exception	{
		logger.log(Status.INFO, "Opening Beneficiary form");
		BeneficiaryIdentificationForm beneficiaryForm = OpenBeneficiaryForm();
		
		beneficiaryForm.SetAdmissionTypeDropdownTo("NORMAL");
		beneficiaryForm.IsRTAAccidentCase("No");
		beneficiaryForm.IsJanadharAvailable("Yes");
		beneficiaryForm.SetIdentityTypeDropdownTo("JAN AADHAR ID");
		beneficiaryForm.InputUniqueIDNumber(GetPropertyFileValue(dataFilePath, "JanadharNumber"));
		beneficiaryForm.ClickOnSearchBeneficiary();
		Thread.sleep(6000);
		ScrollDownToGivenPixels(600);
		
		logger.log(Status.INFO, "Uploading & selecting the patient details from given Janadhaar card");
		beneficiaryForm.ClickOnMOIC();
		Thread.sleep(2000);
		String MOICFilePath = System.getProperty("user.dir") + "\\TestData\\SampleFile.pdf";
		beneficiaryForm.UploadMOICDocument(MOICFilePath);
		Thread.sleep(12000);
		ScrollDownToGivenPixels(400);
		String PatientName = GetPropertyFileValue(dataFilePath, "PatientName");
		String MobileNo = GetPropertyFileValue(dataFilePath, "MobileNumber");
		beneficiaryForm.SelectedMemberInTableIs(PatientName);
		beneficiaryForm.InputMobileNumForSelectedUser(MobileNo);
		
		logger.log(Status.INFO, "Uploading photo & submitting the form");
		ScrollDownToGivenPixels(500);
		beneficiaryForm.captureLivePhotoAndUpload();		
		Thread.sleep(2000);
		ScrollUpToGivenPixels(400);
		beneficiaryForm.UnSelectedMemberInTableIs(PatientName);
		beneficiaryForm.SelectedMemberInTableIs(GetPropertyFileValue(dataFilePath, "PatientName2"));
		ScrollDownToGivenPixels(400);
		beneficiaryForm.ClickOnSubmitBtn();
		Thread.sleep(3000);
		Assert.assertEquals(beneficiaryForm.getPopupMessageAndClickOK(), "Please verify member.");
	}

	public void ValidateNormalTIDNAdmissionFormatOnSuccessPopup() throws Exception	{
		List<String> popupMessages = ValidateNormalTIDCreationFromBeneficiaryForm();
		Assert.assertEquals(popupMessages.get(0), "Beneficiary saved successfully");
		Assert.assertTrue(Arrays.asList('G','P').contains(popupMessages.get(1).charAt(0)));
		Assert.assertTrue(popupMessages.get(1).length()==14, "TID should be of 14 digit");
		Assert.assertTrue(popupMessages.get(2).length()==15, "Admission number should be of 15 digit");
		logger.log(Status.INFO, "Created Admission number "+popupMessages.get(2));
	}
	
	public BeneficiaryIdentificationForm OpenBeneficiaryForm() throws Exception	{
		logger.log(Status.INFO, "Landed on MMCSBY Home page and navigating to TMS");
		MMCSBY_HomePage homePage = new MMCSBY_HomePage(driver);
		TMS_PanelAndDashboard tmsPanel = homePage.clickOnTMSLink();
		
		logger.log(Status.INFO, "Select Aadhaar with valid Unique Id");
		BeneficiaryIdentificationForm beneficiaryForm = tmsPanel.clickOnBeneficiaryForm();
		Assert.assertTrue(beneficiaryForm.getPageHeader().trim().contains("Beneficiary Identification Form"));
		return beneficiaryForm;
	}
}
