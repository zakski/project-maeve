package com.szadowsz.maeve.core.instruction.actions

import com.szadowsz.maeve.core.browser.MaeveBrowser
import com.szadowsz.common.net.Uri

/**
  * Executor for Javascript Actions; provides context points at the start of a scrape, after a page load, before extraction is called, after extraction is
  * called, as a final cleanup action after a scrape.
  *
  * Created on 13/10/2016.
  */
trait ActionExecutor {

  /**
    * Function to execute actions at the start of a scrape.
    *
    * @param browser     the browser to interact with.
    * @param firstTarget the expected first url that will be accessed after this.
    */
  def doFirstExecutionAction(browser: MaeveBrowser, firstTarget: Uri): Unit

  /**
    * Function to execute actions after a page load.
    *
    * @param browser the browser to interact with.
    */
  def doInitialPageAction(browser: MaeveBrowser): Unit

  /**
    * Function to execute actions before extraction is called.
    *
    * @param browser the browser to interact with.
    */
  def doBeforeExtractAction(browser: MaeveBrowser): Unit

  /**
    * Function to execute actions after extraction is called.
    *
    * @param browser the browser to interact with.
    */
  def doAfterExtractAction(browser: MaeveBrowser): Unit

  /**
    * Function to execute final cleanup actions after a scrape.
    *
    * @param browser the browser to interact with.
    */
  def doFinalExecutionAction(browser: MaeveBrowser): Unit
}
