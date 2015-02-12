package code.comet

import code.model.Todo
import net.liftweb._
import net.liftweb.http._
import net.liftweb.http.js.JsCmds
import net.liftweb.json.JsonAST.JValue
import net.liftweb.util._

/**
 * The screen real estate on the browser will be represented
 * by this component.  When the component changes on the server
 * the changes are automatically reflected in the browser.
 */
class TodoNotifier extends CometActor with CometListener {
  private var msgs: List[Todo] = List() // private state

  /**
   * When the component is instantiated, register as
   * a listener with the ChatServer
   */
  def registerWith = CometServer

  /**
   * The CometActor is an Actor, so it processes messages.
   * In this case, we're listening for Vector[String],
   * and when we get one, update our private state
   * and reRender() the component.  reRender() will
   * cause changes to be sent to the browser.
   */
  override def lowPriority = {
    case m: List[Todo] => {
      msgs = m
      val json = net.liftweb.json.compactRender(msgs: JValue)
      println(s"TodoNotifier received update; sending JSON: $json")
      partialUpdate(JsCmds.Run(s"console.log('$json'); Todos.reset(); Todos.add($json)"))
    }
  }

  /**
   * Put the messages in the li elements and clear
   * any elements that have the clearable class.
   */
  def render = JsCmds.Run("")
}
