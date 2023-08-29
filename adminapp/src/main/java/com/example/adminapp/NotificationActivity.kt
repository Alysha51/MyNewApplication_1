package com.example.adminapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedOutputStream
import java.io.BufferedWriter
import java.io.InputStream
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.net.URL
import java.util.UUID
import javax.net.ssl.HttpsURLConnection

private val key= "AAAAov4KWPE:APA91bH70PLd3GrAqwCkaJ-2lQq2eKfzrK3q1tjwFT0yfUyFD7eaN43Y79w_3fFWWfTwzILwg0qKKqt0WaP23LIpgoFYUYB2pnJBOh9b9gK_Zb68_vdPMLj71hZKvbIEx_h9Y76nqt7t"


class NotificationActivity : AppCompatActivity() {
    private var titleEditText: EditText? = null
    private var descriptionEditText: EditText? = null
    private var notificationButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notification)
        titleEditText = findViewById(R.id.edit_text_name)
        descriptionEditText = findViewById(R.id.edit_description)
        notificationButton = findViewById(R.id.button_notification)


        // The topic name can be optionally prefixed with "/topics/".
// Simulate sending a message from one device to another
        notificationButton!!.setOnClickListener(View.OnClickListener {
            Toast.makeText(this@NotificationActivity, "send message to users", Toast.LENGTH_SHORT).show()
            val receivingDeviceToken = "RECEIVING_DEVICE_FCM_TOKEN" // Replace with the actual token
            val messageTitle = titleEditText!!.getText().toString().trim { it <= ' ' }
            val messageBody = descriptionEditText!!.getText().toString().trim { it <= ' ' }
            val message = RemoteMessage.Builder("/topics/promoNotification")
//                    .setMessageId(UUID.randomUUID().toString()) // Set a unique ID for the message
//                    .addData("title", messageTitle)
//                    .addData("body", messageBody)
//                    .build()
//            FirebaseMessaging.getInstance().send(message)
            sendMessage(messageTitle, messageBody, "promoNotification")
        })
    }
    fun sendMessage(title: String, content: String,topic: String) {
        GlobalScope.launch {
            val endpoint = "https://fcm.googleapis.com/fcm/send"
            try {
                val url = URL(endpoint)
                val httpsURLConnection: HttpsURLConnection = url.openConnection() as HttpsURLConnection
                httpsURLConnection.readTimeout = 10000
                httpsURLConnection.connectTimeout = 15000
                httpsURLConnection.requestMethod = "POST"
                httpsURLConnection.doInput = true
                httpsURLConnection.doOutput = true

                // Adding the necessary headers
                httpsURLConnection.setRequestProperty("authorization", "key=$key")
                httpsURLConnection.setRequestProperty("Content-Type", "application/json")

                // Creating the JSON with post params
                val body = JSONObject()

                val data = JSONObject()
                data.put("title", title)
                data.put("content", content)
                body.put("data",data)

                body.put("to","/topics/$topic")

                val outputStream: OutputStream = BufferedOutputStream(httpsURLConnection.outputStream)
                val writer = BufferedWriter(OutputStreamWriter(outputStream, "utf-8"))
                writer.write(body.toString())
                writer.flush()
                writer.close()
                outputStream.close()
                val responseCode: Int = httpsURLConnection.responseCode
                val responseMessage: String = httpsURLConnection.responseMessage
                Log.d("Response:", "$responseCode $responseMessage")
                var result = String()
                var inputStream: InputStream? = null
                inputStream = if (responseCode in 400..499) {
                    httpsURLConnection.errorStream
                } else {
                    httpsURLConnection.inputStream
                }

                if (responseCode == 200) {
                    Log.e("Success:", "notification sent $title \n $content")
                    // The details of the user can be obtained from the result variable in JSON format
                } else {
                    Log.e("Error", "Error Response")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
