package learnscala

import scala.annotation.tailrec

package object ch3 {
  def byName(int: => Int) = {
    println(s"$int")
  }

  def variable_args(name: String, numbers: Int*): Int = {
    val result = numbers.foldLeft((0, 0))((accum, current) => (accum._1 + current, accum._2 + 1))
    if (result._2 == 0) 0 else result._1 / result._2
  }

  def method(name: String) = {
    def fn(in1: Int, in2: String) = name + in2

    fn(_, _)
  }

  val something: (Int, String) => Int = (a, b) => a + b.length

  sealed trait Glass[+Contents]

  case class Full[Contents](c: Contents) extends Glass[Contents]

  case object EmptyGlass extends Glass[Nothing]

  case class Water(purity: Int)

  def drink[T <: Water](glass: Glass[T]) = glass match {
    case Full(x) => println(s"drinking ${x}")
    case EmptyGlass => println(s"Nothing to drink")
  }

  def hofExample(h: Int => String)(input: Int): String => Int = {
    val res = h(input)
    (x: String) => res.length + x.length
  }

  def reverseString(s: String): String = {
    if (s.length <= 1) s else
      s.last.toString concat reverseString(s.substring(0, s.length - 1))
  }

  def reverseStringTail(s: String): String = {
    @tailrec def go(init: String, result: String): String =
      if (init.length < 2)
        init + result
      else go(init.tail, init.head + result)

    go(s, "")
  }

  def F(n: Int): Int = if (n == 0) 1 else n - M(F(n - 1))

  def M(n: Int): Int = if (n == 0) 0 else n - F(M(n - 1))

  import util.control.TailCalls._

  def tailA(n: Int): TailRec[Int] = {
    if (n == 1) done(1)
    else tailcall((tailA(n - 1).map((x: Int) => x + n)))
  }

  def addTrampo(n: Int) = tailA(n).result

  def addNonTail(n: Int): Int = {
    if (n == 1) 1 else addNonTail(n - 1) + n
  }

}
