package agh.cs.evolution;

import agh.cs.evolution.util.Vector2d;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Map;
import java.util.Set;

public class SimParameters {
    public final Vector2d mapSize;
    public final Vector2d jungleSize;
    public final int startEnergy;
    public final int plantEnergy;
    public final int moveEnergy;

    public SimParameters() {
        this(new Vector2d(100, 30), new Vector2d(10, 10), 20, 25, 1);
    }

    public SimParameters(
            Vector2d mapSize,
            Vector2d jungleSize,
            int startEnergy,
            int plantEnergy,
            int moveEnergy) {
        assert jungleSize.precedes(mapSize);

        this.mapSize = mapSize;
        this.jungleSize = jungleSize;
        this.startEnergy = startEnergy;
        this.plantEnergy = plantEnergy;
        this.moveEnergy = moveEnergy;
    }

    public static SimParameters fromJson(String json) throws ParseException {
        return fromJson(json, true);
    }

    public static SimParameters fromJson(String json, boolean strict) throws ParseException {
        JSONParser parser = new JSONParser();
        Object configObj = parser.parse(json);
        if (!(configObj instanceof JSONObject)) {
            throw new IllegalArgumentException("Expected JSON configuration to be an object.");
        }
        JSONObject config = (JSONObject) configObj;

        Vector2d mapSize = null;
        Vector2d jungleSize = null;
        Integer startEnergy = null;
        Integer plantEnergy = null;
        Integer moveEnergy = null;

        Set<Map.Entry<String, Object>> entrySet = config.entrySet();
        for (Map.Entry<String, Object> e : entrySet) {
            String key = e.getKey();
            Object value = e.getValue();
            switch (key) {
                case "mapSize":
                case "jungleSize":
                    if (!(value instanceof JSONArray)) {
                        throw new IllegalArgumentException("Map and area size must be specified as a list of two positive integers, [width, height]");
                    }
                    JSONArray sizeArray = (JSONArray) value;
                    if (sizeArray.size() != 2 || !(sizeArray.get(0) instanceof Long) || !(sizeArray.get(1) instanceof Long)) {
                        throw new IllegalArgumentException("Map and area size must be specified as a list of two positive integers, [width, height]");
                    }
                    Long width = (Long) sizeArray.get(0);
                    Long height = (Long) sizeArray.get(1);

                    if (width <= 0 || width > Integer.MAX_VALUE ||
                            height <= 0 || height > Integer.MAX_VALUE) {
                        throw new IllegalArgumentException("Map and area size must be specified as a list of two positive integers, [width, height]");
                    }

                    Vector2d bounds = new Vector2d(width.intValue(), height.intValue());
                    if (key.equals("mapSize")) {
                        mapSize = bounds;
                    } else {
                        jungleSize = bounds;
                    }
                    break;
                case "startEnergy":
                case "moveEnergy":
                case "plantEnergy":
                    if (!(value instanceof Long) || ((Long) value) > Integer.MAX_VALUE || ((Long) value) < Integer.MIN_VALUE) {
                        throw new IllegalArgumentException("Energy values must be specified as integers");
                    }
                    Long numericValue = (Long) value;
                    if (key.equals("startEnergy")) {
                        startEnergy = numericValue.intValue();
                    } else if (key.equals("moveEnergy")) {
                        moveEnergy = numericValue.intValue();
                    } else {
                        plantEnergy = numericValue.intValue();
                    }
                    break;
                default:
                    String message = "Unexpected config key \"" + key + "\"";
                    if (strict) {
                        throw new IllegalArgumentException(message);
                    } else {
                        System.err.println("Warning: " + message);
                    }
            }
        }
        if (mapSize == null) throw new IllegalArgumentException("Missing configuration key: mapSize");
        if (jungleSize == null) throw new IllegalArgumentException("Missing configuration key: jungleSize");
        if (startEnergy == null) throw new IllegalArgumentException("Missing configuration key: startEnergy");
        if (moveEnergy == null) throw new IllegalArgumentException("Missing configuration key: moveEnergy");
        if (plantEnergy == null) throw new IllegalArgumentException("Missing configuration key: plantEnergy");

        return new SimParameters(mapSize, jungleSize, startEnergy, plantEnergy, moveEnergy);
    }
}
