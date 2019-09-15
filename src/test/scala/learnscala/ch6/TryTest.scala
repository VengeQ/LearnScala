package learnscala.ch6

import java.util.NoSuchElementException

import scala.util._
import org.scalatest.{FunSuite, Matchers}

class TryTest extends FunSuite with Matchers {
  test("Create test") {
    val success = Success("Good")
    val failure = Failure(new Exception("Bad"))

    success.isSuccess shouldBe true
    failure.isFailure shouldBe true

    val line = Try {
      5 / 0
    }
    intercept[ArithmeticException] {
      line.get
    }
  }

  test("Reading test"){
    val success = Success("Good")
    val failure = Failure(new Exception("Bad"))

    failure.getOrElse(2) shouldEqual 2

  }

  test("Effect test"){
    def result(n:Int, d:Int) = Try{
      n/d
    }

    val r = result(10,0)
    r.collect{case x => x.toString}
  }
}
