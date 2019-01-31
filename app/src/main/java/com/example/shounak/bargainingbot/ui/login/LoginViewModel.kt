package com.example.shounak.bargainingbot.ui.login

import androidx.lifecycle.ViewModel
import com.example.shounak.bargainingbot.data.repository.UserRepository
import com.google.firebase.auth.FirebaseUser

/**
 * Created by Shounak on 30-Jan-19
 */




class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {



//        TODO("Move code from Activity that isn't related to ui")


suspend fun updateData(user : FirebaseUser){

    val name = user.displayName
    val email  = user.email
    val uid = user.uid
    val photoUrl = user.photoUrl

    userRepository.setCurrentUser(uid, name!!, email, photoUrl )

}




}