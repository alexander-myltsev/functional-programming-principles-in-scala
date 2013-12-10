package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(column: Int, row: Int): Int = (column, row) match {
    case (c, r) if r < c => throw new IllegalArgumentException("must be: r >= c")
    case (c, r) if (column == 0 || column == row) => 1
    case _ => pascal(column, row - 1) + pascal(column - 1, row - 1)
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def parenthesesCounter(cs: List[Char], leftParenthesesCount: Int): Boolean =
      if (leftParenthesesCount < 0) false
      else cs match {
        case Nil => leftParenthesesCount == 0
        case ')' :: xs => parenthesesCounter(xs, leftParenthesesCount - 1)
        case '(' :: xs => parenthesesCounter(xs, leftParenthesesCount + 1)
        case x :: xs => parenthesesCounter(xs, leftParenthesesCount)
      }
    parenthesesCounter(chars, 0)
  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    def countChangeWays(money: Int, coins: List[Int]): Int =
      if (money == 0) 1
      else if (coins.isEmpty || money < 0) 0
      else countChangeWays(money, coins.tail) + countChangeWays(money - coins.head, coins)
    countChangeWays(money, coins.sorted)
  }
}