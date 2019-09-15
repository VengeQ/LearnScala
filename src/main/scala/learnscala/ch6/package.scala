package learnscala

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.util.Try

package object ch6 {
  case class Err(text:String)
  case class Ok[A](ok:A)

  object Exercises{
    object First{
      val list=List("first", "second", "third")

      case class User(id:Long, name:String)
      val users = List(User(1,"first"), User(2,"second"), User(3,"third"))

    }

    object Second{
      def opt = Option(scala.util.Random.nextInt(10)).fold(9)(_-1)
    }

    object Third{
      val t = Try[Int](throw new OutOfMemoryError()).filter(_ > 10).recover {
        case _: OutOfMemoryError => 100
      }
    }
    object Fourth{
    //  val t =Future[Int](throw new OutOfMemoryError()).filter(_ > 10).recover {
     //   case _: OutOfMemoryError => 100
    //  }(20) -Can't compile
    }

    object Fifth{
      def either(i: Int): Boolean =
        Either.cond(i > 10, i * 10, new IllegalArgumentException("Give me more")).forall(_ < 100)
    }
  }
}
