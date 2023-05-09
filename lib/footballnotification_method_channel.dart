import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'footballnotification_platform_interface.dart';

/// An implementation of [FootballnotificationPlatform] that uses method channels.
class MethodChannelFootballnotification extends FootballnotificationPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('footballnotification');

  @override
  Future getPlatformVersion(
      {required String token, required bool notification, required List<int> leagueid}) async {
    if (notification == true) {
      await methodChannel.invokeMethod<String>('getPlatformVersion', {
        "token": token,
        "leagueid": leagueid
      });
    } else {
      await methodChannel.invokeMethod<String>('notificatonoff');
    }
  }
}
