package com.szadowsz.maeve.core.instruction.target.multi.feeder

import com.szadowsz.common.net.Uri

import scala.collection.mutable.ArrayBuffer

/**
  * Created on 18/10/2016.
  */
case class FragmentFeederTarget(
                        override val baseUrl: Uri,
                        override val seq: ArrayBuffer[String] = ArrayBuffer(),
                        override val hist: ArrayBuffer[String] = ArrayBuffer()
                      ) extends FeederTarget[String,FragmentFeederTarget] {


  def currentUrl() = baseUrl.withFragment(seq.head)

  def next() = {
    hist += seq.remove(0)
    this
  }

  def reset() = {
    seq.clear()
    hist.clear()
    this
  }
}
