package blog.hari.view

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import blog.hari.commonutils.TemplateInput.*
import blog.rishabh.verbose.R
import kotlinx.android.synthetic.main.activity_template.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.util.*
import android.graphics.Color
import blog.hari.commonutils.Constants.Companion.REQUEST_CODE_STT
import blog.hari.commonutils.Constants.Companion.TEMPLATE_ITEM
import blog.hari.commonutils.DataProcessor

class CreateTemplateActivity : AppCompatActivity() {

    private val textToSpeechEngine: TextToSpeech by lazy {
        TextToSpeech(
            this
        ) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeechEngine.language = Locale.UK
            }
        }
    }

    companion object {
        const val REQUEST_CODE_CREATE_TEMPLATE = 1
        const val REQUEST_KEY = "templatevalue"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_template)
        Objects.requireNonNull(supportActionBar)?.hide()
        toolbar.title = "Create Template"
        toolbar.setSubtitleTextColor(Color.WHITE)
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener {
            val intent = Intent()
            intent.putExtra(REQUEST_KEY, et_template_text_input.text.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        btn_create_template.setOnClickListener {
            val sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            sttIntent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            sttIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5)

            sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-us")
            sttIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now!")

            try {
                startActivityForResult(sttIntent, REQUEST_CODE_STT)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Toast.makeText(this, "Your device does not support STT.", Toast.LENGTH_LONG).show()
            }
        }
        btn_updated_template.setOnClickListener {
            if (isValidTemplate(et_template_text_input.text.toString())) {
                DataProcessor.clearListItem(TEMPLATE_ITEM)
                DataProcessor.saveString(et_template_text_input.text.toString(), TEMPLATE_ITEM)
                listOfHeaderItem(DataProcessor.getString(TEMPLATE_ITEM,""))
                Toast.makeText(this, "Created Template", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Invalid Input", Toast.LENGTH_LONG).show()
            }
        }
        btn_save_template.setOnClickListener {
                DataProcessor.clearListItem(TEMPLATE_ITEM)
                DataProcessor.saveString(et_template_text_input.text.toString(), TEMPLATE_ITEM)
                Toast.makeText(this, "Saved successfully", Toast.LENGTH_LONG).show()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_CREATE_TEMPLATE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    result?.let {
                        Log.d("RecognizeText", it[0])
                        et_template_text_input.setText(getTemplateVoiceData(it[0]).toString())
                        excelFileName(getItem(et_template_text_input.text.toString())[0])
                        if (isValidTemplate(et_template_text_input.text.toString())) {
                            DataProcessor.clearListItem(TEMPLATE_ITEM)
                            DataProcessor.saveString(et_template_text_input.text.toString(), TEMPLATE_ITEM)
                            listOfHeaderItem(DataProcessor.getString(TEMPLATE_ITEM,""))

                            Toast.makeText(this, "Created Template", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(this, "Invalid Input", Toast.LENGTH_LONG).show()
                        }

                    }
                }
            }
        }
    }


    override fun onPause() {
        textToSpeechEngine.stop()
        super.onPause()
    }

    override fun onDestroy() {
        textToSpeechEngine.shutdown()
        super.onDestroy()
    }
}