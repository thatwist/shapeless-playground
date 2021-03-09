object Projections {

  object MapUtils {

    implicit class MapWithGetAsInstanceOf[A](map: Map[A, Any]) {
      /** get by key and cast to T, exception if cannot cast */
      def getAs[T](key: A): Option[T] = map.get(key).asInstanceOf[Option[T]]
    }
  }

  import MapUtils._

  type Query = Nothing
  type Projection = Nothing

  sealed trait Predicate {
    val op: String
  }

  def mkQuery(filter: Traversable[Predicate] = Set()): Query = ???

  def mkProjection(fields: Traversable[String] = Set()): Projection = ???

  def findWithProjection(query: Query, projection: Projection,
    offset: Option[Int] = None, limit: Option[Int] = None,
    sort: Option[String] = None, order: Boolean = true): Seq[Map[String, Any]] = ???


  def findFields(filter: Traversable[Predicate], fields: Traversable[String],
    offset: Option[Int] = None, limit: Option[Int] = None,
    sort: Option[String] = None, order: Boolean = true): Seq[Map[String, Any]] = {
    findWithProjection(query = mkQuery(filter), projection = mkProjection(fields),
      offset = offset, limit = limit, sort = sort, order = order)
    ???
  }


  import scala.language.higherKinds
  import shapeless._

  trait Projectable[A[_]] {

    type Repr

    def project(map: Map[String, Any], fields: Repr): A[_]
  }

  object Projectable {



    def find(query: Q)

    implicit object Tuple1Projectable extends Projectable[Tuple1] {
      override def project(map: Map[String, Any], fields: List[String]): Tuple1[T1] = {
        fields.headOption.flatMap{ head =>
          map.getAs[T1](head)
        }.map(Tuple1.apply).getOrElse(throw new IllegalArgumentException("failed to project"))
      }
    }

    implicit class Tuple2Projectable[T1, T2](tuple2: (T1, T2)) extends Projectable[Tuple1[T1]] {
      override def project(map: Map[String, Any], fields: List[String]): Tuple1[T1] = {
        fields.headOption.flatMap{ head =>
          map.getAs[T1](head)
        }.map(Tuple1.apply).getOrElse(throw new IllegalArgumentException("failed to project"))
      }
    }
  }
}