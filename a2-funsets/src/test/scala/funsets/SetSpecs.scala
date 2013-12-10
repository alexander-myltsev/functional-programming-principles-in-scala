package funsets

import org.scalatest.FunSuite

import org.scalatest.PropSpec
import org.scalatest.prop.PropertyChecks
import org.scalatest.matchers.ShouldMatchers

class SetsSpec extends PropSpec with PropertyChecks with ShouldMatchers {
  import FunSets._
  import SetExtension._
  import scala.collection.immutable

  val emptySet: Set = (x => false)
  val universalSet: Set = (x => true)

  property("union -- from definition") {
    forAll { (l1: immutable.Set[Int], l2: immutable.Set[Int]) =>
      val unionSet: Set = FunSets.union(toFunSet(l1), toFunSet(l2))
      (l1 forall (contains(unionSet, _))) should be(true)
      (l2 forall (contains(unionSet, _))) should be(true)
      ((l1 ++ l2) forall (contains(unionSet, _))) should be(true)
    }
  }

  property("union -- no additional elements") {
    forAll { (n: Int, l: immutable.Set[Int]) =>
      whenever(!(l contains n)) {
        val unionSet: Set = FunSets.union(toFunSet(l), toFunSet(l))
        (!contains(unionSet, n)) should be(true)
      }
    }
  }

  property("union -- with empty set") {
    forAll { (l: immutable.Set[Int]) =>
      {
        val unionSet: Set = FunSets.union(emptySet, toFunSet(l))
        (l forall (contains(unionSet, _))) should be(true)
      }
    }
  }

  property("intersect -- from definition") {
    forAll { (l1: immutable.Set[Int], l2: immutable.Set[Int]) =>
      val intersectSet: Set = FunSets.intersect(toFunSet(l1), toFunSet(l2))
      ((l1 intersect l2) forall (contains(intersectSet, _))) should be(true)
      ((l1 diff l2) forall (!contains(intersectSet, _))) should be(true)
      ((l2 diff l1) forall (!contains(intersectSet, _))) should be(true)
    }
  }

  property("intersect -- no additional elements") {
    forAll { (n: Int, l: immutable.Set[Int]) =>
      whenever(!(l contains n)) {
        val unionSet: Set = FunSets.union(toFunSet(l), toFunSet(l))
        (contains(unionSet, n)) should be(false)
      }
    }
  }

  property("intersect -- with universal set") {
    forAll { (l: immutable.Set[Int]) =>
      {
        val intersectSet: Set = FunSets.intersect(universalSet, toFunSet(l))
        (l forall (contains(intersectSet, _))) should be(true)
      }
    }
  }

  property("diff") {
    forAll { (s1: immutable.Set[Int], s2: immutable.Set[Int]) =>
      {
        val diffSet: Set = FunSets.diff(toFunSet(s1), toFunSet(s2))
        ((s1 diff s2) forall (contains(diffSet, _))) should be(true)
        ((s2 diff s1) forall (!contains(diffSet, _))) should be(true)
      }
    }
  }

  property("filter") {
    forAll { (s: immutable.Set[Int]) =>
      {
        def even(x: Int) = x % 2 == 0
        val filterSet: Set = FunSets.filter(toFunSet(s), even)
        ((s filter even) forall (contains(filterSet, _))) should be(true)
        ((s filter (x => !even(x))) forall (!contains(filterSet, _))) should be(true)
      }
    }
  }

  property("map") {
    forAll { (s1: immutable.Set[Int]) =>
      {
        val s = s1.map(x => x % 300)
        def double(x: Int) = 2 * x
        val mappedSet: Set = FunSets.map(toFunSet(s), double)
        ((s map double) forall (contains(mappedSet, _))) should be(true)
      }
    }
  }
}