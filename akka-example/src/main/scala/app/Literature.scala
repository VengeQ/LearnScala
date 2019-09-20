package app

import java.util.Date

import akka.actor.Actor
import app.Literature._

class Literature extends Actor {
  override def receive: Receive = {
    case Book(id, t, da, de) => sender() ! Definition(s"m $id It's book '$t'")
    case Paper(id, t, d, p@pp, g@gg) => (pp, gg) match {
      case (true, _) => sender() ! Definition(s"m $id It's periodic $gg `$t`")
      case (false, _) => sender() ! Definition(s"m $id It's non-periodic $gg `$t`")
    }
  }
}

object Literature {

  final case class Book(id: Int, title: String, date: Date, definition: String)

  final case class Paper(id: Int, title: String, date: Date, isPeriodic: Boolean, genre: PaperGenre)

  sealed trait PaperGenre

  final case object Scientific extends PaperGenre

  final case object Magazine extends PaperGenre

  final case class Definition(string: String)

}