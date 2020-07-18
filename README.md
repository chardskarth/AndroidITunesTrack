# My ITunesTrackApplication
Welcome to my first android app. This is a sample application as part of my android learning!

* **Dependency Injection**. Using a lightweight DI library: **Koin**. One of the goals achieved here decoupling usages from creation of the classes. This also allows us to rely on abstractions over implementation.

* **ViewModels, DataBinding, BindingAdapters**. Thanks to **android jetpack**, android layouts can now be decorated and given business logic **separately**.

* **Kotlin Typealiases** are utilized in this project. They are found in the `KoinModule.kt` of each (`package feature`)[#Organizing package]. Type aliases, with proper naming conventions, aims to help in reading `long nested type parameters`. 

* **No mapper library yet**. `object-to-object` mapping is done simply via extension functions and data class constructors.

* :( **Plain UI** - will still have to work on this.

## Features
* Seamless scrolling through iTunes music track

*Other features in progress:*
* Searching of tracks
* Viewing more details of the track
* Persisting last viewed date of a track
* Persisting last state after closing the application

## Architecture
Package are organized `by feature` then `by layer`.
Here's a sample structure:
```
  track
  ├── gateway
  │   ├── impl
  │   └── model
  ├── mapper
  ├── model
  ├── ui
  └── viewmodel
	
  musicplayer
  ├── gateway
  ├── mapper
  ├── model
  ├── ui
  └── viewmodel
```

> `track` is an ambiguous definition on its own. But when seen besides the `musicplayer`, the reader you should at least grasp the idea that the application has a *music track* and a *music player*.

>ps. `musictrack` here is just for example. Unfortunately there's no music player feature here. There's none yet at least ;)

### Relying on abstraction over implementation
This allows ease in future modifications. (ie, changing where the data is retrieved or migrating to a different database).  (see `ITunesApi.kt` interface).

### Gateway
This is where data from outside of the application are either retrieved or sent. 

At the time of writing, the implementation choice for `HttpClient` is `Ktor`. Instantiation and Configuration of `HttpClient` is done in each implementing classes(ie. ITunesKtorApi.kt) with the help of custom made kotlin dsl (see `KtorApi.kt` and `HttpBuilderFeatureConfig.kt`).

Another class to take note of: `ApiResultCallback.kt`. Android `ViewModels` can listen to results of api by creating its own implementation of `ApiResultCallbackHandler`.

**And besides from Rest API**, we also have Android's `DataSource` and `DataSourceFactory` in the `gateway` package.

### Model
[What are domain models?](https://medium.com/@olegchursin/a-brief-introduction-to-domain-modeling-862a30b38353)

> The Domain Model is your organized and structured knowledge of the problem. The Domain Model should represent the vocabulary and key concepts of the problem domain and it should identify the relationships among all of the entities within the scope of the domain.

To separate concerns between business requirements and implementation details, we depend mostly on Domain models.

### Mapper and DTOs (or Resource/Persistence Models)

In line with what was mentioned above, we have representations of models that exist due to technical reasons. 

Verbosely, they can be broken down further into:
* DTOs - a more general representation of model which we pass around different technical layers. (ie. a Message Queue, a Rest API or a Database)
* Resource/Response Model - somewhat similar to DTOs but are specifically used in REST API.
* Persistence Entity Model - somewhat similar to DTOs but are specifically used in Database.

*This project however, due to simplicity of the features at the time, is not yet able to demo this in detail.*  

What you can see here is how `ITunesApi` requires a `searchTerm: String` and `page: Int` as ResourceModel and they are **mapped from** a `Response model` to `Domain model` in return. 

### UIs

This is where we put, `Activities`, `Fragments`, `View Adapters` classes. For ease of reference, we can call these `UI controllers`. We can expect `UI controllers` to be responsible for Android OS communication, handle system notifications, handle activity lifecycle and strip it out of other responsibilities and defer them to `View models` as described below.

### ViewModel and DataBinding
View models are given responsibility for View data ownership, data source management and persistence across different activities which share UI display data. 

This project makes use of android's `DataBinding` and `BindingAdapters` to further separate data updates and 

### Future improvements for architecture:

* Goal for Persistence over Android's SQLite database
* Utilize gradle multiproject or kotlin-multiplatform to show shared library access with a co-existing web application.
* Better `ApiResultCallback`: add `Flow` to streamline response callback over simultaneous api calls.
* `Retrofit`-like interface implementation for `KtorApis`



