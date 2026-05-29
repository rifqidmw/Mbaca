-keepattributes *Annotation*
-keepattributes Signature
-keepattributes SourceFile,LineNumberTable
-keepattributes InnerClasses
-keepattributes EnclosingMethod

-keep class com.thelazyproject.mbaca.favorite.** { *; }

-keep public class * extends android.app.Activity
-keep public class * extends androidx.fragment.app.Fragment

-keep @dagger.hilt.EntryPoint interface *
-keep class dagger.hilt.** { *; }
-keep class javax.inject.** { *; }

-keep class * implements androidx.viewbinding.ViewBinding {
    public static *** bind(android.view.View);
    public static *** inflate(android.view.LayoutInflater);
}

-keep class androidx.lifecycle.** { *; }
-dontwarn androidx.lifecycle.**

-keep class kotlin.** { *; }
-keep class kotlin.Metadata { *; }
-dontwarn kotlin.**

-keepclassmembernames class kotlinx.** {
    volatile <fields>;
}

