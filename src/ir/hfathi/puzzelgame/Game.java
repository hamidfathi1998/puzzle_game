package ir.hfathi.puzzelgame;


public class Game {


    public static void main(String args[]) {
        int[][] randomNumber = {
                {1, 2, 3},
                {7, 4, 8},
                {0, 6, 5}};

        System.out.println(new Al().solve(randomNumber));
    }
}
