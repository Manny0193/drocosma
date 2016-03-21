package com.parrot.freeflight;

import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;

import com.parrot.freeflight.activities.TestActivity;

/**
 * Created by Manuel Peña on 8/3/2016.
 */
public class CommandRecognitionTest implements RecognitionListener {
    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {

    }

    @Override
    public void onError(int error) {
       String msj = getErrorText(error);
        TestActivity.setErrorText(msj);
    }

    @Override
    public void onResults(Bundle results) {
        for(String result : results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)) {
            if (result.equalsIgnoreCase("take off")) {
                TestActivity.setResults(0);
                break;
            } else if (result.equalsIgnoreCase("land")) {
                TestActivity.setResults(1);
                break;
            } else if (result.equalsIgnoreCase("turn left")) {
                TestActivity.setResults(2);
                break;
            } else if (result.equalsIgnoreCase("turn right")) {
                TestActivity.setResults(3);
                break;
            } else if (result.equalsIgnoreCase("move up")) {
                TestActivity.setResults(4);
                break;
            } else if (result.equalsIgnoreCase("move down")) {
                TestActivity.setResults(5);
                break;
            } else if (result.equalsIgnoreCase("move forward")) {
                TestActivity.setResults(6);
                break;
            } else if (result.equalsIgnoreCase("move backward")) {
                TestActivity.setResults(7);
                break;
            } else if (result.equalsIgnoreCase("move left")) {
                TestActivity.setResults(8);
                break;
            } else if (result.equalsIgnoreCase("move right")) {
                TestActivity.setResults(9);
                break;
            } else if (result.equalsIgnoreCase("stop")) {
                TestActivity.setResults(10);
                break;
            }else
                TestActivity.setErrorText(result);
        }

    }

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

}

