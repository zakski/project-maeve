package com.szadowsz.maeve.gglegrp.actions

import java.util.Random
import java.util.concurrent.TimeUnit

import com.szadowsz.maeve.core.browser.MaeveBrowser
import com.szadowsz.maeve.core.instruction.actions.ActionExecutor
import com.szadowsz.common.net.Uri
import org.openqa.selenium.By

/**
  * General Purpose Executor for accessing google. Signs into google as a first action.
  *
  * Created on 19/10/2016.
  */
class GgleExecutor(private val username: String,private val password: String) extends ActionExecutor {
  final override def doFirstExecutionAction(browser: MaeveBrowser, firstTarget: Uri): Unit = {
    val rand = new Random()
    val loginURI = Uri("https://accounts.google.com/ServiceLogin?hl=en&passive=true").appendQuery("continue", firstTarget.toString)
    browser.get(loginURI)
    browser.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS)
    browser.findElement(By.id("Email")).sendKeys(username)
    Thread.sleep(1000 + rand.nextInt(4)*1000)
    browser.findElement(By.id("next")).click()
    Thread.sleep(5000 + rand.nextInt(4)*1000)
    browser.findElement(By.id("Passwd")).sendKeys(password)
    Thread.sleep(1000 + rand.nextInt(4)*1000)
    browser.findElement(By.id("signIn")).click()
    Thread.sleep(10000+ rand.nextInt(4)*1000)
    doInitialPageAction(browser)
  }

  override def doInitialPageAction(browser: MaeveBrowser): Unit = {}

  override def doBeforeExtractAction(browser: MaeveBrowser): Unit = {}

  override def doAfterExtractAction(browser: MaeveBrowser): Unit = {}

  override def doFinalExecutionAction(browser: MaeveBrowser): Unit = {}
}
