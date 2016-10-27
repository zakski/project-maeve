package com.szadowsz.maeve.core.instruction.target.multi

import com.szadowsz.maeve.core.instruction.target.Profile
import com.szadowsz.common.net.Uri


/**
  * Created on 24/05/2016.
  */
case class RelativeUriProfile(
                               override val baseUrl: Uri,
                               override val seq: Seq[Uri],
                               override val hist: Seq[Uri] = Nil
                             ) extends MultiTarget[Uri,RelativeUriProfile] {

  override def currentUrl(): Uri = baseUrl.appendRelative(seq.head)

  def next() = copy(seq = seq.tail, hist = hist :+ seq.head)

  def reset() = copy(seq = hist ++ seq, hist = Nil)
}
