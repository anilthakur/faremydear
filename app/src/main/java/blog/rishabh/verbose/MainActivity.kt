package blog.rishabh.verbose

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import blog.hari.commonutils.Constants
import blog.hari.commonutils.DataProcessor
import blog.hari.commonutils.TemplateInput
import blog.hari.commonutils.TemplateInput.*
import blog.hari.view.CreateFreeItemActivity
import blog.hari.view.CreateTemplateActivity
import blog.hari.view.CreateTemplateActivity.Companion.REQUEST_CODE_CREATE_TEMPLATE
import blog.hari.view.CreateTemplateActivity.Companion.REQUEST_KEY
import blog.hari.view.VoiceRecognitionActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    companion object {
        const val REQUEST_TEMPLATE_CODE = 10
    }

    var templateValue = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (DataProcessor.getString(Constants.TEMPLATE_ITEM, "").isNotEmpty()) {
            templateValue = DataProcessor.getString(Constants.TEMPLATE_ITEM, "")
            listOfHeaderItem(templateValue)
        }

        btn_create_template.setOnClickListener {
            val intent = Intent(this, CreateTemplateActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_CREATE_TEMPLATE)
        }
        btn_start_voice_recognition.setOnClickListener {
            if (templateValue.isNotEmpty() && isValidTemplate(templateValue)) {
                val intent = Intent(this, VoiceRecognitionActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please Check template or Create ", Toast.LENGTH_LONG).show()
            }
        }

        btn_free_item.setOnClickListener {
            if (templateValue.isNotEmpty() && isValidTemplate(templateValue)) {
                val intent = Intent(this, CreateFreeItemActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Please Check template or Create ", Toast.LENGTH_LONG).show()
            }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_CREATE_TEMPLATE) {
            if (resultCode == Activity.RESULT_OK) {
                templateValue = DataProcessor.getString(Constants.TEMPLATE_ITEM, "")

            }
        }
    }


}
