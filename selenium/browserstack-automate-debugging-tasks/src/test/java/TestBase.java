import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;


public class TestBase {

    public static WebDriver driver;
    public String username = System.getenv("BROWSERSTACK_USERNAME");
    public String accesskey = System.getenv("BROWSERSTACK_ACCESS_KEY");
    @BeforeMethod
    public void setup(@Optional String browser, Method method) throws MalformedURLException {

        MutableCapabilities capabilities = new MutableCapabilities();
        capabilities.setCapability("browserName", "Chrome");
        HashMap<String, Object> browserstackOptions = new HashMap<String, Object>();
        browserstackOptions.put("os", "Windows");
        browserstackOptions.put("osVersion", "11");
        browserstackOptions.put("browserVersion", "latest");
        browserstackOptions.put("buildName", "Debugging assignments");
        browserstackOptions.put("sessionName", method.getName());
        browserstackOptions.put("networkLogs", "true");

        capabilities.setCapability("bstack:options", browserstackOptions);

        driver = new RemoteWebDriver(new URL("https://"+username+":"+accesskey+"@hub-cloud.browserstack.com/wd/hub"),capabilities);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
    }

    @AfterMethod
    public void teardown(ITestResult result){
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        if( result.getStatus() == ITestResult.SUCCESS) {
            jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \""+result.getName()+" passed!\"}}");
        }
        else{
            jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"failed\", \"reason\": \""+result.getThrowable()+"\"}}");
        }
        driver.close();
    }

}
