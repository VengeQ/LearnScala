package learnscala

package object ch4 {

  object ImplicitConversion {

    case class Person(name: String)

    case class Client(name: String, account: Int)

    implicit def ClientToPerson(c: Client) = Person(c.name)

    def personNameToUpper(person: Person) = person.name.toUpperCase
  }

  object implicitParameter {

    case class A[T](value: T)

    case class B[T](value: T)

    def withImplicit[T](a: A[T])(implicit b: B[String]) = s"${a.value} ${b.value * 2}"
  }

  object ImplicitClass {

    case class A[T](value: T) {
      def action = this.value
    }

    implicit class ExtendA[T](a: A[T]) {
      def doubleAction = this.a.value.toString * 2
    }

  }

}
