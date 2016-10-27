package com.szadowsz.maeve.core.util.recovery

import java.io.File

import com.szadowsz.maeve.core.instruction.MaeveInstruction
import com.szadowsz.common.io.read.FReader
import com.szadowsz.common.io.write.FWriter

/**
  * Created on 13/10/2016.
  */
object RecoveryUtil {


  def doRecovery(path: String, instr: MaeveInstruction[_]): MaeveInstruction[_] = {
    //    instr.recovMode match {
    //      case RecoveryMode.PAGES =>
    instr.applyLinkHistory(doRecovery(path, instr.name))
    //      case RecoveryMode.PARTIAL_RESULT => instr.applyWorkerHistory(doRecovery(path, instr.name))
    //      case _ =>
    //        instr.applyLinkHistory(doRecovery(path, instr.name + "-PAGES"))
    //          .applyWorkerHistory(doRecovery(path, instr.name + "-Workers"))
    //    }
  }

  def doRecovery(path: String, name: String): List[String] = {
    val filepath = path + name + ".recov"
    if (new File(filepath).exists) {
      val reader = new FReader(filepath)
      Iterator.continually(reader.readLineOpt()).takeWhile(_.isDefined).map(_.get).toList
    } else {
      Nil
    }
  }

  def appendRecovery(path: String, name: String, line: String): Unit = {
    val filepath = path + name + ".recov"
    val writer = new FWriter(filepath, true)
    writer.writeLine(line)
    writer.close()
  }
}
