package implementation

import sys.process._
import java.io.File
import java.net.URL
import scala.language.postfixOps
import java.io.BufferedOutputStream
import java.io.FileOutputStream

object SubDownloader {

  def targetName(url:URL) : String = {
    val path = url.getPath()
    val name = url.getPath().substring(path.lastIndexOf("/") + 1)
    if (name.nonEmpty) name else "subtitle.zip"
  }
  
  def downloadTo(url:URL, dir:File) : File = {
    val outputfile = new File(dir, targetName(url))
    val conn = url.openConnection
    val out = new BufferedOutputStream(new FileOutputStream(outputfile))
    val in = conn.getInputStream
    val buffer:Array[Byte] = new Array[Byte](1024)
    Iterator.continually(in.read(buffer)).takeWhile(_ != -1).foreach(n => out.write(buffer,0,n))
    in.close()
    out.close()
    outputfile
  }
 
}