package com.mediaplayer.manthanshah.booklist;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getName();

    private QueryUtils(){
    }

    /**
     * Public helper method to fetch books data and calling all the networking code within this method.
     * @param requestUrl takes current request url
     * @return List of {@link} Book objects.
     */

    public static List<Book> fetchBooksData(String requestUrl){
        URL url = createURL(requestUrl);
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(url);
        }catch (IOException e){
            Log.e(LOG_TAG,"Error while closing input Stream", e);
        }
        List<Book> books = extractBookFromJSON(jsonResponse);
        return books;
    }

    /**
     *  Helper method to create {@link}URL object
     * @param stringUrl takes a String url
     * @return URL object
     */
    @Nullable
    private static URL createURL(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        }catch (MalformedURLException e){
            Log.wtf(LOG_TAG,"Error while creating URL", e);
            e.printStackTrace();
            return null;
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = " ";
        // If url is null return early
        if(url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(2000);
            urlConnection.connect();
            if(urlConnection.getResponseCode()== 200){
                Log.wtf(LOG_TAG,"Connection Success!");
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.wtf(LOG_TAG, "error while connecting, http error code: " + urlConnection.getResponseCode());
                return null;
            }
        }catch (IOException e){
            Log.e(LOG_TAG, "Error reading the input stream", e);
        }finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    @NonNull
    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if(inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Book} objects that has been built up from
     * parsing a JSON response.
     */
    @Nullable
    private static List<Book> extractBookFromJSON(final String JSON_RESPONSE) {
        // Empty book List that we can start adding books to.
        List<Book> books = new ArrayList<>();

        try {

            JSONObject root = new JSONObject(JSON_RESPONSE);
            JSONArray items = root.getJSONArray("items");

            for(int i = 0;i < items.length();i++){

                String authorNames = "";
                String description;
                String buyLink = "";
                JSONObject element = items.getJSONObject(i);
                JSONObject volumeInfo = element.getJSONObject("volumeInfo");
                JSONObject searchInfo = element.optJSONObject("searchInfo");

                // When book has no description

                description = volumeInfo.optString("description");
                if(description == null){
                    description = searchInfo.optString("textSnippet");
                }

                String title = volumeInfo.getString("title");

                JSONArray authors = volumeInfo.optJSONArray("authors");
                // When book has more than one author
                if(authors!=null){
                    if(authors.length()> 1){
                        for (int j = 0;j < authors.length();j++ ){
                            authorNames += authors.getString(j);
                            authorNames += " ";
                        }
                    } else{
                        authorNames = authors.getString(0);
                    }
                }

                JSONObject saleInfo = element.getJSONObject("saleInfo");



                if(saleInfo.getString("buyLink") == "")
                {
                    buyLink = "";
                }
                else
                {
                    buyLink = saleInfo.getString("buyLink");
                }

                books.add(new Book(title, authorNames,description, buyLink));
            }
            return books;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
