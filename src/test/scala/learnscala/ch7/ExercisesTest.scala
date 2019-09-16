package learnscala.ch7

import org.scalatest.{FunSuite, Matchers}

class ExercisesTest extends FunSuite with Matchers {
  test("2 Ex") {
    import Monoid._
    implicit val monoidOr: Monoid[Boolean] = new Monoid[Boolean] {
      override def id: Boolean = false

      override def combine(l: Boolean, r: Boolean): Boolean = l || r
    }

    val ic = implicitly[Monoid[Boolean]]
    for (_ <- 0 to 100) {
      val f = scala.util.Random.nextBoolean()
      val s = scala.util.Random.nextBoolean()
      val t = scala.util.Random.nextBoolean()
      ic.combine(f, ic.combine(s, t)) shouldEqual ic.combine(ic.combine(f, s), t)
      ic.combine(f, ic.id) shouldEqual f
    }
  }

  test("3 Ex") {
    import Monoid._
    implicit val monoidAnd: Monoid[Boolean] = new Monoid[Boolean] {
      override def id: Boolean = true

      override def combine(l: Boolean, r: Boolean): Boolean = l && r
    }

    val ic = implicitly[Monoid[Boolean]]
    for (_ <- 0 to 100) {
      val f = scala.util.Random.nextBoolean()
      val s = scala.util.Random.nextBoolean()
      val t = scala.util.Random.nextBoolean()
      ic.combine(f, ic.combine(s, t)) shouldEqual ic.combine(ic.combine(f, s), t)
      ic.combine(f, ic.id) shouldEqual f
    }
  }

  test("4 Ex") {
    import Monoid._

    implicit def MonoidOption[A: Monoid]: Monoid[Option[A]] = new Monoid[Option[A]] {
      override def id: Option[A] = None

      override def combine(l: Option[A], r: Option[A]): Option[A] = (l, r) match {
        case (Some(la), Some(rb)) => Option(implicitly[Monoid[A]].combine(la, rb))
        case _ => l orElse r
      }
    }

    implicit val monoidString: Monoid[String] = new Monoid[String] {
      override def id: String = ""

      override def combine(l: String, r: String): String = l + r
    }

    val s1 = Option("hello")
    val s2 = Option("world")
    val s3: Option[String] = None

    val ic = implicitly[Monoid[Option[String]]]

    ic.combine(s1, s2) shouldEqual Some("helloworld")
    ic.combine(s1, s3) shouldEqual Some("hello")
    ic.combine(s3, s2) shouldEqual Some("world")
    ic.combine(s3, ic.id) shouldEqual None


  }

  test("5 ex") {
    import Monoid._
    implicit def eitherMonoid[L, R: Monoid]: Monoid[Either[L, R]] = new Monoid[Either[L, R]] {
      private val em = implicitly[Monoid[R]]

      override def id: Either[L, R] = Right(em.id)

      override def combine(l: Either[L, R], r: Either[L, R]): Either[L, R] = (l, r) match {
        case (le @ Left(_), _) => le
        case (_, le@Left(_)) => le
        case (Right(la), Right(lb)) => Right(em.combine(la, lb))
      }
    }
  }
  test("6 ex") {
    import Monoid._
    /*
    How i can define id for [A] ???
    implicit def generalMonoid[T:Monoid,A[T]]:Monoid[A[T]] = new Monoid[A[T]] {
      private val em = implicitly[Monoid[T]]
      override def id: A[T] = em.id

      override def combine(l: A[T], r: A[T]): A[T] = ???
    }
    */

  }
}
