<p align="center">  
PhotoWeather is a demo application Utilizing the MVVM + Kotlin + Flows + Room + Hilt.<br>

It allows the user to take an image using their camera and detects the location of the user, added some information about the weather and saves the image. It also allows sharing of the image with the weather info through third party applications.</br>

## Application MAD Score cards

![Summary](https://github.com/Karim-92/PhotoWeather/blob/master/summary.png)
![Kotlin](https://github.com/Karim-92/PhotoWeather/blob/master/kotlin.png)
![Jetpack](https://github.com/Karim-92/PhotoWeather/blob/master/jetpack.png)

## Architecture
  - PhotoWeather is based on MVVM architecture and a repository pattern.

![architecture](https://user-images.githubusercontent.com/24237865/77502018-f7d36000-6e9c-11ea-92b0-1097240c8689.png)
  
## Usage
  - To use the application and build it correctly you need to signup for the openweather developer API program, get a developer Key and create a file called "keys.properties" in the root directory of the project. You'll then need to add an entry with your developer key. 
  
  ```xml
  API_KEY = "Your developer key here"
```
  And that's it! you're ready to build the project.
  
## Libraries used

  - [Bindables](https://github.com/skydoves/bindables) - Android DataBinding kit for notifying data changes to UI layers.
  - [Retrofit2 & OkHttp3](https://github.com/square/retrofit) - construct the REST APIs and paging network data.
  - [Moshi](https://github.com/square/moshi/) - A modern JSON library for Kotlin and Java.
  - [Glide](https://github.com/bumptech/glide), [GlidePalette](https://github.com/florent37/GlidePalette) - loading images.
  - [TransformationLayout](https://github.com/skydoves/transformationlayout) - implementing transformation motion animations.
  - [WhatIf](https://github.com/skydoves/whatif) - checking nullable object and empty collections more fluently.
  - [Bundler](https://github.com/skydoves/bundler) - Android Intent & Bundle extensions that insert and retrieve values elegantly.
  - [Timber](https://github.com/JakeWharton/timber) - logging.
  - [Hilt](https://dagger.dev/hilt/) - Dependency injection library built on top of Dagger2.
  - [EasyPermissions](https://github.com/googlesamples/easypermissions) - A library that makes the process of acquiring permissions elegant and simple.
  - [ImagePicker](https://github.com/Dhaval2404/ImagePicker) - Easy to use and configurable library to Pick an image from the Gallery or Capture image using Camera.
  - [Lottie](https://github.com/airbnb/lottie-android) - Lottie is a mobile library for Android and iOS that parses Adobe After Effects animations exported as json with Bodymovin and renders them natively on mobile!

## Influence
- This was built as a task for Robusta Studios application process.

