package com.mrkv.walkingnavigator


import android.app.AlertDialog.Builder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.map.VisibleRegionUtils
import com.yandex.mapkit.mapview.MapView
import com.yandex.mapkit.search.*
import com.yandex.mapkit.search.Response
import com.yandex.mapkit.search.SearchFactory
import com.yandex.mapkit.search.SearchManagerType
import com.yandex.mapkit.search.SearchOptions
import com.yandex.mapkit.search.SearchType
import com.yandex.mapkit.search.Session.SearchListener

const val API_KEY = BuildConfig.MAP_API_KEY

class MainActivity : AppCompatActivity() {

    private lateinit var mapView: MapView
    private lateinit var routeText: TextView
    private lateinit var routeEditText: EditText
    private lateinit var routeButton: Button
    private lateinit var searchManager: SearchManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey(API_KEY)
        MapKitFactory.initialize(this)
        SearchFactory.initialize(this)
        setContentView(R.layout.activity_main)
        mapView = findViewById(R.id.mapview)
        Log.d("MainActivity", "onCreate started")

        routeText = findViewById(R.id.routeText)
        routeEditText = EditText(this)
        routeButton = findViewById(R.id.routeButton)

//        searchManager = SearchFactory.getInstance().createSearchManager(SearchManagerType.COMBINED)
//        val searchListener = object : SearchListener {
//            override fun onSearchResponse(response: Response) {
//                // Search response processing
//                val resultList = response.collection.children
//                // resultList contains list of the search result found
//            }
//
//            override fun onSearchError(error: com.yandex.runtime.Error) {
//                // Handling a search error
//            }
//        }

        routeText.setOnClickListener {
            findAddress(routeEditText)
        }
    }

//    private fun searchAdress(query: String, searchListener: SearchListener) {
//        val searchOptions = SearchOptions().apply {
//            searchTypes = SearchType.GEO.value
//            resultPageSize = 32
//        }
//        val visibleBounds = mapView.map.visibleRegion
//        val session = searchManager.submit(
//            query,
//            VisibleRegionUtils.toPolygon(visibleBounds),
//            searchOptions,
//            searchListener
//        )
//    }

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

    private fun findAddress(editText: EditText) {
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
}

//    private fun findInYandexMapKit(searchManager: SearchManager) {
//
//        val searchOptions = SearchOptions().apply {
//            searchTypes = SearchType.GEO.ordinal
//            resultPageSize = 32
//        }
//
//        val searchSessionListener = object : SearchSessionListener {
//            override fun onSearchResponse(response: Response) {
//
//            }
//
//            override fun onSearchError(error: Error) {
//
//            }
//        }
//        val session = searchManager.submit(
//            "Where I go?",
//            VisibleRegionUtils.toPolygon(map.visibleregion),
//            searchOptions,
//            searchSessionListener,
//            )
//    }
//}