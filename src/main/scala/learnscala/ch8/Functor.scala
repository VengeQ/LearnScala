package learnscala.ch8

object Functor {

  trait Functor[F[_]] {
    def map[A, B](fa: F[A])(fn: A => B): F[B]
    def mapC[A,B](f: A => B): F[A] => F[B]
  }

}
