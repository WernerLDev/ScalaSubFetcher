
import ui.MainWindow
import javax.swing.UIManager
import implementation.SubFetcherCli

object ScalaSubFetcher {

	def main(args: Array[String]) = {


		if(args.length > 0) {
		  val lang = {
		    if(args.length == 2) args(1)
		    else "All"
		  }
		  val file = args(0)
		  val fetcher = SubFetcherCli(file, lang)
		  fetcher.runCmd()
		} else {
	    val win = new MainWindow

      win.top.open
    }
	}

}