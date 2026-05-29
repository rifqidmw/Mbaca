-keep class com.thelazyproject.mbaca.core.security.** { *; }

-keep class com.thelazyproject.mbaca.core.data.source.remote.response.** { *; }
-keep class com.thelazyproject.mbaca.core.domain.model.** { *; }
-keep class com.thelazyproject.mbaca.core.data.source.local.entity.** { *; }

-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-dontwarn androidx.room.paging.**

-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}


