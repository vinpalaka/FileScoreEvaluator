package main;

import exceptions.ScoreEvaluationException;
import model.Score;
import reader.ScoreOutput;
import reader.ScoreReader;

import java.io.IOException;
import java.util.List;

public class FileScoreEvaluator {
    public static void main(String[] args) throws IOException, ScoreEvaluationException {
        String filePath = args[0];
        int x = Integer.parseInt(args[1]);
        ScoreReader scoreReader = new ScoreReader();
        List<Score> scorees = scoreReader.getTopScoresFromFile(filePath, x);
        ScoreOutput scoreOutput = new ScoreOutput();
        scoreOutput.printScores(scorees);
    }
}
