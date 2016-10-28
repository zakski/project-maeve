package com.szadowsz.maeve.core.instruction.target.multi

import com.szadowsz.maeve.core.instruction.target.Target
import com.szadowsz.common.net.Uri

/**
  * Trait for the common scenario in which we need to construct and target multiple uris from some provided sequence.
  *
  * Created on 16/10/2016.
  */
trait MultiTarget[T <: Any , P <: MultiTarget[T,P]] extends Target[P] {

  val baseUrl: Uri

  val hist: Seq[T]

  val seq: Seq[T]

  /**
    * Checker Function to see if we are at the beginning of the target sequence
    *
    * @return true if it is at the beginning, false otherwise.
    */
  override def atBeginning:Boolean = hist.isEmpty

  /**
    * Checker Function to see if we are at the end of the target sequence
    *
    * @return true if it is at the end, false otherwise.
    */
  override def isDone: Boolean = seq.isEmpty
}
