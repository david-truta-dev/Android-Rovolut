# Retrofit + Gson
-keep class com.google.gson.** { *; }
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions
-dontwarn retrofit2.**
-dontwarn okhttp3.**
-dontwarn okio.**

# Keep model classes used with Retrofit & Gson
-keepclassmembers class * {
    @retrofit2.http.* <methods>;
}

# Coil (image loading)
-keep class coil.** { *; }
-dontwarn coil.**

# Lottie
-keep class com.airbnb.lottie.** { *; }

# RxJava
-dontwarn io.reactivex.**

# DataStore + Kotlin Serialization
-keep class kotlinx.serialization.** { *; }
-dontwarn kotlinx.serialization.**

# General Kotlin/Coroutines
-keepclassmembers class kotlinx.coroutines.** { *; }
-dontwarn kotlinx.coroutines.**

# Hilt
-keep class dagger.** { *; }
-keep interface dagger.** { *; }
-keep class javax.inject.** { *; }
-keep class * {
    @dagger.** *;
}
-keep class * {
    @javax.inject.* *;
}
-dontwarn dagger.hilt.**
-dontwarn javax.inject.**

# ViewModel (optional but useful if you're doing reflection)
-keep class androidx.lifecycle.ViewModel { *; }
-keep class * extends androidx.lifecycle.ViewModel

# Avoid removing Kotlin metadata
-keepattributes *Annotation*
-keep class kotlin.Metadata { *; }
