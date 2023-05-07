import 'footballnotification_platform_interface.dart';

class Footballnotification {
  Future getPlatformVersion({
    required bool notification,
    required String token,
  }) {
    return FootballnotificationPlatform.instance
        .getPlatformVersion(token: token, notification: notification);
  }
}
