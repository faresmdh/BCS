package m.ify.computersciencebouira.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.gson.Gson
import kotlinx.coroutines.launch
import m.ify.computersciencebouira.API.DB
import m.ify.computersciencebouira.Helpers.Dialogs
import m.ify.computersciencebouira.Helpers.StateSaver
import m.ify.computersciencebouira.R
import m.ify.computersciencebouira.databinding.ActivityLoginBinding

class ActivityLogin : AppCompatActivity() {

    private lateinit var b:ActivityLoginBinding
    private val db = DB()
    private lateinit var dialogs:Dialogs
    private lateinit var stateSaver:StateSaver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(b.root)

        dialogs = Dialogs(this)
        stateSaver = StateSaver(this)

        b.registerTV.setOnClickListener {
            startActivity(Intent(this,ActivitySignup::class.java))
        }

        b.signInBtn.setOnClickListener {
            if (validateFields(b.emailET,b.passwordET)) {
                dialogs.startLoadingDialog()
                val email = b.emailET.text.toString().trim()
                val password = b.passwordET.text.toString()
                lifecycleScope.launch {
                    try {
                        var students = db.signIn(emailStr = email, passwordStr = password)
                        if (students.isEmpty()){
                            students = db.saveStudentInfo(emailStr = email, nameStr = "undefined name")
                        }
                        stateSaver.setText("student", Gson().toJson(students[0]))
                        dialogs.stopLoadingDialog()
                        startActivity(Intent(this@ActivityLogin,ActivityLevel::class.java))
                        finish()
                    }catch (e:Exception){
                        dialogs.stopLoadingDialog()
                        if (e.message.toString() == "Invalid login credentials"){
                            b.passwordET.error = getString(R.string.wrong_email_password)
                            b.passwordET.requestFocus()
                        }else{
                            dialogs.startNoInternet()
                            Log.d("Fares error is here:",e.message.toString())
                        }
                    }
                }
            }
        }


    }

    private fun validateFields(emailEt: EditText, passwordEt: EditText):Boolean{
        return validateEmail(emailEt) && validatePassword(passwordEt)
    }

    private fun validateEmail(editText: EditText):Boolean{
        return if (editText.text.toString().isEmpty()){
            editText.error = getString(R.string.email_error1)
            editText.requestFocus()
            false
        }else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(editText.text.toString()).matches()){
            editText.error = getString(R.string.email_error2)
            editText.requestFocus()
            false
        }else{
            editText.error = null
            true
        }
    }

    private fun validatePassword(editText: EditText):Boolean{
        return if (editText.text.toString().isEmpty()){
            editText.error = getString(R.string.password_error1)
            editText.requestFocus()
            false
        }else{
            editText.error = null
            true
        }
    }

}