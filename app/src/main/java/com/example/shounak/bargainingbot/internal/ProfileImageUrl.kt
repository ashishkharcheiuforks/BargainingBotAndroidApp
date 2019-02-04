package com.example.shounak.bargainingbot.internal

import android.net.Uri

/**
 * Created by Shounak on 04-Feb-19
 */

private const val GOOGLE_URL = "googleusercontent.com"
private const val FACEBOOK_URL = "graph.facebook.com"


private const val GOOGLE_PROFILE_IMAGE_QUERY_KEY = "sz"
private const val GOOGLE_PROFILE_IMAGE_QUERY_VALUE = "200"
private const val FACEBOOK_PROFILE_IMAGE_QUERY_KEY = "type"
private const val FACEBOOK_PROFILE_IMAGE_QUERY_VALUE = "large"

object ProfileImageUrl {


    fun getLargePhotoUrl(photoUrl: Uri?): Uri? {
        val urlString = photoUrl.toString()
        if (urlString.contains(GOOGLE_URL)) {
            return Uri.parse(urlString)
                .buildUpon()
                .appendQueryParameter(GOOGLE_PROFILE_IMAGE_QUERY_KEY, GOOGLE_PROFILE_IMAGE_QUERY_VALUE)
                .build()

        } else if (urlString.contains(FACEBOOK_URL)) {
            return Uri.parse(urlString)
                .buildUpon()
                .appendQueryParameter(FACEBOOK_PROFILE_IMAGE_QUERY_KEY, FACEBOOK_PROFILE_IMAGE_QUERY_VALUE)
                .build()

        }

        return null
    }

}