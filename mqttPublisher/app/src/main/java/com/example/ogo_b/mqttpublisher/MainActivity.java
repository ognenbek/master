package com.example.ogo_b.mqttpublisher;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.MqttClient;


import java.security.Security;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Timer timer;

    {
        Security.addProvider(new org.spongycastle.jce.provider.BouncyCastleProvider());
    }


//    public static String Broker = "tcp://192.168.100.5:1883";
public static String Broker = "ssl://192.168.1.11:8883";



    String xacmlStore = "<Request xmlns=\"urn:oasis:names:tc:xacml:3.0:core:schema:wd-17\" CombinedDecision=\"false\" ReturnPolicyIdList=\"false\">\n" +
            "<Attributes Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:environment\">\n" +
            "<Attribute AttributeId=\"environmentId\" IncludeInResult=\"false\">\n" +
            "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#integer\">9</AttributeValue>\n" +
            "</Attribute>\n" +
            "</Attributes>\n" +
            "<Attributes Category=\"urn:oasis:names:tc:xacml:1.0:subject-category:access-subject\">\n" +
            "<Attribute AttributeId=\"roleId\" IncludeInResult=\"false\">\n" +
            "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">A</AttributeValue>\n" +
            "</Attribute>\n" +
            "</Attributes>\n" +
            "<Attributes Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:action\">\n" +
            "<Attribute AttributeId=\"actionId\" IncludeInResult=\"false\">\n" +
            "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">store</AttributeValue>\n" +
            "</Attribute>\n" +
            "</Attributes>\n" +

            "</Request>";

    String xacmlProcess = "<Request xmlns=\"urn:oasis:names:tc:xacml:3.0:core:schema:wd-17\" CombinedDecision=\"false\" ReturnPolicyIdList=\"false\">\n" +
            "<Attributes Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:environment\">\n" +
            "<Attribute AttributeId=\"environmentId\" IncludeInResult=\"false\">\n" +
            "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#integer\">9</AttributeValue>\n" +
            "</Attribute>\n" +
            "</Attributes>\n" +
            "<Attributes Category=\"urn:oasis:names:tc:xacml:1.0:subject-category:access-subject\">\n" +
            "<Attribute AttributeId=\"roleId\" IncludeInResult=\"false\">\n" +
            "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">A</AttributeValue>\n" +
            "</Attribute>\n" +
            "</Attributes>\n" +
            "<Attributes Category=\"urn:oasis:names:tc:xacml:3.0:attribute-category:action\">\n" +
            "<Attribute AttributeId=\"actionId\" IncludeInResult=\"false\">\n" +
            "<AttributeValue DataType=\"http://www.w3.org/2001/XMLSchema#string\">process</AttributeValue>\n" +
            "</Attribute>\n" +
            "</Attributes>\n" +

            "</Request>";



    private MqttAndroidClient clientSubscribe;
    private static String clientID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Properties properties = new Properties();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String clientId = MqttClient.generateClientId();
//        final String clientIdSubscriber = MqttClient.generateClientId();
        clientID = getString(R.string.clientId);
        final MqttAndroidClient client =
                new MqttAndroidClient(this.getApplicationContext(), Broker,
                        clientId);

        Button btnSendProcessButton = findViewById(R.id.btnProcessingCredetnials);
        btnSendProcessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Process token acquire","Clicked");
                CredentialsPublisher.publish(client, getApplicationContext(), xacmlProcess);
            }
        });

        Button btnSendCredentials = findViewById(R.id.btnSendCredentials);
        btnSendCredentials.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Store token acquire","Clicked");
                CredentialsPublisher.publish(client, getApplicationContext(), xacmlStore);
            }
        });

        Button btnSendTokenResourceServer = findViewById(R.id.btnSendTokenResourceServer);
        btnSendTokenResourceServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TokenResourceServerPublisher.publish(client, getApplicationContext());
            }
        });

        Button btnSendData = findViewById(R.id.btnSendData);
        btnSendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Timer timer = getTimer();
                timer.scheduleAtFixedRate(new TimerTask() {

                                              @Override
                                              public void run() {
                                                  // Magic here
                                                  Calendar calendar = Calendar.getInstance();
                                                  int day = calendar.get(Calendar.DAY_OF_MONTH);
                                                  int month = calendar.get(Calendar.MONTH);
                                                  int year = calendar.get(Calendar.YEAR);
                                                  DataPublisher.publish(client, getApplicationContext(), year+"."+month+"."+day + ";" + String.valueOf(new Random().nextInt(61) + 20));
                                              }
                                          },
                        0, 1000);
//                DataPublisher.publish(client, getApplicationContext(), "09272018" +  String.valueOf(new Random().nextInt(61) + 20));


            }
        });

        Button btnStopData = findViewById(R.id.btnStopSendData);
        btnStopData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopTimer();
                DataPublisher.publish(client, getApplicationContext(), "stopStreaming");
            }
        });

        Button btnChangeToken = findViewById(R.id.btnChangeToken);
        btnChangeToken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences(getApplicationContext().getString(R.string.sharedPrefName), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();


                String token = pref.getString(getApplicationContext().getString(R.string.tokenSharedPreftitle), "NOT FOUND");
                Log.d("TOKEN", "Original token: " + token);

                Random random = new Random();
                int place = random.nextInt(10) + 36;
                StringBuilder strBuild = new StringBuilder(token);
                strBuild.setCharAt(place, '1');
                token = strBuild.toString();
                Log.d("TOKEN", "Changed token: " + token);


                editor.putString(getApplicationContext().getString(R.string.tokenSharedPreftitle), token);
                editor.commit();

            }
        });
    }

    private Timer getTimer(){
        timer = new Timer();
        return timer;
    }
    private void stopTimer(){
        timer.cancel();
    }
}
