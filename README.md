# Child Voice Companion App

This is an Android application that allows users to have a voice chat with an AI assistant powered by OpenAI.

## Features

*   **Login:** A simple login screen to get started.
*   **Voice Chat:** Record your voice and send it to the OpenAI API for a response.
*   **Text-to-Speech:** The AI's response is converted to speech and spoken aloud.

## Getting Started

### Prerequisites

*   [Android Studio](https://developer.android.com/studio)
*   An OpenAI API Key.

### Building and Running the App

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/your-username/android_chatgpt.git
    ```

2.  **Set up your OpenAI API Key:**
    *   Create a file named `local.properties` in the root directory of the project.
    *   Add the following line to the `local.properties` file, replacing `YOUR_API_KEY` with your actual OpenAI API key:
        ```
        openai.apiKey="YOUR_API_KEY"
        ```

3.  **Open the project in Android Studio:**
    *   Launch Android Studio and select "Open" from the welcome screen.
    *   Navigate to the cloned repository and select the `android_chatgpt` directory.

4.  **Set up an Android Emulator:**
    *   In Android Studio, go to **Tools > Device Manager**.
    *   Click on **Create device**.
    *   Choose a device definition (e.g., Pixel 6) and click **Next**.
    *   Select a system image (e.g., API Level 34) and click **Next**.
    *   Click **Finish** to create the emulator.

5.  **Run the app:**
    *   Select the emulator you just created from the device dropdown menu in the toolbar.
    *   Click the **Run** button (the green play icon).

## How to Test

### Login Screen

*   The login screen is a simple placeholder.
*   Enter any non-empty username and password and click the **Login** button to proceed to the main screen.

### Voice Chat

1.  On the main screen, you will see a microphone button.
2.  Press the microphone button to start recording your voice. A "Recording started" message will appear.
3.  Press the microphone button again to stop recording. A "Recording stopped" message will appear.
4.  The app will then send your recorded audio to the OpenAI API for transcription and get a response.
5.  The AI's response will be displayed on the screen and spoken aloud.
