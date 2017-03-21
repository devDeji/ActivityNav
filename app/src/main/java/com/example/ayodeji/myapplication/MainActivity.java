package com.example.ayodeji.myapplication;

import android.Manifest;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.ayodeji.myapplication.dummy.DummyContent;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ItemFragment.OnListFragmentInteractionListener, ItemFragment2.OnListFragmentInteractionListener, ItemListDialogFragment.Listener {

    private static final int RESULT_CAMERA = 200;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 500;
    WebView myWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        myWebView = (WebView) findViewById(R.id.webview);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    private void doMySearch(String query) {
        Toast.makeText(this, "Its alive::doMySearch" +
                "!!!:" + query, Toast.LENGTH_LONG).show();
        SearchRecentSuggestions suggestions = new SearchRecentSuggestions(this,
                MySuggestionProvider.AUTHORITY, MySuggestionProvider.MODE);
        suggestions.saveRecentQuery(query, null);
        myWebView.setVisibility(View.VISIBLE);
        myWebView.loadUrl("https://www.google.com/search?q=" + query);
        myWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        /**
         * SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
         //SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
         //searchView.setSearchableInfo( searchManager.getSearchableInfo(getComponentName()) );


         MenuItem searchItem = menu.findItem(R.id.app_bar_search);
         SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
         searchView.setSearchableInfo( searchManager.getSearchableInfo(getComponentName()) );


         searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override public boolean onQueryTextSubmit(String query) {
        Toast.makeText(MainActivity.this, "onQueryTextListener sucka!", Toast.LENGTH_LONG).show();
        return false;
        }

        @Override public boolean onQueryTextChange(String newText) {
        Toast.makeText(MainActivity.this, "onQueryTextChanged sucka!", Toast.LENGTH_LONG).show();
        return false;
        }
        });

         Get the SearchView and set the searchable configuration
         SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
         SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
         // Assumes current activity is the searchable activity
         searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
         searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default**/
        return true;
    }

    //Called from the search config file should be used instead of the searchView instantiation On the onCreateOptionsMenu
    @Override
    public boolean onSearchRequested() {
        Toast.makeText(this, "Onsearch requested", Toast.LENGTH_LONG).show();
        Bundle appData = new Bundle();
        appData.putBoolean("DummyBool", true);
        startSearch(null, false, appData, false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.app_bar_search
                ) {
            onSearchRequested();
            return true;
        }

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
        //return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    public void showCamera(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                showCameraPreview();
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                    Toast.makeText(this, "Camera permission is needed to show the camera preview.", Toast.LENGTH_LONG).show();
                }
                requestPermissions(new String[]{Manifest.permission.CAMERA}, RESULT_CAMERA);
            }
        } else {
            Toast.makeText(this, "Permission is requied mate!", Toast.LENGTH_LONG).show();
        }
    }

    public void showContact() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_CONTACTS)) {
                Toast.makeText(this, "Permission is required", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    private void showCameraPreview() {
        Toast.makeText(this, "Its alive!", Toast.LENGTH_LONG).show();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            View view = findViewById(R.id.drawer_layout);
            showCamera(view);
        } else if (id == R.id.nav_gallery) {
            myWebView.setVisibility(View.GONE);
            ItemFragment itemfragment = new ItemFragment();
            FragmentTransaction fragTrans = getSupportFragmentManager().beginTransaction();
            fragTrans.replace(R.id.frame, itemfragment, "Item test Fragment");
            fragTrans.commit();
        } else if (id == R.id.nav_slideshow) {
            myWebView.setVisibility(View.GONE);
            ItemFragment2 itemfragment2 = new ItemFragment2();
            FragmentTransaction fragTrans2 = getSupportFragmentManager().beginTransaction();
            fragTrans2.replace(R.id.frame, itemfragment2, "Item test Fragment");
            fragTrans2.commit();
        } else if (id == R.id.nav_manage) {
            showContact();
        } else if (id == R.id.nav_share) {
            myWebView.setVisibility(View.GONE);

            ItemListDialogFragment itemfragment3 = new ItemListDialogFragment();
            FragmentTransaction fragTrans3 = getSupportFragmentManager().beginTransaction();
            fragTrans3.replace(R.id.frame, itemfragment3, "Item test Fragment");
            fragTrans3.commit();

        } else if (id == R.id.nav_send) {
            Intent in = new Intent(MainActivity.this, Main2Activity.class);
            startActivity(in);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RESULT_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showCameraPreview();
            } else {
                Toast.makeText(this, "Permission was not granted", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == MY_PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted to retrieve contact", Toast.LENGTH_LONG).show();
                // permission was granted, yay! Do the
                // contacts-related task you need to do.
            } else {
                Toast.makeText(this, "Permission was not granted to retrieve contact", Toast.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        String url = item.content;
        myWebView.setVisibility(View.VISIBLE);
        myWebView.loadUrl(url);
        myWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        //Remove whatever fragment is on frame
        getSupportFragmentManager().beginTransaction().
                remove(getSupportFragmentManager().findFragmentById(R.id.frame)).commit();
    }

    @Override
    public void onItemClicked(int position) {

    }
}
