package lila
package model

import Pos._
import format.Visual

class BishopTest extends LilaSpec {

  "a bishop" should {

    val bishop = White - Bishop

    def moves(pos: Pos): Valid[Set[Pos]] = Board.empty place bishop at pos flatMap { b ⇒
      b actorAt pos map (_.moves)
    }

    "move in any of 8 positions, 2 and 1 squares away" in {
      moves(E4) must bePoss(F3, G2, H1, D5, C6, B7, A8, D3, C2, B1, F5, G6, H7)
    }

    "move in any of 8 positions, 2 and 1 squares away, even when at the edges" in {
      moves(H7) must bePoss(G8, G6, F5, E4, D3, C2, B1)
    }

    "not move to positions that are occupied by the same colour" in {
      val board = Visual << """
k B



N B    P

PPPPPPPP
 NBQKBNR
"""
      board movesFrom C4 must bePoss(board, """
k B   x
     x
x   x
 x x
N B    P
 x x
PPPPPPPP
 NBQKBNR
""")
    }

    "capture opponent pieces" in {
      val board = Visual << """
k B
     q
p

N B    P

PPPPPPPP
 NBQKBNR
"""
      board movesFrom C4 must bePoss(board, """
k B
     x
x   x
 x x
N B    P
 x x
PPPPPPPP
 NBQKBNR
""")
    }
    "threaten its enemies" in {
      Visual << """
k B
     q
p

N B    P

PPPPPPPP
 NBQKBNR
""" actorAt C4 map (_ threatens A6) must succeedWith(true)
    }
  }
}
