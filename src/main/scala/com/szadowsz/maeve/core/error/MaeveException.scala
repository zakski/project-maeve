package com.szadowsz.maeve.core.error

import org.openqa.selenium.WebDriverException

/**
  * General Root Exception to build on for all scraping specific issues that can occur.
  *
  * Created on 12/10/2016.
  */
class MaeveException(message: String, cause: Throwable) extends WebDriverException(message, cause) {

  def this() {
    this(null, null)
  }

  def this(message: String) {
    this(message, null)
  }

  def this(cause: Throwable) {
    this(cause.toString, cause)
  }
}
