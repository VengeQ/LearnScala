package learnscala

package object ch6 {
  case class Err(text:String)
  case class Ok[A](ok:A)
}
