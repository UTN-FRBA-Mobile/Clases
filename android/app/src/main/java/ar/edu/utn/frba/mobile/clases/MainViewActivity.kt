package ar.edu.utn.frba.mobile.clases

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ar.edu.utn.frba.mobile.clases.databinding.MainActivityBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

class MainViewActivity : AppCompatActivity() {

    lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setFirebaseTokenInView()

        binding.reloadButton.setOnClickListener { setFirebaseTokenInView() }

        binding.copyButton.setOnClickListener { copyTokenToClipboard() }

        binding.subscribeButton.setOnClickListener { subscribeToTopic() }
    }

    private fun setFirebaseTokenInView() {
        val firebaseTokenText = MyPreferences.getFirebaseToken(this)

        if (binding.firebaseToken.text != null) {
            binding.firebaseToken.text = firebaseTokenText
            binding.reloadButton.visibility = View.GONE
            binding.copyButton.visibility = View.VISIBLE
            binding.topicContainer.visibility = View.VISIBLE
        } else {
            binding.copyButton.visibility = View.GONE
            binding.topicContainer.visibility = View.GONE
            binding.reloadButton.visibility = View.VISIBLE
        }
    }

    private fun subscribeToTopic() {
        val topicText = binding.topic.text.toString()
        FirebaseMessaging.getInstance().subscribeToTopic(topicText)
        Toast.makeText(this, "Subscripto a $topicText", Toast.LENGTH_SHORT).show()
        binding.topic.setText("")
    }

    fun copyTokenToClipboard() {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Firebase token", binding.firebaseToken.text)
        clipboard.setPrimaryClip(clip)
    }
}