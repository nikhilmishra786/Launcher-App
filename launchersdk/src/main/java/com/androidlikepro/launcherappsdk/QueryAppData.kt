package com.androidlikepro.launcherappsdk

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.drawable.Drawable

object QueryAppData {
    private lateinit var appInfoList: MutableList<AppInfo>

    fun getAppInfo(context: Context): MutableList<AppInfo> {
        val packageManager = context.packageManager
        val intent = Intent(Intent.ACTION_MAIN, null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)
        val allApps: List<ResolveInfo> = packageManager.queryIntentActivities(intent, 0)
        appInfoList = ArrayList()

        for (resolveInfo in allApps) {
            val app = AppInfo(
                appName = getLabel(packageManager, resolveInfo).toString(),
                packageName = getPackageName(resolveInfo).toString(),
                appIcon = getIcon(packageManager, resolveInfo)
            )
            appInfoList.add(app)
        }
        appInfoList = appInfoList.sortedWith(
            compareBy({ it.appName })
        ) as MutableList<AppInfo>

        return appInfoList
    }

    fun getLabel(packageManager: PackageManager, resolveInfo: ResolveInfo): CharSequence {
        return resolveInfo.loadLabel(packageManager)
    }

    fun getPackageName(resolveInfo: ResolveInfo): CharSequence {
        return resolveInfo.activityInfo.packageName
    }

    fun getIcon(packageManager: PackageManager, resolveInfo: ResolveInfo): Drawable {
        return resolveInfo.loadIcon(packageManager)
    }


}