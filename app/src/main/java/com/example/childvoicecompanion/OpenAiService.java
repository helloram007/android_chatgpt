package com.example.childvoicecompanion;

import com.openai.client.OpenAIClient;
import com.openai.model.chat.ChatCompletionRequest;
import com.openai.model.chat.ChatCompletionResponse;
import com.openai.model.audio.TranscriptionRequest;
import com.openai.model.audio.TranscriptionResponse;
import com.openai.model.chat.ChatMessage;
import com.openai.model.chat.ChatMessageRole;

import java.io.File;

import okhttp3.OkHttpClient;

public class OpenAiService {

    private static final String API_KEY = BuildConfig.OPENAI_API_KEY;
    private OpenAIClient client;

    public OpenAiService() {
        client = new OpenAIClient(API_KEY);
    }

    public String getCompletion(String prompt) throws ApiException {
        ChatCompletionRequest request = new ChatCompletionRequest.Builder()
                .model("gpt-3.5-turbo")
                .addMessage(new ChatMessage(ChatMessageRole.USER, prompt))
                .build();
        try {
            ChatCompletionResponse response = client.chatCompletions().create(request);
            return response.getChoices().get(0).getMessage().getContent();
        } catch (Exception e) {
            throw new ApiException("Error getting completion: " + e.getMessage());
        }
    }

    public String getTranscription(String filePath) throws ApiException {
        try {
            File file = new File(filePath);
            TranscriptionRequest request = new TranscriptionRequest.Builder()
                    .file(file)
                    .model("whisper-1")
                    .build();
            TranscriptionResponse response = client.audio().transcriptions().create(request);
            return response.getText();
        } catch (Exception e) {
            throw new ApiException("Error getting transcription: " + e.getMessage());
        }
    }
}
