package app

import java.util.Date

import akka.actor._
import app.Librarian.{BookRequest, ReturnBook}
import app.Literature.Book

import scala.concurrent.Await
import scala.concurrent.duration._

object AkkaApp extends App {
  val library = ActorSystem("Library")

  val storage =library.actorOf(Props[BookStorage], "storage")
  val librarianProp= Props(classOf[Librarian], storage)

  val client = library.actorOf(Props[Client], "client")
  val librarian = library.actorOf(librarianProp, "librarian")

  val book = Book(1, "Learn Scala Programming", new Date(),
    "A step-by-step guide in building high-performance scalable applications with the latest features of Scala.")
  val book2 = Book(2, "Ping pong", new Date(),
    "Ping pong guide")

  librarian ! BookRequest(client, book)
  librarian ! BookRequest(client, book2)
  Thread.sleep(10)
  librarian ! ReturnBook(client,book)

  Await.result(library.terminate(), 5.seconds)

}
