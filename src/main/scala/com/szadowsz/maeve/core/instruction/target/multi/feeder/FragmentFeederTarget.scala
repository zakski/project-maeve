package com.szadowsz.maeve.core.instruction.target.multi.feeder

import com.szadowsz.common.net.Uri

import scala.collection.mutable.ArrayBuffer

/**
  * Class for Uri targeting during the scraping process, if we are expecting to differentiate web pages by their fragment.
  *
  * Created on 18/10/2016.
  */
case class FragmentFeederTarget(
                        override val baseUrl: Uri,
                        override val seq: ArrayBuffer[String] = ArrayBuffer(),
                        override val hist: ArrayBuffer[String] = ArrayBuffer()
                      ) extends FeederTarget[String,FragmentFeederTarget] {


  /**
    * Builds and returns the current Uri
    *
    * @return the current uri
    */
  def currentUrl() = baseUrl.withFragment(seq.head)

  /**
    * Method to update the url to the next in sequence.
    *
    * @return the instance, updated to the next target in the sequence
    */
  def next() = {
    hist += seq.remove(0)
    this
  }

  /**
    * Method to reset the target.
    *
    * @return a version of the target sequence with a cleared history.
    */
  def reset() = {
    seq.clear()
    hist.clear()
    this
  }
}
