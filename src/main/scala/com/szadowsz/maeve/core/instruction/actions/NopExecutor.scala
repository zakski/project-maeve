package com.szadowsz.maeve.core.instruction.actions

import com.szadowsz.maeve.core.browser.MaeveBrowser
import com.szadowsz.common.net.Uri

/**
  * NOP executor for when no javascript interaction is called for.
  *
  * Created on 15/10/2016.
  */
final class NopExecutor extends ActionExecutor {

  /**
    * Function to execute actions at the start of a scrape.
    *
    * @param browser     the browser to interact with.
    * @param firstTarget the expected first url that will be accessed after this.
    */
  override def doFirstExecutionAction(browser: MaeveBrowser, firstTarget: Uri): Unit = {}

  /**
    * Function to execute actions after a page load.
    *
    * @param browser the browser to interact with.
    */
  override def doInitialPageAction(browser: MaeveBrowser): Unit = {}

  /**
    * Function to execute actions before extraction is called.
    *
    * @param browser the browser to interact with.
    */
  override def doBeforeExtractAction(browser: MaeveBrowser): Unit = {}

  /**
    * Function to execute actions after extraction is called.
    *
    * @param browser the browser to interact with.
    */
  override def doAfterExtractAction(browser: MaeveBrowser): Unit = {}

  /**
    * Function to execute final cleanup actions after a scrape.
    *
    * @param browser the browser to interact with.
    */
  override def doFinalExecutionAction(browser: MaeveBrowser): Unit = {}
}
