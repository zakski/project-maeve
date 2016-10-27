package com.szadowsz.maeve.core.instruction.target

import com.szadowsz.common.net.Uri

/**
  * Created on 15/05/2016.
  */
trait Profile[P <: Profile[P]] {

  def currentUrl(): Uri

  def next(): P

  def reset(): P

  def atBeginning: Boolean

  def isDone: Boolean
}