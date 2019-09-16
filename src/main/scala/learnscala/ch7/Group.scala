package learnscala.ch7

import learnscala.ch7.Monoid.Monoid

object Group {

  trait Group[S] extends Monoid[S] {
    def inverse(a: S): S
  }

  object implicits {
    implicit val intAddMonoid: Monoid[Int] = new Monoid[Int] {
      override def id: Int = 0

      override def combine(l: Int, r: Int): Int = l + r
    }
    implicit val intAddGroup: Group[Int] = new Group[Int] {
      override def inverse(a: Int): Int = intAddMonoid.id - a

      override def id: Int = intAddMonoid.id

      override def combine(l: Int, r: Int): Int = intAddMonoid.combine(l, r)
    }
  }

}
