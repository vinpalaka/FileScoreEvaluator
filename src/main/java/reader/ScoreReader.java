package reader;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.ScoreEvaluationException;
import model.Score;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class ScoreReader {

    private PriorityQueue<Score> priorityQueue;

    public ScoreReader() {
        Comparator<Score> scoreComparator = new Comparator<Score>() {
            public int compare(Score s1, Score s2) {
                if(s1.getScore() >= s2.getScore()) {
                    return 1;
                } else {
                    return -1;
                }

            }
        };
        priorityQueue = new PriorityQueue<Score>(11, scoreComparator);
    }

    public List<Score> getTopScoresFromFile(String file, int count) throws IOException, ScoreEvaluationException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String currentLine = reader.readLine();
        int i = 0;
        while(i < count) {
            Score score = getScore(currentLine);
            priorityQueue.add(score);
            currentLine = reader.readLine();
            i++;
        }
        while (currentLine != null) {
            Score score = getScore(currentLine);
            if(priorityQueue.peek().getScore() <= score.getScore()) {
                priorityQueue.remove();
                priorityQueue.add(score);
            }
            currentLine = reader.readLine();
        }

        reader.close();
        ArrayList<Score> scores = new ArrayList<Score>();
        while(!priorityQueue.isEmpty()) {
            scores.add(priorityQueue.poll());
        }
        Collections.reverse(scores);
        return scores;
    }

    private Score getScore(String currentLine) throws JsonProcessingException, ScoreEvaluationException {
        int indexOfColon = currentLine.indexOf(':');
        String jsonString = currentLine.substring(indexOfColon + 1);
        String scoreString = currentLine.substring(0, indexOfColon);
        Score score = new Score();
        if(!scoreString.matches("\\d+"))
            throw new ScoreEvaluationException("Found Invalid Score " + scoreString);

        score.setScore(Integer.parseInt(scoreString));
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = null;
        try {
            jsonNode = objectMapper.readTree(jsonString);
        } catch (JsonParseException j) {
            throw new ScoreEvaluationException("Found Line with Invalid Json");
        }
        String id = jsonNode.get("id").textValue();
        if(id == null) {
            throw new ScoreEvaluationException("No Id found" + scoreString);
        }
        score.setId(id);
        return score;
    }

}
