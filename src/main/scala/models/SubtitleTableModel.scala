package models

import javax.swing.table.DefaultTableModel

case class Subtitle(name:String, language:String, downloadlink:String) {
  override def toString() = name + " : " + language + " : " + downloadlink
}


class SubtitleTableModel extends DefaultTableModel{

  addColumn("Name")
  addColumn("Language")
  
  def addSubtitle(name:String, lang:String) : Unit = addRow(Array[Object](name, lang))
  def addSubtitle(sub:Subtitle) : Unit = addRow(Array[Object](sub.name, sub.language))
  
  def emptyTable() : Unit = this.setNumRows(0)
  
  def filterSubtitles(lang:String, subtitles:List[Subtitle]) : Unit = {
    
    emptyTable
    subtitles.filter(_.language == lang || lang == "All") foreach addSubtitle
    
  }
  
  override def isCellEditable(x:Int, y:Int) : Boolean = false

}