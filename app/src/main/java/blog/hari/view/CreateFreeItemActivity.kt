package blog.hari.view

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import blog.hari.commonutils.Constants.Companion.FREE_ITEM
import blog.hari.commonutils.Constants.Companion.REQUEST_CODE_STT
import blog.hari.commonutils.DataProcessor
import blog.hari.commonutils.TemplateInput
import blog.hari.commonutils.VoiceRecognitionConstant
import blog.rishabh.verbose.R
import kotlinx.android.synthetic.main.activity_create_freeitem.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.util.*

class CreateFreeItemActivity : AppCompatActivity() {
    private var listOfdata = mutableListOf<String>()
    private var excelFileName = "Default.xls"
    private var lastWord = ""
    private val textToSpeechEngine: TextToSpeech by lazy {
        TextToSpeech(
            this
        ) { status ->
            if (status == TextToSpeech.SUCCESS) {
                textToSpeechEngine.language = Locale.UK
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_freeitem)
        Objects.requireNonNull(supportActionBar)?.hide();
        toolbar.title = "Free Item"
        toolbar.setSubtitleTextColor(Color.WHITE)
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        excelFileName = TemplateInput.fileName
        tv_template.text = VoiceRecognitionConstant.getTemplateText()
        btn_stt.setOnClickListener {
            val sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            sttIntent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            sttIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5)

            sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            sttIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now!")

            try {
                startActivityForResult(sttIntent, REQUEST_CODE_STT)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Toast.makeText(this, "Your device does not support STT.", Toast.LENGTH_LONG).show()
            }
        }
        button.setOnClickListener {
            saveFreeItemExcelData()

        }

        tv_updated.setOnClickListener {
            if (VoiceRecognitionConstant.isValidInput(et_text_input.text.toString())) {
                listOfdata.add(et_text_input.text.toString())

                Toast.makeText(this, "Updated successfully", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Incorrect Input", Toast.LENGTH_LONG).show()
            }
            lastWord = ""
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_STT -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    result?.let {
                        Log.d("RecognizeText", it[0])
                        et_text_input.setText(VoiceRecognitionConstant.getInputData(it[0]))
                        val recognizedText = et_text_input.text
                        if (VoiceRecognitionConstant.isValidInput(recognizedText.toString())) {
                            lastWord = recognizedText.toString()
                            listOfdata.add(recognizedText.toString())
                            Toast.makeText(this, "Your Data Submitted", Toast.LENGTH_LONG)
                                .show()
                        } else {
                            Toast.makeText(this, "Incorrect Input", Toast.LENGTH_LONG).show()
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


    private fun saveFreeItemExcelData() {
        if (listOfdata.size > 0) {
            DataProcessor.saveListItem(listOfdata as ArrayList<String>?, FREE_ITEM)
            Toast.makeText(this, "Saved", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "Data not found", Toast.LENGTH_LONG).show()
        }

    }
}