// ignore_for_file: await_only_futures

import 'package:flutter/material.dart' hide Intent;
import 'dart:async';
import 'package:flutter/services.dart';
import 'package:footballnotification/footballnotification.dart';
import 'package:receive_intent/receive_intent.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  final _footballnotificationPlugin = Footballnotification();

  Intent? initialIntent;

  Future<void> initPlatformState() async {
    String platformVersion;

    try {
      platformVersion = await _footballnotificationPlugin.getPlatformVersion(
        leagueid: [98,292,169,17,71,262,281,265,242,239, 140,78,135,61,39, 88, 235,218,179,94,207, 103,79, 62,253,133,540, 949,13,16,43,40,144,119,210,136],
              notification: true, token: "") ??
          'Unknown platform version';
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  Future<void> _init() async {
    final receivedIntent = await ReceiveIntent.getInitialIntent();

    if (!mounted) return;

    setState(() {
      initialIntent = receivedIntent;
      print(initialIntent!.extra);
    });
  }

  @override
  void initState() {
    _init();
    super.initState();
  }

  @override
  void dispose() {
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Column(
          children: [
            Center(
              child: MaterialButton(
                color: Colors.indigo,
                onPressed: () {
                  initPlatformState();
                },
                child: Text("background"),
              ),
            ),
            MaterialButton(
              color: Colors.indigo,
              onPressed: () {},
              child: Text("Notification Permission"),
            ),
            _buildFromIntent("INITIAL", initialIntent!),
            // StreamBuilder<Intent?>(
            //   stream: ReceiveIntent.receivedIntentStream,
            //   builder: (context, snapshot) =>
            //       _buildFromIntent("STREAMED", snapshot.data!),
            // )
          ],
        ),
      ),
    );
  }

  Widget _buildFromIntent(String label, Intent intent) {
    return Center(
      child: Column(
        children: [
          Text(label),
          Text(
              "fromPackage: ${intent.fromPackageName}\nfromSignatures: ${intent.fromSignatures}"),
          Text(
              'action: ${intent.action}\ndata: ${intent.data}\ncategories: ${intent.categories}'),
          Text("extras: ${intent.extra}")
        ],
      ),
    );
  }
}
