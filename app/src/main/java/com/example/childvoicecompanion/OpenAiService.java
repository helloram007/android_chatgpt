package com.example.childvoicecompanion;

import com.openai.client.OpenAIClient;
import com.openai.model.CompletionRequest;
import com.openai.model.CompletionResponse;

public class OpenAiService {

    private static final String API_KEY = "YOUR_API_KEY"; // TODO: Replace with your API key
    private OpenAIClient client;

    public OpenAiService() {
        client = new OpenAIClient(API_KEY);
    }

    public String getCompletion(String prompt) {
        CompletionRequest request = new CompletionRequest.Builder()
                .prompt(prompt)
                .model("text-davinci-003")
                .maxTokens(100)
                .build();
        try {
            CompletionResponse response = client.completions().create(request);
            return response.getChoices().get(0).getText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
