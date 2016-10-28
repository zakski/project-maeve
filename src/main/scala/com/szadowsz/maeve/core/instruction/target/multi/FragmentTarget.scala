package com.szadowsz.maeve.core.instruction.target.multi

import com.szadowsz.common.net.Uri

/**
  * Class for Uri targeting during the scraping process, if we are expecting to differentiate web pages by their fragment.
  *
  * Created on 18/10/2016.
  */
case class FragmentTarget(
                        override val baseUrl: Uri,
                        override val seq: Seq[String],
                        override val hist: Seq[String] = Nil
                      ) extends MultiTarget[String,FragmentTarget] {


  /**
    * Builds and returns the current Uri
    *
    * @return the current uri
    */
  def currentUrl() = baseUrl.withFragment(seq.head)

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
