# MqttFix_simple
| [中文](./README-zh.md) | English |
The MqttFix project is an alternative to [paho.mqtt.android](https://github.com/eclipse-paho/paho.mqtt.android).  
It mainly solves compatibility issues with Android API 31 and above.  
Since MqttAndroid hasn't been updated for a long time, this provides an alternative solution.  
The issues addressed by the original project include:
- [x] 1. v4 package -> androidx.localbroadcastmanager:localbroadcastmanager:1.0.0
- [x] 2. Adaptation for Android 14 projects
- [x] 3. PendingIntent Bug

## How to Use

1. Add dependencies
```gradle  
// compile 'org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.1.0'
// compile 'org.eclipse.paho:org.eclipse.paho.android.service:1.1.1'

implementation "org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.5" // Original Mqtt dependency  
implementation 'androidx.work:work-runtime:2.7.1' // WorkManager  
implementation 'androidx.localbroadcastmanager:localbroadcastmanager:1.0.0' // Replace v4 & do not use 1.1.0  
implementation 'com.github.QingHeYang:MqttFix:1.0.3-fix2'
```

2. Permissions
```xml
<uses-permission android:name="android.permission.INTERNET" />
```  

3. Service
```xml
<service android:name="cloud.deepblue.mqttfix.mqtt.MqttService" />
```  

4. Usage  
   The usage is identical to [paho.mqtt.android](https://github.com/eclipse-paho/paho.mqtt.android), except that the package name is different.  
   You can simply replace the dependency without needing to modify any code.

