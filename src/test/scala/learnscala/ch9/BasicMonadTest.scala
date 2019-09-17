package learnscala.ch9

import org.scalatest.{FunSuite, Matchers}

class BasicMonadTest extends FunSuite with Matchers {
  test("Monad option test") {
    import BasicMonad._
    import BasicMonad.implicits._

    val im = implicitly[Monad[Option]]
    val imf = implicitly[Monad[Option]]

    val s1 = im.unit("Hello")
    val s2 = im.unit("Bye")
    val s3 = im.unit(None)

    im.flatMap(s1)(s => Option(s.length)) shouldBe Some(5)
    im.flatMap(s2)(s => Option(s.length)) shouldBe Some(3)
  //  im.flatMap(s3)(s => Option(s.length)) shouldBe None


  }
}
