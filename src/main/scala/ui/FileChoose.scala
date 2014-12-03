package ui

import scala.swing.FileChooser
import java.io.File
import javax.swing.filechooser.FileNameExtensionFilter
import hasher.OpenSubtitlesHasher

class FileChoose {

  def run() : Unit = {
    val chooser = new FileChooser(new File("."))
    val filter = new FileNameExtensionFilter("Movies", "mkv", "movies")
    chooser.title = "Choose file"
    chooser.controlButtonsAreShown = true
    chooser.fileFilter = filter
    val result = chooser.showOpenDialog(null)
    if (result == FileChooser.Result.Approve) {
      val hash = OpenSubtitlesHasher.computeHash(chooser.selectedFile)
      val length = chooser.selectedFile.length()
      println("Approve -- " + chooser.selectedFile)
      println("Hash: " + hash)
      println("Size: " + length)
      Some(chooser.selectedFile)
    } else None
  }
  
}