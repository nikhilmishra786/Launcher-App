package com.androidlikepro.launcherapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.androidlikepro.launcherappsdk.AppInfo

class AppListAdapter(context: Context, var appInfoList: List<AppInfo>) :
    RecyclerView.Adapter<AppListAdapter.ViewHolder>() {

    fun filterList(filterList: List<AppInfo>){
        appInfoList = filterList
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val appIcon: ImageView = itemView.findViewById(R.id.launcher_icon_iv_item)
        val appName: TextView = itemView.findViewById(R.id.app_name_tv_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.app_layout_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.appIcon.setImageDrawable(appInfoList[position].appIcon)
        holder.appName.text = appInfoList[position].appName
        holder.itemView.setOnClickListener {
            val launchIntent =
                holder.itemView.context.packageManager.getLaunchIntentForPackage(appInfoList[position].packageName)
            holder.itemView.context.startActivity(launchIntent)
            Toast.makeText(
                holder.itemView.context,
                appInfoList[position].appName,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun getItemCount() = appInfoList.size


}