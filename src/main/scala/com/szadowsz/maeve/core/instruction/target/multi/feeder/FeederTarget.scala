package com.szadowsz.maeve.core.instruction.target.multi.feeder

import com.szadowsz.maeve.core.instruction.target.multi.MultiTarget

import scala.collection.mutable.ArrayBuffer

/**
  * Trait for the scenario in which we need to add to the target sequence mid run.
  *
  * Created on 18/10/2016.
  */
trait FeederTarget[T <: Any,P <: FeederTarget[T,P]] extends MultiTarget[T,P] {

  override val seq: ArrayBuffer[T]

  /**
    * Method to add a value to the end of the queue
    *
    * @param value the added value
    */
  def addToQueue(value : T) : Unit = seq += value
}
