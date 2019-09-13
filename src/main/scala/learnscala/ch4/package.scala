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

  object ContextBounds {

    trait CanBeInteger[T] {
      def asInt(input: T): Int
    }

    def compareAsInt[CA, CB](a: CA, b: CB)(implicit ca: CanBeInteger[CA], cb: CanBeInteger[CB]): String =
      ca.asInt(a) - cb.asInt(b) match {
        case 0 => "="
        case x if x > 0 => ">"
        case _ => "<"
      }

    case class Person(name: String)

    case class Client(id: Long)

    object implicits {
      implicit val personAsInt: CanBeInteger[Person] = (input: Person) => input.name.length
      implicit val clientAsInt: CanBeInteger[Client] = (input: Client) => input.id.toInt
    }

  }

  object TypeClassesExample {

    object OO {

      trait Food {
        def isReady: Boolean
      }

      case class MoonCake(inPacking: Boolean) extends Food {
        override def isReady: Boolean = !inPacking
      }

      case class Fruit[T](kind: String, isClean: Boolean) extends Food {
        override def isReady: Boolean = isClean && kind.toLowerCase.contains("a")
      }

      case class Pie(temper: Int) extends Food {
        override def isReady: Boolean = if (temper < 40) true else false
      }

      def isFoodReady(f: Food) = f.isReady
    }

    object TC {

      trait Food[T] {
        def isReady(t: T): Boolean
      }

      case class MoonCake(inPacking: Boolean)

      case class Fruit[T](kind: String, isClean: Boolean)

      case class Pie(temper: Int)

      def isFoodReady[F: Food](f: F): Boolean = implicitly[Food[F]].isReady(f)

      implicit val moonCake: Food[MoonCake] = (t: MoonCake) => !t.inPacking

      implicit def fruit[A]: Food[Fruit[A]] = (f: Fruit[A]) => f.isClean && f.kind.toLowerCase.contains("a")

      implicit val pie: Food[Pie] = (p: Pie) => if (p.temper < 40) true else false
    }

    object TCVariance {

      trait Food[T] {
        def isReady(t: T): Boolean
      }

      case class MoonCake(inPacking: Boolean)

      case class Fruit[T](kind: T, isClean: Boolean)

      case class Pie(temper: Int)


      def isFoodReady[F: Food](f: F): Boolean = implicitly[Food[F]].isReady(f)

      implicit val moonCake: Food[MoonCake] = (t: MoonCake) => !t.inPacking

      implicit def fruit: Food[Fruit[String]] = (f: Fruit[String]) => f.isClean && f.kind.toLowerCase.contains("a")

      implicit val pie: Food[Pie] = (p: Pie) => if (p.temper < 40) true else false

      implicit def fruitDelegate[T](implicit convert: T => Boolean): Food[Fruit[T]] = (t: Fruit[T]) => convert(t.kind) && t.isClean

      implicit val isReadyWithPeel: Int => Boolean = x => x >= 0

      implicit def makeNewFood[A, B](implicit ev1: Food[A], ev2: Food[B]): Food[(A, B)] =
        (t: (A, B)) => ev1.isReady(t._1) && ev2.isReady(t._2)
    }

    object Exercises {
      def compare[T](x: T, y: T)(implicit ev: Ordering[T]): Int = ev.compare(x, y)

    }

  }

}
