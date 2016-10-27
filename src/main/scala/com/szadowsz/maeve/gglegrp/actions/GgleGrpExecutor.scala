package com.szadowsz.maeve.gglegrp.actions

import com.szadowsz.maeve.core.browser.MaeveBrowser
import org.openqa.selenium.{By, Keys}
import org.openqa.selenium.interactions.Actions

import scala.collection.JavaConverters._

/**
  * Scrapes each topic url, for smaller groups where we can make 1st pass to grab the topics and a 2nd pass to read their content. Does not work on large
  * groups as the connection to the non-headless browser can timeout.
  *
  * Created on 15/10/2016.
  */

class GgleGrpExecutor(val year1: Int, val year2: Int, usr: String, pwd: String) extends GgleExecutor(usr, pwd) {

  override def doBeforeExtractAction(browser: MaeveBrowser): Unit = {
    val scrollAction: Actions = new Actions(browser)
    scrollAction.keyDown(Keys.CONTROL).sendKeys(Keys.END).perform()
    Thread.sleep(5000)
  }

  override def doInitialPageAction(browser: MaeveBrowser): Unit = {
    browser.findElement(By.id("b_filters")).click()
    val xpathBase = "//table[@class='IVILX2C-L-d']/tbody/tr[last()]/td/table/tbody/tr[last()"
    browser.findElement(By.xpath(xpathBase + "-1]/td/span/span/span")).click()
    val dateBoxes = browser.findElements(By.xpath(xpathBase+"]/td/div/input")).asScala
    dateBoxes.head.sendKeys("01/01/" + year1)
    dateBoxes.last.click()
    dateBoxes.last.clear()
    dateBoxes.last.sendKeys("01/01/" + year2)
    browser.findElement(By.className("gwt-PopupPanel")).click()
    browser.findElement(By.id("f_apply_filters")).click()
    Thread.sleep(5000)
  }

  override def doAfterExtractAction(browser: MaeveBrowser): Unit = {}

  override def doFinalExecutionAction(browser: MaeveBrowser): Unit = {}
}
