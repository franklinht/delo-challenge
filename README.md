# Wellcome to the Delo-challenge by Franklin
Simple app which shows a couple of Porguese postal code and addresses

### Archtecture
I've Decided to use MVVM, that was the challenge suggestion.

### Instructions to build
We have 2 main schemas.
* **debug** - It is responsible to set the BuildConfig with debug/develop config (ie. API Keys or Base URLs)
* **release** - As debug variant, we can set the application to release, using proguard and other optimizations

### Features
* You can see an image as a separated fragment. Just click on image from the list
* Internet Connection verification

### Important:
* **Retrofit2** - just because I have to request to a server API
* **Moshi** - handle json response
* **AndroidX** - Migrated to AndroidX
* **Android Lifecycle** - binds, remove smelly code, livedata
* **Activity + Fragment** - easy way to separate the lifecycle and memory optimization
* **RecyclerView + GridLayoutManager** - Just for better performance against GridLayout
* **JUnit** for unit tests

### TESTS
* When you run tests, please, turn animations of on your cellphone ( [AnimationsOFF-DEVICE](https://stackoverflow.com/a/44005848) )
* The test purpose is run the adresses list and the hit the position 0 and then copy the address text

### Next Steps
* Proguard config
* buildTypes tuning
* test coverage
* Check ssl/https certificates when request to Unsplash server (avoiding Man in the middle attacks)
