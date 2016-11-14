package HMM;

/**
 * Created by kdonahoe on 11/13/16.
 */

public class TransitionMatrix {
    MatrixNode[][] matrix;
    MapCreator.Map map;

    public TransitionMatrix(MapCreator.Map map) {
        this.map = map;
        matrix = new MatrixNode[16][16];
        initializeMatrix();

    }

    // Initializes the Transition Matrix - the initial probability for each location equal,
    // and the value depends on how many walls exist.
    public void initializeMatrix() {
        for(int r=0; r<16; r++) {
            for(int c=0; c<16; c++) {
                matrix[r][c] = new MatrixNode();
            }
        }

        for(int r=0; r<16; r++) {
            for(int c=0; c<16; c++) {
                Double prob = getProb(r, c);
                matrix[r][c].prob = prob;
            }
        }
    }

    /*
        Calculates the probability of a particular entry in the transition matrix.
     */
    public Double getProb(int row, int column) {
        // row = L1, column = L2
        MapCreator.MapNode currentNode = null;
        MapCreator.MapNode nextNode = null;

        for(int r=0; r<4; r++) {
            for(int c=0; c<4; c++) {
                if(row == map.map[r][c].index) {
                    currentNode = map.map[r][c];
                    break;
                }
            }
        }
        for(int r=0; r<4; r++) {
            for(int c=0; c<4; c++) {
                if(column == map.map[r][c].index) {
                    nextNode = map.map[r][c];
                    break;
                }
            }
        }

        if(nextNode.isWall) {
            return 0.0;
        }

        // Calculate probability for north/south neighbors
        if(Math.abs(currentNode.index - nextNode.index) == 1 && (sum(currentNode.index, nextNode.index) != 7)
                && (sum(currentNode.index, nextNode.index) != 15) && (sum(currentNode.index, nextNode.index) != 23)) {
            return 0.25;
        }

        // Calculate probability for east/west neighbors
        if(Math.abs(currentNode.index - nextNode.index) == 4) {
            return 0.25;
        }

        // Calculate probability for corner nodes
        if((currentNode.index == 0 || currentNode.index == 12 || currentNode.index == 3 || currentNode.index == 15)
                && (currentNode.index == nextNode.index)) {
            return 0.5;
        }

        // Calculate probability for nodes along an edge
        if(currentNode.index == nextNode.index && (currentNode.index == 1 || currentNode.index == 2 || currentNode.index == 4 ||
                currentNode.index == 8 || currentNode.index == 7 || currentNode.index == 11 || currentNode.index == 13 ||
                currentNode.index == 14)) {
            return 0.25;
        }

        return 0.0;
    }

    public int sum(int a, int b) {
        return a+b;
    }

    public void printMatrix() {
        for(int r=0; r<16; r++) {
            for(int c=0; c<16; c++) {
                System.out.print(matrix[r][c].prob + " ");
            }
            System.out.println();
        }
    }
}
