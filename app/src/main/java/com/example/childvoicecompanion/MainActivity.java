package com.example.childvoicecompanion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText promptEditText;
    private Button sendButton;
    private TextView responseTextView;
    private OpenAiService openAiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        promptEditText = findViewById(R.id.prompt);
        sendButton = findViewById(R.id.send);
        responseTextView = findViewById(R.id.response);
        openAiService = new OpenAiService();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prompt = promptEditText.getText().toString();
                if (!prompt.isEmpty()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String response = openAiService.getCompletion(prompt);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    responseTextView.setText(response);
                                }
                            });
                        }
                    }).start();
                }
            }
        });
    }
}
