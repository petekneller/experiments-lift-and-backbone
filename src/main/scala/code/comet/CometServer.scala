package code.comet

import code.model.Todo
import net.liftweb._
import net.liftweb.actor._
import net.liftweb.http._

/**
 * A singleton that provides chat features to all clients.
 * It's an Actor so it's thread-safe because only one
 * message will be processed at once.
 */
object CometServer extends LiftActor with ListenerManager {
  private var msgs = List(Todo("example todo...", 1, false)) // private state

  def messages = msgs

  /**
   * When we update the listeners, what message do we send?
   * We send the msgs, which is an immutable data structure,
   * so it can be shared with lots of threads without any
   * danger or locking.
   */
  def createUpdate = msgs

  /**
   * process messages that are sent to the Actor.  In
   * this case, we're looking for Strings that are sent
   * to the ChatServer.  We append them to our Vector of
   * messages, and then update all the listeners.
   */
  override def lowPriority = {
    case t: Todo => msgs :+= t; updateListeners()
  }
}
