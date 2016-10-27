package com.szadowsz.maeve.core.instruction.target.multi

import com.szadowsz.common.net.Uri

import scala.collection.mutable.ArrayBuffer

/**
  * Created on 18/10/2016.
  */
case class FragmentTarget(
                        override val baseUrl: Uri,
                        override val seq: Seq[String],
                        override val hist: Seq[String] = Nil
                      ) extends MultiTarget[String,FragmentTarget] {


  def currentUrl() = baseUrl.withFragment(seq.head)

  def next() = copy(seq = seq.tail, hist = hist :+ seq.head)

  def reset() = copy(seq = hist ++ seq, hist = Nil)

}
