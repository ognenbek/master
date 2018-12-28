package com.example.ogo_b.mqttpublisher;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.ogo_b.mqttTools.MqttTools;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import mqttSubscribers.AuthorisationResponseSubscriber;
import mqttSubscribers.TokenReceiveSubscriber;

/**
 * Created by ogo_b on 9/27/2018.
 */

public class DataPublisher {
    private static String clientID;
    public static void publish(final MqttAndroidClient client, final Context context, final String data) {
        clientID = context.getString(R.string.clientId);
        final SharedPreferences pref = context.getSharedPreferences(context.getString(R.string.sharedPrefName),Context.MODE_PRIVATE);
        final String token = pref.getString(context.getString(R.string.tokenSharedPreftitle), "NOT FOUND");
        try {

            MqttTools mqttTools = MqttTools.getinstance(context);
            MqttConnectOptions connectionOptions = mqttTools.getConnectOptions();
            connectionOptions.setCleanSession(true);
            IMqttToken tokenMqtt = client.connect(connectionOptions);

            tokenMqtt.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    String topic = "datareceivetopic";
                    JSONObject request = new JSONObject();
                    try {
                        request.put(context.getString(R.string.tokenTitle), token);
                        request.put(context.getString(R.string.dataTitle),  data);
                        request.put(context.getString(R.string.taskIdTitle), pref.getString("taskId", "NOT FOUND"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        Log.d("tag","entered");

                        MqttMessage message = new MqttMessage();
                        message.setPayload(request.toString().getBytes("UTF-8"));
                        Log.d("TOPIC",topic);
                        Log.d("TOKEN",token);
                        Log.d("MESSAGE", data);

                        client.publish(topic, request.toString().getBytes(), 0, true);
                        client.disconnect();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                }
            });
        }  catch (MqttException e) {
            e.printStackTrace();
        }

    }
}
