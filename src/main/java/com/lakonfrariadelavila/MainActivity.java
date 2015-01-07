package com.lakonfrariadelavila;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;
import android.widget.Toast;

import org.osmdroid.bonuspack.location.NominatimPOIProvider;
import org.osmdroid.bonuspack.location.POI;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        // Open Main Fragment
        Fragment fragment = MapViewFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();

    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments

        // NOT WORKS -> must be called using AsyncTask
        GeoPoint startPoint = new GeoPoint(48.13, -1.63);
        NominatimPOIProvider poiProvider = new NominatimPOIProvider();
        poiProvider.setService(NominatimPOIProvider.MAPQUEST_POI_SERVICE);
        ArrayList<POI> pois = new ArrayList<POI>();
        String query = new String();

        /*GeoPoint startPoint = new GeoPoint(48.13, -1.63);
        OverpassAPIProvider overpassProvider = new OverpassAPIProvider();
        BoundingBoxE6 oBB = new BoundingBoxE6(startPoint.getLatitude()+0.5, startPoint.getLongitude()+0.5,
                startPoint.getLatitude()-0.5, startPoint.getLongitude()-0.5);
        ArrayList<POI> pois = new ArrayList<POI>();
        String query = new String();*/

        switch (position) {
            case 0:
                // NOTHING
                break;
            case 1:
                // Looking for POINTS OF INTEREST
                query = "service";
                break;
            case 2:
                // Looking for PLACES TO EAT
                query = "restaurant";
                break;
            case 3:
                // Looking for PLACES TO BUY
                query = "shop";
                break;
            case 4:
                // Looking for PLACES TO SLEEP
                query = "hotel";
                break;
            case 5:
                // Looking for OTHER PLACES
                query = "cinema";
                break;
        }

        // Not section one
        if ( position > 0 ) {
            pois = poiProvider.getPOICloseTo(startPoint, query, 50, 0.1);

            /*String oUrl = overpassProvider.urlForPOISearch(query, oBB, 100, 30);
            pois = overpassProvider.getPOIsFromUrl(oUrl);*/

            String s = "Found " + (pois != null ? pois.size() : 0);
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        }
    }

    public void onSectionAttached(int number) {

        String[] mTitles = getResources().getStringArray(R.array.title_section_array);
        mTitle = mTitles[number-1];

        /*switch (number) {
            case 1:
                mTitle = getString(R.string.title_section1);
                break;
            case 2:
                mTitle = getString(R.string.title_section2);
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
        }*/
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);

            /*
             Search Engine
            */
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView = (SearchView) menu.findItem(R.id.action_search)
                    .getActionView();
            if (null != searchView) {
                searchView.setSearchableInfo(searchManager
                        .getSearchableInfo(getComponentName()));
                searchView.setIconifiedByDefault(false);
            }

            SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
                public boolean onQueryTextChange(String newText) {
                    // this is your adapter that will be filtered
                    return true;
                }

                public boolean onQueryTextSubmit(String query) {
                    //Here u can get the value "query" which is entered in the search box.

                    // DEBUG SEARCH STRING
                    String s = getResources().getString(R.string.toast_search_query);
                    Toast.makeText(getApplicationContext(), s + query , Toast.LENGTH_SHORT).show();

                    return true;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
            /*
             End Search Engine
            */

            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
