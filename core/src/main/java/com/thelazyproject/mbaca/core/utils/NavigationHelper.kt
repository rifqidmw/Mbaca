package com.thelazyproject.mbaca.core.utils

import android.content.Context
import android.content.Intent
import android.widget.Toast

object NavigationHelper {

    private const val PACKAGE_NAME = "com.thelazyproject.mbaca"
    private const val DETAIL_NOVEL_ACTIVITY = "$PACKAGE_NAME.ui.detail.DetailNovelActivity"
    private const val FAVORITE_ACTIVITY = "$PACKAGE_NAME.favorite.FavoriteActivity"

    const val EXTRA_NOVEL_ID = "extra_novel_id"

    /**
     * Navigate to Detail Novel Activity from any module
     */
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

    /**
     * Navigate to Favorite Activity from any module
     */
    fun navigateToFavorite(context: Context) {
        try {
            val intent = Intent()
            intent.setClassName(context.packageName, FAVORITE_ACTIVITY)
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
}

