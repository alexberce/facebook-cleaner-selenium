package com.invobox.core;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class TestBase {
    public static final int FIND_WAIT = 15;
    public static final int LONG_WAIT = 30;
    protected static final ThreadLocal<RemoteWebDriver> THREAD_LOCAL_DRIVER = new InheritableThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> THREAD_LOCAL_FIND_WAIT = new InheritableThreadLocal<>();
    protected static final ThreadLocal<WebDriverWait> THREAD_LOCAL_LONG_WAIT = new InheritableThreadLocal<>();
    private static ChromeDriverService chromeService;
    protected String browser;
    private static final ThreadLocal<String> WINDOW_HANDLE = new InheritableThreadLocal<>();

    private void startChromeService() {
        if (chromeService == null) {
            System.out.println("--- STARTING CHROMEDRIVER SERVICE!!!");
            chromeService = new ChromeDriverService.Builder()
                    .usingDriverExecutable(new File(System.getProperty("webdriver.chrome.driver")))
                    .usingAnyFreePort()
                    .build();
            try {
                chromeService.start();
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    protected void rootInit() {
        browser = System.getProperty("browser");
        RemoteWebDriver driver;
        initDriverPaths();
        startChromeService();
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        DesiredCapabilities dc = DesiredCapabilities.chrome();
        dc.setCapability(ChromeOptions.CAPABILITY, options);
        WebDriverWait findWait, longWait;
        findWait = new WebDriverWait(driver, FIND_WAIT);
        longWait = new WebDriverWait(driver, LONG_WAIT);
        WINDOW_HANDLE.set(null);
        THREAD_LOCAL_DRIVER.set(driver);
        THREAD_LOCAL_FIND_WAIT.set(findWait);
        THREAD_LOCAL_LONG_WAIT.set(longWait);
    }

    protected void rootTearDown() {
        try {
            RemoteWebDriver driver = getDriver();
            if (driver != null)
                driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected com.invobox.core.WebElement findElement(String locator) {
        RemoteWebDriver driver = getDriver();
        WebDriverWait findWait = THREAD_LOCAL_FIND_WAIT.get();
        try {
            findWait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(locator)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        WebElement element = new WebElement(driver.findElement(By.xpath(locator)), driver);
        driver.executeScript("arguments[0].scrollIntoView(false);", element.getElement());
        return element;
    }

    private void initDriverPaths() {
        String osName = System.getProperty("os.name");
        String osArch = System.getProperty("os.arch");
        String chromedriverPath = "src/test/resources/chromedriver_";
        String geckodriverPath = "src/test/resources/geckodriver_";
        if (osName.toLowerCase().contains("linux")) {
            if (osArch.equalsIgnoreCase("x86")) {
                chromedriverPath += "linux-i386";
                geckodriverPath += "linux-i386";
            } else {
                chromedriverPath += "linux-amd64";
                geckodriverPath += "linux-amd64";
            }
        } else if (osName.toLowerCase().contains("mac")) {
            chromedriverPath += "mac";
            geckodriverPath += "mac";
        } else {
            chromedriverPath += "win32.exe";
            if (osArch.equalsIgnoreCase("x86")) {
                geckodriverPath += "win32.exe";
            } else {
                geckodriverPath += "x64.exe";
            }
        }
        System.setProperty("webdriver.gecko.driver", geckodriverPath);
        System.setProperty("webdriver.chrome.driver", chromedriverPath);
        System.setProperty("webdriver.chrome.logfile", "chrome.out");
    }

    protected List<WebElement> findElements(String locator) {
        RemoteWebDriver driver = getDriver();
        List<WebElement> result = new ArrayList<>();

        for (org.openqa.selenium.WebElement element : driver.findElements(By.xpath(locator))) {
            result.add(new WebElement(element, driver));
        }
        return result;
    }

    public RemoteWebDriver getDriver() {
        return THREAD_LOCAL_DRIVER.get();
    }

    public WebDriverWait getLongWait() {
        return THREAD_LOCAL_LONG_WAIT.get();
    }
}
