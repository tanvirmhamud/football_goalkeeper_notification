package com.example.footballnotification

import android.app.*
import android.content.*
import android.content.Context.ACTIVITY_SERVICE
import android.os.Build
import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.startForegroundService
import com.example.backgroundservice.Mybackground
import com.example.backgroundservice.Notification.Livematchnotification.Notification2
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result


/** FootballnotificationPlugin */
class FootballnotificationPlugin: FlutterPlugin, MethodCallHandler, ActivityAware {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel
  private lateinit var context: Context
  private lateinit var activity: Activity
//  lateinit var notificationManager: NotificationManager
//  lateinit var notificationChannel: NotificationChannel
//  lateinit var builder: Notification.Builder
//  private val channelId = "i.apps.notifications"
//  private val description = "Test notification"

  override fun onAttachedToEngine(@NonNull flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "footballnotification")
    channel.setMethodCallHandler(this)
  }

  @RequiresApi(Build.VERSION_CODES.O)
  override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
    if (call.method == "getPlatformVersion") {
      var token : String ? = call.argument("token");
      context = activity.applicationContext;
      if (isMyServiceRunning(Mybackground::class.java) == false){
        var service = Intent(context, Mybackground::class.java)
        service.putExtra("token",token);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
          activity.startService(service)
        } else {
          activity.startService(service)
        }
      }else{
        var service = Intent(context, Mybackground::class.java)
        activity.stopService(service);
        service.putExtra("token",token);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
          activity.startService(service)
        } else {
          activity.startService(service)
        }
      }


    } else if(call.method == "notificatonoff") {
      var service = Intent(context, Mybackground::class.java)
      activity.stopService(service);
    }
  }







  private fun isMyServiceRunning(serviceClass: Class<*>): Boolean {
    val manager: ActivityManager =activity.getSystemService(ACTIVITY_SERVICE) as ActivityManager
    for (service in manager.getRunningServices(Int.MAX_VALUE)) {
      if (serviceClass.name == service.service.getClassName()) {
        return true
      }
    }
    return false
  }

  override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
    context = binding.applicationContext
    channel.setMethodCallHandler(null)
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    activity = binding.activity;
  }

  override fun onDetachedFromActivityForConfigChanges() {
    Log.d("TAG", "onAttachedToService");
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    Log.d("TAG", "onAttachedToService");
  }

  override fun onDetachedFromActivity() {
    Log.d("g", "onAttachedToService");
  }



  @RequiresApi(Build.VERSION_CODES.O)
  fun notificationsend() {
    var CHANNEL_ID : String = "CHANNEL_ID"

    val name = "Channel Name"
    val descriptionText = "Description"
    val importance = NotificationManager.IMPORTANCE_DEFAULT
    val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
      description = descriptionText
    }
    var builder = Notification.Builder(context, CHANNEL_ID)
      .setSmallIcon(R.drawable.ic_launcher_foreground)
      .setContentTitle("ascascascas")
      .setContentText("sdvsvsvsdv")
      .build()

// Issue the new notification.
    val notificationManager: NotificationManager =
      context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
    NotificationManagerCompat.from(context).apply {
      notificationManager.notify(1002, builder)
    }
  }




}
