package implementation

import scala.language.postfixOps
import scala.collection.JavaConversions._
import org.apache.xmlrpc.client.XmlRpcClient
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl
import org.apache.xmlrpc.client.XmlRpcSunHttpTransportFactory
import java.net.URL
import java.util.HashMap
import xmlrpc.StringTypeFactory
import models.Subtitle


case class OSAPIParameters(user:String,pwd:String,agent:String,url:String,language:String) 
case class LoginResult(seconds:String, status:String, token:String)
case class LogoutResult(seconds:String, status:String)

class XmlRpc(inp :OSAPIParameters) {

	val config = new XmlRpcClientConfigImpl
    config.setServerURL(new URL(inp.url))
    config.setUserAgent(inp.agent)
    config.setEnabledForExtensions(true)

    
    val client = new XmlRpcClient
    client.setTransportFactory(new XmlRpcSunHttpTransportFactory(client))
    client.setConfig(config)
    client.setTypeFactory(new StringTypeFactory(client))
    
    def getServerInfo() : Object = client.execute("ServerInfo", new Array[Object](0))

    def login():LoginResult = {
      val params = Array[Object](inp.user, inp.pwd, inp.language, inp.agent)
      
      val result = client.execute("LogIn", params).asInstanceOf[java.util.HashMap[Any, Any]]
      new LoginResult(result.get("seconds").toString, result.get("status").toString, result.get("token").toString)
    }
	
	def getAvailableLanguages() : List[String] = {
	  val result = client.execute("GetSubLanguages", Array[Object]("en")).asInstanceOf[java.util.HashMap[Any, Any]]
	  val datarows = result.get("data").asInstanceOf[Array[Any]].toList
	  datarows.map(x => {
		  x match {
	  		case m : HashMap[Any, Any] => m.get("LanguageName").toString
		  }
	  })
	}
    
    def searchSubtitles(token:String, hash:String, size:Long) : List[Subtitle] = {
      
      val movieMap = new HashMap[String, Object]
      movieMap.put("sublanguageid", "all")
      movieMap.put("moviehash", hash)
      movieMap.put("moviebytesize", new java.lang.Long(size))
      
      val params = new java.util.Vector[Any]
      params.add(token)
      params.add(Array[Object](movieMap))
      
      val result = client.execute("SearchSubtitles", params).asInstanceOf[java.util.HashMap[Any, Any]]
      if(result.get("data") == false) List()
      else {
         val datarows = result.get("data").asInstanceOf[Array[Any]].toList
	     val subtitles = datarows.map(x => {
	        x match {
	          case m : HashMap[Any, Any] => Subtitle(
	        		  							m.get("MovieReleaseName").toString,
	        		  							m.get("LanguageName").toString,
	        		  							m.get("ZipDownloadLink").toString)
	        }
	      })
	      subtitles.sortBy(_.language)
      }
    }
    
    def logout(token:String):LogoutResult = {
      val params = new Array[Object](1)
      params(0) = new String(token)
      val result = client.execute("LogOut", params).asInstanceOf[java.util.HashMap[Any, Any]]
      new LogoutResult(result.get("seconds").toString, result.get("status").toString)
    }
}


object OpenSubtitle {

  val params = new OSAPIParameters("", "", "ScalaSubFetcher V1.0", "http://api.opensubtitles.org/xml-rpc", "en")
  val client = new XmlRpc(params)
  
  val token = client.login().token 
  
  def logout():LogoutResult = client.logout(token)
  
  def searchSubtitles(moviehash:String, size:Long) : List[Subtitle] = client.searchSubtitles(token, moviehash, size)
  def getAvailableLanguages() : List[String] = "All" :: client.getAvailableLanguages
  
}