package com.rex.facemessage;

import android.os.AsyncTask;

import com.getpebble.android.kit.PebbleKit;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rexyeung on 2015-03-14.
 */
public class GetTextSentiment extends AsyncTask<String, Void, HttpResponse> {

    String input;

    GetTextSentiment(String str) {
        input = str;
    }

    @Override
    protected HttpResponse doInBackground(String... params) {

        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://access.alchemyapi.com/calls/text/TextGetTextSentiment");

            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("apikey", "2c1092552a7bf9a70bedf8b67809d542c5889c7d"));
            pairs.add(new BasicNameValuePair("text", this.input));
            pairs.add(new BasicNameValuePair("outputMode", "json"));
            post.setEntity(new UrlEncodedFormEntity(pairs));

            HttpResponse response = client.execute(post);
//            int responseCode = response.getStatusLine().getStatusCode();
//            Log.i(getLocalClassName(), new Integer(responseCode).toString());

            return response;
        }
        catch (ClientProtocolException e){
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(HttpResponse httpResponse) {
        //int responseCode = httpResponse.getStatusLine().getStatusCode();

        try {

            //You can only call this once!
            String str = EntityUtils.toString(httpResponse.getEntity());
            JSONObject j = new JSONObject(str);
            System.out.println(j.getJSONObject("docSentiment").getString("score"));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}