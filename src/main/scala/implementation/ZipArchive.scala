package implementation

import scala.collection.JavaConversions._
import scala.language.postfixOps
import java.io.File
import java.util.zip._
import java.io.OutputStream
import java.io.InputStream
import java.io.FileOutputStream

class ZipArchive(source:String) {

  val zipfile = new ZipFile(source)
  
  def unzipTo(targetdir:String) : Unit = {
    if(new File(source).exists) {
      unzipAll(zipfile.entries.toList, new File(targetdir)) 
    }
  }
  
  def unzipAll(entries:List[ZipEntry], target:File) : Unit = entries match {
    case entry :: entries => 
      if(entry.isDirectory) new File(target, entry.getName) mkdirs
      else save(zipfile.getInputStream(entry), new FileOutputStream(new File(target, entry.getName)))
      unzipAll(entries, target)
    case _ =>
  }
  
  def save(in:InputStream, out:OutputStream) : Unit = {
    val buffer:Array[Byte] = new Array[Byte](1024)
    Iterator.continually(in.read(buffer)).takeWhile(_ != -1).foreach(n => out.write(buffer,0,n))
    in.close()
    out.close()
  }
  
}