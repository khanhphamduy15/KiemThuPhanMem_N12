package test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SearchTest {
    private WebDriver driver;
    private int passCount = 0; // Số bài kiểm thử thành công
    private int failCount = 0; // Số bài kiểm thử thất bại

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", 
        		"D:\\trannam\\Kiemthu\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @DataProvider(name = "searchData")
    public Object[][] searchData() {
        return new Object[][] {
            { "thám tử lừng danh conan", true },       // Có sản phẩm
            { "conan", true }, // Có sản phẩm
            { "   conan", true }, // Có sản phẩm
            { "conan?????", true }, // Có sản phẩm
            { "unknownbook", false }, // Có sản phẩm
            { "", false } // Không có sản phẩm
            
        };
    }

    @Test(dataProvider = "searchData")
    public void testSearch(String keyword, boolean hasResults) {
        try {
            driver.get("https://nxbkimdong.com.vn");

            WebElement searchBox = driver.findElement(By.id("searchtext"));
            searchBox.clear();
            //searchBox.click();
            searchBox.sendKeys(keyword);

            driver.findElement(By.id("searchsubmit")).click();
            Thread.sleep(2000);

            if (!hasResults) {
                // Kiểm tra hiển thị thông báo "Không tìm thấy sản phẩm"
                WebElement Result = driver.findElement(By.cssSelector(".search-title>h3"));
                Assert.assertTrue(Result.getText().equals("KHÔNG TÌM THẤY KẾT QUẢ PHÙ HỢP CHO"));
            } else {
                // Kiểm tra có ít nhất một sản phẩm hiển thị
                boolean isProductDisplayed = driver.findElements(By.className("product-item")).size() > 1;
                Assert.assertTrue(isProductDisplayed, 
                    " TÌM THẤY KẾT QUẢ TÌM KIẾM PHÙ HỢP CHO: " + keyword);
            }
            passCount++; // Nếu không có lỗi, bài kiểm thử thành công
        } catch (AssertionError | Exception e) {
            failCount++; // Nếu có lỗi hoặc thất bại
            System.err.println("Test failed for keyword: " + keyword);
            e.printStackTrace();
        }
    }

    @AfterClass
    public void tearDown() {
        // Tính toán và hiển thị tỉ lệ
        int totalTests = passCount + failCount;
        float passPercentage = (float) passCount / totalTests * 100;
        float failPercentage = (float) failCount / totalTests * 100;

        System.out.println("Kết quả kiểm thử:");
        System.out.println("Số bài kiểm thử thành công (Pass): " + passCount);
        System.out.println("Số bài kiểm thử thất bại (Fail): " + failCount);
        System.out.println("Tỉ lệ Pass: " + passPercentage + "%");
        System.out.println("Tỉ lệ Fail: " + failPercentage + "%");

        driver.quit(); // Đóng trình duyệt
    }
}
