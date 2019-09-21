package app

import java.util.Date

import javax.inject._
import akka.actor.{Actor, ActorRef}
import app.BookStorage.LibraryRequest
import app.Client.{BookFromLibrarian, MissingBook}
import app.Librarian.{BookFromStorage, BookNotFound, BookRequest, ReturnBook}
import app.Literature.{Book, Definition, Paper}

class Librarian(bookStorage: ActorRef) extends Actor {
  private var book: Option[Book] = None
  private var currentClient: Option[ActorRef] = None

  override def receive: Receive = {

    //From client
    case ReturnBook(_, book) => {
      println("Ok) Come again!")
      bookStorage ! LibraryRequest(book, true)
    }
    case BookRequest(c, b) => {
      currentClient = Option(c)

      bookStorage ! LibraryRequest(b, false)
    }
    //From Storage
    case BookNotFound => {
      currentClient match {
        case Some(c) => c ! MissingBook
        case None => println("Warn!")
      }
    }
    case  BookFromStorage(book: Book) => {
      currentClient match {
        case Some(c) =>c ! BookFromLibrarian(book)
        case None => println("Warn!")
      }
    }
  }
}

object Librarian {

  final case class BookRequest(client: ActorRef, book: Book)

  final case class BookFromStorage(book: Book)

  final case object BookNotFound

  final case class ReturnBook(client: ActorRef, book: Book)

  def apply(bookStorage: ActorRef): Librarian = new Librarian(bookStorage)

}