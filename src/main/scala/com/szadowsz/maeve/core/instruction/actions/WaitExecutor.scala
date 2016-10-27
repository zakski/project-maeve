package com.szadowsz.maeve.core.instruction.actions

import com.szadowsz.maeve.core.browser.MaeveBrowser
import com.szadowsz.common.net.Uri

/**
  * Created on 18/10/2016.
  */
class WaitExecutor extends ActionExecutor {
  final override def doInitialPageAction(browser: MaeveBrowser): Unit = {Thread.sleep(5000)}

  override def doBeforeExtractAction(browser: MaeveBrowser): Unit = {}

  override def doAfterExtractAction(browser: MaeveBrowser): Unit = {}

  override def doFirstExecutionAction(browser: MaeveBrowser,target : Uri): Unit = {}

  override def doFinalExecutionAction(browser: MaeveBrowser): Unit = {}

}
