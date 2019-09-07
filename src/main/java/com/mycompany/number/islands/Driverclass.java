/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.number.islands;

//Initial Template for Java
import java.util.*;
import java.lang.*;

public class Driverclass {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();

        while (t-- > 0) {
            int N = sc.nextInt();
            int M = sc.nextInt();

            ArrayList<ArrayList<Integer>> list = new ArrayList<>(N);

            // creating arraylist of arraylist
            for (int i = 0; i < N; i++) {
                ArrayList<Integer> temp = new ArrayList<>(M);
                list.add(i, temp);
            }

            // adding elements to the arraylist of arraylist
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    int val = sc.nextInt();
                    list.get(i).add(j, val);
                }
            }

            System.out.println(new Islands().findIslands(list, N, M));

        }
    }
}

/*This is a function problem.You only need to complete the function given below*/
//User function Template for Java
class Islands {

    // Function to find the number of island in the given list
    // N, M: size of list row and column respectively
    static int findIslands(ArrayList<ArrayList<Integer>> list, int N, int M) {
        List<Island> islands = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if (list.get(i).get(j) == 1) {
                    Piece piece = new Piece(i, j);
                    if (islands.isEmpty()) {
                        Island island = new Island(piece);
                        islands.add(island);
                    } else {
                        boolean buildNewIsland = true;
                        for (Island island : islands) {
                            if (island.isPart(piece)) {
                                island.addPiece(piece);
                                buildNewIsland = false;
                                break;
                            }
                        }
                        if (buildNewIsland) {
                            islands.add(new Island(piece));
                        }
                    }
                }
            }
        }

        Iterator<Island> iterator1 = islands.stream().iterator();
        while (iterator1.hasNext()) {
            Island island1 = iterator1.next();
            Iterator<Island> iterator2 = islands.stream().iterator();
            while (iterator2.hasNext()) {
                Island island2 = iterator2.next();
                if (island1 != island2 && island1.isPart(island2)) {
                    island1.join(island2);
                }
            }
        }
        islands = islands.stream().filter(island -> !island.pieces.isEmpty()).collect(java.util.stream.Collectors.toList());
        return islands.size();

    }

}

class Island {

    List<Piece> pieces = new ArrayList<>();

    Island(Piece piece) {
        pieces.add(piece);
    }

    void addPiece(Piece piece) {
        pieces.add(piece);
    }

    boolean isPart(Piece piece) {
        for (Piece islandPiece : pieces) {
            if (islandPiece.x == piece.x - 1 && islandPiece.y == piece.y - 1) {
                return true;
            }
            if (islandPiece.x == piece.x - 1 && islandPiece.y == piece.y) {
                return true;
            }
            if (islandPiece.x == piece.x && islandPiece.y == piece.y - 1) {
                return true;
            }
            if (islandPiece.x == piece.x && islandPiece.y == piece.y + 1) {
                return true;
            }
            if (islandPiece.x == piece.x + 1 && islandPiece.y == piece.y) {
                return true;
            }
            if (islandPiece.x == piece.x + 1 && islandPiece.y == piece.y + 1) {
                return true;
            }
            if (islandPiece.x == piece.x - 1 && islandPiece.y == piece.y + 1) {
                return true;
            }
            if (islandPiece.x == piece.x + 1 && islandPiece.y == piece.y - 1) {
                return true;
            }
        }
        return false;
    }

    boolean isPart(Island island) {
        if (island.pieces.stream().anyMatch((piece) -> (this.isPart(piece)))) {
            return true;
        }
        return false;
    }

    Island join(Island island) {
        if (this.isPart(island)) {
            this.pieces.addAll(island.pieces);
            island.pieces.clear();
        }
        return this;
    }
}

class Piece {

    int x;
    int y;

    public Piece(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
