package com.szadowsz.maeve.core.instruction.actions

import com.szadowsz.maeve.core.browser.MaeveBrowser
import com.szadowsz.common.net.Uri

/**
  * Created on 13/10/2016.
  */
trait ActionExecutor {

  def doFirstExecutionAction(browser: MaeveBrowser, firstTarget: Uri): Unit

  def doInitialPageAction(browser: MaeveBrowser): Unit

  def doBeforeExtractAction(browser: MaeveBrowser): Unit

  def doAfterExtractAction(browser: MaeveBrowser): Unit

  def doFinalExecutionAction(browser: MaeveBrowser): Unit
}
