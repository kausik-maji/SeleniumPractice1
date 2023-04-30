package ObjectRepository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import LibraryFiles.ReusableLibraryFile;

public class PatientAdmissionForm extends ReusableLibraryFile{

	By pageHeader = By.xpath("//div[@class='container-fluid']/h5");
	By TIDNumber = By.id("txtuid");
	By SubmitBtn = By.xpath("//button[contains(text(),' Submit')]");
	By SearchBtn = By.xpath("//button[contains(text(),' Search')]");
	
	/*4.Blood Group----------------> By.id("ddABloodGroup")
	5.Allergy--------------------> By.xpath(//input[@placeholder='Allergy'])
	6.Doctor Name----------------> By.id("ddlAdmDoctor")
	7.Initial Assessment---------> By.xpath(//input[@placeholder='Intial Assessment'])
	8.Medico Legal Case Yes------> By.id("flexRadioDefault3")
	9.Medico Legal Case No-------> By.id("flexRadioDefault4")
	10.Remarks-------------------> By.xpath(//textarea[@class='form-control'])
	11.Blood Pressure------------> By.xpath(//input[@placeholder="Blood Pressure"])
	12.Procedure Consent Yes-----> By.id("flexRadioDefault1")
	13.Procedure Consent No------> By.id("flexRadioDefault2")
	14.Hospital Grade------------> By.id("ddhospitalGrade")
	15.Submit 2------------------> By.xpath(//button[text()='Submit'])
	*16.Details of Patients------> By.xpath(//button[contains(text(),' Detail of Patient ')])
	*17.Admssion Details---------> By.xpath(//button[contains(text(),' Admission Details ')])
	*18.Admssion Type------------> By.id("ddAdmissionType")*/
	
	public PatientAdmissionForm(WebDriver driver) {
		this.driver = driver;
	}
	
	public void InputTIDNumber(String value)	{
		driver.findElement(TIDNumber).sendKeys(value);
	}
	
	public void ClickOnSearchBtn()	{
		driver.findElement(SearchBtn).click();
	}
	
	public void ClickOnSubmitBtn()	{
		driver.findElement(SubmitBtn).click();
	}
}
