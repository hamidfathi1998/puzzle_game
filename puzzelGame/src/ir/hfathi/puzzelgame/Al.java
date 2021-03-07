package ir.hfathi.puzzelgame;

import java.util.*;

/**
 * Created by hamid on 08/04/2019.
 */
public class Al {


    private static final int TOP = 0;
    private static final int RIGHT = 1;
    private static final int LEFT = 2;
    private static final int BOTTOM = 3;
    private static final String TOP_S = "T -- ";
    private static final String RIGHT_S = "R -- ";
    private static final String LEFT_S = "L -- ";
    private static final String BOTTOM_S = "B -- ";
    private static final int[][] baseMatrix = {
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 0}};
    private String solution = "";
    private int d = 0;
    private ArrayList<AlModel> root = new ArrayList<AlModel>();

    public String solve(int[][] randomNumber) {
        solution = "";
        int[] content = convert2(randomNumber);
        while (d < 20) {
            if (root.size() == 0) root.addAll(createLevel(randomNumber, content, ""));
            else
                for (AlModel item : root)
                    item.setChilds(createLevel(
                            item.getNumber(),
                            convert2(item.getNumber()), item.getSolution()));
            for (AlModel item : root)
                if (checkEqual(item.getNumber()))
                    return item.getSolution() + " d = " + d;
            root = removeRoot(root);
            d++;
        }
        return "not answer";
    }

    private ArrayList<AlModel> removeRoot(ArrayList<AlModel> root) {
        ArrayList<AlModel> newRoot = new ArrayList<AlModel>();
        for (AlModel alModel : root) {
            if (alModel.getChilds() == null) return root;
            newRoot.addAll(alModel.getChilds());
        }
        return newRoot;
    }

    private ArrayList<AlModel> createLevel(int[][] randomNumber, int[] baseRandom, String oldSolution) {
        ArrayList<AlModel> root = new ArrayList<AlModel>();
        int zeroPosition = getZeroPosition(randomNumber);
        int[] ways = checkWay(zeroPosition);
        for (int v = 0; v < ways.length; v++) {
            if (createNextWay(ways[v], oldSolution)) {
                randomNumber = convert(baseRandom, false);
                replace(randomNumber, zeroPosition, ways[v]);
                root.add(new AlModel(randomNumber, oldSolution + solution));
            }
        }
        return root;
    }

    private boolean createNextWay(int way, String solution) {
        if (solution.equals("")) return true;
        String oldWay = solution.substring(solution.length() - 5, solution.length());
        switch (way) {
            case TOP:
                if (oldWay.equals(BOTTOM_S)) return false;
                break;
            case BOTTOM:
                if (oldWay.equals(TOP_S)) return false;
                break;
            case RIGHT:
                if (oldWay.equals(LEFT_S)) return false;
                break;
            case LEFT:
                if (oldWay.equals(RIGHT_S)) return false;
                break;
        }
        return true;
    }
//
//    private int createNextWay(int[] ways) {
//        int randomN = new Random().nextInt(ways.length);
//        if (solution.equals("")) return ways[randomN];
//        switch (solution.substring(solution.length() - 5, solution.length())) {
//            case TOP_S:
//                return checkNewNumber(randomN, ways, BOTTOM);
//            case BOTTOM_S:
//                return checkNewNumber(randomN, ways, TOP);
//            case RIGHT_S:
//                return checkNewNumber(randomN, ways, LEFT);
//            case LEFT_S:
//                return checkNewNumber(randomN, ways, RIGHT);
//        }
//        return 0;
//    }


//    public static void printMatrix(int[][] array) {
//        System.out.println("{ " + array[0][0] + " , " + array[0][1] + " , " + array[0][2] + " , \n  " +
//                array[1][0] + " , " + array[1][1] + " , " + array[1][2] + " , \n  " +
//                array[2][0] + " , " + array[2][1] + " , " + array[2][2] + " }\n");
//    }

    public int[] checkWay(int zeroPosition) {
        switch (zeroPosition) {
            case 0:
                return new int[]{RIGHT, BOTTOM};
            case 1:
                return new int[]{LEFT, RIGHT, BOTTOM};
            case 2:
                return new int[]{LEFT, BOTTOM};
            case 3:
                return new int[]{RIGHT, BOTTOM, TOP};
            case 4:
                return new int[]{TOP, LEFT, RIGHT, BOTTOM};
            case 5:
                return new int[]{LEFT, BOTTOM, TOP};
            case 6:
                return new int[]{RIGHT, TOP};
            case 7:
                return new int[]{LEFT, RIGHT, TOP};
            case 8:
                return new int[]{LEFT, TOP};
            default:
                return new int[]{};
        }
    }

    public Boolean checkEqual(int[][] rm) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (baseMatrix[i][j] != rm[i][j])
                    return false;
        return true;
    }

    public int[][] initializeRandomNumber() {
        int[] numbers = new int[9];
        int[][] rm = {
                {0, 0, 0},
                {0, 0, 0},
                {0, 0, 0}};
        Random random = new Random();
        int randomN = 0;

        for (int r = 0; r < 8; r++) {
            randomN = random.nextInt(8) + 1;
            for (int index = 0; index < r; index++) {
                while (randomN == numbers[index]) {
                    randomN = random.nextInt(8) + 1;
                    index = 0;
                }
            }
            numbers[r] = randomN;
        }
        rm = convert(numbers, true);
        return rm;
    }

    public static int[][] convert(int[] numbers, boolean isZero) {
        int[][] rm = new int[3][3];
        int counter = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (i == 2 && j == 2 && isZero) break;
                else rm[i][j] = numbers[counter++];
        return rm;
    }

    public static int[] convert2(int[][] numbers) {
        int[] rm = new int[9];
        int counter = 0;
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                rm[counter++] = numbers[i][j];
        return rm;
    }

    public int getZeroPosition(int[][] randomMatrix) {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (randomMatrix[i][j] == 0)
                    return (i * 3) + j;
        return 0;
    }

    public void replace(int[][] content, int state, int target) {
        int x1 = 0, x2 = 0, y1 = 0, y2 = 0;

        //region state0
        switch (state) {
            case 0:
                x1 = 0;
                y1 = 0;
                break;
            case 1:
                x1 = 0;
                y1 = 1;
                break;
            case 2:
                x1 = 0;
                y1 = 2;
                break;
            case 3:
                x1 = 1;
                y1 = 0;
                break;
            case 4:
                x1 = 1;
                y1 = 1;
                break;
            case 5:
                x1 = 1;
                y1 = 2;
                break;
            case 6:
                x1 = 2;
                y1 = 0;
                break;
            case 7:
                x1 = 2;
                y1 = 1;
                break;
            case 8:
                x1 = 2;
                y1 = 2;
                break;
        }
        //endregion

        //region targetXY
        switch (target) {
            case TOP:
                solution = TOP_S;
                y2 = y1;
                x2 = x1 - 1;
                break;
            case RIGHT:
                solution = RIGHT_S;
                y2 = y1 + 1;
                x2 = x1;
                break;
            case LEFT:
                solution = LEFT_S;
                y2 = y1 - 1;
                x2 = x1;
                break;
            case BOTTOM:
                solution = BOTTOM_S;
                y2 = y1;
                x2 = x1 + 1;
                break;
        }
        //endregion

        content[x1][y1] = content[x2][y2];
        content[x2][y2] = 0;
    }
    
}
