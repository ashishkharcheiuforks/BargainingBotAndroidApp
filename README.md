# BargainingBotAndroidApp
Android app for bargaining prices of drinks in a restaurant.

This repo is part of the [Bargaining Bot](https://github.com/shounakmulay/BargainingBot) project.

### Key features: 
* [Model-View-ViewModel](https://medium.com/upday-devs/android-architecture-patterns-part-3-model-view-viewmodel-e7eeee76b73b) architecture pattern
* [Kodein](https://github.com/Kodein-Framework/Kodein-DI) for dependency injection
* [Room](https://developer.android.com/topic/libraries/architecture/room) for local sq-lite storage
* [LiveData](https://developer.android.com/reference/android/arch/lifecycle/LiveData) and [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) to implement the observer pattern

```diff
!! The Dialogflow sdk used in this project might be depricated October 2019.
```
##

### Try it for yourself:
* First you will need to connect this app to your Firebase project. Follow the steps in the [official documentation](https://firebase.google.com/docs/android/setup?authuser=0). 
* Next setup Firebase Authentication. This app uses both Google and Facebook login. If you want to setup Facebook login you will need to [register your app with Facebook](https://developers.facebook.com/docs/facebook-login/android).
* If you don't want to setup the entire backend system and just try the app, you will need to modify the network classes to send back dummy data.
* If you have setup the entire system then at this point everything should work properly.

##

#### Some other libraries used:
* **[Groupie](https://github.com/lisawray/groupie)** for recycler views.
* **[Glide](https://github.com/bumptech/glide)** for image loading and caching.
