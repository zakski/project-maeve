package com.szadowsz.maeve.core.instruction.extractor

import com.szadowsz.maeve.core.instruction.MaeveInstruction
import com.szadowsz.common.net.Uri

/**
  * Created on 14/10/2016.
  */
private[maeve] trait DataExtractor[P <: Any] {

  def extract(queryUrl: Uri, returnedUrl: Uri, inst : MaeveInstruction[_], page : P)

  def shouldContinue():Boolean
}
