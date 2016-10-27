package com.szadowsz.maeve.core.instruction.target.multi

import com.szadowsz.maeve.core.instruction.target.Profile
import com.szadowsz.common.net.Uri


/**
  * Created on 24/05/2016.
  */
case class PathProfile(
                       override val baseUrl: Uri,
                       override val seq: Seq[String],
                       override val hist: Seq[String] = Nil
                     ) extends MultiTarget[String,PathProfile] {


  def currentUrl() = baseUrl.appendPath(seq.head)

  def next() = copy(seq = seq.tail, hist = hist :+ seq.head)

  def reset() = copy(seq = hist ++ seq, hist = Nil)
}
