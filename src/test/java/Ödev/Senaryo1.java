package Ödev;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utlls.App;
import utlls.Device;
import utlls.Driver;

import java.text.MessageFormat;
import java.time.Duration;


public class Senaryo1 {
    By lContinue = By.id("com.android.permissioncontroller:id/continue_button");
    By lOkButton = By.id("android:id/button1");
    By lOkButton1 = By.id("com.touchboarder.android.api.demos:id/buttonDefaultPositive");
    By lapıdemos = By.xpath("//*[@text='API Demos']");
    String textXpath = "//*[@text=\"{0}\"]";

    By lInputBox=By.id("com.touchboarder.android.api.demos:id/edit");
    By lKapaliButton=By.id("com.touchboarder.android.api.demos:id/toggle1");
    By lKapaliButton2=By.id("com.touchboarder.android.api.demos:id/toggle2");
    By lSelectBox=By.id("com.touchboarder.android.api.demos:id/spinner1");



    AppiumDriver<MobileElement> driver;
    WebDriverWait wait;

    @BeforeTest
    public void beforeTest() {
        Driver.runAppium();
        driver = Driver.getDriver(Device.PIXEL2, App.APIDEMO);
        wait = new WebDriverWait(driver, 4);
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

    /*
    1.    Scenario 1
        a.    Views->Controls'a tiklayin
        b.    Light Theme'e tiklayin
        c.    Inputbox'a "Controls" metnini send ediniz
        d.    Checkbox1'e tiklayin ve checked oldugunu assert edin
        e.    RadioButton1'e tiklayin  ve checked oldugunu assert edin
        f.    Star'a  tiklayin  ve checked oldugunu assert edin
        g.    Ilk button'a  tiklayin ve textinin ON, statüsünün checked oldugunu assert edin
        h.    Ikinci button'un textinin OFF, statüsünün unchecked oldugunu assert edin
        i.    Selectbox'dan Mars secenegini seciniz.
     */
    @Test
    public void test1() {
        click(xpathOfText("Views"));
        click(xpathOfText("Controls"));
        click(xpathOfText("01. Light Theme"));

        MobileElement inputBox = driver.findElement(lInputBox);
        inputBox.sendKeys("Controls");

        MobileElement checkBox = driver.findElement(xpathOfText("Checkbox 1"));
        click(xpathOfText("Checkbox 1"));
        // Assert.assertTrue(checkBox.isSelected());
        Assert.assertEquals(checkBox.getAttribute("checked"),"true");
        Assert.assertTrue(Boolean.valueOf(checkBox.getAttribute("checked")));
        Assert.assertTrue(Boolean.parseBoolean(checkBox.getAttribute("checked")));

        MobileElement radioBox = driver.findElement(xpathOfText("RadioButton 1"));
        click(xpathOfText("RadioButton 1"));
        //Assert.assertTrue(radioBox.isSelected());
        //Assert.assertTrue(Boolean.valueOf(radioBox.getAttribute("checked")));
        Assert.assertTrue(Boolean.parseBoolean(radioBox.getAttribute("checked")));

        MobileElement star = driver.findElement(xpathOfText("Star"));
        click(xpathOfText("Star"));
        // Assert.assertTrue(star.isSelected());
        Assert.assertTrue(Boolean.parseBoolean(star.getAttribute("checked")));

        MobileElement kapaliButton = driver.findElement(lKapaliButton);
        //kapaliButton.click();
        click(lKapaliButton);
        String expectedText="ON";
        Assert.assertEquals(kapaliButton.getText(),expectedText);
        //Assert.assertTrue(kapaliButton.isSelected());
        Assert.assertTrue(Boolean.parseBoolean(kapaliButton.getAttribute("checked")));

        MobileElement kapaliButton2 = driver.findElement(lKapaliButton2);
        String expectedText2="OFF";
        Assert.assertEquals(kapaliButton2.getText(),expectedText2);
        //Assert.assertFalse(kapaliButton2.isSelected());-->çalışıyor
        Assert.assertFalse(Boolean.parseBoolean(kapaliButton2.getAttribute("checked")));

        swipeV(.6, .4);

        MobileElement selectBox = driver.findElement(lSelectBox);
        //selectBox.click();
        click(lSelectBox);
        click(xpathOfText("Mars"));
    }


    public void click(By locator) {
        driver.findElement(locator).click();
    }

    By xpathOfText(String... text) {
        return By.xpath(MessageFormat.format(textXpath, text));
    }

    void waitForVisibility(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
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
