package app

import java.util.Date

import akka.actor.Actor
import app.Client.{FindBook, GetBook, ReturnBook}
import app.Librarian.{GetBookToClient, Yes}
import app.Literature.{Book, Definition, Paper}

class Librarian extends Actor{
  private var book:Option[Book]=None
  override def receive: Receive =  {
    case Book(id, t, da, de) => sender() ! Definition(s"m $id It's book '$t'")
    case Paper(id, t, d, p@pp, g@gg) => (pp, gg) match {
      case (true, _) => sender() ! Definition(s"m $id It's periodic $gg `$t`")
      case (false, _) => sender() ! Definition(s"m $id It's non-periodic $gg `$t`")
    }
    case s:String => println(s)
    case ReturnBook(l, t) => println("Ok) Come again!")
    case FindBook(c, t) => {
      println(s"I find book $t")
      Librarian.innerLibrary.find(t == _.title) match {
        case Some(x) => {
          println(s"Book found")
          book=Option(x)
          c ! Yes
        }
        case None => {
          println(s"Book didn't found")
          c ! None
        }
      }
    }

    case GetBook => {
      println("Get book")
      val book = this.book
      this.book =None
      sender() ! GetBookToClient(book.get.id)

    }

  }
}

object Librarian{
  sealed trait LibrarianMessage
  final case object Yes extends LibrarianMessage
  final case object No extends LibrarianMessage
  final case class BookWasReturn(id:Int) extends LibrarianMessage
  final case class GetBookToClient(id:Int) extends LibrarianMessage

  val innerLibrary =List (
    Book(1, "Learn Scala Programming", new Date(),
    "A step-by-step guide in building high-performance scalable applications with the latest features of Scala."),
    Book(2, "Scala with Cats", new Date(),
      "The aims of this book are two-fold: to introduce monads, functors, and other\n" +
        "functional programming patterns as a way to structure program design, and to\n" +
        "explain how these concepts are implemented in Cats.")
  )


}