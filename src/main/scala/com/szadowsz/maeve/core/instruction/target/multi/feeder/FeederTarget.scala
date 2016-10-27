package com.szadowsz.maeve.core.instruction.target.multi.feeder

import com.szadowsz.maeve.core.instruction.target.multi.MultiTarget

import scala.collection.mutable.ArrayBuffer

/**
  * Created on 18/10/2016.
  */
trait FeederTarget[T <: Any,P <: FeederTarget[T,P]] extends MultiTarget[T,P] {

  override val seq: ArrayBuffer[T]

  def addToQueue(value : T) : Unit = seq += value
}
