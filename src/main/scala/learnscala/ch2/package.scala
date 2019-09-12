package learnscala

package object ch2 {
  def singleT[T <: Singleton](t:T):T =t
  final val four =singleT('4')
  final val three =singleT(3)

}
case class Outer(inner:String)