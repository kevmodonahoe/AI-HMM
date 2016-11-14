package HMM;

/**
 * Created by kdonahoe on 11/11/16.
 */

import java.util.ArrayList;

public class BeliefState {
    ArrayList<Double> state;
    MapCreator.Map map;

    public BeliefState() {
        state = new ArrayList<>();
    }

    public BeliefState(MapCreator.Map map) {
        this.map = map;
    }

    /*
        Initializes the first belief state with proper probabilities.
     */
    public void initialBelifState(int numWalls, ArrayList<Integer> wallLocations) {
        Double prob = (1.0 / (16 - numWalls));
        for(int i=0; i<16; i++) {
            if(wallLocations.contains(i)) {
                state.add(0.0); // if there's a wall in that location, the probability of moving there should be 0%
            } else {
                state.add(prob);
            }
        }
    }



}
