package learnscala.ch7

import learnscala.ch7.Monoid.{Monoid}
import learnscala.ch7.Semigroup.Semigroup
import org.scalacheck.Prop.forAll
import org.scalacheck.{Arbitrary, Gen, Prop}
import org.scalatest.{FunSuite, Matchers}

class MonoidTest extends FunSuite with Matchers {

  def associativity[S: Semigroup : Arbitrary]: Prop =
    forAll((a: S, b: S, c: S) => {
      val sg = implicitly[Semigroup[S]]
      sg.combine(sg.combine(a, b), c) == sg.combine(a, sg.combine(b, c))
    })

  def identity[S: Monoid : Arbitrary]: Prop =
    forAll((a: S) => {
      val m = implicitly[Monoid[S]]
      m.combine(a, m.id) == a && m.combine(m.id, a) == a
    })

  def monoidProp[S: Monoid : Arbitrary]: Prop = associativity[S] && identity[S]

  test("List monoid test") {
    import Monoid.MonoidList
    val i = List(1, 2, 3)
    val i2 = List(-1, -2, -3)
    def icl[T] = implicitly[Monoid[List[T]]]
    icl.combine(i, i2) shouldEqual List(1, 2, 3, -1, -2, -3)
  }

  test("ListMonoid property test"){
    import Monoid.MonoidList
    def listGen:Gen[List[Int]] = for {
      value1 <- Gen.posNum[Int]
      value2 <- Gen.posNum[Int]
      value3 <- Gen.posNum[Int]
    } yield List(value1, value2, value3)

    implicit val arbList:Arbitrary[List[Int]] = Arbitrary(listGen)
    val property ={
      monoidProp[List[Int]]
    }.check()
  }

  test("String monoid test"){
    implicit val MonoidString:Monoid[String] = new Monoid[String]{
      override def id: String = ""
      override def combine(l: String, r: String): String = l+r
    }
    def stringGen:Gen[String] = Gen.numStr
    implicit val arbString:Arbitrary[String] = Arbitrary(stringGen)
    monoidProp[String].check()

  }
}


