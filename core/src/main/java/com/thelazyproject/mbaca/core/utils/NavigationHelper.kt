package com.thelazyproject.mbaca.core.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest

object NavigationHelper {

    private const val PACKAGE_NAME = "com.thelazyproject.mbaca"
    private const val DETAIL_NOVEL_ACTIVITY = "$PACKAGE_NAME.ui.detail.DetailNovelActivity"
    private const val FAVORITE_ACTIVITY = "$PACKAGE_NAME.favorite.FavoriteActivity"

    const val EXTRA_NOVEL_ID = "extra_novel_id"

    fun navigateToDetail(context: Context, novelId: String) {
        try {
            val intent = Intent()
            intent.setClassName(context.packageName, DETAIL_NOVEL_ACTIVITY)
            intent.putExtra(EXTRA_NOVEL_ID, novelId)
            context.startActivity(intent)
        } catch (e: ClassNotFoundException) {
            Toast.makeText(
                context,
                "Detail screen not available. Please reinstall the app.",
                Toast.LENGTH_LONG
            ).show()
            e.printStackTrace()
        } catch (e: Exception) {
            Toast.makeText(
                context,
                "Error opening detail: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
            e.printStackTrace()
        }
    }

    fun navigateToFavorite(context: Context) {
        try {
            val intent = Intent()
            intent.setClassName(context.packageName, Class.forName(FAVORITE_ACTIVITY).name)
            context.startActivity(intent)
        } catch (e: ClassNotFoundException) {
            Toast.makeText(
                context,
                "Favorite feature not available. Please reinstall the app.",
                Toast.LENGTH_LONG
            ).show()
            e.printStackTrace()
        } catch (e: Exception) {
            Toast.makeText(
                context,
                "Error opening favorites: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
            e.printStackTrace()
        }
    }

    fun installFavoriteModule(context: Context) {
        val splitInstallManager = SplitInstallManagerFactory.create(context)
        val moduleChat = "favorite"
        if (splitInstallManager.installedModules.contains(moduleChat)) {
            navigateToFavorite(context)
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(moduleChat)
                .build()
            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    Toast.makeText(context, "Success installing module", Toast.LENGTH_SHORT).show()
                    navigateToFavorite(context)
                }
                .addOnFailureListener {
                    Toast.makeText(context, "Error installing module", Toast.LENGTH_SHORT).show()
                }
        }
    }
}

