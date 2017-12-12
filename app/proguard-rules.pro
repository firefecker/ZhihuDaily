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
#---------------------------------内部部分文件禁止混淆-------------------------------
-keep class com.fire.zhihudaily.entity.** { *; }

#---------------------------------第三方依赖禁止混淆-------------------------------
# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# Gson
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.holosense.canteen.network.response.** { *; }
# OkHttp
-dontwarn okio.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault
# Okio
-dontwarn com.squareup.**
-dontwarn okio.**
-keep public class org.codehaus.* { *; }
-keep public class java.nio.* { *; }
# Retrofit
-keep class retrofit2.** { *; }
-dontwarn retrofit2.**
-keepattributes Signature
-keepattributes Exceptions
-dontwarn okio.**
-dontwarn javax.annotation.**

-keep class com.darylteo.rx.** { *; }
-dontwarn com.darylteo.rx.**


#-------------------------------------------基本不用动区域--------------------------------------------
#---------------------------------基本指令区----------------------------------
-optimizationpasses 5   # 代码混淆压缩比，在0和7之间，默认为5，一般不需要改
-dontusemixedcaseclassnames # 混淆时不使用大小写混合，混淆后的类名为小写
-dontskipnonpubliclibraryclasses    # 指定不去忽略非公共的库的类
-dontskipnonpubliclibraryclassmembers   # 指定不去忽略非公共的库的类的成员
-dontpreverify  # 不做预校验，preverify是proguard的4个步骤之一,Android不需要preverify，去掉这一步可加快混淆速度
-verbose    # 有了verbose这句话，混淆后就会生成映射文件,包含有类名->混淆后类名的映射关系,然后使用printmapping指定映射文件的名称
-printmapping proguardMapping.txt   #输出mapping.txt,文件路径在app/build/outputs/mapping/..
-optimizations !code/simplification/cast,!field/*,!class/merging/*  #指定混淆时候采用的算法，后面的参数是一个过滤器，这个过滤器是谷歌推荐的算法，一般不做更改
-keepattributes *Annotation*,InnerClasses   #保留注解和内部类
-keepattributes Signature   #保留泛型
-keepattributes EnclosingMethod #保留反射
-keepattributes SourceFile,LineNumberTable  ##抛出异常时保留代码行号


#---------------------------------默认保留区---------------------------------
# 保留了继承自Activity、Application这些类的子类
# 因为这些子类，都有可能被外部调用
# 比如说，第一行就保证了所有Activity的子类不要被混淆
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
# 如果有引用android-support-v4.jar包，可以添加下面这行
-keep class android.support.** {*;}
-keep public class * extends android.support.v4.view {*;}
# 保留所有的本地native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
# 保留在Activity中的方法参数是view的方法，
# 从而我们在layout里面编写onClick就不会被影响
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}
# 枚举类不能被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
# 保留自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
# 保留自定义控件（继承自View或者自定义容器）不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
# 保留Parcelable序列化的类不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
# 保留Serializable序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
# 对于R（资源）下的所有类及其方法，都不能被混淆
-keep class **.R$* {
 *;
}
# 对于带有回调函数onXXEvent的，不能被混淆，针对eventbus和Rxbus等事件相关的
-keepclassmembers class * {
    void *(**On*Event);
}

#---------------------------------webview------------------------------------
# 对WebView的处理
-keepclassmembers class fqcn.of.javascript.interface.for.Webview {
   public *;
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebViewClient {
    public void *(android.webkit.WebView, jav.lang.String);
}
#   针对JsonObject的处理
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
