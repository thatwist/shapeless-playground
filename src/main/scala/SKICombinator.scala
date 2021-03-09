import scala.language.{higherKinds, reflectiveCalls}

object SKICombinator {

  trait λ { type ap[_ <: λ] <: λ }
  type I = λ { type ap[X <: λ] = X }
  type K = λ { type ap[X <: λ] = λ { type ap[Y <: λ] = X } }
  type S = λ { type ap[X <: λ] = λ { type ap[Y <: λ] = λ { type ap[Z <: λ] = X#ap[Z]#ap[Y#ap[Z]] } } }

  // try it
  // type Y = S#ap[I]#ap[I]#ap[S#ap[I]#ap[I]]

}
