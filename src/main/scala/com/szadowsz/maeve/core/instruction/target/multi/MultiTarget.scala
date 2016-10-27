package com.szadowsz.maeve.core.instruction.target.multi

import com.szadowsz.maeve.core.instruction.target.Profile
import com.szadowsz.common.net.Uri

/**
  * Created on 16/10/2016.
  */
trait MultiTarget[T <: Any , P <: MultiTarget[T,P]] extends Profile[P] {

  val baseUrl: Uri

  val hist: Seq[T]

  val seq: Seq[T]

  override def atBeginning:Boolean = hist.isEmpty

  override def isDone: Boolean = seq.isEmpty
}
