package app

import java.util.Date
import java.util.concurrent.atomic.AtomicLong

import javax.inject._
import akka.actor.{Actor, ActorRef}
import app.BookStorage.LibraryRequest
import app.Librarian.{BookFromStorage, BookNotFound}
import app.Literature.Book

import scala.collection.mutable

@Singleton
class BookStorage extends Actor {
    override def receive: Receive = {
    case LibraryRequest(t, f) => if (!f) {
      BookStorage.innerLibrary.find { case (b, c) => c >= 1 && b.title == t.title } match {
        case Some((b, c)) => {
          BookStorage.innerLibrary.update(b, c - 1)
          println(s"Get book. Counter `${b.title}`: ${c-1}")
          sender() ! BookFromStorage(b)
        }
        case None => sender() ! BookNotFound
      }
    } else {
      BookStorage.innerLibrary.find { case (b, c) => b.id == t.id } match {
        case Some((b, c)) =>
          BookStorage.innerLibrary.update(b, c + 1)
          println(s"Return book. Counter `${b.title}`: ${c+1}")
        case None => BookStorage.innerLibrary.update(t, 1)
          println(s"Return book. Counter `${t.title}`: 1")
      }
    }
  }
}



object BookStorage {
  val innerLibrary = mutable.Map(
    Book(1, "Learn Scala Programming", new Date(),
      "A step-by-step guide in building high-performance scalable applications with the latest features of Scala.") -> 2,
    Book(2, "Scala with Cats", new Date(),
      "The aims of this book are two-fold: to introduce monads, functors, and other\n" +
        "functional programming patterns as a way to structure program design, and to\n" +
        "explain how these concepts are implemented in Cats.") -> 4
  )

  val idCounter =new AtomicLong(1)
  idCounter.addAndGet(1)
  case class LibraryRequest(book: Book, findBook: Boolean)

}

