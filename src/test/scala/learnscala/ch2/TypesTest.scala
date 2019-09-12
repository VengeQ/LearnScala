package learnscala.ch2

import learnscala.Outer
import org.scalatest.{FunSuite, Matchers}

class TypesTest extends FunSuite with Matchers {
  test("singleton type test") {
    val a = four
    val double_a = a match {
      case '4' => 4 * 4
      case 3 => 3 * 3
    }
    double_a shouldEqual 16
  }

  test("type projection test") {
    val t = Outer("string")
    case class Lock() {

      final case class Key()

      def open(key: Key): Lock = this

      def close(key: Key): Lock = this

      def openWithMaster(key: Lock#Key): Lock = this

      def makeKey: Key = new Key

      def makeMasterKey: Lock#Key = new Key
    }
  }
  type Or[A, B]
  type StringOrInt = Or[String, Int]
  test("infix type test" ){

  }
}

