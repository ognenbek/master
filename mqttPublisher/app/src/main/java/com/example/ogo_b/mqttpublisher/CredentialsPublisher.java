package com.example.ogo_b.mqttpublisher;

import android.content.Context;
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

import mqttSubscribers.TokenReceiveSubscriber;

/**
 * Created by ogo_b on 8/27/2018.
 */

public class CredentialsPublisher {
    private static String clientID;
    public static void publish(final MqttAndroidClient client, final Context context, final String xacml) {
        clientID = context.getString(R.string.clientId);
        final TokenReceiveSubscriber subscriber = new TokenReceiveSubscriber();
        subscriber.initialize(context);

        try {

            MqttTools mqttTools = MqttTools.getinstance(context);
            MqttConnectOptions connectionOptions = mqttTools.getConnectOptions();
            connectionOptions.setCleanSession(true);
            IMqttToken token = client.connect(connectionOptions);

            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    String topic = "clientCredentials";
                    JSONObject request = new JSONObject();
                    try {
                        request.put(context.getString(R.string.clientIdtitle), clientID);
                        request.put(context.getString(R.string.passwordTitle), context.getString(R.string.password));
                        request.put(context.getString(R.string.TokenReceiveTopic), context.getString(R.string.topicReceiveToken));
                        request.put(context.getString(R.string.xacmltitle), xacml);
                    } catch (JSONException e) {
                        Log.d("tag","json error");
                        e.printStackTrace();
                    }
                    try {
                        Log.d("tag","entered");

                        MqttMessage message = new MqttMessage();
                        message.setPayload(request.toString().getBytes("UTF-8"));
                        client.publish(topic, request.toString().getBytes(), 0, true);
                        subscriber.subscriberConnect();
                        client.disconnect();
                    } catch (Exception e) {
                        Log.d("tag", "first exception");
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                }
            });
        }  catch (MqttException e) {
            Log.d("tag", "second exception");
            e.printStackTrace();
        }

    }
}
