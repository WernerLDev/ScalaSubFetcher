package ui

import scala.swing._

object LoadingWindow extends Frame{
  
  val progressBar = new ProgressBar() {min = 0; max = 100}
  
  title = "Loading..."
  preferredSize = new Dimension(250, 80)
  size = new Dimension(200, 200)
  centerOnScreen
  
  contents = new BoxPanel(Orientation.Vertical ) {
    contents += new Label { text = "Loading the application..." }
    contents += progressBar
    border = Swing.EmptyBorder(10)
  }
  
  def progress(p:Int) : Unit = {
    progressBar.value = p
    Thread.sleep(100)
    
    if(p < 100) progress(p + 5)
  }
  
}