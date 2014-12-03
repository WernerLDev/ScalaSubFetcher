package ui

import scala.swing._
import Swing._
import ListView._
import swing.ComboBox
import javax.swing.plaf.metal.MetalIconFactory
import javax.swing.table.DefaultTableModel
import models.SubtitleTableModel
import scala.swing.event.ButtonClicked
import java.io.File
import javax.swing.filechooser.FileNameExtensionFilter
import hasher.OpenSubtitlesHasher
import implementation._
import scala.swing.event.WindowActivated
import javax.swing.ListSelectionModel
import scala.swing.event.SelectionChanged
import java.net.URL




class MainWindow(languages:List[String]) extends SimpleSwingApplication {

  setSystemLookAndFeel()
  val subtitles = new SubtitleTableModel()
  var foundSubtitles:List[models.Subtitle] = List()
  var moviedir:String = ""
  
  def top = new MainFrame {
    title = "Scala subtitle fetcher"
    
    preferredSize = new Dimension(600, 400)
    size = new Dimension(600, 400)
    centerOnScreen
    val selectbtn = new Button {
      text = "Select a movie from your harddrive"
      icon = MetalIconFactory.getFileChooserListViewIcon()
    }
    
    val windowtop = new BoxPanel(Orientation.Horizontal){
        contents += selectbtn
        border = Swing.EmptyBorder(5)
    }
    
    val downloadbtn = new Button {
      text = "Download"
      icon = MetalIconFactory.getTreeFloppyDriveIcon()
      enabled = false
    }
    
    val status = new Label { text = "" }
    
    val footer = new BorderPanel {
      add(status, BorderPanel.Position.West )
      add(downloadbtn, BorderPanel.Position.East)
      border = Swing.EmptyBorder(5, 5, 0, 0)
    }
    
    val subtable = new Table(3, 2) {
      selection.elementMode = Table.ElementMode.Row 
      selection.intervalMode = Table.IntervalMode.Single 
      
    }
    subtable.model = subtitles
    subtable.peer.getColumnModel().getColumn(0).setResizable(true)
    subtable.peer.getColumnModel().getColumn(0).setPreferredWidth(250)
    subtable.peer.getColumnModel().getColumn(1).setResizable(true)
    subtable.peer.getColumnModel().getColumn(1).setPreferredWidth(1)
    subtable.rowHeight = 23;
    val subscroll = new ScrollPane(subtable)
    
    
    
    val languagecb = new ComboBox(languages)
    val aboutbtn = new Button { text = "About" }
    
    val topright = new BoxPanel(Orientation.Horizontal) {
      contents += new Label { text = "Language" }
      contents += HStrut(10)
      contents += languagecb
      contents += HStrut(30)
      contents += aboutbtn
      border = Swing.EmptyBorder(5)
    }

    
    
    val head = new BorderPanel {
      add(topright, BorderPanel.Position.East )
      add(windowtop, BorderPanel.Position.West)
    }
    
    contents = new BorderPanel {
      add(head, BorderPanel.Position.North)
      add(subscroll, BorderPanel.Position.Center)
      add(footer, BorderPanel.Position.South)
      border = Swing.EmptyBorder(5)
    }
    
    listenTo(downloadbtn, selectbtn, languagecb.selection, aboutbtn)
    reactions += {

      case ButtonClicked(`aboutbtn`) => AboutWindow.open
      case ButtonClicked(`downloadbtn`) => {
        
        val selected = subtitles.getValueAt(subtable.peer.getSelectedRow(), 0)
        val downloadlink = foundSubtitles.zipWithIndex.filter(_._2 == subtable.peer.getSelectedRow()).head._1.downloadlink
        downloadbtn.enabled = false
        status.text = "Downloading subtitle for " + selected
        Thread.sleep(50)
        println("Downloading... : " + selected)
        val zip = SubDownloader.downloadTo(new URL(downloadlink), new File("/tmp"))
        val archive = new ZipArchive(zip.getPath())
        archive.unzipTo(moviedir)
        println("Unzipped to: " + zip.getPath())
        println(downloadlink)
        downloadbtn.enabled = true
        status.text = "Found " + foundSubtitles.length + " subtitles"
        Dialog.showConfirmation(contents.head, 
				      "Subtitle downloaded to " + moviedir, 
				      optionType=Dialog.Options.Default ,
				      title="Download complete")
      }
      case SelectionChanged(`languagecb`) => subtitles.filterSubtitles(languagecb.selection.item, foundSubtitles)
      case ButtonClicked(`selectbtn`) => {
        val chooser = new FileChooser(new File("."))
	    val filter = new FileNameExtensionFilter("Movie files (.mkv, .avi, .mov, .mp4)", "mkv", "avi", "mov", "mp4")
	    chooser.title = "Choose a movie file"
	    chooser.controlButtonsAreShown = true
	    chooser.fileFilter = filter
	    val result = chooser.showOpenDialog(null)
	    if (result == FileChooser.Result.Approve) {
	      val hash = OpenSubtitlesHasher.computeHash(chooser.selectedFile)
	      val length = chooser.selectedFile.length()
	      downloadbtn.enabled = true
	      println("File: " + chooser.selectedFile)
	      println("Dir: " + chooser.selectedFile.getParent())
	      println("Hash: " + hash)
	      println("Size: " + length.toInt)
	      foundSubtitles = OpenSubtitle.searchSubtitles(hash, length)
	      moviedir = chooser.selectedFile.getParent
	      foundSubtitles foreach subtitles.addSubtitle
	      status.text = "Found " + foundSubtitles.length + " subtitles"
	    } else None
      }
    }
    
  }
  
  def setSystemLookAndFeel() {
    import javax.swing.UIManager
    UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel")
  } 
}