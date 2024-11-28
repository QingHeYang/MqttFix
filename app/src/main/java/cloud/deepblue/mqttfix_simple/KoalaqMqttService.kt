package cloud.deepblue.koalaq_android.mqtt

import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.IBinder
import android.util.Log

import cloud.deepblue.mqttfix.mqtt.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage

class KoalaqMqttService: Service() {
    val TAG = "KoalaqMqttService"

    var connOption = MqttConnectOptions()

    val subscribeList = mutableListOf<String>()


    companion object {
        var client: MqttAndroidClient? = null
        fun sendMessage(
            topic: String,
            qos: Int,
            message: String,
            success: () -> Unit,
            failed: () -> Unit
        ) {
            if (client?.isConnected == false) {
                return
            }
            val msg = MqttMessage(message.toByteArray())
            msg.setQos(qos)
            try {
                client?.publish(topic, msg, null, object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                        success()
                    }

                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                        failed()
                    }

                })
            } catch (_: MqttException) {
            }

            fun sendMessage(topic: String, qos: Int, message: String) {
                if (client == null) {
                    return
                }
                val msg = MqttMessage(message.toByteArray())
                msg.setQos(qos)
                client?.publish(topic, msg)
            }
        }
    }
    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        init()
        return START_REDELIVER_INTENT;
    }

    fun init() {
        var mqUrl = "tcp://ip:1883"
        client = MqttAndroidClient(this, mqUrl, "test_id")
        client!!.setCallback(mqttCallback)
        connOption.setCleanSession(true);
        connOption.setConnectionTimeout(10);
        connOption.setKeepAliveInterval(10);
        connOption.sslHostnameVerifier
        connOption.setAutomaticReconnect(true);
        connOption.userName = "username"
        connOption.password = "password".toCharArray()
        doConnect()
    }

    private fun doConnect() {
        if (isConnectIsNormal()) {
            if (!client!!.isConnected) {
                try {
                    client!!.connect(connOption, this, object : IMqttActionListener {
                        override fun onSuccess(asyncActionToken: IMqttToken?) {
                            doSubscribeTopic()
                        }

                        override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                            if (exception != null) {
                                exception.printStackTrace()
                            }
                        }
                    })
                } catch (e: MqttException) {
                    Log.e(TAG, "doConnect: ${e.message}")
                }
            }
            return
        } else {
                doConnect()
        }
    }

    val mqttCallback = object: MqttCallbackExtended{
        override fun connectionLost(cause: Throwable?) {
        }

        override fun messageArrived(topic: String?, message: MqttMessage?) {
            val msgStr = String(message!!.payload)
            when (topic){
            }
        }

        override fun deliveryComplete(token: IMqttDeliveryToken?) {
            
        }

        override fun connectComplete(reconnect: Boolean, serverURI: String?) {

        }

    }

    private fun doSubscribeTopic() {
        subscribeList.forEach {
            try {
                client?.subscribe(it, 1,this, object : IMqttActionListener {
                    override fun onSuccess(asyncActionToken: IMqttToken?) {
                    }

                    override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    }

                })
            } catch (e: MqttException) {
            }
        }
    }

    private fun isConnectIsNormal(): Boolean {
        val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = manager.getNetworkCapabilities(manager.activeNetwork)
        if (networkCapabilities != null) {
            return (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                    || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
        }
        return false
    }
}