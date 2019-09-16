package learnscala.ch7

import scala.annotation.tailrec

object Monoid {
  import Semigroup._
  trait Monoid[A] extends Semigroup[A]{
    def id:A
  }

  implicit def SemigroupList[T]:Semigroup[List[T]] = (l: List[T], r: List[T]) => {
    @tailrec def go(init: List[T], result: List[T]): List[T] = {
      init match {
        case Nil => result
        case x :: Nil => x :: result
        case x :: xs => go(init.tail, x :: result)
      }
    }
    go(l.reverse, r)
  }

  implicit def MonoidList[T]:Monoid[List[T]] = new Monoid[List[T]]{
    override def id: List[T] = List.empty
    override def combine(l: List[T], r: List[T]): List[T] = SemigroupList[T].combine(l,r)
  }
}
