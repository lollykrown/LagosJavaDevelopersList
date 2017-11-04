package com.example.android.lagosjavadev;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Kayode Agboola on 30-Sep-17.
 */

public class JavaDevsLoader extends AsyncTaskLoader<List<JavaDevs>> {

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = JavaDevsLoader.class.getName();

    /**
     * Query URL
     */
    private String mUrl;

    /**
     * Constructs a new {@link JavaDevs}.
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public JavaDevsLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<JavaDevs> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of developers.
        List<JavaDevs> developers = QueryUtils.fetchDevelopersData(mUrl);
        return developers;
    }
}

