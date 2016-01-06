package pku.java.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class LoginSina {
	
	// Mac
	private final static String DRIVER_PATH  = "lib/chromedriver";
	
	// Windows
//	private final static String DRIVER_PATH_WIN = "lib/chromedriver.exe";
	
	// 登录新浪的URL
	private final static String LOGIN_URL = "http://login.sina.com.cn/sso/login.php";

	private static String username;
	private static String password;
	
	public static void setUsername(String username) {
		LoginSina.username = username;
	}

	public static void setPassword(String password) {
		LoginSina.password = password;
	}
	
	public static WebDriver login() {
		// 如果使用Windows系统，用DRIVER_PATH_WIN路径
		System.getProperties().setProperty("webdriver.chrome.driver", DRIVER_PATH);
		
		WebDriver webDriver = new ChromeDriver();
		webDriver.get(LOGIN_URL);
		
		webDriver.findElement(By.cssSelector("input[name=\"username\"]")).clear();
		webDriver.findElement(By.cssSelector(
				"input[name=\"username\"]")).sendKeys(username);
		webDriver.findElement(By.cssSelector("input[name=\"password\"]")).clear();
		webDriver.findElement(By.cssSelector(
				"input[name=\"password\"]")).sendKeys(password);
		
		WebElement loginBtn = webDriver.findElement(
				By.xpath(".//*[@id='vForm']/div[3]/ul/li[6]/div[2]/input"));
		loginBtn.click();
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		WebElement element = webDriver.findElement(By.xpath("/html"));
//		System.out.println(element.getAttribute("outerHTML"));		
		return webDriver;
//		writeFile(element.getAttribute("outerHTML"));
	}

	public static void writeFile(String str) {
		File file = new File("/Users/tony/Desktop/weibo.html");
		BufferedWriter bw = null;
		
		try {
			bw = new BufferedWriter(new FileWriter(file));
			bw.write(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}	
}
