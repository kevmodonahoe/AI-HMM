package HMM;

/**
 * Created by kdonahoe on 11/14/16.
 */
public class Robot {
    int x,y;

    public Robot(int x, int y) {
        this.x = x;
        this.y = y;
    }


    /*
        Moves clockwise.

        0 = North
        1 = East
        2 = South
        3 = West
     */
    public void moveRobot(int direction, MapCreator.Map map) {
        switch (direction) {
            case 0:
                if(this.y - 1 >= 0 && !map.map[this.x][this.y - 1].isWall) {
                    this.y = this.y -1;
                }
                break;
            case 1:
                if(this.x + 1 < 4 && !map.map[this.x + 1][this.y].isWall) {
                    this.x = this.x + 1;
                }
                break;
            case 2:
                if(this.y + 1 < 4 && !map.map[this.x][this.y + 1].isWall) {
                    this.y = this.y + 1;
                }
                break;
            case 3:
                if(this.x - 1 >= 0 && !map.map[this.x - 1][this.y].isWall) {
                    this.x = this.x - 1;
                }
                break;
            default:
                break;
        }
    }

    /*
        Returns the string corresponding to the direction passed in.
     */
    public String moveDirection(int direction) {
        String dir = "";
        switch (direction) {
            case 0:
                dir = "North";
                break;
            case 1:
                dir = "East";
                break;
            case 2:
                dir = "South";
                break;
            case 3:
                dir = "West";
                break;
            default:
                dir = direction + " is an invalid Direction - so robot stayed in place!";
                break;
        }
        return dir;
    }

}
