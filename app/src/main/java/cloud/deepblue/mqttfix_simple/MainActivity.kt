package cloud.deepblue.mqttfix_simple

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cloud.deepblue.koalaq_android.mqtt.KoalaqMqttService

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startService(Intent(this, KoalaqMqttService::class.java))
    }
}