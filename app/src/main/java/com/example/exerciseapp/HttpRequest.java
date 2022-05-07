package com.example.exerciseapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpRequest {
    public static String execute(String targetURL) {
        URL url;
        HttpURLConnection urlConnection = null;
        try {
            url = new URL(targetURL);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            InputStream input;
            int status = urlConnection.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK){
                input = urlConnection.getErrorStream();
            }
            else {
                input = urlConnection.getInputStream();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line;
            StringBuffer response = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                response.append(line);
                response.append("\r");
            }
            reader.close();
            return response.toString();
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }
}
