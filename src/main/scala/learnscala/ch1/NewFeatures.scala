package learnscala.ch1

import java.util.concurrent.atomic.AtomicLong

case class User(name: String, surname: String, email: String)

case class Person(id: Long, name: String)

object NewFeatures {
  def naiveToJsonString(p: Product): String =
    (for {i <- 0 until p.productArity} yield
      s""""${p.productElementName(i)}": "${p.productElement(i)}"""")
      .mkString("{ ", ", ", " }")


}

object UserDb {
  private val id = new AtomicLong(0);


  private val p = Person(1l, "Vasya")

  def getById(id: Long): Option[Person] = if (p.id == id) Some(p) else None
  def update(u: Option[Person]): Option[Person] =
    if (u.isDefined) Some(Person(u.get.id, u.get.name*2)) else None

  def save(u: Option[Person]): Boolean = if (u.isDefined) true else false
}

