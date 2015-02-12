package code.model

import net.liftweb.json.Extraction
import net.liftweb.json.JsonAST.JValue

case class Todo(title: String, order: Int, done: Boolean)

object Todo {
  implicit val formats = net.liftweb.json.DefaultFormats

  implicit def toJson(todo: Todo): JValue =
    Extraction.decompose(todo)

  implicit def toJson(todos: Seq[Todo]): JValue =
    Extraction.decompose(todos)
  
  def unapply(json: JValue): Option[Todo] =
    Extraction.extractOpt[Todo](json)
}
