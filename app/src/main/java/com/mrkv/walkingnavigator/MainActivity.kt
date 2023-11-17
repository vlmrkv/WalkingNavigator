package com.mrkv.walkingnavigator


import android.annotation.SuppressLint
import android.app.AlertDialog.Builder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.map.VisibleRegionUtils
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.search.*
import com.yandex.mapkit.search.SearchFactory

const val API_KEY = BuildConfig.MAP_API_KEY

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var mapView: MapView
    private lateinit var bottomSheet: View
    private lateinit var routeText: TextView
    private lateinit var routeEditText: EditText
    private lateinit var routeStartButton: Button
    private lateinit var searchManager: SearchManager
    private lateinit var topAppBar: Toolbar
    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey(API_KEY)
        MapKitFactory.initialize(this)
        SearchFactory.initialize(this)
        setContentView(R.layout.activity_main)
        actionBar?.show()
        mapView = findViewById(R.id.mapview)
        Log.d("MainActivity", "onCreate started")

        // Initialization views
        bottomSheet = findViewById(R.id.bottom_sheet)
        routeText = findViewById(R.id.routeText)
        routeEditText = EditText(this)
        routeStartButton = findViewById(R.id.routeStartButton)
        drawerLayout = findViewById(R.id.drawer_layout)
        topAppBar = findViewById(R.id.top_app_bar)
        setSupportActionBar(topAppBar)
        topAppBar.setNavigationOnClickListener {
            drawerLayout.open()
        }

        val navigationView = findViewById<NavigationView>(R.id.navigationView)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open_nav, R.string.close_nav)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        routeText.setOnClickListener {
            findAddressDialog(routeEditText)
        }

        routeStartButton.setOnClickListener {
            hidePanel(bottomSheet)
        }
    }

    @SuppressLint("MissingSuperCall")
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        // Return bottomSheet with click on back center_button
        hidePanel(bottomSheet)
    }

    // Search
    private fun searchAddress(query: String, searchListener: Session.SearchListener) {
        searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED)
        val searchSessionListener = object : Session.SearchListener {
            override fun onSearchResponse(response: Response) {
                // Search response processing
                val resultList = response.collection.children
                // resultList contains list of the search result found
                // TODO:
            }

            override fun onSearchError(error: com.yandex.runtime.Error) {
                // Handling a search error
            }
        }

        val searchOptions = SearchOptions().apply {
            searchTypes = SearchType.GEO.value
            resultPageSize = 32
        }
        val visibleBounds = mapView.map.visibleRegion
        val session = searchManager.submit(
            query,
            VisibleRegionUtils.toPolygon(visibleBounds),
            searchOptions,
            searchSessionListener
        )
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
        Log.d("MainActivity", "onStart started")
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
        Log.d("MainActivity", "onStop started")
    }

    private fun findAddressDialog(editText: EditText) {
        val findAddressDialog = Builder(this)
        findAddressDialog.setTitle("Find address")
            .setMessage("Input address")
            .setView(editText)
            .setPositiveButton("OK") { dialog, _ ->
                routeText.text = getString(R.string.finish_text) + " " + editText.text
                dialog.dismiss()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun hidePanel(view: View) {
        view.visibility = if (view.visibility == View.VISIBLE) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }
}