# Core Module ProGuard Rules

# Keep security classes
-keep class com.thelazyproject.mbaca.core.security.** { *; }

# Keep data models
-keep class com.thelazyproject.mbaca.core.data.source.remote.response.** { *; }
-keep class com.thelazyproject.mbaca.core.domain.model.** { *; }
-keep class com.thelazyproject.mbaca.core.data.source.local.entity.** { *; }

# Room
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

# Retrofit & OkHttp
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

# Certificate Pinning
-keep class okhttp3.CertificatePinner { *; }
-keep class okhttp3.CertificatePinner$Pin { *; }

