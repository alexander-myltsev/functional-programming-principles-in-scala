package funsets

import FunSets._
import scala.language.implicitConversions
import scala.collection.immutable

object SetExtension {
  def toFunSet(l: immutable.Set[Int]): Set = (x => l.contains(x))
}