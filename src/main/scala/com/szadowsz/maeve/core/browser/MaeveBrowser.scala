package com.szadowsz.maeve.core.browser

import com.gargoylesoftware.htmlunit.html.HtmlPage
import com.szadowsz.common.net.Uri
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.openqa.selenium.WebDriver
import org.w3c.{dom => w3c}

/**
  * Created on 16/10/2016.
  */
trait MaeveBrowser extends WebDriver {


  /**
    * Function to access a page
    *
    * @param url the uri to access.
    */
  def get(url : Uri) : Unit = get(url.toString)

  /**
    * Gets the true currently accessed uri.
    *
    * @return the uri the driver is currently accessing.
    */
  def getURI: Uri = Uri(getCurrentUrl)

  /**
    * Get the currently accessed web page.
    *
    * @return A representation of an HTML page returned from a server.
    */
  def getPageAsHtml: HtmlPage = throw new UnsupportedOperationException("Cannot Provide HtmlUnit HTML Page")

  /**
    * Get the currently accessed web page as Jsoup Document.
    *
    * @return A HTML document.
    */
  def getPageAsJsoup: Document = Jsoup.parse(getPageSource)

  /**
    * Get the currently accessed web page as Jsoup Document.
    *
    * @return A HTML document.
    */
  def getPageAsDom: w3c.Document

  /**
    * Function to clear all broswer history.
    */
  def clearHistory() : Unit = {
    manage().deleteAllCookies()
  }
}
