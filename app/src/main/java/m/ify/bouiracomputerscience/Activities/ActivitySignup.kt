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
import m.ify.computersciencebouira.databinding.ActivitySignupBinding

class ActivitySignup : AppCompatActivity() {

    private lateinit var b:ActivitySignupBinding
    private val db = DB()
    private lateinit var dialogs: Dialogs
    private lateinit var stateSaver: StateSaver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(b.root)

        dialogs = Dialogs(this)
        stateSaver = StateSaver(this)

        b.registerBtn.setOnClickListener {
            if (validateFields(b.emailET,b.passwordET,b.nameET)){
                dialogs.startLoadingDialog()
                val email = b.emailET.text.toString().trim()
                val password = b.passwordET.text.toString()
                val name = b.nameET.text.toString().trim()
                lifecycleScope.launch {
                    try {
                        val students = db.signUp(nameStr = name, emailStr = email, passwordStr = password)
                        stateSaver.setText("student", Gson().toJson(students[0]))
                        dialogs.stopLoadingDialog()
                        val i = Intent(this@ActivitySignup,ActivityLevel::class.java)
                        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(i)
                    }catch (e:Exception){
                        dialogs.stopLoadingDialog()
                        if(e.message.toString() == "User already registered"){
                            b.emailET.error = getString(R.string.email_error3)
                            b.emailET.requestFocus()
                        }else{
                            dialogs.startNoInternet()
                            Log.d("Fares error is here:",e.message.toString())
                        }
                    }
                }
            }
        }

    }

    private fun validateFields(emailEt: EditText, passwordEt: EditText, nameET: EditText):Boolean{
        return validateName(nameET) && validateEmail(emailEt) && validatePassword(passwordEt)
    }

    private fun validateName(editText: EditText): Boolean {
        return if (editText.text.toString().isEmpty()){
            editText.error = getString(R.string.nameError1)
            editText.requestFocus()
            false
        }else if(editText.text.toString().length < 6){
            editText.error = getString(R.string.nameError2)
            editText.requestFocus()
            false
        }else{
            editText.error = null
            true
        }
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
        }else if (editText.text.toString().length < 6){
            editText.error = getString(R.string.password_error2)
            editText.requestFocus()
            false
        }else{
            editText.error = null
            true
        }
    }

}