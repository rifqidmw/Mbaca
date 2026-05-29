# Consumer ProGuard Rules for Core Module
# These rules will be applied to the app module automatically

# Keep all public APIs in core module
-keep public class com.thelazyproject.mbaca.core.** { public *; }

# Keep data models (never obfuscate data classes)
-keep class com.thelazyproject.mbaca.core.data.source.remote.response.** { *; }
-keep class com.thelazyproject.mbaca.core.domain.model.** { *; }
-keep class com.thelazyproject.mbaca.core.data.source.local.entity.** { *; }

# Keep domain use cases
-keep class com.thelazyproject.mbaca.core.domain.usecase.** { *; }

# Keep repository interfaces
-keep interface com.thelazyproject.mbaca.core.domain.repository.** { *; }

# Room Database
-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
-keep @androidx.room.Dao interface *

# Retrofit API interfaces
-keep interface com.thelazyproject.mbaca.core.data.source.remote.network.** { *; }

# SQLCipher
-keep class net.sqlcipher.** { *; }
-keep class net.sqlcipher.database.** { *; }

# Security classes (never obfuscate encryption/security)
-keep class com.thelazyproject.mbaca.core.security.** { *; }

