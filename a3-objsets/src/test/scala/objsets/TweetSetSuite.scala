package objsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class TweetSetSuite extends FunSuite {
  trait TestSets {
    val set1 = new Empty
    val set2 = set1.incl(new Tweet("a", "a body", 20))
    val set3 = set2.incl(new Tweet("b", "b body", 20))
    val c = new Tweet("c", "c body", 7)
    val d = new Tweet("d", "d body", 9)
    val set4c = set3.incl(c)
    val set4d = set3.incl(d)
    val set5 = set4c.incl(d)
  }

  def asSet(tweets: TweetSet): Set[Tweet] = {
    var res = Set[Tweet]()
    tweets.foreach(res += _)
    res
  }

  def size(set: TweetSet): Int = asSet(set).size

  test("filter: on empty set") {
    new TestSets {
      assert(size(set1.filter(tw => tw.user == "a")) === 0)
    }
  }

  test("filter: a on set5") {
    new TestSets {
      assert(size(set5.filter(tw => tw.user == "a")) === 1)
    }
  }

  test("filter: 20 on set5") {
    new TestSets {
      assert(size(set5.filter(tw => tw.retweets == 20)) === 2)
    }
  }

  test("union: set4c and set4d") {
    new TestSets {
      assert(size(set4c.union(set4d)) === 4)
    }
  }

  test("union: with empty set (1)") {
    new TestSets {
      assert(size(set5.union(set1)) === 4)
    }
  }

  test("union: with empty set (2)") {
    new TestSets {
      assert(size(set1.union(set5)) === 4)
    }
  }

  test("union of two parts of set - odd/even retweets") {
    val oddTweets = TweetReader.allTweets filter (x => x.retweets % 2 == 1)
    val evenTweets = TweetReader.allTweets filter (x => x.retweets % 2 == 0)
    assert(TweetReader.allTweets.size == oddTweets.size + evenTweets.size)
  }

  test("union of two parts of set - odd/even text size") {
    val oddTweets = TweetReader.allTweets filter (x => x.text.length % 2 == 1)
    val evenTweets = TweetReader.allTweets filter (x => x.text.length % 2 == 0)
    assert(TweetReader.allTweets.size == oddTweets.size + evenTweets.size)
  }

  test("mostRetweets") {
    val mostTweet = TweetReader.allTweets.mostRetweeted
    assert(TweetReader.allTweets.filter(p => p.retweets > mostTweet.retweets).isEmpty)
  }

  test("descending: set5") {
    new TestSets {
      val trends = set5.descendingByRetweet
      assert(!trends.isEmpty)
      assert(trends.head.user == "a" || trends.head.user == "b")
    }
  }

  test("descending") {
    def isSorted(head: Tweet, tail: TweetList): Boolean = {
      if (tail.isEmpty) true
      else if (head.retweets >= tail.head.retweets) isSorted(tail.head, tail.tail)
      else false
    }
    val descTweets = TweetReader.allTweets.descendingByRetweet
    assert(isSorted(descTweets.head, descTweets.tail))
  }
}
