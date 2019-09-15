package learnscala.ch6

import java.util.NoSuchElementException

import org.scalatest.{FunSuite, Matchers}

class OptionTest extends FunSuite with Matchers {
  test("Create test") {
    val opt = None
    val opt2 = Some(3)
    opt2.isDefined shouldBe true
    opt2.tail shouldBe Nil

    var x = 4
    opt2.getOrElse({
      x += 2
      4
    }) shouldBe 3
    x shouldEqual 4

    opt.getOrElse({
      x += 6
      4
    }) shouldBe 4
    x shouldBe 10

    val opt3 = Option(null)
    opt3 shouldBe None

    val opt4 = Option.when(2 > 21)(4)
    opt4 shouldBe None
  }

  test("Getting test") {
    val some = Option(1)
    val none: Option[Int] = None

    intercept[NoSuchElementException] {
      none.get
    }

    some.contains(1) shouldBe true
    none.contains(1) shouldBe false
    some.forall(_ > 2) shouldBe false
    none.forall(x => x > 2) shouldBe true


  }

  test("Another tests") {
    val someList = Option(List(1, 3, 5))
    someList.flatMap(l => {
      val s = l.sum
      if (s >= 10) Option(s) else None
    }) shouldBe None


    val listOfSomes = List(Option(1), None, Option(9))
    listOfSomes.flatMap(x => {
      x
    }) shouldBe List(1, 9)

    val x1: Int => Option[Int] = x => Option(x)
    val x2: Int => Option[String] = x => Option(x.toString)
    val x3: String => Option[Double] = x => Option(x.length * 2.5)
    val start = Option(10)

    start.flatMap(x1).flatMap(x2).flatMap(x3).getOrElse(0) shouldBe 10.toString.length * 2.5

    start.toRight("Left") shouldBe Right(10)
  }
}
