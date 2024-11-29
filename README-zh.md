# MqttFix_simple
| 中文 | [English](README.md) |

MqttFix项目是[paho.mqtt.android](https://github.com/eclipse-paho/paho.mqtt.android)的替代品
主要解决版本 android api 31以上 兼容问题  
因为MqttAndroid已经很久不更新了，所以提供一个替代的解决方案  
原项目问题列表：
- [x] 1. v4包->androidx.localbroadcastmanager:localbroadcastmanager:1.0.0
- [x] 2. 适配android 14项目
- [x] 3. PendingIntent Bug

## 使用方法
1. 添加依赖
```gradle  

//compile 'org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.1.0'
//compile 'org.eclipse.paho:org.eclipse.paho.android.service:1.1.1'

implementation "org.eclipse.paho:org.eclipse.paho.client.mqttv3:1.2.5" // Origin Mqtt depdency
implementation 'androidx.work:work-runtime:2.7.1' // WorkManager 
implementation 'androidx.localbroadcastmanager:localbroadcastmanager:1.0.0' //replace v4 & do not use 1.1.0  
implementation 'com.github.QingHeYang:MqttFix:1.0.3-fix2'
```

2. 权限
```xml
<uses-permission android:name="android.permission.INTERNET" />
```  

3. service
```xml
<service android:name="cloud.deepblue.mqttfix.mqtt.MqttService" />
```  

4. 使用方法
   与[paho.mqtt.android](https://github.com/eclipse-paho/paho.mqtt.android)一致，只是包名不一样  
   你可以只替换依赖，不用修改任何代码  


