package reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Score;

import java.util.List;

public class ScoreOutput {
    public void printScores(List<Score> score) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(score));
    }
}
