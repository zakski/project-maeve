package com.szadowsz.maeve.core.instruction.target.multi

import com.szadowsz.maeve.core.instruction.target.Profile
import com.szadowsz.common.net.Uri


/**
  * Created on 24/05/2016.
  */
case class QueryProfile(
                         override val baseUrl: Uri,
                         key: String,
                         override val seq: Seq[String],
                         override val hist: Seq[String] = Nil
                       ) extends MultiTarget[String,QueryProfile] {

  def currentUrl() = baseUrl.appendQuery(key, seq.head)

  def next() = copy(seq = seq.tail, hist = hist :+ seq.head)

  def reset() = copy(seq = hist ++ seq, hist = Nil)
}
