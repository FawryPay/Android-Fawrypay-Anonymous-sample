# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\Android_sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

##---------------Begin: settings, recommended for libraries (https://www.guardsquare.com/en/proguard/manual/examples#library):
-keepparameternames
-renamesourcefileattribute SourceFile
-keepattributes Exceptions, InnerClasses, Signature, Deprecated, SourceFile, LineNumberTable, *Annotation*, EnclosingMethod

# Preserve all native method names and the names of their classes.
-keepclasseswithmembernames, includedescriptorclasses class * {
    native <methods>;
}

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
##---------------End: settings, recommended for libraries

##---------------Begin: proguard configuration for Gson  ----------
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# For using GSON @Expose annotation
-keepattributes *Annotation*

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Prevent proguard from stripping interface information from TypeAdapterFactory,
# JsonSerializer, JsonDeserializer instances (so they can be used in @JsonAdapter)
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer
-keep class * implements com.google.gson.** { *;}

##---------------End: proguard configuration for Gson  ----------

##---------------Begin: custom library settings
# Keep all classes from "data" subfolder (and nested subfolders), because they can be serialized/deserialized.
-keep class com.fawry.fawrypay.FawrySdk { *; }
-keep class com.fawry.fawrypay.FawrySdk$*  { *; }
-keep class com.fawry.fawrypay.interfaces.FawrySdkCallbacks { *; }
-keep class com.fawry.fawrypay.FawrySdk$Languages { *; }
-keep class com.fawry.fawrypay.FawrySdk$PaymentMethods { *; }
-keep class com.fawry.fawrypay.models.** { *;}
-keep class com.fawry.fawrypay.interfaces.** { *; }
-keep class com.fawry.fawrypay.utils.** { *; }
-keep class com.fawry.fawrypay.ui.payment_module.payment_frag.** { *; }
-keep class com.fawry.fawrypay.ui.payment_module.** { *; }
-keep class com.fawry.fawrypay.ui.address_module.** { *; }
-keep class com.fawry.fawrypay.ui.manage_cards_module.** { *; }
-keep class com.fawry.fawrypay.ui.manage_cards_module.card_list_frag.** { *; }
-keep class com.fawry.fawrypay.services.** { *; }
-keep class com.fawry.fawrypay.PaymentStatusService { *; }

##---------------End: custom library settings  ----------

# Keep RecyclerView classes and interfaces
-keep class androidx.recyclerview.widget.** { *; }
-keep class androidx.recyclerview.widget.RecyclerView { *; }
-keep class androidx.recyclerview.widget.LinearLayoutManager { *; }

# Keep ViewHolder classes and interfaces
-keep class **.MyViewHolder { *; }

# Keep LayoutManager classes and interfaces
-keep class androidx.recyclerview.widget.RecyclerView$LayoutManager { *; }

# Keep ItemAnimator classes and interfaces
-keep class androidx.recyclerview.widget.RecyclerView$ItemAnimator { *; }

# Keep any classes that implement RecyclerView.OnScrollListener or RecyclerView.ItemDecoration
-keep class * extends androidx.recyclerview.widget.RecyclerView$OnScrollListener { *; }
-keep class * extends androidx.recyclerview.widget.RecyclerView$ItemDecoration { *; }

# Keep any classes that implement RecyclerView.Adapter or RecyclerView.ViewHolder
-keep class * extends androidx.recyclerview.widget.RecyclerView$Adapter { *; }
-keep class * extends androidx.recyclerview.widget.RecyclerView$ViewHolder { *; }