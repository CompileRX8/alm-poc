package com.vha.techdev.webapp.test;
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import com.vha.techdev.ComponentInfo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.io.File;
import java.io.FileOutputStream;

@RunWith(JUnit4.class)
public class SimpleTest {
    private WebDriver driver;
    private Selenium s;

    @Before
    public void setup() throws Exception {
        int seleniumPort = Integer.parseInt(System.getProperty("selenium.port", "4444"));
        String browser = System.getProperty("seleniumBrowser", "*firefox");
        String serverUrl = System.getProperty("serverUrl", "http://localhost:9090/");

        this.driver = new FirefoxDriver();
        this.s = new WebDriverBackedSelenium(this.driver, System.getProperty("serverUrl", "http://localhost:9090/"));

        s.open("index.html");
    }

    @After
    public void teardown() throws Exception {
        driver.close();
        s.close();
    }

    @Test
    public void testSimple() throws Exception {

        // wait a bit ajax response
        Thread.sleep(1000);
        WebElement version = driver.findElement(By.id("version"));
        Assert.assertEquals(ComponentInfo.IMPLEMENTATION.toString(), version.getText());

        WebElement who = driver.findElement(By.id("who"));
        who.sendKeys("foo");
        WebElement sendBtn = driver.findElement(By.id("send-btn"));
        sendBtn.click();
        // wait a bit ajax response
        Thread.sleep(1000);
        WebElement response = driver.findElement(By.id("response"));
        Assert.assertEquals("Hello foo", response.getText());
    }

    @Test
    public void testFileUpload() throws Exception {
        long fileLength = 16384L;
        File tempFile = createTestFile(fileLength);
        String expectedResponse = tempFile.getName() + ":application/octet-stream:" + fileLength;

        WebElement uploadFile = driver.findElement(By.id("uploadfile"));
        uploadFile.sendKeys(tempFile.getAbsolutePath());

        WebElement uploadBtn = driver.findElement(By.id("upload-btn"));
        uploadBtn.click();
        // wait a bit ajax response
        Thread.sleep(1000);
        WebElement fileResponse = driver.findElement(By.id("file-response"));
        Assert.assertEquals(expectedResponse, fileResponse.getText());
    }

    private File createTestFile(long fileSize) throws Exception {
        File tempFile = File.createTempFile("testFile", null);
        tempFile.deleteOnExit();

        tempFile.createNewFile();

        FileOutputStream out = new FileOutputStream(tempFile);

        long bytesWritten = 0;
        while(bytesWritten < fileSize) {
            long bytesToWrite = Math.min(4096L, fileSize - bytesWritten);
            byte[] buf = new byte[(int)bytesToWrite];
            for(int i = 0; i < buf.length; i++) {
                buf[i] = (byte)(i & 0xFF);
            }
            out.write(buf);
            out.flush();
            bytesWritten += buf.length;
        }
        out.close();

        return tempFile;
    }
}
