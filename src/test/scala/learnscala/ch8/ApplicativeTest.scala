package learnscala.ch8

import learnscala.ch8.Applicative.Applicative
import org.scalacheck._
import org.scalacheck.Prop.forAll
import org.scalatest.{FunSuite, Matchers}

class ApplicativeTest extends FunSuite with Matchers {
  test("Application prop test") {
    def identityProp[A, F[_]](implicit A: Applicative[F], arbFA: Arbitrary[F[A]]): Prop =
      forAll {
        as: F[A] => A(as)(A.unit((a: A) => a)) == as
      }

    def homomorphism[A, B, F[_]](implicit A: Applicative[F],
                                 arbA: Arbitrary[A],
                                 arbB: Arbitrary[B],
                                 cogenA: Cogen[A]): Prop = {
      forAll((f: A => B, a: A) => {
        A(A.unit(a))(A.unit(f)) == A.unit(f(a))
      })
    }
  }
}
