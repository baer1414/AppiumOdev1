package Gun4;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utlls.App;
import utlls.Device;

import java.time.Duration;

public class Scroll1 {

    AppiumDriverLocalService service;
    AppiumDriver<MobileElement> driver;
    WebDriverWait wait;
    Device device = Device.PIXEL2;
    App app = App.APIDEMO;

    By lAccecibility = By.xpath("//android.widget.TextView[@content-desc='Accessibility']");
    By lAutoComlete = By.xpath("//android.widget.TextView[@content-desc='Auto Complete']");
    By lViews = By.xpath("//android.widget.TextView[@content-desc='Views']");
    By lTextSwitcher = By.xpath("//android.widget.TextView[@content-desc='TextSwitcher']");


    @BeforeTest
    public void beforeTest(){
        service = new AppiumServiceBuilder()
                .withIPAddress("127.0.0.1")
                .usingAnyFreePort()
                .build();

        service.clearOutPutStreams();
        service.start();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("appium:udid", device.udid);
        capabilities.setCapability("appium:version", device.version);
        capabilities.setCapability("appium:deviceName", device.deviceName);
        capabilities.setCapability("platformName", device.platformName);

        capabilities.setCapability("appium:appPackage", app.appPackage);
        capabilities.setCapability("appium:appActivity", app.appActivity);

        driver = new AndroidDriver<>(service.getUrl(), capabilities);
        wait = new WebDriverWait(driver, 20);

        wait.until(ExpectedConditions.visibilityOfElementLocated(lAccecibility));

    }

    @Test
    public void testScroll(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(lViews)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(lAutoComlete));
        swipeUntil(lTextSwitcher);

        driver.hideKeyboard();
    }

    public void swipeUntil(By locator){
        while (driver.findElements(locator).size()<=0)
            swipeV(.6, .4);
    }


    /**
     * ekranda swipe islemi yapar
     * @param startPoint yüzde olarak 0, 1 arasi double sayi
     * @param stopPoint  yüzde olarak 0, 1 arasi double
     */
    public void swipeV(double startPoint, double stopPoint){
        int w = driver.manage().window().getSize().width;
        int h = driver.manage().window().getSize().height;

        new TouchAction<>(driver)
                .press(PointOption.point(w/2, (int)(h*startPoint)))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                .moveTo(PointOption.point(w/2, (int)(h*stopPoint)))
                .release()
                .perform();

    }

    /**
     * ekranda swipe islemi yapar
     * @param startPoint yüzde olarak 0, 1 arasi double sayi
     * @param stopPoint  yüzde olarak 0, 1 arasi double
     */
    public void swipeH(double startPoint, double stopPoint){
        int w = driver.manage().window().getSize().width;
        int h = driver.manage().window().getSize().height;

        new TouchAction<>(driver)
                .press(PointOption.point((int)(w*startPoint), h/2))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                .moveTo(PointOption.point((int)(w*stopPoint), h/2))
                .release()
                .perform();
    }

    @AfterTest
    public void afterTest(){
        driver.quit();
        service.stop();
    }
}
