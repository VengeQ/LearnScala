package learnscala.ch9

import scala.language.higherKinds

object BasicMonad {

  trait Monad[F[_]] {
    def unit[A](a: => A): F[A]

    def flatMap[A, B](a: F[A])(f: A => F[B]): F[B]
  }

  trait MonadF[F[_]] {
    def unit[A](a: => A): F[A]

    def map[A, B](a: F[A])(f: A => B): F[B]

    def flatten[A](fa: F[F[A]]): F[A]
  }

  object implicits {

    implicit def MonadOption[M]: Monad[Option] = new Monad[Option] {
      override def unit[A](a: => A): Option[A] = a match {
        case None | null => None:Option[A]
        case a => Some(a)
      }

      override def flatMap[A, B](a: Option[A])(f: A => Option[B]): Option[B] = a match {
        case None => None
        case Some(value) => (f(value))
      }
    }

    implicit def MonadFOption[M]: MonadF[Option] = new MonadF[Option] {
      override def unit[A](a: => A): Option[A] = a match {
        case None | null => None
        case a => Some(a)
      }

      override def map[A, B](a: Option[A])(f: A => B): Option[B] = a match {
        case None => None
        case Some(value) => Some(f(value))
      }

      override def flatten[A](fa: Option[Option[A]]): Option[A] = fa match {
        case Some(x@Some(v)) => Some(v)
        case _ => None
      }
    }
  }

}
