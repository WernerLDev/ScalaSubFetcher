package implementation

import hasher.OpenSubtitlesHasher
import implementation._
import java.io.File
import java.net.URL

case class SubtitleCli(id:Int, name:String, language:String, downloadurl:String) {
  override def toString() = "[" + id + "] : \t" + language + "\t\t\t : " + name
}

case class SubFetcherCli(file:String, language:String) {

  def runCmd() = {
    val f = new File(file)
    val length = f.length()
    val hash = OpenSubtitlesHasher.computeHash(f)
    val subtitles = OpenSubtitle.searchSubtitles(hash, length)
    val subs = subtitles.zipWithIndex.map(x => SubtitleCli(x._2, x._1.name, x._1.language, x._1.downloadlink))
    subs foreach (x => {
      printf("[%-2s] %-15.15s %-20s\n", x.id, x.language, x.name.trim)
    })
    val choice = getInt("Enter number: ")
    val subchoice = subs.filter(_.id == choice.getOrElse(-1))
    if(subchoice.length > 0) {
      val subtitle = subchoice.head
      println("Downloading " + subtitle.name)
      val zipfile = SubDownloader.downloadTo(new URL(subtitle.downloadurl), new File("/tmp"))
      val archive = new ZipArchive(zipfile.getPath())
      archive.unzipTo(f.getParent())
      println("Subtitle downloaded to " + f.getParent())
    } else {
      println("Invalid option")
    }
    
  }

  def getInt(msg:String):Option[Int] = {
    val out = scala.io.StdIn.readLine(msg)
    try {
      Some(out.toInt)
    } catch {
      case e:Exception => None
    }
  }

}
