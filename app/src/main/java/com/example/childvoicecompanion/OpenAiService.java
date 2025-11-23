package com.example.childvoicecompanion;

import com.openai.client.OpenAIClient;
import com.openai.model.chat.ChatCompletionRequest;
import com.openai.model.chat.ChatCompletionResponse;
import com.openai.model.chat.ChatMessage;
import com.openai.model.chat.ChatMessageRole;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OpenAiService {

    private static final String API_KEY = BuildConfig.OPENAI_API_KEY;
    private OpenAIClient client;
    private final OkHttpClient httpClient = new OkHttpClient();

    public OpenAiService() {
        client = new OpenAIClient(API_KEY);
    }

    public String getCompletion(String prompt) {
        ChatCompletionRequest request = new ChatCompletionRequest.Builder()
                .model("gpt-3.5-turbo")
                .addMessage(new ChatMessage(ChatMessageRole.USER, prompt))
                .build();
        try {
            ChatCompletionResponse response = client.chatCompletions().create(request);
            return response.getChoices().get(0).getMessage().getContent();
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
            String responseBody = response.body().string();
            JSONObject jsonObject = new JSONObject(responseBody);
            return jsonObject.getString("text");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
