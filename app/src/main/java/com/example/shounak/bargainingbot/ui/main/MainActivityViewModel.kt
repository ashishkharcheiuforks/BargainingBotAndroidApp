package com.example.shounak.bargainingbot.ui.main

import androidx.lifecycle.ViewModel
import com.example.shounak.bargainingbot.data.repository.UserRepository
import com.example.shounak.bargainingbot.internal.lazyDeferred

/**
 * Created by Shounak on 02-Feb-19
 */
class MainActivityViewModel(
   private val userRepository: UserRepository) :  ViewModel() {


   val user by lazyDeferred {
      userRepository.getCurrentUser()
   }






}