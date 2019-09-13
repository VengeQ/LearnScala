package learnscala.ch5

import org.scalatest.{FunSuite, Matchers}
import org.scalacheck.Prop.{forAll, propBoolean}

import scala.language.postfixOps
import scala.annotation.tailrec
import scala.math.Ordering

class SimpleTest extends FunSuite with Matchers {
  test("Smoke test") {


  }

  test("exercises test1") {
    def propInv = forAll {
      l: List[Int] => l.length == l.sortWith((x, y) => x.compareTo(y) > 0).length
    }

    propInv.check

    def propIdem = forAll {
      l: List[Int] => l.sorted == l.sorted.sorted.sorted
    }

    propIdem.check

    def checkAll[T](list: List[T])(implicit ev:Ordering[T]):Boolean = {
      list match {
        case Nil => true
        case x::Nil => true
        case x::xs => ev.lteq(x, xs.head) && checkAll(xs)
      }
    }

    def propInduct = forAll {
      l: List[Int] =>
        checkAll(l.sorted)
    }

    propInduct.check
  }
}
