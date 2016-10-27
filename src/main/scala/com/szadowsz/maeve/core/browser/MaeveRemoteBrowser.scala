package com.szadowsz.maeve.core.browser

import javax.xml.parsers.DocumentBuilderFactory

import org.openqa.selenium.chrome.ChromeDriver
import org.w3c.{dom => w3c}

/**
  * Created on 16/10/2016.
  */
class MaeveRemoteBrowser(private val conf: MaeveConf) extends ChromeDriver(conf.buildChromeProfile) with MaeveBrowser {
  //extends FirefoxDriver(conf.buildFirefoxProfile()) with MaeveBrowser {
  private lazy val docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()

  /**
    * Get the currently accessed web page as Jsoup Document.
    *
    * @return A HTML document.
    */
  override def getPageAsDom: w3c.Document =  docBuilder.parse(getPageSource)
}
