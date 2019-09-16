package learnscala.ch8

import learnscala.ch8.Functor.Functor

object Applicative {
  trait Applicative[F[_]] extends Functor[F]{
    def apply[A,B](a: F[A])(f: F[A => B]): F[B]
    def unit[A](a: => A): F[A]
  }


}
