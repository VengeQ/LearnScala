package learnscala.ch6
import org.scalatest.{FunSuite, Matchers}

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.util.Try

class FutureTest extends FunSuite with Matchers {


  test("Futures example"){
    val s =Future.fromTry{
      Thread.sleep(1000)
      Try(10/2)
    }
    while (!s.isCompleted){
      Thread.sleep(100)
    }

    s.value.get.get shouldBe 10/2

  }


}
