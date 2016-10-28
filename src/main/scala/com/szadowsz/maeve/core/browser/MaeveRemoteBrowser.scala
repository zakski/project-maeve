package com.szadowsz.maeve.core.browser

import javax.xml.parsers.DocumentBuilderFactory

import org.openqa.selenium.chrome.ChromeDriver
import org.w3c.{dom => w3c}

/**
  * This is an extension to the Chrome Driver to bring it under the Maeve Browser Umbrella. It basically provides the additional page options that we require
  * to the chrome driver. Was originally built to be used with the Firefox Driver, however firefox is in a state of transition and the new driver library
  * does not currently work with the latest firefox version.
  *
  * Created on 16/10/2016.
  */
class MaeveRemoteBrowser(private val conf: MaeveConf) extends ChromeDriver(conf.buildChromeProfile) with MaeveBrowser {
  private lazy val docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder()

  /**
    * Get the currently accessed web page as Jsoup Document.
    *
    * @return A HTML document.
    */
  override def getPageAsDom: w3c.Document =  docBuilder.parse(getPageSource)
}
