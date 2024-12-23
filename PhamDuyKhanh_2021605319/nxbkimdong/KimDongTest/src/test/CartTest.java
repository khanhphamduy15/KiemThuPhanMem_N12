package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CartTest {
	private WebDriver driver;

	@BeforeClass
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "F:\\ky7\\KiemThuPM\\BTL\\chromedriver-win64\\chromedriver.exe");
		driver = new ChromeDriver();
	}

	@Test
	public void testAddToCart() {
		driver.get("https://nxbkimdong.com.vn/products/cai-tet-cua-meo-con-5");

		driver.findElement(By.className("btnAddToCart")).click();
		try {
			Thread.sleep(2000); 
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateCart() throws Exception {
		String[] quantities = { "2", "-7", "e5", "9999999999", "0" }; 
		driver.get("https://nxbkimdong.com.vn/cart");

		int passCount = 0, failCount = 0;

		for (int i = 0; i < quantities.length; i++) {
			String quantity = quantities[i]; 

			if (quantity != null) { 
				System.out.println("Quantity: " + quantity);

				driver.findElement(By.name("updates[]")).clear();
				driver.findElement(By.name("updates[]")).sendKeys(quantity); 
				driver.findElement(By.name("update")).click();
				Thread.sleep(2000); 
					passCount++;
			} else {
				failCount++; 
			}
		}

		float passPercentage = (float) passCount / (passCount + failCount) * 100;
		float failPercentage = (float) failCount / (passCount + failCount) * 100;
		System.out.println("Tỷ lệ pass: " + passPercentage + "%");
		System.out.println("Tỷ lệ fail: " + failPercentage + "%");
	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}
