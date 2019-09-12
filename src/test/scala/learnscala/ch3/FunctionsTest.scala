package learnscala.ch3

import org.scalatest.{FunSuite, Matchers}

class FunctionsTest extends FunSuite with Matchers {
  test("by name test") {
    byName(23)
  }
  test("variable_args") {
    variable_args("hello", 1, 2, 3, 4, 5) shouldBe 3
  }
  test("Clojure example") {
    val free = 10
    def cloj(i:Int) = free +i
    cloj(20) shouldBe 30
  }
  test("Partial fn test"){
    method("Hello")(1," world") shouldBe "Hello world"
  }
  test("Fn literal"){
    something(23,"hello") shouldBe 28
  }
  test("poly fn test"){
    val glass = Full(Water(1))
    drink(glass)
  }
  test("HOF example"){
    val h = hofExample(x =>(x*x).toString)(23)
    h("hello") shouldBe 8
  }
  test("Recursion examples"){
    reverseString("abcde") shouldBe "edcba"
    reverseStringTail("abcde") shouldBe "edcba"
  }
  test("Trampolining test"){
    addTrampo(100000) shouldBe (1 to 100000).foldLeft(0)(_+_)
  }
}

