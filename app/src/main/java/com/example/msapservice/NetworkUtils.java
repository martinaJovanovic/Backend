package com.example.msapservice;

import android.net.Uri;
import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.MissingResourceException;

public class NetworkUtils {

    public NetworkUtils() {

    }

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    static String getBackendInfo()
    {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String JSONString = null;
            try{
                Uri builtUri = Uri.parse("http://10.0.2.2:5000/getjobs/emulator")
                        .buildUpon()
                        .build();

                URL requestUrl = new URL(builtUri.toString());
                urlConnection = (HttpURLConnection) requestUrl.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();
                Log.d(LOG_TAG,"Uspesna konekcija.");

                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder builder = new StringBuilder();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while((line = reader.readLine()) != null)
                {
                    builder.append(line);
                    builder.append("\n");
                }
                JSONString=builder.toString();
            }
            catch (IOException e){
                e.printStackTrace();
                return null;
            }
            finally {
                if(urlConnection!=null){
                    urlConnection.disconnect();
                }
                if(reader != null)
                {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            Log.d(LOG_TAG, JSONString);
            return JSONString;
        }
}
