package com.example.msapservice;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Backend extends AsyncTask<Void,Void,String> {

    private static final String LOG_TAG= Backend.class.getSimpleName();

    public Backend() {

    }

    @Override
    protected String doInBackground(Void... voids) {
        /*try {
         Thread.sleep(600000);
        }
        catch (InterruptedException e) {
         e.printStackTrace();
        }*/
        return NetworkUtils.getBackendInfo();
    }

    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);

        try {
            JSONArray Array = new JSONArray(s);

            for (int i = 0; i < Array.length(); i++)
            {
                JSONObject jsonObject = Array.getJSONObject(i);
                if (jsonObject.getString("jobType").contains("PING")) {
                    Log.d(LOG_TAG, "JSON = " + jsonObject.toString());
                    Log.d(LOG_TAG, "Type = " + jsonObject.getString("jobType"));
                    Log.d(LOG_TAG, "Host = " + jsonObject.getString("host"));
                    Log.d(LOG_TAG, "Count = " + jsonObject.getString("count"));
                    Log.d(LOG_TAG, "PacketSize = " + jsonObject.getString("packetSize"));
                    Log.d(LOG_TAG, "Period = " + jsonObject.getString("jobPeriod"));
                    Log.d(LOG_TAG, "Date = " + jsonObject.getString("date"));

                    String count = (String) jsonObject.getString("count");
                    String packetSize = (String) jsonObject.getString("packetSize");
                    String host = (String) jsonObject.getString("host");

                    try {
                        String pingCmd = "ping  -c  " + count + " -s " + packetSize +" " + host;
                        String pingResult = "";
                        Runtime r = Runtime.getRuntime();
                        Process p = r.exec(pingCmd);
                        BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            pingResult += inputLine;
                        }
                        in.close();
                        Log.d(LOG_TAG, "Rezultat od ping: " + pingResult);
                        Service.pingResult = pingResult;
                    }
                    catch (IOException e) {
                        System.out.println(e);
                    }
                }
            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
