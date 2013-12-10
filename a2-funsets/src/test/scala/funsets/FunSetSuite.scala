package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {
  import FunSets._
  import SetExtension._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("singletonSet(1) does not contain 1") {
    new TestSets {
      assert(!contains(s1, 2), "Singleton not")
    }
  }

  test("union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("forall: {1,2,3,4}") {
    assert(forall(toFunSet(Set(1, 2, 3, 4)), x => x < 5), "All elements in the set are strictly less than 5")
  }

  test("forall: {-1000,0}") {
    assert(forall(toFunSet(Set(-1000)), x => x < 1000), "All elements in the set are strictly less than 1000")
  }

  test("forall & filter: even") {
    val s: Set = toFunSet((-500 to 500).toSet)
    def even(x: Int) = x % 2 == 0
    assert(forall(FunSets.filter(s, even), even), "The set of all even numbers should contain only even numbers")
  }

  test("forall & map: doubling numbers") {
    val s: Set = toFunSet((-500 to 500).toSet)
    def even(x: Int) = x % 2 == 0
    def double(x: Int) = x * 2
    assert(forall(FunSets.map(s, double), even), "The set obtained by doubling all numbers should contain only even numbers")
  }

  test("exists: given {1,3,4,5,7,1000}") {
    val s: Set = toFunSet(Set(1, 3, 4, 5, 7, 1000))
    assert(!FunSets.exists(s, x => x == 2), "2 shouldn't exist in the given set")
  }

  test("exists & filter: even") {
    val s: Set = toFunSet((-300 to 300).toSet)
    def even(x: Int) = x % 2 == 0
    def odd(x: Int) = !even(x)
    assert(!FunSets.exists(FunSets.filter(s, even), odd), "The set of all even numbers should not contain odd element.")
  }
}