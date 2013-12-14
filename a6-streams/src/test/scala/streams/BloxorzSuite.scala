package streams

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import Bloxorz._

@RunWith(classOf[JUnitRunner])
class BloxorzSuite extends FunSuite {

  trait SolutionChecker extends GameDef with Solver with StringParserTerrain {
    /**
     * This method applies a list of moves `ls` to the block at position
     * `startPos`. This can be used to verify if a certain list of moves
     * is a valid solution, i.e. leads to the goal.
     */
    def solve(ls: List[Move]): Block =
      ls.foldLeft(startBlock) {
        case (block, move) => move match {
          case Left => block.left
          case Right => block.right
          case Up => block.up
          case Down => block.down
        }
      }
  }

  trait Level1 extends SolutionChecker {
      /* terrain for level 1*/

    val level =
    """ooo-------
      |oSoooo----
      |ooooooooo-
      |-ooooooooo
      |-----ooToo
      |------ooo-""".stripMargin

    val optsolution = List(Right, Right, Down, Right, Right, Right, Down)
  }

  test("terrain function level 1") {
    new Level1 {
      assert(terrain(Pos(0, 0)), "0,0")
      assert(!terrain(Pos(4, 11)), "4,11")
    }
  }

  test("findChar level 1") {
    new Level1 {
      assert(startPos == Pos(1, 1))
    }
  }

  test("neighborsWithHistory") {
    new Level1 {
      val actual = neighborsWithHistory(Block(Pos(1, 1), Pos(1, 1)), List(Left, Up)).toSet
      val expected = Set(
        (Block(Pos(1, 2), Pos(1, 3)), List(Right, Left, Up)),
        (Block(Pos(2, 1), Pos(3, 1)), List(Down, Left, Up)))
      assert(actual == expected)
    }
  }

  test("newNeighborsOnly") {
    new Level1 {
      val actual: Stream[(Block, List[Move])] = newNeighborsOnly(
        Set(
          (Block(Pos(1, 2), Pos(1, 3)), List(Right, Left, Up)),
          (Block(Pos(2, 1), Pos(3, 1)), List(Down, Left, Up))).toStream,
        Set(Block(Pos(1, 2), Pos(1, 3)), Block(Pos(1, 1), Pos(1, 1))))

      val expected: Set[(Block, List[Move])] = Set(
        (Block(Pos(2, 1), Pos(3, 1)), List(Down, Left, Up)))

      assert(actual.toSet == expected)
    }
  }

  test("`pathsFromStart` are legal") {
    new Level1 {
      assert(pathsFromStart.take(30).toList.forall(_._1.isLegal))
    }
  }

  test("`pathsFromStart` path lengths are increasing") {
    new Level1 {
      val lengthStream: Stream[Int] = pathsFromStart map (x => x._2.length)
      val ascendingPairs: Stream[(Int, Int)] = lengthStream zip lengthStream.drop(1)
      assert(ascendingPairs.take(30).toList.forall(x => x._1 <= x._2))
    }
  }

  test("optimal solution for level 1") {
    new Level1 {
      assert(solve(solution) == Block(goal, goal))
    }
  }

  test("optimal solution length for level 1") {
    new Level1 {
      assert(solution.length == optsolution.length)
    }
  }

  test("no solution") {
    new SolutionChecker {
      val level =
        """----------
          |oSoTooo---
          |----------""".stripMargin

      assert(solution.isEmpty)
    }
  }
}
