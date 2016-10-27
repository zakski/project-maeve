package com.szadowsz.maeve.gglegrp

import com.szadowsz.common.io.explore.{ExtensionFilter, FileFinder}
import com.szadowsz.maeve.core.util.recovery.RecoveryUtil
import com.szadowsz.common.io.read.FReader
import com.szadowsz.common.net.Uri

/**
  * Created on 21/10/2016.
  */
object GrpComErrorDelta {

  def calcDelta(groupDir: String, recoveDir: String, group : String): List[String] = {
    val topicFiles = FileFinder.search(groupDir, Option(new ExtensionFilter(".txt", true)))
    val topicUrls = topicFiles.map { file =>
      val read = new FReader(file.getAbsolutePath)
      val line = read.readLine()
      line.substring(line.indexOf(" ")).trim
    }
    val history = RecoveryUtil.doRecovery(recoveDir,group)
    history.filterNot(topicUrls.contains).distinct.map(Uri(_).fragment)
  }
}
