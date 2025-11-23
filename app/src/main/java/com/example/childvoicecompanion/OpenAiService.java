package com.example.childvoicecompanion;

import com.openai.client.OpenAIClient;
import com.openai.model.CompletionRequest;
import com.openai.model.CompletionResponse;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OpenAiService {

    private static final String API_KEY = "YOUR_API_KEY"; // TODO: Replace with your API key
    private OpenAIClient client;
    private final OkHttpClient httpClient = new OkHttpClient();

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

    public String getTranscription(String filePath) {
        try {
            File file = new File(filePath);
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", file.getName(),
                            RequestBody.create(MediaType.parse("audio/mpeg"), file))
                    .addFormDataPart("model", "whisper-1")
                    .build();

            Request request = new Request.Builder()
                    .url("https://api.openai.com/v1/audio/transcriptions")
                    .header("Authorization", "Bearer " + API_KEY)
                    .post(requestBody)
                    .build();

            Response response = httpClient.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
