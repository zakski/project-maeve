package com.szadowsz.maeve.core.instruction.extractor

import com.szadowsz.maeve.core.instruction.MaeveInstruction
import com.szadowsz.common.net.Uri

/**
  * Base trait for extraction of Data from a Webpage.
  *
  * Created on 14/10/2016.
  */
private[maeve] trait DataExtractor[P <: Any] {

  /**
    * Generic method to extract data from a webpage.
    *
    * @param queryUrl the expected url of the page.
    * @param returnedUrl the actual url of the page.
    * @param inst the current maeve instruction.
    * @param page the webpage in whatever format is being provided.
    */
  def extract(queryUrl: Uri, returnedUrl: Uri, inst : MaeveInstruction[_], page : P)

  /**
    * Function to check if retrieval is finished for the current page.
    *
    * @return true if we should continue to extract data from the current page, false otherwise.
    */
  def shouldContinue():Boolean
}
