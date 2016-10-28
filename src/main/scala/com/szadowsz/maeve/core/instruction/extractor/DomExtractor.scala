package com.szadowsz.maeve.core.instruction.extractor

import javax.xml.xpath.{XPathConstants, XPathFactory}

import org.w3c.dom.{Document, Element, Node, NodeList}

/**
  * Base trait for extraction of Data from an xml Dom page.
  *
  * Created on 14/10/2016.
  */
trait DomExtractor extends DataExtractor[Document]{

  protected val xPath = XPathFactory.newInstance.newXPath

  /**
    * Utility Function to extract data via xpath
    *
    * @param thePath the xpath command
    * @param theElement the base element to use it on
    * @return a list of nodes that satisfy the criteria.
    */
  protected def getXPath(thePath: String, theElement: Element): List[Node] = {
    val nodes = xPath.evaluate(thePath, theElement, XPathConstants.NODESET).asInstanceOf[NodeList]
    (0 until nodes.getLength).map(nodes.item).toList
  }
}
