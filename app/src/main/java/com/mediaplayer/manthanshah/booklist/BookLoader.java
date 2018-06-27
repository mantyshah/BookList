package com.mediaplayer.manthanshah.booklist;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    private static final String LOG_TAG = BookLoader.class.getName();
    private String [] urls;

    BookLoader(Context context, String... url){
        super(context);
        urls = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        if(urls.length < 1 || urls[0] == null){
            return null;
        }
        return QueryUtils.fetchBooksData(urls[0]);
    }
}
