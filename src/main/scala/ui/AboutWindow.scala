package ui

import scala.swing._
import scala.swing.SimpleSwingApplication

object AboutWindow extends Frame {


	title = "About Scala subfetcher"
	
	preferredSize = new Dimension(330, 170)
	size = new Dimension(330, 170)
	centerOnScreen
	
	
	val buttonsize = new Dimension(100,25)
	val footer = new BorderPanel{
	  add(new BoxPanel(Orientation.Horizontal ){
	    contents += new Button {
	      text = "Ok"
	      this.minimumSize = buttonsize
	      this.maximumSize = buttonsize
	      this.preferredSize = buttonsize
	      this.action = Action("Ok") {
	        dispose
	      }
	    }
	  }, BorderPanel.Position.East)
	}
	
	contents = new BorderPanel {
	  add(new Label {
	    text = "<html>This is a simple subtitle fetcher written in Scala.<br>It uses the opensubtitles.org API<br>Written by Werner Langenhuisen<br>Copyright 2016 - www.icedev.nl"
	    
	  }, BorderPanel.Position.Center)
	  add(footer, BorderPanel.Position.South)
	  border = Swing.EmptyBorder(5)
	}
    
    
}