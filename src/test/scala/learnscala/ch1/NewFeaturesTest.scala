package learnscala.ch1

import org.scalatest.{FunSuite, Matchers}
import scala.util.chaining._

class NewFeaturesTest extends FunSuite with Matchers {
  test("StringOps Test") {
    "203".toIntOption shouldBe Some(203)
    "2a2".toIntOption shouldBe None
    "false".toBooleanOption shouldBe Some(false)
    "TruE".toBooleanOption shouldBe Some(true)
    "TruuE".toBooleanOption shouldBe None
  }

  test("Product test") {
    val person = User("Daniil", "Balukin", "mail@mail.ru")
    NewFeatures.naiveToJsonString(person) shouldBe (s"""{ "name": "Daniil", "surname": "Balukin", "email": "mail@mail.ru" }""")
  }

  test("pipe test") {
    import UserDb._
    val p1 =Person(1l,"Vasya")
    val doAll = (getById _).andThen(update _).andThen( save _)
    getById(1l).pipe(update).pipe(save) shouldBe doAll(1l)
  }
}

