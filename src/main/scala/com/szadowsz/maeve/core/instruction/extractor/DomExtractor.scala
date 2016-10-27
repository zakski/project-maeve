package com.szadowsz.maeve.core.instruction.extractor

import javax.xml.xpath.{XPathConstants, XPathFactory}

import org.w3c.dom.{Document, Element, Node, NodeList}

/**
  * Created on 14/10/2016.
  */
trait DomExtractor extends DataExtractor[Document]{

  protected val xPath = XPathFactory.newInstance.newXPath

  protected def getXPath(thePath: String, theElement: Element): List[Node] = {
    val nodes = xPath.evaluate(thePath, theElement, XPathConstants.NODESET).asInstanceOf[NodeList]
    (0 until nodes.getLength).map(nodes.item).toList
  }
}
