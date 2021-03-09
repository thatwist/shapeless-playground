import scala.language.implicitConversions

trait AsString[T] {
  def asString(t: T): String
}



object AsString {
  implicit class AnyAsString[T](t: T) {
    def asString(implicit inst: AsString[T]): String = inst.asString(t)
  }
}

object AsStringInstances {
  implicit val intAsString: AsString[Int] = (t: Int) => t.toString
  implicit val stringAsString: AsString[String] = (t: String) => t
  implicit val longAsString: AsString[Long] = (t: Long) => t.toString
  implicit val shortAsString: AsString[Short] = (t: Short) => t.toString
  implicit val booleanAsString: AsString[Boolean] = (t: Boolean) => t.toString
}

object Hello {
  def main(args: Array[String]): Unit = {
    println("Hello, world!")

    import AsString._
    import AsStringInstances._

    "test".asString
    1.asString

    case class U1(i: Int, s: String)

    import shapeless._
    val g = Generic[U1]
    val l = g.to(U1(1,
      "1",
    ))

    val gg: String = "greeting"
    type p = gg.type
    val k = 1
    //    type K = k.type

    //    val i: 1.type = 1


    val i = 1

    trait Tag[T] {
      type Tag = T
    }
    type @@[T, U] = T with Tag[U]

    val int: Int @@ String = 1.asInstanceOf[Int @@ String]

    implicitly[Int @@ String <:< Int]
    // implicitly[Int @@ String =:= Int]
    // implicitly[Int @@ String =:= Int @@ Double]

  }
}

object Test1 extends App {
  trait not_sub[A, B]

  implicit def implInst[A, B]: not_sub[A, B] = null
  implicit def implInst1[A, B >: A]: not_sub[A, B] = null
  // implicit def implInst2[A, B >: A]: not_sub[A, B] = null

  trait not[T] {
    type l[U] = not_sub[U, T]
  }

  def func[A: not[Unit]#l](a: A): A = a

  func(1)
  func("")
  func((_: Int) => 1)
  func[Unit](())
}

object Test2 extends App {
  val id: Int => Int = (i: Int) => i
  type Id[A] = A

  def id1[A](a: A): Id[A] = a
}


object NextTest {
  import shapeless.nat
  import shapeless.SingletonTypeMacros
}