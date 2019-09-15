package learnscala.ch6

import org.scalatest.{FunSuite, Matchers}


class ExercisesTest extends FunSuite with Matchers {
  test("First ex") {
    import Exercises.First._

    val first = list.headOption match {
      case Some(value) => value
      case None => None
    }
    first shouldBe "first"

    val user_2 = users.find(_.id == 2) match {
      case None => None
      case Some(user) => user
    }
    user_2 shouldBe User(2, "second")
  }

  test("Second ex") {
    import Exercises.Second._
    for (_ <- 0 to 100) {
      val result = opt
      result should be <= 9
      result should be >= -1
    }
  }

  test("Third ex") {
    import Exercises.Third._
   // t OutOfMemory is fatal error.
  }

  test("Fifth ex") {
    import Exercises.Fifth._
    either(1) shouldBe true
  }


}
