package learnscala.ch7

import learnscala.ch7.Group.Group
import learnscala.ch7.Group.implicits._
import org.scalacheck._
import org.scalacheck.Prop._
import org.scalatest.{FunSuite, Matchers}

class GroupTest extends FunSuite with Matchers{
  def invertibility[G: Group : Arbitrary]: Prop =
    forAll((a: G) => {
      val sg = implicitly[Group[G]]
      sg.combine(a,sg.inverse(a)) == sg.id
    })

  test("Group test"){
    val stringGen:Gen[String] =Gen.numStr
    implicit val arbString:Arbitrary[String] = Arbitrary(stringGen)
    invertibility.check()
  }
}
