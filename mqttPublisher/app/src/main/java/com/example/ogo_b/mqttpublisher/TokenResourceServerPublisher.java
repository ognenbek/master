package com.example.ogo_b.mqttpublisher;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
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
import org.spongycastle.util.encoders.Encoder;

import java.util.Base64;

import mqttSubscribers.AuthorisationResponseSubscriber;

/**
 * Created by ogo_b on 8/31/2018.
 */

public class TokenResourceServerPublisher {
    private static String clientID;
    public static void publish(final MqttAndroidClient client, final Context context) {
        clientID = context.getString(R.string.clientId);
        final AuthorisationResponseSubscriber subscriber = new AuthorisationResponseSubscriber();
        subscriber.initialize(context);

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
                    String topic = "tokenreceivetopic";
                    JSONObject request = new JSONObject();
                    try {
                        String taskId = "maximumData" + System.currentTimeMillis();
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("taskId", taskId);
                        editor.commit();

                        //change the signed token
                        request.put(context.getString(R.string.tokenTitle), token);


                        request.put(context.getString(R.string.taskIdTitle), taskId);
                        request.put(context.getString(R.string.toolTitle), "hadoop");
                        Log.d("ALG", getAlgorithms());
                        request.put(context.getString(R.string.algorithmTitle), getAlgorithms());
                        request.put(context.getString(R.string.responseTokenTopic), context.getString(R.string.responseTokenTopic) + context.getString(R.string.clientId));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        Log.d("tag","entered");

                        MqttMessage message = new MqttMessage();
                        message.setPayload(request.toString().getBytes("UTF-8"));
                        Log.d("TOPIC",topic);
                        Log.d("TOKEN",token);

                        client.publish(topic, request.toString().getBytes(), 0, true);
                        subscriber.subscriberConnect();
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
    private static String getAlgorithms(){
        JSONObject json = new JSONObject();
        try {
            json.put("map", getMapAlgorithm());
            json.put("combine","");
            json.put("reduce", getReduceAlgorithm());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return json.toString();
    }
    private static String getMapAlgorithm(){
        String alg = "import java.io.IOException;\n" +
                "\n" +
                "import org.apache.hadoop.io.FloatWritable;\n" +
                "import org.apache.hadoop.io.LongWritable;\n" +
                "import org.apache.hadoop.io.Text;\n" +
                "import org.apache.hadoop.io.Writable;\n" +
                "import org.apache.hadoop.mapreduce.Mapper;\n" +
                "\n" +
                "public class MaxTempMapper extends Mapper<LongWritable, Text, Text, Writable> {\n" +
                "\t@Override\n" +
                "\tprotected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Writable>.Context context)\n" +
                "\t\t\tthrows IOException, InterruptedException {\n" +
                "\t\tString []line = value.toString().split(\";\");\n" +
                "\t\tString interval = line[0];\n" +
                "\t\tString temperature = line[1];\n" +
                "\t\tSystem.out.println(\"temperature: \" + temperature);\n" +
                "\t\tif(interval!=null && !temperature.isEmpty())\n" +
                "\t\t\tcontext.write(new Text(interval), new FloatWritable(Float.parseFloat(temperature)));\n" +
                "\t}\n" +
                "}";
        return alg.replaceAll("\n", "");
    }

    private static String getReduceAlgorithm(){
        String alg = "import java.io.IOException;\n" +
                "\n" +
                "import org.apache.hadoop.io.FloatWritable;\n" +
                "import org.apache.hadoop.mapreduce.Reducer;\n" +
                "import org.apache.hadoop.io.Text;\n" +
                "import org.apache.hadoop.io.Writable;\n" +
                "\n" +
                "public class maxTempReducer extends Reducer<Text, Writable, Text, Writable>{\n" +
                "\t@Override\n" +
                "\tprotected void reduce(Text key, Iterable<Writable> values,\n" +
                "\t\t\tReducer<Text, Writable, Text, Writable>.Context context) throws IOException, InterruptedException {\n" +
                "\t\tfloat maxValue = Float.MIN_VALUE;\n" +
                "\t\tSystem.out.println(\"Reduce: \" + values.toString());\n" +
                "\t\tfor (Writable value : values) {\n" +
                "\t\t\tSystem.out.println(\"key: \" + key + \"value: \" + value);\n" +
                "\t\tmaxValue = Math.max(maxValue, ((FloatWritable) value).get());\n" +
                "\t\t}\n" +
                "\t\tcontext.write(key, new FloatWritable(maxValue));\n" +
                "\t\t}\n" +
                "}";

        return alg.replaceAll("\n", "");
    }
}
