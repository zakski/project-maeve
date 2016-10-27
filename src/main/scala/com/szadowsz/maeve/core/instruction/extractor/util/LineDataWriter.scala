package com.szadowsz.maeve.core.instruction.extractor.util

import java.io.File

import com.szadowsz.maeve.core.instruction.extractor.DataExtractor
import com.szadowsz.common.io.write.FWriter

/**
  * Created on 17/10/2016.
  */
trait LineDataWriter {
  this: DataExtractor[_] =>

  def exists(path : String, name : String):Boolean = new File(path + name + ".txt").exists()

  def write(path : String, name : String, lines: List[String], append : Boolean): Unit = {
    val writer = FWriter(path + name + ".txt", append)
    lines.foreach(writer.writeLine)
    writer.close()
  }
}
