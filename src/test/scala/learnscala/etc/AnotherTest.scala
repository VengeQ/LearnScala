package learnscala.etc

import org.scalatest.{FunSuite, Matchers}

class AnotherTest extends FunSuite with Matchers {
  test("lazy unstrict test") {
    def find[A](list: List[A])(value: A) = {
      @annotation.tailrec def go(l: List[A]): Option[A] = l match {
        case Nil => None
        case x :: xs => {
          println(s"$x  ::   $xs")
          if (x == value) Some(x) else go(xs)
        }
      }

      go(list)
    }

    def contain[A](list: List[A])(value: A) = {
      @annotation.tailrec def go(l: List[A]): Boolean = l match {
        case Nil => false
        case x :: xs => {
          println(s"$x  ::   $xs")
          x == value || go(xs)
        }
      }

      go(list)
    }


    LazyList.iterate(1)(_ + 1).take(10).toList shouldEqual (1 to 10).toList
    find(List(1, 2, 3, 4, 5, 6, 7, 8))(3) shouldEqual Some(3)
    contain(List(1, 2, 3, 4, 5, 6, 7, 8))(3) shouldEqual true

  }
  test("Tuple flat test") {
    val tu = for {
      i <- 1 to 3
      j <- 4 to 6
    } yield (i, j)

    tu.flatMap((x) => List(x._1, x._2)).foreach(println)


  }

  test("FlatMap as Fold") {
    def flatMapWithFoldForList[T, R](opt: List[T])(fn: T => List[R]): List[R] = {
      opt.foldLeft(List.empty[R])((accum, cur) => accum ::: fn(cur))
    }

    flatMapWithFoldForList(List(List(1, 2, 3), List(4, 5, 6)))((x: List[Int]) => x) shouldEqual List(1, 2, 3, 4, 5, 6)

    implicit class ListWithFmFold[T](list: List[T]) {
      def flatMapWithFoldForList[R](fn: T => List[R]): List[R] = {
        list.foldLeft(List.empty[R])((accum, cur) => accum ::: fn(cur))
      }
    }

    List(List(1, 2, 3), List(4, 5, 6)).flatMapWithFoldForList(x => x) shouldEqual List(1, 2, 3, 4, 5, 6)
    List(List(1, 2, 3), List(4, 5, 6)).flatMap(x => x)

  }
}
