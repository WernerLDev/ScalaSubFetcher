
import implementation.OpenSubtitle
import ui.MainWindow
import ui.LoadingWindow
import javax.swing.UIManager
import scala.concurrent._
import ExecutionContext.Implicits.global

object Hi {

	def main(args: Array[String]) = {
	  //println("Hello world")
	  //val test = OpenSubtitle.token
	  //println("Token is: " + test)
	  //println(OpenSubtitle.logout.status)
	  println("Fetching languages")
	  LoadingWindow.open
	  
	  val availableLanguages:Future[List[String]] = Future {
		OpenSubtitle.getAvailableLanguages
      }
	  
      availableLanguages.onSuccess {
      	case langs => 
      	  	println("Creating window")
      	  	val win = new MainWindow(langs)
      	  	win.top.open
      	  	LoadingWindow.close
      }
      availableLanguages.onFailure {
        case t => println("An error has occured: " + t.getMessage())
      }
      
      LoadingWindow.progress(1)
	}

}
