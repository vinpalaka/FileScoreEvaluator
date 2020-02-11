package reader;

import exceptions.ScoreEvaluationException;
import model.Score;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ScoreReaderTest {

    @Test(expected = ScoreEvaluationException.class)
    public void errorFile() throws IOException, ScoreEvaluationException {
        ScoreReader scoreReader = new ScoreReader();
        String filePath = "src/test/resources/scoresError.txt";
        scoreReader.getTopScoresFromFile(filePath, 5);
    }

    @Test()
    public void properFile() throws IOException, ScoreEvaluationException {
        ScoreReader scoreReader = new ScoreReader();
        ScoreOutput scoreOutput = new ScoreOutput();
        String filePath = "src/test/resources/scores.txt";
        List<Score> list = scoreReader.getTopScoresFromFile(filePath, 3);
        scoreOutput.printScores(list);
    }
}