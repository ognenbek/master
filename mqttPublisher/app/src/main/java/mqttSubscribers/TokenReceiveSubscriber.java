package mqttSubscribers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.ogo_b.mqttTools.MqttTools;
import com.example.ogo_b.mqttpublisher.R;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import static com.example.ogo_b.mqttpublisher.MainActivity.Broker;

/**
 * Created by ogo_b on 8/30/2018.
 * This subscriber collects the token that was generated on the authorisation server. The token will be saved in the private sharedPreferences
 * with key "token".
 */

public class TokenReceiveSubscriber {
    private MqttAndroidClient clientSubscribe;
    private Context context;

    public void initialize(final Context context){
        Log.d("tag","subsciber started");
        this.context = context;
        final String clientIdSubscriber = MqttClient.generateClientId();
        clientSubscribe = new MqttAndroidClient(context,  Broker, clientIdSubscriber);
        clientSubscribe.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean reconnect, String serverURI) {
                subscribeToTopic(context.getString(R.string.topicReceiveToken) + context.getString(R.string.clientId));
            }

            @Override
            public void connectionLost(Throwable cause) {

            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {
                Log.d("tag","message arrived");
                String token = new String(message.getPayload());
                SharedPreferences pref = context.getSharedPreferences(context.getString(R.string.sharedPrefName),Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString(context.getString(R.string.tokenSharedPreftitle), token);
                editor.commit();
                Log.d("TOKEN", token);
                clientSubscribe.disconnect();
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }

    private void subscribeToTopic(String topic){
        Log.d("TOPIC", topic);
        try {
            clientSubscribe.subscribe( topic, 0, null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d("CONNECT","connected");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.d("tag", exception.getMessage());
                }
            });
        } catch (MqttException e) {
            Log.d("tag", e.getMessage());
            e.printStackTrace();
        }
    }


    public void subscriberConnect(){
        try {
            MqttTools mqttTools = MqttTools.getinstance(context);
            MqttConnectOptions connectOptions = mqttTools.getConnectOptions();
            IMqttToken token = clientSubscribe.connect(connectOptions);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d("tag","Connected");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {

                }
            });
        } catch (MqttException e) {
            Log.d("tag", "subscriber exception");
            e.printStackTrace();
        }
    }
}
