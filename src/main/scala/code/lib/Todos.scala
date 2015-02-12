package code.lib

import code.comet.CometServer
import code.model._
import net.liftweb._
import common._
import http._
import rest._
import util._
import Helpers._
import json._
import scala.xml._

object Todos extends RestHelper {

  // Serve /api/item and friends
  serve( "api" / "todos" prefix {

    // /api/item returns all the items
    case Nil Get _ => CometServer.messages: JValue

    case Nil JsonPost Todo(todo) -> _ => {
      CometServer ! todo
      todo: JValue
    }

  })

}