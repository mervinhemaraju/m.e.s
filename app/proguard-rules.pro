##-keep class com.th3pl4gu3.mauritius_emergency_services.BuildConfig { *; }
#
######################################
########### Kotlin Metadata ##########
######################################
#-dontwarn org.jetbrains.annotations.**
#-keep class kotlin.Metadata { *; }
#
## ----------- > Serializations
#-keepattributes *Annotation*, InnerClasses
#-dontnote kotlinx.serialization.AnnotationsKt # core serialization annotations
#
## kotlinx-serialization-json specific. Add this if you have java.lang.NoClassDefFoundError kotlinx.serialization.json.JsonObjectSerializer
#-keepclassmembers class kotlinx.serialization.json.** {
#    *** Companion;
#}
#-keepclasseswithmembers class kotlinx.serialization.json.** {
#    kotlinx.serialization.KSerializer serializer(...);
#}
#
## Change here com.th3pl4gu3.mauritius_emergency_services
#-keep,includedescriptorclasses class com.th3pl4gu3.mauritius_emergency_services.**$$serializer { *; } # <-- change package name to your app's
#-keepclassmembers class com.th3pl4gu3.mauritius_emergency_services.** { # <-- change package name to your app's
#    *** Companion;
#}
#-keepclasseswithmembers class com.th3pl4gu3.mauritius_emergency_services.** { # <-- change package name to your app's
#    kotlinx.serialization.KSerializer serializer(...);
#}
#
################################
########### Retrofit2 ##########
################################
## Retrofit does reflection on generic parameters. InnerClasses is required to use Signature and
## EnclosingMethod is required to use InnerClasses.
#-keepattributes Signature, InnerClasses, EnclosingMethod
#
## Retrofit does reflection on method and parameter annotations.
#-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
#
## Keep annotation default values (e.g., retrofit2.http.Field.encoded).
#-keepattributes AnnotationDefault
#
## Retain service method parameters when optimizing.
#-keepclassmembers,allowshrinking,allowobfuscation interface * {
#    @retrofit2.http.* <methods>;
#}
#
## Ignore annotation used for build tooling.
##-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
#
## Ignore JSR 305 annotations for embedding nullability information.
#-dontwarn javax.annotation.**
#
## Guarded by a NoClassDefFoundError try/catch and only used when on the classpath.
#-dontwarn kotlin.Unit
#
## Top-level functions that can only be used by Kotlin.
#-dontwarn retrofit2.KotlinExtensions
#-dontwarn retrofit2.KotlinExtensions$*
#
## With R8 full mode, it sees no subtypes of Retrofit interfaces since they are created with a Proxy
## and replaces all potential values with null. Explicitly keeping the interfaces prevents this.
#-if interface * { @retrofit2.http.* <methods>; }
#-keep,allowobfuscation interface <1>
#
## Keep inherited services.
#-if interface * { @retrofit2.http.* <methods>; }
#-keep,allowobfuscation interface * extends <1>
#
## Keep generic signature of Call, Response (R8 full mode strips signatures from non-kept items).
#-keep,allowobfuscation,allowshrinking interface retrofit2.Call
#-keep,allowobfuscation,allowshrinking class retrofit2.Response
#
## With R8 full mode generic signatures are stripped for classes that are not
## kept. Suspend functions are wrapped in continuations where the type argument
## is used.
#-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
#
#######################################
########### Print Usage ###############
##uncomment line to enable print usage#
#######################################
##-printusage usage.txt