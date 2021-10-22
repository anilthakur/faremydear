package blog.hari.view

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import blog.hari.commonutils.CommonExcelFile.exportDataIntoWorkbook1
import blog.hari.commonutils.Constants.Companion.FILE_NAME
import blog.hari.commonutils.Constants.Companion.FREE_ITEM
import blog.hari.commonutils.Constants.Companion.REQUEST_CODE_STT
import blog.hari.commonutils.DataProcessor
import blog.hari.commonutils.Entities
import blog.hari.commonutils.TemplateInput.*
import blog.hari.commonutils.VoiceRecognitionConstant.*
import blog.hari.commonutils.VoiceRecognitionData.getExcelData
import blog.rishabh.verbose.R
import kotlinx.android.synthetic.main.activity_voice_recognition.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import java.util.*
import kotlin.collections.LinkedHashMap

class VoiceRecognitionActivity : AppCompatActivity() {
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
        setContentView(R.layout.activity_voice_recognition)
        Objects.requireNonNull(supportActionBar)?.hide();
        toolbar.title = "Voice Recognition"
        toolbar.setSubtitleTextColor(Color.WHITE)
        toolbar.setTitleTextColor(Color.WHITE)
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener {
            finish()
        }
        excelFileName = fileName
        tv_file_name.text = "$excelFileName.xls"
        tv_template.text = getTemplateText()
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
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    5
                )
            } else {
                generateExcel()

            }


        }

        tv_updated.setOnClickListener {
            if (isValidInput(et_text_input.text.toString())) {
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
                        et_text_input.setText(getInputData(it[0]))
                        val recognizedText = et_text_input.text
                        if (isValidInput(recognizedText.toString())) {
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


    private fun generateExcel() {
        var linkedHashMap = linkedMapOf<String, List<Entities>>()
        val linkedHashMap1 = getExcelData(listOfdata)
        if (DataProcessor.getListItem(FREE_ITEM) != null && DataProcessor.getListItem(FREE_ITEM)
                .isNotEmpty()
        ) {
            linkedHashMap = getExcelData(DataProcessor.getListItem(FREE_ITEM))
        }
        linkedHashMap1.putAll(linkedHashMap)

        if (linkedHashMap1.size > 0) {

            exportDataIntoWorkbook1(
                this,
                DataProcessor.getString(FILE_NAME, "Default.xls"),
                listOfFinalHeaderItem,
                linkedHashMap1
            )
            Toast.makeText(this, "Excel file generate successfully", Toast.LENGTH_LONG)
                .show()
        } else {
            Toast.makeText(this, "Incorrect Input", Toast.LENGTH_LONG)
        }


    }
}