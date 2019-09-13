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

  test("Context bound test"){
    import learnscala.ch4.{ContextBounds =>CB}
    val p =CB.Person("Vasya")
    val c =CB.Client(23)
    import CB.implicits._
    CB.compareAsInt(p,c) shouldEqual "<"
    CB.compareAsInt(c,p) shouldEqual ">"
  }

  test ("Type classes test"){
    import learnscala.ch4.{TypeClassesExample => TCE}

    {
      import TCE.OO._
      val fruit = Fruit("Apple", true)
      val mk = MoonCake(false)
      val pie = Pie(50)
      isFoodReady(fruit) shouldEqual true
      isFoodReady(mk) shouldEqual true
      isFoodReady(pie) shouldEqual false
    }
    {
      import TCE.TC._
      val fruit = Fruit("Apple", true)
      val mk = MoonCake(false)
      val pie = Pie(50)
      isFoodReady(fruit) shouldEqual true
      isFoodReady(mk) shouldEqual true
      isFoodReady(pie) shouldEqual false
    }
    {
      import TCE.TCVariance._
      val fruit = Fruit(10, true)
      val fruit2 = Fruit(-110, true)

      isFoodReady(fruit) shouldEqual true
      isFoodReady(fruit2) shouldEqual false

      val fruitWithPie =(fruit, Pie(39))
      isFoodReady(fruitWithPie) shouldEqual true

    }
  }
  test("Exercises test"){


  }
}
