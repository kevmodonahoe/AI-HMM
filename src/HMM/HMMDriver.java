package HMM;

/**
 * Created by kdonahoe on 11/12/16.
 */

import java.util.ArrayList;

public class HMMDriver {
    public static void main(String[] args) {
        System.out.println("The robot is represented by the 'O'.");
        System.out.println();

        Robot robot = new Robot(0, 0);
        int[] moves = new int[4];
        moves[0] = 1;
        moves[1] = 1;
        moves[2] = 1;
        moves[3] = 1; // moves = [e, w, w, e]

        int numWalls = 3;
        int size = 4;
        MapCreator mapCreator = new MapCreator(size, numWalls);
        MapCreator.Map map = mapCreator.new Map();
        map.initializeMap();

        TransitionMatrix transitionMatrix = new TransitionMatrix(map);
//        transitionMatrix.printMatrix();

        HMMProblem problem = new HMMProblem(moves, robot, transitionMatrix, map, numWalls);

        System.out.println("The order of these probabilities correspond to the columns of the map: \n" +
                "For example: the first prob in the distribution corresponds to the upper left corner tile, \n" +
                "             the second prob corresponds to the tile below that, ...");

        System.out.println();


        System.out.println("Left to do: " +
                "3) FOL problem ");

    }
}
