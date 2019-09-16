package learnscala.ch8

import learnscala.ch8.Functor.Functor
import org.scalacheck.{Arbitrary, Cogen, Prop}
import org.scalatest.{FunSuite, Matchers}
import org.scalacheck.Prop.forAll

class FunctorTest extends FunSuite with Matchers {

  def id[A, F[_]](implicit F: Functor[F], arbFA: Arbitrary[F[A]]): Prop =
    forAll { as: F[A] => F.map(as)(identity) == as }

  def associativity[A, B, C, F[_]](implicit F: Functor[F],
                                   arbFA: Arbitrary[F[A]],
                                   arbB: Arbitrary[B],
                                   arbC: Arbitrary[C],
                                   cogenA: Cogen[A],
                                   cogenB: Cogen[B]): Prop = {
    forAll((as: F[A], f: A => B, g: B => C) => {
      F.map(F.map(as)(f))(g) == F.map(as)(f andThen g)
    })
  }

  test("Functor law test") {
    implicit val optionFunctor: Functor[Option] = new Functor[Option] {
      override def map[A, B](in: Option[A])(f: A => B): Option[B] = in.map(f)

      def mapC[A, B](f: A => B): Option[A] => Option[B] = (_: Option[A]).map(f)

    }

    val p1= {
      associativity[Int, String, Long, Option]
    }.check
    val p2 = {
      associativity[String, Int, Boolean, Option]
    }.check
  }
}
