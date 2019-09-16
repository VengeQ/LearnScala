package learnscala.ch7

import learnscala.ch7.Monoid.Monoid

object Foldable {
  trait MonoidFoldable[A, F[_]] {
    def foldRight(as: F[A])(i: A, op: (A,A) => A): A
    def foldLeft(as: F[A])(i: A, op: (A,A) => A): A
    def foldBalanced(as: F[A])(i: A, op: (A,A) => A): A
  }

  implicit def listMonoidFoldable[A : Monoid]: MonoidFoldable[A, List] = new MonoidFoldable[A, List] {
    private val m = implicitly[Monoid[A]]
    override def foldRight(as: List[A])(i: A, combine: (A, A) => A): A = as.foldRight(m.id)(m.combine)

    override def foldLeft(as: List[A])(i: A, combine: (A, A) => A): A = as.foldLeft(m.id)(m.combine)

    override def foldBalanced(as: List[A])(i: A, combine: (A, A) => A): A = as match {
      case Nil => m.id
      case List(one) => one
      case _ => val (l, r) = as.splitAt(as.length/2)
        m.combine(foldBalanced(l)(m.id, m.combine), foldBalanced(r)(m.id, m.combine))
    }


  }
}
