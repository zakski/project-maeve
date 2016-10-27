package com.szadowsz.maeve.gglegrp.extractor

import java.text.SimpleDateFormat
import java.util.Calendar

import com.szadowsz.maeve.core.instruction.MaeveInstruction
import com.szadowsz.maeve.core.instruction.extractor.JsoupExtractor
import com.szadowsz.maeve.core.instruction.extractor.util.LineDataWriter
import com.szadowsz.common.net.Uri
import org.apache.commons.lang3.StringEscapeUtils
import org.jsoup.Jsoup
import org.jsoup.nodes.Document.OutputSettings
import org.jsoup.nodes.{Document, Element}
import org.jsoup.safety.Whitelist
import org.slf4j.LoggerFactory

import scala.collection.JavaConverters._
import scala.collection.mutable.ArrayBuffer
import scala.util.{Failure, Success, Try}

/**
  * Created on 18/10/2016.
  */
class GrpTopicsExtractor() extends JsoupExtractor with LineDataWriter {
  private val logger = LoggerFactory.getLogger(this.getClass)

  val contSettings = new OutputSettings()
    .prettyPrint(false)

  def buildFileName(timeElement: Element, fullTitle: String, length: Int = 20): String = {

    val time = Try(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(timeElement.text()))) match{
      case Success (d) =>  d
      case Failure(_) =>
        val d = new SimpleDateFormat("dd MMM").parse(timeElement.text())
        val c = Calendar.getInstance()
        c.setTimeInMillis(System.currentTimeMillis())
        c.get(Calendar.YEAR) + "-" + new SimpleDateFormat("MM-dd").format(d)
    }

    val smallTitle = fullTitle.replaceAll("[^a-zA-Z0-9]", "")
    time + "-" + smallTitle.substring(0, Math.min(smallTitle.length, length))
  }

  override def extract(queryUrl: Uri, returnedUrl: Uri, instr: MaeveInstruction[_], page: Document): Unit = {
    val titleElement = page.select("span[id='t-t']").asScala.toList
    val timeElements = page.select("span[class='IVILX2C-tb-Q IVILX2C-b-Cb']").asScala.toList
    val contentElements = page.select("div[class='IVILX2C-tb-W'] div[class='IVILX2C-tb-P'][tabindex=0]").asScala.toList
    var userElements = page.select("span[class='IVILX2C-D-a']").asScala.toList.map(_.text())

    val nonEmpty = titleElement.nonEmpty && timeElements.nonEmpty && contentElements.nonEmpty

    if (nonEmpty && timeElements.length == contentElements.length) {
      val topic = titleElement.head.text()
      var fileName = buildFileName(timeElements.head, topic)
      val path = instr.dPath + s"/${fileName.substring(0, 4)}/"
      var count = 1
      var tmp = fileName
      while (exists(path, tmp)) {
        count += 1
        tmp = fileName + s"-$count"
      }
      fileName = tmp
      logger.info(s"Writing to file $fileName")
      val content: ArrayBuffer[String] = ArrayBuffer(s"URL: $returnedUrl")
      content += s"TITLE: $topic \n"

      contentElements.zipWithIndex.foreach { case (post, i) =>
        content += "----------------------------------------------------------------------------\n"
        val user = if (userElements.length > i) userElements(i) else "UNKNOWN"
        val time = timeElements(i).attr("title")
        content += (time + " - " + user + ":")

        var cleanedUp = StringEscapeUtils.unescapeHtml4(Jsoup.clean(post.html, "", Whitelist.none(), contSettings)).dropWhile(_ == "\n").toString
        do {
          cleanedUp = cleanedUp.trim
          cleanedUp = if (cleanedUp.startsWith("\n")) cleanedUp.substring(1) else cleanedUp
        } while (cleanedUp.startsWith("\n") || cleanedUp.startsWith(" "))
        content += cleanedUp
      }
      write(instr.dPath + s"/${fileName.substring(0, 4)}/", fileName, content.toList, false)
      // }
    } else {
      throw new Exception("Missing Information")
    }
  }

  override def shouldContinue(): Boolean = false
}
