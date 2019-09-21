package app

import akka.actor.{Actor, ActorRef}
import app.Client.{BookFromLibrarian, MissingBook}
import app.Literature.Book

class Client extends Actor {
  override def receive: Receive = {
    case BookFromLibrarian(book: Book) => {
      println("Thank you!")
    }
    case MissingBook
    => println("Saaaaad!!")
  }
}

object Client {

  final case object MissingBook
  final case class BookFromLibrarian(book: Book)

}

