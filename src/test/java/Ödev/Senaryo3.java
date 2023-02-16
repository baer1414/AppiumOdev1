package Ödev;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.App;
import utils.Device;
import utils.Driver;

import java.text.MessageFormat;
import java.time.Duration;
import java.util.Random;

public class Senaryo3 {
    /*
    a.    10 ile 20 arasinda random bir sayi secin
    b.    Views->TextSwitcher'a tiklayin
    c.    Secilen random sayiya gelinceye kadar Next butonuna tiklayin
    d.    Istenilen sayiya ulasilinca navigate().back() ile geri gelin.
     */

    By lContinue = By.id("com.android.permissioncontroller:id/continue_button");
    By lOkButton = By.id("android:id/button1");
    By lOkButton1 = By.id("com.touchboarder.android.api.demos:id/buttonDefaultPositive");
    By lapıdemos = By.xpath("//*[@text='API Demos']");
    String textXpath = "//*[@text=\"{0}\"]";

    AppiumDriver<MobileElement> driver;
    WebDriverWait wait;

    @BeforeTest
    public void beforeTest() {
        Driver.runAppium();
        driver = Driver.getDriver(Device.PIXEL2, App.APIDEMO);
        wait = new WebDriverWait(driver, 3);
        click(lContinue);
        click(lOkButton);
        click(lOkButton1);
        click(lapıdemos);
    }

    @AfterTest
    public void afterTest() {
        driver.quit();
        Driver.stopAppium();
    }

    @Test
    public void test1() {
        click(xpathOfText("Views"));
        swipeUntil(xpathOfText("TextSwitcher"), .6, .4);
        click(xpathOfText("TextSwitcher"));
        Random random=new Random();
        int sayi=random.nextInt(10)+10;
        for (int i =0; i <= sayi; i++) {
            click(xpathOfText("NEXT"));
        }
        driver.navigate().back();





    }


    public void click(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator)).click();
    }

    By xpathOfText(String... text) {
        return By.xpath(MessageFormat.format(textXpath, text));
    }


    public void swipeV(double startPoint, double endPoint) {
        int w = driver.manage().window().getSize().width;
        int h = driver.manage().window().getSize().height;

        new TouchAction<>(driver)
                .press(PointOption.point(w / 2, (int) (h * startPoint)))
                .waitAction(WaitOptions.waitOptions(Duration.ofMillis(500)))
                .moveTo(PointOption.point(w / 2, (int) (h * endPoint)))
                .release()
                .perform();
    }

    public void swipeUntil(By locator, double start, double end) {
        while (driver.findElements(locator).size() <= 0)
            swipeV(start, end);
    }

}
