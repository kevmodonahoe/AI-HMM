package HMM;

/**
 * Created by kdonahoe on 11/12/16.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class HMMDriver {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("How many walls would you like to use? (0-9) \n");
        String userInput = br.readLine();
        int numWalls = Integer.parseInt(userInput);

        System.out.println("Specify a first  move: 0 = North, 1 = East, 2 = South, 3 = West");
        String firstMove = br.readLine();
        int first = Integer.valueOf(firstMove);
        System.out.println("Specify a second  move: 0 = North, 1 = East, 2 = South, 3 = West");
        String secondMove = br.readLine();
        int second = Integer.valueOf(secondMove);
        System.out.println("Specify a third  move: 0 = North, 1 = East, 2 = South, 3 = West");
        String thirdMove = br.readLine();
        int third = Integer.valueOf(thirdMove);
        System.out.println("Specify a fourth  move: 0 = North, 1 = East, 2 = South, 3 = West");
        String fourthMove = br.readLine();
        int fourth = Integer.valueOf(fourthMove);

        Robot robot = new Robot(0, 0); // starting location of the robot is (0, 0), though the robot doesn't know this itself
        int[] moves = new int[4];
        moves[0] = first;
        moves[1] = second;
        moves[2] = third;
        moves[3] = fourth;

        int size = 4;
        MapCreator mapCreator = new MapCreator(size, numWalls);
        MapCreator.Map map = mapCreator.new Map();
        map.initializeMap();

        TransitionMatrix transitionMatrix = new TransitionMatrix(map);
//        transitionMatrix.printMatrix();

        System.out.println("The robot is represented by the 'O'.");
        System.out.println();

        HMMProblem problem = new HMMProblem(moves, robot, transitionMatrix, map, numWalls);

        System.out.println("The order of these probabilities correspond to the columns of the map: \n" +
                "For example: the first prob in the distribution corresponds to the upper left corner tile (0, 0), \n" +
                "             the second prob corresponds to the tile below that (0, 1)...");

        System.out.println();
    }
}
