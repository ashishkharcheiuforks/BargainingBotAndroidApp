package com.example.shounak.bargainingbot.internal

import android.net.Uri

/**
 * Singleton to return small and large profile image URL
 */

private const val GOOGLE_URL = "googleusercontent.com"
private const val FACEBOOK_URL = "graph.facebook.com"


private const val GOOGLE_PROFILE_IMAGE_QUERY_KEY = "sz"
private const val GOOGLE_PROFILE_IMAGE_QUERY_VALUE_LARGE = "200"
private const val GOOGLE_PROFILE_IMAGE_QUERY_VALUE_SMALL = "50"
private const val FACEBOOK_PROFILE_IMAGE_QUERY_KEY = "type"
private const val FACEBOOK_PROFILE_IMAGE_QUERY_VALUE_LARGE = "large"
private const val FACEBOOK_PROFILE_IMAGE_QUERY_VALUE_SMALL = "small"

object ProfileImageUrl {


    fun getLargePhotoUrl(photoUrl: Uri?): Uri? {
        val urlString = photoUrl.toString()
        if (urlString.contains(GOOGLE_URL)) {
            return Uri.parse(urlString)
                .buildUpon()
                .appendQueryParameter(GOOGLE_PROFILE_IMAGE_QUERY_KEY, GOOGLE_PROFILE_IMAGE_QUERY_VALUE_LARGE)
                .build()

        } else if (urlString.contains(FACEBOOK_URL)) {
            return Uri.parse(urlString)
                .buildUpon()
                .appendQueryParameter(FACEBOOK_PROFILE_IMAGE_QUERY_KEY, FACEBOOK_PROFILE_IMAGE_QUERY_VALUE_LARGE)
                .build()

        }

        return null
    }

    fun getSmallPhotoUrl(photoUrl: Uri?): Uri? {
        val urlString = photoUrl.toString()
        if (urlString.contains(GOOGLE_URL)) {
            return Uri.parse(urlString)
                .buildUpon()
                .appendQueryParameter(GOOGLE_PROFILE_IMAGE_QUERY_KEY, GOOGLE_PROFILE_IMAGE_QUERY_VALUE_SMALL)
                .build()

        } else if (urlString.contains(FACEBOOK_URL)) {
            return Uri.parse(urlString)
                .buildUpon()
                .appendQueryParameter(FACEBOOK_PROFILE_IMAGE_QUERY_KEY, FACEBOOK_PROFILE_IMAGE_QUERY_VALUE_SMALL)
                .build()

        }

        return null
    }

}