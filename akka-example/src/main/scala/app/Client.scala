package app

import akka.actor.{Actor, ActorRef}
import app.Client.GetBook
import app.Librarian.{GetBookToClient, No, Yes}

class Client extends Actor {
  override def receive: Receive = {
    case Yes => {
      println("Give me this book")
      sender() ! GetBook
    }
    case No => sender() ! "Saaaaad!!"
    case GetBookToClient(i) => sender() ! "Thanks!"
  }
}

object Client {

  final case class Request(request: ClientRequest)

  sealed trait ClientRequest

  final case class FindBook(client:  ActorRef, title: String) extends ClientRequest

  final case object GetBook extends ClientRequest

  final case class ReturnBook(client: ActorRef,title: String) extends ClientRequest

}

