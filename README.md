# Welcome to the Delo-challenge by Franklin
Simple app which shows a couple of Portuguese postal code and addresses

### Architecture
I've Decided to use MVVM, that was the challenge suggestion.

### Instructions to build
* I added some //TODO marks in order to show things that I failed
* This project doesnt contain Tests implementation
* I didn't implement the Filter feature
* If you think its taking too long time to load the Database, please close the APP and open it again (i didn't implement thread optimizations)
We have 2 main schemas.
* **debug** - It is responsible to set the BuildConfig with debug/develop config (ie. API Keys or Base URLs)
* **release** - As debug variant, we can set the application to release, using proguard and other optimizations

### Features
* You can see an image as a separated fragment. Just click on image from the list
* Internet Connection verification

### Important:
* **Retrofit2** - just because I have to request to a server API
* **Moshi** - handle json response (deprecated)
* **ROOM** - Database manipulation
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
* Filtering postal codes
* Better CSV manipulation (in order to save into database properly and better)
* test coverage (unit tests and instrumentation tests)
* Check ssl/https certificates when request to Unsplash server (avoiding Man in the middle attacks)
