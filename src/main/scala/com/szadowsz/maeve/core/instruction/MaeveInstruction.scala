package com.szadowsz.maeve.core.instruction

import com.szadowsz.maeve.core.browser.{MaeveBrowser, MaeveConf}
import com.szadowsz.maeve.core.instruction.actions.ActionExecutor
import com.szadowsz.maeve.core.instruction.extractor.{DataExtractor, DomExtractor, HtmlExtractor, JsoupExtractor}
import com.szadowsz.maeve.core.instruction.target.Profile
import com.szadowsz.maeve.core.instruction.target.multi.MultiTarget
import com.szadowsz.maeve.core.instruction.target.multi.feeder.FragmentFeederTarget
import com.szadowsz.maeve.core.instruction.target.single.SingleTarget
import com.szadowsz.common.net.Uri

/**
  * Created on 12/10/2016.
  */
case class MaeveInstruction
[P <: Profile[P]]
(
  name: String,
  target: P,
  actionSet: ActionExecutor,
  extractor: DataExtractor[_],
  dPath: String,
  isHeadless: Boolean = true,
  recovEnabled: Boolean = true,
  conf: MaeveConf = MaeveConf()
) {

  protected def fastForwardMultiTargets(history: List[String], target: P): P = {
    var mTarget = target
    while (!mTarget.isDone && history.contains(mTarget.currentUrl().toString)) {
      mTarget = mTarget.next()
    }
    mTarget
  }

  def applyLinkHistory(history: List[String]): MaeveInstruction[P] = {
    val usedLinks = history.toSet
    if (usedLinks.nonEmpty) {
      target match {
        case feed: FragmentFeederTarget =>
          val t = feed.reset()
          t.addToQueue(Uri(history.last).fragment)
          copy(target = t.asInstanceOf[P])

        case mult: MultiTarget[_, _] => copy(target = fastForwardMultiTargets(history, target))

        case sing: SingleTarget => this

        case _ => this
      }
    } else {
      this
    }
  }

  def getCurrentUrl: Uri = target.currentUrl()

  def doBefore(browser: MaeveBrowser) = actionSet.doFirstExecutionAction(browser,target.currentUrl())

  def doInitialAction(browser: MaeveBrowser) = actionSet.doInitialPageAction(browser)

  def doBeforeAction(browser: MaeveBrowser) = actionSet.doBeforeExtractAction(browser)

  def doAfterAction(browser: MaeveBrowser): Unit = actionSet.doAfterExtractAction(browser)

  def doAfter(browser: MaeveBrowser) = actionSet.doFinalExecutionAction(browser)

  def shouldContinueMiningPage(): Boolean = extractor.shouldContinue()

  def update(): MaeveInstruction[P] = copy(target = target.next())

  def isDone: Boolean = target.isDone

  def extractData(browser: MaeveBrowser) = {
    extractor match {
      case html: HtmlExtractor => html.extract(Uri(browser.getCurrentUrl), getCurrentUrl, this, browser.getPageAsHtml)
      case dom: DomExtractor => dom.extract(Uri(browser.getCurrentUrl), getCurrentUrl, this, browser.getPageAsDom)
      case jsoup: JsoupExtractor => jsoup.extract(Uri(browser.getCurrentUrl), getCurrentUrl, this, browser.getPageAsJsoup)
    }
  }

  def overrideConf(defaults: MaeveConf): MaeveConf = conf.overrideConf(defaults)
}