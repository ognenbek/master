package com.example.ogo_b.mqttTools;

import android.content.Context;

import com.example.ogo_b.mqttpublisher.R;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by ogo_b on 8/30/2018.
 */

public class MqttTools {
    private static MqttTools instance;
    private MqttConnectOptions options;
    private static Context context;
    private MqttTools(){

    }
    public static MqttTools getinstance(Context context1){
        if(instance==null)
            instance = new MqttTools();
        context = context1;
        return instance;
    }
    public MqttConnectOptions getConnectOptions(){
        if(options == null)
            options = generateOptions();
        return options;
    }
    private MqttConnectOptions generateOptions(){
        MqttConnectOptions connectionOptions = new MqttConnectOptions();
        connectionOptions.setCleanSession(true);

        InputStream trustStoresIs = context.getResources().openRawResource(R.raw.androidkeystore);

        String trustStoreType = KeyStore.getDefaultType();
        KeyStore trustStore = null;
        try {
            trustStore = KeyStore.getInstance("BKS");
            trustStore.load(trustStoresIs, "ogobe123".toCharArray());

            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(trustStore);

            SSLContext cont = SSLContext.getInstance("SSL");
            cont.init(null, tmf.getTrustManagers(), null);

            SSLSocketFactory sslsocketfactory = (SSLSocketFactory) cont.getSocketFactory();
            connectionOptions.setSocketFactory(sslsocketfactory);
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    return  connectionOptions;
    }
}
