package com.szadowsz.maeve.gglegrp

import com.szadowsz.maeve.core.MaeveDriver
import com.szadowsz.maeve.core.browser.MaeveConf
import com.szadowsz.maeve.core.instruction.MaeveInstruction
import com.szadowsz.maeve.core.instruction.actions.WaitExecutor
import com.szadowsz.maeve.core.instruction.target.multi.FragmentTarget
import com.szadowsz.maeve.gglegrp.extractor.GrpTopicsExtractor
import com.szadowsz.common.io.read.FReader
import com.szadowsz.common.net.Uri
import org.slf4j.LoggerFactory

import scala.collection.mutable.ArrayBuffer

/**
  * Rendered Necessary Unfortunately.
  *
  * Created on 19/10/2016.
  */
class GgleTopicScraper(private val link: String, private val dir: String, private val recoveryDir: Option[String], private val deltaMode: Boolean) {
  private val logger = LoggerFactory.getLogger(this.getClass)

  private val urlBase = Uri("https://groups.google.com/forum/")
  private val group: String = link.substring(link.lastIndexOf("/") + 1)
  private val groupDir: String = dir + s"${group.replaceAll("\\.", "/")}/"

  private def buildConfig(): MaeveConf = {
    MaeveConf()
      .setJavaScriptEnabled(true)
      .setHTTPProxy("proxy", 8080, Nil)
      .setThrowExceptionOnScriptError(false)
  }


  def execute(): Unit = {
    System.setProperty("webdriver.chrome.driver", ".\\util-tarbh\\chromedriver_win32\\chromedriver.exe")
    val conf = buildConfig()
    val scraper = new MaeveDriver(conf)
    recoveryDir.foreach(p => scraper.setRecoveryDirectory(p))

    val topics = if (!deltaMode) {
      val read = new FReader(dir + s"$group.txt")

      val buff = ArrayBuffer[String]()
      var l: Option[String] = None
      do {
        l = read.readLineOpt()
        l.foreach(s => buff += s)
      } while (l.isDefined)
      buff.toList.distinct
    } else {
      GrpComErrorDelta.calcDelta(groupDir, recoveryDir.getOrElse("./recovery/"), group)
    }
    logger.info(s"Discovered ${topics.length} Topics")
    val target = FragmentTarget(urlBase, topics)
    val filter = new GrpTopicsExtractor()
    val actions = new WaitExecutor

    val instruction = MaeveInstruction(group, target, actions, filter, groupDir, false, !deltaMode, MaeveConf().setNoProxy())

    scraper.feedInstruction(instruction)
    scraper.scrapeUsingCurrInstruction()
  }
}
