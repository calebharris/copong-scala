package copong.facades.faye

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

/**
  * @author caleb
  */

@js.native
@JSImport("faye", JSImport.Namespace)
object Faye extends js.Object {

  @js.native
  class Subscription extends js.Object

  @js.native
  class Client(val endpoint: String) extends js.Object {
    def subscribe[A <: js.Object](channel: String, callback: js.Function1[A, Unit]): Subscription = js.native
    def publish[A <: js.Object](channel: String, event: A): Unit = js.native
  }

}
