
object PeanoNumbers {

  import language.higherKinds

  sealed trait Nat {
    type Out <: Nat
  }

  case object Zero extends Nat {
    override type Out = Zero.type
  }

  case class Succ[S <: Nat](s: S) extends Nat {
    override type Out = Succ[S]
  }

  val _0 = Zero
  val _1 = Succ(_0)
  val _2 = Succ(_1)
  val _3 = Succ(_2)
  val _4 = Succ(_3)

  type _0 = Zero.type
  type _1 = Succ[_0]
  type _2 = Succ[_1]
  type _3 = Succ[_2]
  type _4 = Succ[_3]

  trait PeanoInt[T <: Nat] {
    def int: Int
  }

  implicit val peanoZero: PeanoInt[Zero.type] = new PeanoInt[Zero.type] {
    override def int: Int = 0
  }

  implicit def peanoSucc[S <: Nat](implicit pred: PeanoInt[S]): PeanoInt[Succ[S]] =
    new PeanoInt[Succ[S]] {
      override def int: Int = pred.int + 1
    }

  def peanoInt[N <: Nat](implicit peanoInt: PeanoInt[N]): Int = peanoInt.int

  implicit class NatToInt[N <: Nat](n: N) {
    def toInt(implicit peanoInt: PeanoInt[N]): Int = peanoInt.int
  }

  val _0i = _0.toInt
  val _1i = _1.toInt
  val _2i = _2.toInt
}

object PeanoLogic {

  import PeanoNumbers._

  import scala.annotation.showAsInfix
  import scala.language.existentials
  import scala.language.reflectiveCalls

  @showAsInfix
  trait LessThan[A <: Nat, B <: Nat]

  type `_<_` = LessThan[A, B] forSome {
    type A <: Nat
    type B <: Nat
  }

  implicit def zeroLessThanAnySucc[S <: Nat]: _0 LessThan Succ[S] = new LessThan[_0, Succ[S]] {}

  implicit def anyLessThanAny[A <: Nat, B <: Nat](implicit less: A LessThan B): Succ[A] LessThan Succ[B] =
  new LessThan[Succ[A], Succ[B]] {}

}

