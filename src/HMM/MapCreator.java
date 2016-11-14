package HMM;

import java.util.Random;
import java.util.ArrayList;

/**
 * Created by kdonahoe on 11/13/16.
 */
public class MapCreator {
    int rows, columns, numWalls;

    public MapCreator(int size, int numWalls) {
        this.rows = size;
        this.columns = size;
        this.numWalls = numWalls;
    }
    public class MapNode {
        boolean isWall;
        int depth;
        boolean isRobot;
        int color;
        int index;

        // 0: red, 1: green, 2: blue, 3: yellow
        public MapNode(int x, int y, int c, int i, boolean isWall) {
            this.color = c;
            this.index = i;
            depth = 0;
            this.isRobot = false;
            this.isWall = isWall;
        }
    }

    public class Map {
        MapNode[][] map;
        MapNode startNode;

        public Map() {
            this.map = new MapNode[rows][columns];
            startNode = new MapNode(0, 0, 0, 0, false);
        }

        public MapNode[][] initializeMap() {
            int color = 0;
            int index = 0;
            for(int r=0; r<rows; r++) {
                for (int c = 0; c < columns; c++) {
                    this.map[r][c] = new MapNode(r, c, color, index, false);
                    index++;
                }
                if (color == 3) {
                    color = 0;
                } else {
                    color++;
                }
            }


            for(int i=0; i<numWalls; i++) {
                Random rand = new Random();
                int r = rand.nextInt(3) + 1;
                int c = rand.nextInt(3) + 0;
                map[r][c].isWall = true;
                map[r][c].color = -1;
            }

            return map;
        }

        /*
            Returns all of the locations occupied by a wall - this function is used for initializing the probability distribution in HMMProblem
         */
        public ArrayList<Integer> wallLocations() {
            ArrayList<Integer> locations = new ArrayList<>();
            int location = 0;
            for(int r=0; r<4; r++) {
                for(int c=0; c<4; c++) {
                    if(map[r][c].isWall) {
                        locations.add(location);
                    }
                    location++;
                }
            }
            return locations;
        }


        public void printMaze(MapNode[][] map, Robot robot) {
            for(int c=0; c<columns; c++) {
                for(int r=0; r<rows; r++) {
                    if(robot.x == r && robot.y == c) {
                        System.out.print("O");
                        continue;
                    }
                    switch (map[r][c].color) {
                        case -1:
                            System.out.print("W");
                            break;
                        case 0:
                            System.out.print("R");
                            break;
                        case 1:
                            System.out.print("G");
                            break;
                        case 2:
                            System.out.print("B");
                            break;
                        case 3:
                            System.out.print("Y");
                            break;
                        default:
                            break;
                    }
                }
                System.out.println("");
            }
            System.out.println("");
        }
    }

}
