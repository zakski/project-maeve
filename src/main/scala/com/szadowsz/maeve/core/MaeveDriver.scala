// Copyright 2016 zakski.
// See the LICENCE.txt file distributed with this work for additional
// information regarding copyright ownership.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.szadowsz.maeve.core

import org.openqa.selenium.Proxy
import com.szadowsz.maeve.core.browser.{MaeveBrowser, MaeveConf, MaeveHeadlessBrowser, MaeveRemoteBrowser}
import com.szadowsz.maeve.core.error.InvalidProxyException
import com.szadowsz.maeve.core.instruction.MaeveInstruction
import com.szadowsz.maeve.core.util.recovery.RecoveryUtil
import org.slf4j.LoggerFactory

import scala.util.Try
import scala.util.control.NonFatal

/**
  * Created on 13/05/2016.
  */
class MaeveDriver(config: MaeveConf) {
  private val logger = LoggerFactory.getLogger(classOf[MaeveDriver])

  private val defaultConf = testProxy(config)

  private var recovPath: String = "./recovery/"

  private var browser: MaeveBrowser = _
  private var instr: MaeveInstruction[_] = _

  /**
    * Method to Check the proxy configuration is valid, and attempt to fix it if it is not.
    *
    * @param config the default maeve configuration
    * @return the corrected maeve configuration
    */
  private def testProxy(config: MaeveConf): MaeveConf = {
    val test = new MaeveHeadlessBrowser(config.setJavaScriptEnabled(false))
    test.get("https://google.com/ncr") // try to connect to google with no country specific redirect for consistency
    val result = Try(test.getPageAsHtml.getUrl.toString)

    // should return a basic google.com address
    if (result.isSuccess && result.get == "https://www.google.com/") {
      logger.info("Proxy configured correctly")
      config
    } else {
      val proxy = config.getProxy
      if (proxy.getProxyAutoconfigUrl != null) {
        logger.error("Invalid Proxy Auto Configuration URL detected: {}, Reconfiguring...", proxy.getProxyAutoconfigUrl)
        testProxy(config.setNoProxy())
      } else if (proxy.getHttpProxy != null) {
        logger.error("Invalid Http Proxy detected: {}, Reconfiguring...", proxy.getHttpProxy)
        testProxy(config.setNoProxy())
      } else {
        throw new InvalidProxyException()
      }
    }
  }

  def setRecoveryDirectory(path: String): Unit = recovPath = path

  def feedInstruction(instruction: MaeveInstruction[_]) = {
    logger.info("Initialising Browser...")
    if (instruction.isHeadless) {
      browser = new MaeveHeadlessBrowser(instruction.overrideConf(defaultConf))
      logger.info("Initialised Headless Browser")
    } else {
      browser = new MaeveRemoteBrowser(instruction.overrideConf(defaultConf))
      logger.info("Initialised Remote Browser")
    }
    val ffInstr = if (instruction.recovEnabled) {
      logger.info("Attempting Recovery")
      RecoveryUtil.doRecovery(recovPath, instruction)
    } else {
      instruction
    }

    instr = ffInstr
  }

  protected def pullPage(): Unit = {
    val page = instr.getCurrentUrl.toString
    if (browser.getCurrentUrl != page) {
      logger.info("Retrieving Page: {}", page)
      browser.get(page)
      logger.info("Retrieved Page: {}", browser.getTitle)
      instr.doInitialAction(browser)
    }
  }

  protected def extractData(): Unit = {
    logger.info("Extracting Data From: {}", browser.getTitle)
    do {
      instr.doBeforeAction(browser)
      instr.extractData(browser)
      instr.doAfterAction(browser)
    } while (instr.shouldContinueMiningPage())
  }


  def scrapePage() = {
    pullPage()
    var tries = 0
    var success = false
    do {
      try {
        extractData()
        success = true
      } catch {
        case NonFatal(e) =>
          if (tries < 3) {
            tries += 1
            logger.error("Failed to Extract Data", e)
            browser.navigate().refresh()
            Thread.sleep(5000)
          } else {
            throw e
          }
      }
    } while (!success)
    if (instr.recovEnabled) {
      RecoveryUtil.appendRecovery(recovPath, instr.name, instr.getCurrentUrl.toString)
    }
  }

  def scrapeUsingCurrInstruction(): Unit = {
    logger.info("Scraping Against Current Instruction: {}", instr.name)
    if (!instr.isDone) {
      instr.doBefore(browser)
      do {
        scrapePage()
        instr = instr.update()
      } while (!instr.isDone)
      instr.doAfter(browser)
    }
    browser.quit()
  }

  def scrapeUsingInstruction(instruction: MaeveInstruction[_]):Unit = {
    feedInstruction(instruction)
    scrapeUsingCurrInstruction()
  }
}
