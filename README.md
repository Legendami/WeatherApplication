![](https://openweathermap.org/themes/openweathermap/assets/img/logo_white_cropped.png)
# Weather Application
A simple offline-first weather application for android build with Kotlin, CleanMVVM Architecture, RxJava 2 and Dagger 2

## Features

- Supports offline persistent no-sql data with Realm
- Build exclusively in AndroidX
- Implements LiveData
- Made in [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- Designed in a MVVM pattern
- Implements basic levels of security
- Uses Dagger 2 and some RxJava
- Uses Android Jetpack libraries

## The App
The app a Weather App quite similar to how you would find them on Google Play or even on the App Store. It features the current, hourly and daily weather of each capital in Europe. 

### Architecture & pattern
I chose to use an MVVM design pattern because i believe it is the best way to have a seperation of concerns and business logic within a app. The MVVM pattern presents a better separation of concerns with viewmodels. A viewmodel translates the data of the model layer into something the view layer can use. The controller is no longer responsible for this task. Proper MVVM assumes at least a moderately complex application, which deals with data from "somewhere". In this case it is both from local persistent no-sql Realm database or the provided server endpoint. I also am a personal fan of MVVM as i've been using it for some time in building Xamarin apps in the past with [MvvmCross](https://www.mvvmcross.com/)

![](https://josipsalkovic.com/wp-content/uploads/2019/12/mvvm_architecture.png)

Initially this meant having a remote datasource and a local datasource. And so i used a traditional [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) approach. The goals of clean architecture is to provide a way to organize code in a way that it encapsulates the business logic but keeps it separate from the delivery mechanisms. Though traditionally in Java you would use callbacks, once the clean architecture was implemented within Kotlin, i moved over to using LiveData, injecting dependencies with Dagger and handling observables with RxJava.

The app has some different approaches for doing the same things. Sometimes it uses @inject to inject dependencies, other times the app uses modules that @provides those and other times it uses simple Companion Objects. This is intentional, as to showcase how all roads can lead to Rome.

![](https://josipsalkovic.com/wp-content/uploads/2019/12/clean-mvvm-1536x413.png)

![](https://blog.cleancoder.com/uncle-bob/images/2012-08-13-the-clean-architecture/CleanArchitecture.jpg)

### Data
For the weatherdata we are using a free account access to the OpenWeather API. This will deliver an extensive enough weatherdata for each given long and lat of a location:
- Current Forecast
- Hourly Forecast for 2 days
- Daily Forecast for 7 days

When the app loads the list of cities, the app only gets the current forecast for each city. After clicking on a city, the hourly and daily forecast are pulled in from the API as well. Every time any type of data is pulled from the API, it is persistently saved offline in a NoSQL Realm Database. If internet is unavailable, it will rely on the last saved data from the local Realm database.

Free access to the OpenWeather Api does have some limitations:
- No more then 60 calls per minute
- No more then 1000 calls per day

The app has around 40-50 capitals for the entire list of European cities. This means that after loading the list with current weather per city, you should be able to click and check weather from each other city up to 60 calls within 1 minute. Which is sufficient for this usecase.

### Security
As a mobile developer, and especially on Android, developing with "security first" in mind is paramount. Even though the data being fetched in this app is not really sensitive data, i wanted to provide some basic security implementations. You want to keep your client as dumb as possible but at the same time, the security of your complete architecture stack will be just as strong as your weakest link. So i made sure to use the highest ciphersuite available from your server, checked through SSLlabs. I made sure to have a NetworkSecurityConfig that enforces SSL and i created a keystore implementation so we can encrypt the local Realm Database. This however still needs to be implemented in the future since i want to run on the higest NIST P-384 requirements available with caching and sharedpreferences. Take a peek in in the Security Folder for WIP.

### User Experience
I added some quality of life extra's like app-icons, splashscreen, cardviews and toast messages for whenever something goes wrong with error handling. Since the application needs data first, opening the app without any internet connection will prompt a message asking for a connection to get the first weather forecast. The UI has stock Android elements with a sharp black-white contrast and simple elegant cards.

![citylist](/assets/CityList.png "City List"){ width=50% } ![cityweather](/assets/CityWeather.png "City Weather"){ width=50% }

## Things to do:
- Continue updating and implementing preliminary security work from the security folder
- Seperate retrofit calls with reactive single usecases and handle those with seperate callbacks
- Make all weather icons from the API available offline for consistency
- Maybe add a nice picture from the Google Search Api from each City as a gradient background
