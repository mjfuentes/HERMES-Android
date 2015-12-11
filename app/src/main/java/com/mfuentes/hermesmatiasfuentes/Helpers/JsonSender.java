package com.mfuentes.hermesmatiasfuentes.Helpers;

import android.os.AsyncTask;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mfuentes.hermesmatiasfuentes.model.Notificacion;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class JsonSender extends AsyncTask {
    private Notificacion notificacion;
    public JsonSender(String contenido, String contexto, String categoria, String nene) {
        notificacion = new Notificacion(contenido,contexto,categoria,new Date(),nene);
    }

    @Override
    protected Object doInBackground(Object[] params) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            objectMapper.setDateFormat(df);
            List<Notificacion> notificacions = new ArrayList<>();
            notificacions.add(notificacion);
            String json = objectMapper.writeValueAsString(notificacions);
            String port = CurrentUser.getInstance().getConfiguracion().getPuerto();
            String ip = CurrentUser.getInstance().getConfiguracion().getIp();
            URL url = new URL("http://" + ip +":" + port);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setInstanceFollowRedirects(false);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            conn.setRequestProperty("Content-Length", Integer.toString(json.length()));
            conn.setUseCaches(false);
            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            wr.write(json.getBytes());
            wr.close();
            int res = conn.getResponseCode();
            System.out.println(res);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
