package com.szadowsz.maeve.core.instruction.actions
import com.szadowsz.maeve.core.browser.MaeveBrowser
import com.szadowsz.common.net.Uri

/**
  * Created on 15/10/2016.
  */
final class NopExecutor extends ActionExecutor {
  override def doInitialPageAction(browser: MaeveBrowser): Unit = {}

  override def doBeforeExtractAction(browser: MaeveBrowser): Unit = {}

  override def doAfterExtractAction(browser: MaeveBrowser): Unit = {}

  override def doFirstExecutionAction(browser: MaeveBrowser,target : Uri): Unit = {}

  override def doFinalExecutionAction(browser: MaeveBrowser): Unit = {}
}
