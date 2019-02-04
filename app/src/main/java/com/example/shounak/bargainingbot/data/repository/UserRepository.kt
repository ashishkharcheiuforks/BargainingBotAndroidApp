package com.example.shounak.bargainingbot.data.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.example.shounak.bargainingbot.data.db.entity.User

/**
 * Created by Shounak on 30-Jan-19
 */
interface UserRepository {
    suspend fun getCurrentUser(): LiveData<User>

    suspend fun setCurrentUser(uid: String, name: String, email: String, photoUrl: Uri?)

}