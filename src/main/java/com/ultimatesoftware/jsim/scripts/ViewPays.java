package com.ultimatesoftware.jsim.scripts;

import com.ultimatesoftware.jsim.script.models.JsimScript;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ViewPays extends JsimScript {
    //Transaction Names 
    private static final String T_HIT_SERVER = "B2_VP_1_HitServer";
    private static final String T_APPLY_FILTER = "B2_VP_2_ClickApplyFilter";
    private static final String T_CLICK_VIEWPAYS = "B2_VP_3_ClickViewPays";
    private static final String T_VIEWPAY_FILTER = "B2_VP_4_ApplyViewPaysFilter";
    private static final String T_CLICK_DASHBOARD = "B2_VP_5_ClickDashboard";

    public static void main(String[] args) throws InterruptedException{

        String url = getArg(args, "url");
        String token = getArg(args, "userToken");
        String payDate = getArg(args, "payDate");

        try {
            bootstrap(args);
            startHar();

            //User Flow
            hitServer(url  + token);
            applyFilter(payDate);
            clickViewPays();
            clickViewPayFilter();
            clickDashboard();

            endHar();
            saveHar();
        } finally {
            shutdown();
        }
    }


    private static void hitServer(String url) {
        System.out.println("VP: Hitting server...");
        startTransaction(T_HIT_SERVER);
        driver.get(url);
        endTransaction(T_HIT_SERVER);
    }

    private static void applyFilter(String filterDate) throws InterruptedException {
        System.out.println("VP: Apply Filter...");
        startTransaction(T_APPLY_FILTER);
        WebDriverWait wait = new WebDriverWait(driver, 60);
        think(3);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("overlay")));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class,'btn btn-default filter-button')]"))).click();

        think(2);
        driver.findElement(By.xpath("//input[contains(@class,'dp-input')]")).clear();
        driver.findElement(By.xpath("//input[contains(@class,'dp-input')]")).sendKeys(filterDate);
        think(2);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class,'btn btn-success')]"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("help-cursor-hover")));

        think(2);
        endTransaction(T_APPLY_FILTER);
    }

    private static void clickViewPays() throws InterruptedException {
        System.out.println("VP: Click ViewPays...");
        startTransaction(T_CLICK_VIEWPAYS);
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("overlay")));
        driver.findElement(By.xpath("//div[contains(@class, 'center-child pointer')]")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("cdk-row"))).click();
        think(2);
        endTransaction(T_CLICK_VIEWPAYS);
    }

    private static void clickViewPayFilter() throws InterruptedException {
        System.out.println("VP: ViewPays Filter...");
        startTransaction(T_VIEWPAY_FILTER);
        WebDriverWait wait = new WebDriverWait(driver, 60);

        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("overlay")));
        driver.findElement(By.xpath("//div[contains(@class, 'filters-btn-ctnr')]")).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@class, 'btn btn-default')]")));

        think(2);
        driver.findElement(By.xpath("//button[contains(@data-automation, 'filters-apply-button')]")).click();
        think(2);
        endTransaction(T_VIEWPAY_FILTER);
    }

    private static void clickDashboard() throws InterruptedException {
        System.out.println("VP: Click Dashboard...");
        startTransaction(T_CLICK_DASHBOARD);
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("overlay")));
        driver.findElement(By.xpath("//div[contains(@class, 'breadcrumb-item link')]")).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("overlay")));
        //wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class,'btn btn-default filter-button')]"))).click();
        think(2);
        endTransaction(T_CLICK_DASHBOARD);
    }
}
