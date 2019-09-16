package learnscala.ch7

object Semigroup {

  trait Semigroup[A] {
    def combine(l: A, r: A): A
  }

  final case class Zombie(power: Int, teethLength: Int){

  }

  final case class Circle(r:Int, g:Int, b:Int)

  object implicits {
    implicit def powerSemigroup: Semigroup[Zombie] = (l: Zombie, r: Zombie) => {
      val zombie = if (l.power >= r.power) l else r
      zombie.copy(power = l.power + r.power)
    }

    implicit def teethLengthSemigroup: Semigroup[Zombie] = (l: Zombie, r: Zombie) => {
      Zombie(l.power+r.power, l.teethLength+r.teethLength)
    }

    implicit def circleSemigroup:Semigroup[Circle] = (l: Circle, r: Circle) =>{
      import scala.language.postfixOps
      Circle((l.r + r.r) % 255 , (l.g + r.g) % 255 , (l.b + r.b) % 255 )

    }
  }





}
