package com.szadowsz.maeve.core.instruction.extractor.util

import java.io.File

import com.szadowsz.maeve.core.instruction.extractor.DataExtractor
import com.szadowsz.common.io.write.FWriter

/**
  * Trait to provide support to write lines to text files.
  *
  * Created on 17/10/2016.
  */
trait TxtFileLineWriter {
  this: DataExtractor[_] =>

  /**
    * Function to check if file already exists.
    *
    * @param path the file path of the file.
    * @param name the name of the file, minus its extension.
    * @return true if it exists, false otherwise.
    */
  def exists(path : String, name : String):Boolean = new File(path + name + ".txt").exists()

  /**
    * Function to add lines to a file.
    *
    * @param path the file path of the file.
    * @param name the name of the file, minus its extension.
    * @param lines the lines to add.
    * @param append whether or not to append to the file or overwrite it.
    */
  def write(path : String, name : String, lines: List[String], append : Boolean): Unit = {
    val writer = FWriter(path + name + ".txt", append)
    lines.foreach(writer.writeLine)
    writer.close()
  }
}
