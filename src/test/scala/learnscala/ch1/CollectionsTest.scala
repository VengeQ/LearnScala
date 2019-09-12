package learnscala.ch1

import org.scalatest.{FunSuite, Matchers}

import scala.collection.IterableOnce
import scala.collection.immutable.VectorMap

class CollectionsTest extends FunSuite with Matchers {
  test("Collections Test") {
    Vector.iterate(1, 10)(_ * 2)(4) shouldEqual Math.pow(2, 4)
    val vm:VectorMap[String, Int] =VectorMap("key" ->1, "value" ->2)
    val str = vm.values.fold("".toString)((x, y) => x.toString+y.toString)
    str shouldEqual "12"
  }
}
