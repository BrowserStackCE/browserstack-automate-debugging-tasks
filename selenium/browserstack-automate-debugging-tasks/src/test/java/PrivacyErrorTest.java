import org.testng.annotations.Test;


public class PrivacyErrorTest extends TestBase {
    @Test
    public void trustBadsslPage(){

        driver.get("https://untrusted-root.badssl.com/");
    }
}
