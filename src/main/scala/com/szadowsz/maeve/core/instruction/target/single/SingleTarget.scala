package com.szadowsz.maeve.core.instruction.target.single

import com.szadowsz.maeve.core.instruction.target.Profile
import com.szadowsz.common.net.Uri

/**
  * Created on 16/10/2016.
  */
case class SingleTarget(link: Uri) extends Profile[SingleTarget] {

  override def currentUrl(): Uri = link

  override def next(): SingleTarget = this

  override def reset(): SingleTarget = this

  override def atBeginning: Boolean = true

  override def isDone: Boolean = true
}
