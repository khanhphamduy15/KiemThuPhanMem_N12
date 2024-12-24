package kiemThu;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CartTest {
	private WebDriver driver;

	@BeforeClass
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\ManhDuc\\Desktop\\chromedriver-win64\\chromedriver.exe");
		driver = new ChromeDriver();
	}

	@Test
	public void testAddToCart() throws Exception {
	    // Truy cập trang sản phẩm
	    driver.get("https://nxbkimdong.com.vn/products/cai-tet-cua-meo-con-5");

	    String[] quantities = { "-1", "0", "e5", "1.5", "25" }; // Dữ liệu đầu vào
	    int passCount = 0, failCount = 0;

	    for (String quantity : quantities) {
	        if (quantity != null) {
	            System.out.println("Testing quantity: " + quantity);

	            // Tìm trường input và nhập giá trị
	            WebElement quantityInput = driver.findElement(By.id("Quantity"));
	            quantityInput.clear();
	            quantityInput.sendKeys(quantity);

	            // Bấm nút "Thêm vào giỏ hàng"
	            WebElement addToCartButton = driver.findElement(By.className("btnAddToCart"));
	            addToCartButton.click();

	            // Đợi để kiểm tra popup có xuất hiện không
	            Thread.sleep(2000);
                boolean isPass = false;
	            try {
	                // Kiểm tra xem popup có xuất hiện không
	                WebElement popup = driver.findElement(By.id("modalAddComplete-close"));
	                if (popup.isDisplayed()) {
	                    // Nếu popup xuất hiện, bấm "x" để đóng
	                    popup.click();
	                    System.out.println("Added product to cart successfully: " + quantity);
	                    passCount++;
	                    isPass = true;
	                } else {
	                    // Nếu popup không xuất hiện
	                    System.out.println("Failed to add : " + quantity +" products");
	                }
	            } catch (NoSuchElementException e) {
	                // Nếu popup không tìm thấy
	                System.out.println("Add product to cart failed: " + quantity);
	      
	            }

	            // Đợi một chút trước khi thử với giá trị tiếp theo
	            Thread.sleep(1000);
	            
	        } else {
	            failCount++;
	        }
	    }

	    // Tính tỷ lệ pass/fail
	    float passPercentage = (float) passCount / (passCount + failCount) * 100;
	    float failPercentage = (float) failCount / (passCount + failCount) * 100;

	    System.out.println("Tỷ lệ pass: " + passPercentage + "%");
	    System.out.println("Tỷ lệ fail: " + failPercentage + "%");

	    Thread.sleep(2000); // Đợi thêm trước khi kết thúc
	}




	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}
