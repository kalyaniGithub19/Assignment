package assignments;

import java.util.Scanner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.annotations.Test;

public class GmailOperations 
{	
	public static WebDriver webdriver;
	public static Scanner scanner;
	public static String id="";
	public static String password="";
	public static String to="";
	public static String subject="";
	public static String emailContent="";
	public static WebElement element1;
	public static WebDriverWait wait; 
	
	@BeforeSuite
	public static void variableDeclaration()
	{
		scanner = new Scanner(System.in);
		System.out.println("Enter your full email id: ");
		id = scanner.nextLine();
		System.out.println("Enter your password: ");
		password = scanner.nextLine();
		System.out.println("Enter email id to whom you would like to send an email: ");
		to = scanner.nextLine();
		System.out.println("Enter Subject: ");
		subject = scanner.nextLine();
		System.out.println("Enter email content: ");
		emailContent = scanner.nextLine();	
		
	}
	
	@BeforeMethod
	public static void driverInitialization()
	{
		//Set Gecko driver and create webdriver object
		System.setProperty("webdriver.gecko.driver", "D:/Kalyani/geckodriver-v0.13.0-win64/geckodriver.exe");
		webdriver = new FirefoxDriver();
	}
	
	@AfterMethod
	public static void release()
	{
		//webdriver.close();
		webdriver.quit();
	}
	
  @Test
  public static void sendEmail() throws InterruptedException 
  {
		
		webdriver.get("http://www.gmail.com");
		Thread.sleep(10000);
		//webdriver.manage().window().maximize();
		
		//enter credentials and login
		webdriver.findElement(By.id("Email")).sendKeys(id);
		webdriver.findElement(By.id("next")).click();
		wait = new WebDriverWait(webdriver, 10);
		element1 = wait.until(
		    ExpectedConditions.visibilityOfElementLocated(By.id("Passwd")));
		webdriver.findElement(By.id("Passwd")).sendKeys(password);
		webdriver.findElement(By.xpath("//input[@type='checkbox']")).click();
		webdriver.findElement(By.id("signIn")).click();
		
		//Compose email
		Thread.sleep(7000);
		element1 = wait.until(
			ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='COMPOSE']")));	
		
		webdriver.findElement(By.xpath("//div[text()='COMPOSE']")).click();
		Thread.sleep(5000);
		webdriver.findElement(By.xpath("//textarea[@aria-label='To']")).click();
		webdriver.findElement(By.xpath("//textarea[@aria-label='To']")).sendKeys(to);
		webdriver.findElement(By.xpath("//input[@name='subjectbox']")).click();
		webdriver.findElement(By.xpath("//input[@name='subjectbox']")).sendKeys(subject);
		Thread.sleep(3000);
		webdriver.findElement(By.xpath("//div[@aria-label='Message Body']")).click();
		//webdriver.switchTo().frame(webdriver.findElement(By.xpath("//iframe[@tabindex='1']")));
		webdriver.findElement(By.xpath("//div[@aria-label='Message Body']")).sendKeys(emailContent);
		webdriver.switchTo().defaultContent(); // again switch back to main page
		webdriver.findElement(By.xpath("//div[text()='Send']")).click();
		
		//Verify Email sent successfully, Label Functionality is varified too.
		Thread.sleep(3000);
		String msg = webdriver.findElement(By.xpath("//div[contains(text(),'Your message has been sent.')]")).getText();
		System.out.println(msg);
		Thread.sleep(3000);
		String tempMsg = "Your message has been sent. View message";
		if(msg.contains(tempMsg))
		{
			System.out.println("Email Sent Successfully!");
		}
		else
		{
			System.out.println("Sending Failed!");
		}
		Thread.sleep(3000);		
		
		//logout and close
		webdriver.findElement(By.xpath("//span[@class='gb_8a gbii']")).click();
		webdriver.findElement(By.id("gb_71")).click();
		
  }
  
  @Test
  public static void selfEmail() throws InterruptedException
  {
	  
		webdriver.get("http://www.gmail.com");
		Thread.sleep(5000);
		//webdriver.manage().window().maximize();
		String parentWindow=webdriver.getWindowHandle();
		
		//enter credentials and login
		webdriver.findElement(By.id("Email")).sendKeys(id);
		webdriver.findElement(By.id("next")).click();
		wait = new WebDriverWait(webdriver, 10);
		element1 = wait.until(
		    ExpectedConditions.visibilityOfElementLocated(By.id("Passwd")));
		webdriver.findElement(By.id("Passwd")).sendKeys(password);
		webdriver.findElement(By.xpath("//input[@type='checkbox']")).click();
		webdriver.findElement(By.id("signIn")).click();
		
		//Compose email to self
		Thread.sleep(7000);
		element1 = wait.until(
			ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[text()='COMPOSE']")));	
		
		webdriver.findElement(By.xpath("//div[text()='COMPOSE']")).click();
		Thread.sleep(10000);
		webdriver.findElement(By.xpath("//textarea[@aria-label='To']")).click();
		
		//To id meaning email to self
		webdriver.findElement(By.xpath("//textarea[@aria-label='To']")).sendKeys(id); 
		
		webdriver.findElement(By.xpath("//input[@name='subjectbox']")).click();
		webdriver.findElement(By.xpath("//input[@name='subjectbox']")).sendKeys(subject);
		Thread.sleep(3000);
		webdriver.findElement(By.xpath("//div[@aria-label='Message Body']")).click();
		//webdriver.switchTo().frame(webdriver.findElement(By.xpath("//iframe[@tabindex='1']")));
		webdriver.findElement(By.xpath("//div[@aria-label='Message Body']")).sendKeys(emailContent);
		webdriver.switchTo().defaultContent(); // again switch back to main page
		webdriver.findElement(By.xpath("//div[text()='Send']")).click();
		
		webdriver.navigate().refresh();
		webdriver.switchTo().alert().accept();
		webdriver.switchTo().window(parentWindow);
		webdriver.findElement(By.id("gbqfb"));
		//retrieve the subject line and verify it is incorrect
		String tempSubject = webdriver.findElement(By.xpath("//div[contains(text(),'"+emailContent+"')]")).getText();
		if(subject.contains(tempSubject))
		{
			System.out.println("Received email is invalid");
		}
		else
		{
			System.out.println("Received email is valid");
		}
		
		//logout and close
		webdriver.findElement(By.xpath("//span[@class='gb_8a gbii']")).click();
		webdriver.findElement(By.id("gb_71")).click();

  }
  
  @Test
  public static void labelFunctionality() throws InterruptedException
  {
		webdriver.get("http://www.gmail.com");
		Thread.sleep(5000);

		String tempText = webdriver.findElement(By.xpath("//div[@class='banner']/h1")).getText();
		Thread.sleep(5000);
		if(tempText.equals("One account. All of Google."))
		{
			System.out.println("Label Verified");
		}
		else
		{
			System.out.println("Label not Verified");
		}
  }
  
}
