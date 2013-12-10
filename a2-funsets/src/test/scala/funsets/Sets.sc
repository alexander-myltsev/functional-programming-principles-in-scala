package funsets

object Sets {
  import FunSets._

  implicit def list2set(l: List[Int]): Set = (x => l.contains(x))
                                                  //> list2set: (l: List[Int])Int => Boolean

  val k = List() intersect List()                 //> k  : List[Nothing] = List()
  val universalSet: Set = (x => true)             //> universalSet  : Int => Boolean = <function1>
  val emptySet: Set = (x => false)                //> emptySet  : Int => Boolean = <function1>
  k forall (contains(universalSet, _))            //> res0: Boolean = true
  k forall (contains(emptySet, _))                //> res1: Boolean = true

  val intersectSet = FunSets.intersect(List(), List())
                                                  //> intersectSet  : Int => Boolean = <function1>
  ((List() diff List()) forall (contains(intersectSet, _)))
                                                  //> res2: Boolean = true

  List(0, 0) diff List(0)                         //> res3: List[Int] = List(0)

  (Set(-10)).map(x => math.abs(x)).max            //> res4: Int = 10
}