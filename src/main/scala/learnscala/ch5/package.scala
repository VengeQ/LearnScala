package learnscala

import scala.annotation.tailrec

package object ch5 {
  def factorialT(i: Int): Either[String, Int] = {

    @tailrec def go(i: Int, accum: Int): Int = {
      if (i <= 2) accum else
        go(i - 1, i * accum)
    }

    if (i < 0) Left("Input value can't be lesser than Zero.")
    else Right(go(i, 1))
  }
}
