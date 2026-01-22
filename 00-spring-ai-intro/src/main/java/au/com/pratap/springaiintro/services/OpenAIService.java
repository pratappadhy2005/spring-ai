package au.com.pratap.springaiintro.services;

import au.com.pratap.springaiintro.model.Answer;
import au.com.pratap.springaiintro.model.GetCapitalRequest;
import au.com.pratap.springaiintro.model.GetCapitalResponse;
import au.com.pratap.springaiintro.model.Question;

public interface OpenAIService {
    String getAnswer(String question);

    GetCapitalResponse getCapital(GetCapitalRequest getCapitalRequest);

    Answer getCapitalWithInfo(GetCapitalRequest getCapitalRequest);

    Answer getAnswer(Question question);
}
