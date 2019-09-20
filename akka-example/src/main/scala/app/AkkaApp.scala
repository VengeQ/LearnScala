package app

import java.util.Date

import akka.actor._
import app.Client.FindBook
import app.Literature.Book

import scala.concurrent.Await
import scala.concurrent.duration._

object AkkaApp extends App {
  val library = ActorSystem("Library")


  val client = library.actorOf(Props[Client], "client")
  val librarian = library.actorOf(Props[Librarian], "librarian")

  val book = Book(1, "Learn Scala Programming", new Date(),
    "A step-by-step guide in building high-performance scalable applications with the latest features of Scala.")

  librarian ! FindBook(client, book.title)
  Thread.sleep(1000)
  Await.result(library.terminate(), 5.seconds)
 //   library.terminate()
}
