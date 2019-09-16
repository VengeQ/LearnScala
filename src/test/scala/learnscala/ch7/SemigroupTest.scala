package learnscala.ch7

import org.scalatest.{FunSuite, Matchers}
import org.scalacheck._
import org.scalacheck.Prop._
import Semigroup.{implicits, _}

class SemigroupTest extends FunSuite with Matchers{
  def associativity[S : Semigroup : Arbitrary]: Prop =
    forAll((a: S, b: S, c: S) => {
      val sg = implicitly[Semigroup[S]]
      sg.combine(sg.combine(a, b), c) == sg.combine(a, sg.combine(b, c))
    })

  val zombieGen:Gen[Zombie] = for{
    power <- Gen.posNum[Int]
    teethLength <- Gen.posNum[Int]
  } yield Zombie(power, teethLength)

  test("incorrect Assoc test"){

    import implicits.powerSemigroup
    implicit val arbZombie:Arbitrary[Zombie] = Arbitrary(zombieGen)
    val property ={
      associativity[Zombie]
    }.check()
  }
  test("correct Assoc test"){

    import implicits.teethLengthSemigroup
    implicit val arbZombie:Arbitrary[Zombie] = Arbitrary(zombieGen)
    val property ={
      associativity[Zombie]
      }.check()
  }

  test("Circle test"){
    import implicits.circleSemigroup
    val c1 = Circle(100,100,100)
    val c2 = Circle(200,12,30)
    val ic =implicitly[Semigroup[Circle]]
    ic.combine(c1,c2) shouldEqual Circle(45, 112, 130)
  }

  test("Circle prop test"){
    import implicits.circleSemigroup
    val circleGen:Gen[Circle] = for{
      r <- Gen.posNum[Int]
      g <- Gen.posNum[Int]
      b <- Gen.posNum[Int]
    } yield Circle(r,g,b)

    implicit val arbCircle = Arbitrary(circleGen)
    val property = {
      associativity[Circle]
    }.check
  }
}
