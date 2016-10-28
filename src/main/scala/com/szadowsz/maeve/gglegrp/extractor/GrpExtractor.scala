package com.szadowsz.maeve.gglegrp.extractor

import com.szadowsz.maeve.core.instruction.MaeveInstruction
import com.szadowsz.maeve.core.instruction.extractor.JsoupExtractor
import com.szadowsz.maeve.core.instruction.extractor.util.TxtFileLineWriter
import com.szadowsz.common.net.Uri
import org.jsoup.nodes.Document
import org.slf4j.LoggerFactory

import scala.collection.JavaConverters._

/**
  * Created on 16/10/2016.
  */
class GrpExtractor() extends JsoupExtractor with TxtFileLineWriter {
  private val logger = LoggerFactory.getLogger(this.getClass)

  var processedLinks: Set[String] = Set()
  var topicLinks: List[String] = Nil

  override def extract(queryUrl: Uri, returnedUrl: Uri, instr: MaeveInstruction[_], page: Document): Unit = {
    val links = page.select("div[class='IVILX2C-b-D'] a[href]").asScala.map(_.attr("href")).toSet.diff(processedLinks)
    topicLinks = links.filter(_.startsWith("#!topic/")).toList
    write(instr.dPath, instr.name, topicLinks,true)
    processedLinks = processedLinks.union(links)
  }

  override def shouldContinue(): Boolean = topicLinks.nonEmpty
}
