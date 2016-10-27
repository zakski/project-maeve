package com.szadowsz.maeve.core.util

import scala.collection.JavaConverters._
import org.jsoup.nodes.{Document, Element}
import org.jsoup.parser.Tag

/**
  * Created on 18/05/2016.
  */
object PageDeltaBuilder {

  def build(base: Document, current: Document): Document = {
    val delta = new Document(current.location())

    if (!base.hasSameValue(current)) {
      build(base, current, delta)
    }
val y = delta.text()
    delta
  }


  def build(base: Element, current: Element, delta: Element): Unit = {
    val childNodes = if(base != null){
      base.children().asScala.zipAll(current.children().asScala, null, null)
    } else {
      current.children().asScala.map((null.asInstanceOf[Element],_))
    }

    val changedNodes = childNodes.filter { case (b, c) => c != null && !c.hasSameValue(b)}

    changedNodes.foreach { case (b, c) =>
      val child = new Element(Tag.valueOf(c.tagName), c.baseUri, c.attributes)
      child.appendText(c.ownText())
      build(b, c, child)
      delta.appendChild(child)
    }
  }
}