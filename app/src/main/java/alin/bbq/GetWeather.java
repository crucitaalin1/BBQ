package alin.bbq;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by alinp on 01/08/2017.
 */

public class GetWeather {

    DataProgress listener;
    String url;
    String weather;

    public GetWeather(DataProgress dp, String weatherURL)
    {
        listener = dp;
        url = weatherURL;
    }

    public void execute() throws UnsupportedEncodingException {
        listener.onGet();
        new GetJsonData().execute(url);

    }

    private class GetJsonData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String link = params[0];
            try {
                URL url = new URL(link);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream is = httpURLConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                return buffer.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String res) {
            try {
                parseJSon(res);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listener.onSuccess(weather);

        }

        private void parseJSon(String data) throws JSONException {
            if (data == null)
                return;

            JSONObject jsonData = new JSONObject(data);
            weather = jsonData.getJSONObject("currently").getString("temperature");

            weather = weather + " " + jsonData.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getString("temperatureMin");
            weather = weather + " " + jsonData.getJSONObject("daily").getJSONArray("data").getJSONObject(0).getString("temperatureMax");

            weather = weather + " " + jsonData.getJSONObject("daily").getJSONArray("data").getJSONObject(1).getString("temperatureMin");
            weather = weather + " " + jsonData.getJSONObject("daily").getJSONArray("data").getJSONObject(1).getString("temperatureMax");

            weather = weather + " " + jsonData.getJSONObject("daily").getJSONArray("data").getJSONObject(2).getString("temperatureMin");
            weather = weather + " " + jsonData.getJSONObject("daily").getJSONArray("data").getJSONObject(2).getString("temperatureMax");

            weather = weather + " " + jsonData.getJSONObject("daily").getJSONArray("data").getJSONObject(3).getString("temperatureMin");
            weather = weather + " " + jsonData.getJSONObject("daily").getJSONArray("data").getJSONObject(3).getString("temperatureMax");


            }


        }
    }