package agh.cs.evolution;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import static org.junit.Assert.fail;

public class SimParametersTest {

    final static String[] VALID_JSON = {
            "{\"mapSize\":[100,30],\"jungleSize\":[10,10],\"startEnergy\":50,\"plantEnergy\":25,\"moveEnergy\":1,\"startAnimals\":25,\"grassTurns\":50}",
            "{\"mapSize\":[50,20],\"jungleSize\":[10,10],\"startEnergy\":10,\"plantEnergy\":100,\"moveEnergy\":1,\"startAnimals\":50,\"grassTurns\":250}",
            "{\"startAnimals\":50,\"startEnergy\":10,\"moveEnergy\":1,\"plantEnergy\":100,\"jungleSize\":[10,10],\"mapSize\":[50,20],\"grassTurns\":250}",
            "{\"grassTurns\":250,\"startEnergy\":10,\"plantEnergy\":100,\"mapSize\":[50,20],\"moveEnergy\":1,\"jungleSize\":[10,10],\"startAnimals\":50}",
            "{\"startEnergy\":10,\"plantEnergy\":100,\"mapSize\":[50,20],\"moveEnergy\":1,\"jungleSize\":[10,10],\"startAnimals\":50}"
    };

    final static String[] INVALID_JSON = {
            "\"grassTurns\":250\",startEnergy\":10,\"plantEnergy\":100,\"mapSize\":[50,20],\"moveEnergy\":1,\"jungleSize\":[10,10],\"startAnimals\":50,",
            "{\"grassTurns\":250\",startEnergy:10,\"plantEnergy\":100,\"mapSize\":[50,20],\"moveEnergy\":1,\"jungleSize\":[10,10],\"startAnimals\":50}",
            "{}",
            "[]",
            "",
            "125135",
            "{\"mapSize\":[-1,30],\"jungleSize\":[10,10],\"startEnergy\":50,\"plantEnergy\":25,\"moveEnergy\":1,\"startAnimals\":25,\"grassTurns\":50}",
            "{\"mapSize\":[100,30],\"jungleSize\":[10,10],\"startEnergy\":50,\"plantEnergy\":25,\"moveEnergy\":1,\"startAnimals\":25,\"grassTurns\":-1}",
            "{\"mapSzie\":[100,30],\"jungleSize\":[10,10],\"startEnergy\":50,\"plantEnergy\":25,\"moveEnergy\":1,\"startAnimals\":25,\"grassTurns\":50}",
            "{\"mapSize\":[100,30],\"jungleSize\":[10,10],\"startEnergy\":50,\"plantEnergy\":25,\"moveEnergy\":1.2,\"startAnimals\":25}",
            "{\"mapSize\":[100,30],\"jungleSize\":[10,10],\"startEnergy\":50,\"plantEnergy\":25,\"moveEnergy\":1,\"startAnimals\":25.1}",
            "{\"mapSize\":[100,30],\"jungleSize\":[10,10],\"startEnergy\":50,\"plantEnergy\":25,\"moveEnergy\":1,\"startAnimals\":false}",
            "{\"mapSize\":[100,30],\"jungleSize\":[10,10],\"startEnergy\":50,\"plantEnergy\":25,\"moveEnergy\":1,\"startAnimals\":[]}",
            "{\"mapSize\":[100,30],\"jungleSize\":[10,10],\"startEnergy\":-50.2,\"plantEnergy\":25,\"moveEnergy\":1,\"startAnimals\":25}",
            "{\"mapSize\":[100,30],\"jungleSize\":{},\"startEnergy\":50,\"plantEnergy\":25,\"moveEnergy\":1,\"startAnimals\":25}",
            "{\"mapSize\":[100,30,10,5],\"jungleSize\":[10,10],\"startEnergy\":50,\"plantEnergy\":25,\"moveEnergy\":1,\"startAnimals\":25}",
            "{\"mapSize\":[],\"jungleSize\":[10,10],\"startEnergy\":50,\"plantEnergy\":25,\"moveEnergy\":1,\"startAnimals\":25}"
    };

    final static String[] INVALID_JSON_BROKEN = {
            "{\"grassTurns\":250\"startEnergy\":10,\"plantEnergy\":100,\"mapSize\":[50,20],\"moveEnergy\":1,\"jungleSize\":[10,10],\"startAnimals\":50,}",
            "{\"grassTurns\":250,\"startEnergy\":10,\"plantEnergy\":100,\"mapSize\":[50,20],\"moveEnergy\":1,\"jungleSize\":[10,10],\"startAnimals\":50,}",
    };

    @Test
    public void jsonParsingValid() throws ParseException {
        for (String s : VALID_JSON) {
            SimParameters.fromJson(s);
        }
    }

    @Test
    public void jsonParseBadJungleSizeHitsAssert() throws ParseException {
        try {
            SimParameters sp = SimParameters.fromJson("{\"mapSize\":[10,10],\"jungleSize\":[100,30],\"startEnergy\":50,\"plantEnergy\":25,\"moveEnergy\":1,\"startAnimals\":25}");
            fail("Expected parse to fail with AssertionError!");
        } catch (AssertionError e) {
            StackTraceElement cause = e.getStackTrace()[0];
            assert cause.getClassName().equals(SimParameters.class.getName());
            assert cause.getMethodName().equals("<init>");
        }
    }

    @Test
    public void jsonParsingInvalid() {
        for (String s : INVALID_JSON) {
            try {
                SimParameters sp = SimParameters.fromJson(s);
                fail("String should not have parsed:\n" + s);
            } catch (ParseException | IllegalArgumentException ignore) {
            } catch (AssertionError e) {
                System.out.println(e.getStackTrace()[0]);
                e.printStackTrace();
            }
        }
    }

    // This failing test contains cases that should not parse, but get parsed anyway, since the JSON library is too permissive.
    @Test
    public void jsonParsingInvalidBroken() {
        for (String s : INVALID_JSON_BROKEN) {
            try {
                SimParameters sp = SimParameters.fromJson(s);
                fail("String should not have parsed:\n" + s);
            } catch (ParseException | IllegalArgumentException ignore) {
            }
        }
    }
}