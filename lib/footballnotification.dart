import 'footballnotification_platform_interface.dart';

class Footballnotification {
  Future getPlatformVersion({
    required bool notification,
    required String token,
    required List<int> leagueid,
  }) {
    return FootballnotificationPlatform.instance
        .getPlatformVersion(token: token, notification: notification,leagueid: leagueid);
  }
}
