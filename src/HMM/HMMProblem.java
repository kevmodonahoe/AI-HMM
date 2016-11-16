package HMM;
import java.util.ArrayList;

public class HMMProblem {
    TransitionMatrix transitionMatrix;
    MapCreator.Map map;
    Robot robot;


    public HMMProblem(int[] moves, Robot robot, TransitionMatrix matrix, MapCreator.Map map, int numWalls) {
        this.transitionMatrix = matrix;
        this.map = map;
        this.robot = robot;

        BeliefState beliefState = new BeliefState();
        ArrayList<Integer> wallLocations = map.wallLocations();
        beliefState.initialBelifState(numWalls, wallLocations); // create the first belief state <0.625, 0.625, 0.625, .... 0.0625>

        System.out.println("Belief state for move: 0 " + beliefState.state.toString());
        map.printMaze(map.map, robot);

        filtering(beliefState, moves, 0);
    }

    /*
        The filtering method computes a new belief state for each color reading, given all the evidence up that point.
        This recursive function uses prediction and update functions to calculate the new distribution, and prints
        out the results on each iteration.
     */
    public void filtering(BeliefState currBeliefState, int[] moves, int iteration) {
        if(iteration == moves.length) {
            return;
        }

        BeliefState prediction = predictionStep(currBeliefState);

        ArrayList<Double> sensorModel = getSensorModel(moves[iteration]);
        BeliefState updatedBeliefState = updateStep(prediction, sensorModel);

        System.out.println("Move: " + robot.moveDirection(moves[iteration]));
        iteration++;
        System.out.println("Belief state for move: " + iteration + " " + updatedBeliefState.state.toString());
        map.printMaze(map.map, robot);
        filtering(updatedBeliefState, moves, iteration);
    }

    /*
       Loop through each column of the transition matrix, and multiply it by the previous probability distribution
       for the location.

       | c1  | c2  | c3  |     | c16 |
       |     |     |     | ... |     |
       |     |     |     |     |     |
       |     |     |     |     |     |

       (c1 * x1) + (c2 * x2) + ...., where c1 equals the first column of the transition matrix, and
       x1 equals the probability distribution of the location x1 up to the current moment.

       This represents: P(X_(t+1) | e_(1: t))

    */
    public BeliefState predictionStep(BeliefState beliefState) {
        BeliefState newBeliefState = new BeliefState();

        for(int c=0; c<16; c++) {
            Double sum = 0.0;
            for(int r=0; r<16; r++) {
                Double matrixValue = transitionMatrix.matrix[r][c].prob;
                Double product = matrixValue * beliefState.state.get(c);
                sum += product;
            }
            newBeliefState.state.add(sum);
        }

        return newBeliefState;
    }


    /*
        Update step of filtering.
        Given the belief state and the sensor model, performs vector multiplication to calculate the new distribution,
        and then calls the normalize function on that distribution.
     */
    public BeliefState updateStep(BeliefState prediction, ArrayList<Double> sensorModel) {
        BeliefState updatedBeliefState = new BeliefState();

        for(int i=0; i<16; i++) {
            Double product = prediction.state.get(i) * sensorModel.get(i);
            updatedBeliefState.state.add(product);
        }

        BeliefState normalizedBeliefState = normalizeState(updatedBeliefState);
        return normalizedBeliefState;
    }


    /*
        Normalizes the distribution so that the values add up to 1.
     */
    public BeliefState normalizeState(BeliefState beliefState) {
        BeliefState normalizedState = new BeliefState();

        Double sum = 0.0;
        for(Double prob : beliefState.state) {
            sum+=prob;
        }

        for(int i=0; i<beliefState.state.size(); i++) {
            Double normalizedProb = beliefState.state.get(i) / sum;
            normalizedState.state.add(normalizedProb);
        }

        return normalizedState;
    }

    /*
        Given a robot move, moves the robot on the map, and returns the new sensor model.
        For example:
            If given a "east" move -> moves the robot one spot east, gets the color of that new location
            and returns a sensor model containing the respective probabilities that the robot is now on that location.

                --> So from the example, if the tile the robot is on after the east move is "Green", then all indices of the sensor
                model that correspond to tiles on the map that are green are given a value of 0.88, and all others are given 0.04.

     */
    public ArrayList<Double> getSensorModel(int move) {
        ArrayList<Double> sensorModel = new ArrayList<>();

        robot.moveRobot(move, map);
        int color = map.map[robot.x][robot.y].color;

        for(int r=0; r<4; r++) {
            for(int c=0; c<4; c++) {
                if(map.map[r][c].color == color) {
                    sensorModel.add(0.88);
                } else {
                    sensorModel.add(0.04);
                }
            }
        }

        return sensorModel;
    }
}





