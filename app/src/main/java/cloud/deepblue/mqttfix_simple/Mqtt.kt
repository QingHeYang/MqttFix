package cloud.deepblue.koalaq_android.mqtt

const val R_COV = "android/cov/"

data class MqttConnectEvent(val isConnect: Boolean, val arg: String)

data class MattCovEvent(
    val taskId: String,
    val audio: String,
    val text: String,
    val finished: Boolean
)
