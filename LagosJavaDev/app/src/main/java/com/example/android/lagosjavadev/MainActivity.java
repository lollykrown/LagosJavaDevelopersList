package com.example.android.lagosjavadev;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<JavaDevs>> {

    /** URL for lagos java devs list from github */
    private static final String GITHUB_URL = "https://api.github.com/search/users?q=language%3Ajava+location%3Alagos&type=Users";

    //key variables for the intent keys
    public static final String KEY_NAME = "name";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_URL = "url";

    /** Adapter for the list of developers */
    private JavaDevsAdapter mAdapter;

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;


    /**
     * Constant value for the developer loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int JAVADEVS_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find a reference to the {@link ListView} in the layout
        final ListView javaDevsListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes an empty list of developers as input
        mAdapter = new JavaDevsAdapter(this, new ArrayList<JavaDevs>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        javaDevsListView.setAdapter(mAdapter);

        // Set an item click listener on the ListView, which sends an intent to the profile activity
        javaDevsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                //get items position
                JavaDevs developersList1 = mAdapter.getItem(position);

                Intent skipIntent = new Intent(mAdapter.getContext(), ProfileActivity.class);
                skipIntent.putExtra(KEY_NAME, developersList1.getUsername());
                skipIntent.putExtra(KEY_URL, developersList1.getUrl());
                skipIntent.putExtra(KEY_IMAGE, developersList1.getImageUri());
                mAdapter.getContext().startActivity(skipIntent);
            }
        });

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        javaDevsListView.setEmptyView(mEmptyStateTextView);

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(JAVADEVS_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }


    @Override
    public Loader<List<JavaDevs>> onCreateLoader(int i, Bundle bundle) {

        Uri baseUri = Uri.parse(GITHUB_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "50");

        return new JavaDevsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<JavaDevs>> loader, List<JavaDevs> developers) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No developer found."
        mEmptyStateTextView.setText(R.string.no_jav_dev_list);

        // Clear the adapter of previous dev data
        mAdapter.clear();

        // If there is a valid list of {@link JavaDevs}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (developers != null && !developers.isEmpty()) {
            mAdapter.addAll(developers);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<JavaDevs>> loader) {
        //Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

}