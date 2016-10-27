package com.szadowsz.maeve.core.error

import org.openqa.selenium.WebDriverException

/**
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
