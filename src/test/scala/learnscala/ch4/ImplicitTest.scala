package learnscala.ch4

import org.scalatest.{FunSuite, Matchers}

class ImplicitTest extends FunSuite with Matchers {
  test("Implicit conv test"){
    import learnscala.ch4.{ImplicitConversion =>IC}
    val client =IC.Client("Ivan Ivanov",30)
    import IC.ClientToPerson
    IC.personNameToUpper(client) shouldBe "IVAN IVANOV"
  }

  test("Implicit parameter test"){
    import learnscala.ch4.{implicitParameter => IP}
    val a =IP.A(100)
    implicit val k = IP.B(10) //не подходит по типу
    IP.withImplicit(a)(IP.B("1")) shouldEqual "100 11"

    implicit val b = IP.B("Hello")
    IP.withImplicit(a) shouldEqual "100 HelloHello"
  }

  test("Implicit class test"){
    import learnscala.ch4.{ImplicitClass => IC}
    val a = IC.A("Non")
    a.action shouldEqual "Non"
    a.doubleAction shouldEqual "NonNon"
  }
}
