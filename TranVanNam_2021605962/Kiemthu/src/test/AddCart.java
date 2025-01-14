package test;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class AddCart {
	private WebDriver driver;

	@BeforeClass
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "D:\\trannam\\Kiemthu\\chromedriver-win64\\chromedriver.exe");
		driver = new ChromeDriver();
	}

	@Test
	public void testAddToCart() {
		driver.get("https://nxbkimdong.com.vn/products/cai-tet-cua-meo-con-5");

		// Nhấn nút "Thêm vào giỏ hàng"
		driver.findElement(By.className("btnAddToCart")).click();
		try {
			Thread.sleep(2000); // Chờ 10 giây
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testUpdateCart() throws Exception {
		String[] quantities = { "2", "-7", "e5", "9999999999", "0" }; // Dữ liệu đầu vào đã chuẩn bị sẵn
		driver.get("https://nxbkimdong.com.vn/cart");

		int passCount = 0, failCount = 0;

		// Sử dụng dữ liệu từ cột Quantity trong bài test
		for (int i = 0; i < quantities.length; i++) {
			String quantity = quantities[i]; // Lấy giá trị quantity từ mảng

			if (quantity != null) { // Kiểm tra nếu quantity không null và không rỗng
				System.out.println("Quantity: " + quantity);

				// Thực hiện các kiểm tra hoặc thao tác cần thiết với quantity
				driver.findElement(By.name("updates[]")).clear();
				driver.findElement(By.name("updates[]")).sendKeys(quantity); // Ghi giá trị quantity vào ô
				driver.findElement(By.name("update")).click();
				Thread.sleep(2000); // Đợi để đảm bảo trang đã tải xong

					passCount++;
			} else {
				failCount++; // Nếu quantity null, tăng số lần fail
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

