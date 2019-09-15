package learnscala.ch6

import org.scalatest.{FunSuite, Matchers}

class EitherTest extends FunSuite with Matchers {
  test("Either test"){
    val x:Either[Err, Ok[String]]=Right(Ok("Hello"))
    x.map((ok => ok.ok)).getOrElse("") shouldBe "Hello"

    def y(init:Int) = Either.cond(init>10, init, init.toString)
    y(20) shouldBe Right(20)
    y(5) shouldBe Left("5")
    y(25).isRight shouldBe true

    val right = y(12)
    right.exists(_ == 12) shouldBe true
    right.filterOrElse(_ > 12, "wow") shouldEqual Left("wow")
  }
}
