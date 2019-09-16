package learnscala.ch7

import learnscala.ch7.Foldable.MonoidFoldable
import learnscala.ch7.Monoid.Monoid
import org.scalatest.{FunSuite, Matchers}

class FoldableTest extends FunSuite with Matchers{
  test("List foldable test"){
    val list = List("1","2","3","4","5")
    implicit val MonoidString:Monoid[String] = new Monoid[String]{
      override def id: String = ""
      override def combine(l: String, r: String): String = l+r

    }
    import Foldable.listMonoidFoldable
    def ic[T:Monoid] = implicitly[MonoidFoldable[String,List]]
    ic.foldLeft(list)(MonoidString.id,(x:String,y:String) => MonoidString.combine(x,y)) shouldEqual "12345"
    ic.foldRight(list)(MonoidString.id,(x:String,y:String) => MonoidString.combine(x,y)) shouldEqual "12345"
    ic.foldBalanced(list)(MonoidString.id,(x:String,y:String) => MonoidString.combine(x,y)) shouldEqual "12345"
  }

}
