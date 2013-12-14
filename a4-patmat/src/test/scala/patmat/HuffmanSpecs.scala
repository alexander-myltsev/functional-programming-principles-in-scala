package patmat

import org.scalatest.PropSpec
import org.scalatest.prop.PropertyChecks
import org.scalatest.matchers.ShouldMatchers
import patmat.Huffman._

class HuffmanSpecs extends PropSpec with PropertyChecks with ShouldMatchers {
  property("encode/decode invariant") {
    forAll { (len: Int) =>
      whenever(len != Int.MinValue) {
        val availableChars = frenchCode.asInstanceOf[Fork].chars
        val l = math.abs(len) % 20 + 1
        val s = List.fill(l)(scala.util.Random.nextInt(availableChars.length - 2) + 1)
          .map(availableChars(_))
          .mkString
        val encodedStr = encode(frenchCode)(s.toList)
        assert(decode(frenchCode, encodedStr) === s.toList)
      }
    }
  }

  property("quick-encode/decode invariant") {
    forAll { (len: Int) =>
      whenever(len != Int.MinValue) {
        val availableChars = frenchCode.asInstanceOf[Fork].chars
        val l = math.abs(len) % 20 + 1
        val s = List.fill(l)(scala.util.Random.nextInt(availableChars.length - 2) + 1)
          .map(availableChars(_))
          .mkString
        val encodedStr = quickEncode(frenchCode)(s.toList)
        assert(decode(frenchCode, encodedStr) === s.toList)
      }
    }
  }
}
