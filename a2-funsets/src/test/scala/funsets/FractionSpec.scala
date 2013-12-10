package funsets

/*
import org.scalatest.PropSpec
import org.scalatest.prop.PropertyChecks
import org.scalatest.matchers.ShouldMatchers

class Fraction(n: Int, d: Int) {
  require(d != 0)
  require(d != Integer.MIN_VALUE)
  require(n != Integer.MIN_VALUE)

  val numer = if (d < 0) -1 * n else n
  val denom = d.abs

  override def toString = numer + " / " + denom
}


class FractionSpec extends PropSpec with PropertyChecks with ShouldMatchers {

  property("Fraction constructor normalizes numerator and denominator") {
    forAll { (n: Int, d: Int) =>
      whenever(d != 0 && d != Integer.MIN_VALUE && n != Integer.MIN_VALUE) {
        val f = new Fraction(n, d)

        if (n < 0 && d < 0 || n > 0 && d > 0)
          f.numer should be > 0
        else if (n != 0)
          f.numer should be < 0
        else
          f.numer should be === 0

        f.denom should be > 0
      }
    }
  }

  property("Fraction constructor throws IAE on bad data.") {
    val invalidCombos =
      Table(
        ("n", "d"),
        (Integer.MIN_VALUE, Integer.MIN_VALUE),
        (1, Integer.MIN_VALUE),
        (Integer.MIN_VALUE, 1),
        (Integer.MIN_VALUE, 0),
        (1, 0))

    forAll(invalidCombos) { (n: Int, d: Int) =>
      evaluating {
        new Fraction(n, d)
      } should produce[IllegalArgumentException]
    }
  }
}
*/