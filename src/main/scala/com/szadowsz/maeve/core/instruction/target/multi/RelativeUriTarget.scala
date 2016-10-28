package com.szadowsz.maeve.core.instruction.target.multi

import com.szadowsz.common.net.Uri


/**
  * Class for Uri targeting during the scraping process, if we are expecting to differentiate web pages by relative urls.
  *
  * Created on 24/05/2016.
  */
case class RelativeUriTarget(
                               override val baseUrl: Uri,
                               override val seq: Seq[Uri],
                               override val hist: Seq[Uri] = Nil
                             ) extends MultiTarget[Uri,RelativeUriTarget] {

  /**
    * Builds and returns the current Uri
    *
    * @return the current uri
    */
  override def currentUrl(): Uri = baseUrl.appendRelative(seq.head)

  /**
    * Method to update the url to the next in sequence.
    *
    * @return a fresh instance, updated to the next target in the sequence
    */
  def next() = copy(seq = seq.tail, hist = hist :+ seq.head)

  /**
    * Method to reset the target.
    *
    * @return a fresh version of the target sequence with no history.
    */
  def reset() = copy(seq = hist ++ seq, hist = Nil)
}
