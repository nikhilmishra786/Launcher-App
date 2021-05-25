package com.androidlikepro.launcherapp

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.androidlikepro.launcherappsdk.AppInfo
import com.androidlikepro.launcherappsdk.QueryAppData
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var appInfoList: List<AppInfo>
    lateinit var appListAdapter: AppListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appInfoList = QueryAppData.getAppInfo(this)
        appListAdapter = AppListAdapter(this, appInfoList)
        app_list_rv.layoutManager = LinearLayoutManager(this)
        app_list_rv.adapter = appListAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        val searchItem = menu?.findItem(R.id.actionSearch)
        val searchView = searchItem?.actionView as SearchView
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filter(newText)
                return false
            }
        })
        return true
    }

    fun filter(text: String) {
        val filteredlist: MutableList<AppInfo> = mutableListOf()

        for (item in appInfoList) {
            if (item.appName.toLowerCase(Locale.ROOT).contains(text.toLowerCase(Locale.ROOT))) {
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            no_found_tv.visibility = View.VISIBLE
        } else {
            no_found_tv.visibility = View.GONE
        }
        appListAdapter.filterList(filteredlist)
    }

}