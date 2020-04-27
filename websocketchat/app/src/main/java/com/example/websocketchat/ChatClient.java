package com.example.websocketchat;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.Date;

interface ChatClientInterface{
    void onMessage(String message);
}
public class ChatClient extends WebSocketClient {

    ChatClientInterface observer;

    public ChatClient(URI serverUri, ChatClientInterface observer) {
        super(serverUri);
        this.observer = observer;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
    }

    @Override
    public void onMessage(String message) {
        observer.onMessage(message);


    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
    }

    @Override
    public void onError(Exception ex) {
        observer.onMessage(ex.toString());
    }
}