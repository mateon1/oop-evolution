package agh.cs.evolution;

import java.util.ArrayList;
import java.util.List;

public class OptionsParser {
    public static MoveDirection[] parse(String[] input) {
        List<MoveDirection> directions = new ArrayList<>();
        for (String in : input) {
            switch (in) {
                case "f":
                case "F":
                case "forward":
                case "forwards":
                    directions.add(MoveDirection.FORWARD);
                    break;
                case "b":
                case "B":
                case "backward":
                case "backwards":
                    directions.add(MoveDirection.BACKWARD);
                    break;
                case "l":
                case "L":
                case "left":
                    directions.add(MoveDirection.LEFT);
                    break;
                case "r":
                case "R":
                case "right":
                    directions.add(MoveDirection.RIGHT);
                    break;
                default:
                    throw new IllegalArgumentException("String \"" + in + "\" is not a legal move specification");
            }
        }
        return directions.toArray(new MoveDirection[0]);
    }
}
