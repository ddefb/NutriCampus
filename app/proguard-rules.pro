# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/matt/ProgramFiles/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

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

-dontwarn java.awt.**
-dontwarn java.beans.Beans
-dontwarn javax.security.**
-keep class javamail.** {*;}
-keep class javax.mail.** {*;}
-keep class javax.activation.** {*;}
-keep class com.sun.mail.dsn.** {*;}
-keep class com.sun.mail.handlers.** {*;}
-keep class com.sun.mail.smtp.** {*;}
-keep class com.sun.mail.util.** {*;}
-keep class mailcap.** {*;}
-keep class mimetypes.** {*;}
-keep class myjava.awt.datatransfer.** {*;}
-keep class org.apache.harmony.awt.** {*;}
-keep class org.apache.harmony.misc.** {*;}

# proguard configuration for iText

-keep class org.spongycastle.** { *; }
-dontwarn org.spongycastle.**

-keep class com.itextpdf.** { *; }

-keep class javax.xml.crypto.dsig.** { *; }
-dontwarn javax.xml.crypto.dsig.**

-keep class org.apache.jcp.xml.dsig.internal.dom.** { *; }
-dontwarn org.apache.jcp.xml.dsig.internal.dom.**

-keep class javax.xml.crypto.dom.** { *; }
-dontwarn javax.xml.crypto.dom.**

-keep class org.apache.xml.security.utils.** { *; }
-dontwarn org.apache.xml.security.utils.**

-keep class javax.xml.crypto.XMLStructure
-dontwarn javax.xml.crypto.XMLStructure