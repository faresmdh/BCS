package m.ify.computersciencebouira.Helpers

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LifecycleCoroutineScope
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch
import m.ify.computersciencebouira.API.DB
import m.ify.computersciencebouira.Activities.ActivityLogin
import m.ify.computersciencebouira.Activities.ActivityMain
import m.ify.computersciencebouira.Activities.ActivityOnBoarding
import m.ify.computersciencebouira.R

class Dialogs(private val context: Context) {

    private val layoutInflater = LayoutInflater.from(context)
    private lateinit var loadingDialog: BottomSheetDialog
    private lateinit var noNetDialog: BottomSheetDialog
    private lateinit var nightModeDialog: BottomSheetDialog
    private lateinit var signoutDialog: BottomSheetDialog
    private lateinit var langDialog: BottomSheetDialog
    private lateinit var passwordDialog: BottomSheetDialog

    fun startLoadingDialog() {
        loadingDialog = BottomSheetDialog(context)
        val view = layoutInflater.inflate(R.layout.dialog_load, null)

        loadingDialog.setContentView(view)
        loadingDialog.setCancelable(false)
        loadingDialog.show()
    }

    fun stopLoadingDialog() {
        loadingDialog.dismiss()
    }

    fun startNoInternet() {
        noNetDialog = BottomSheetDialog(context)
        val view = layoutInflater.inflate(R.layout.dialog_no_net, null)

        val close = view.findViewById<MaterialButton>(R.id.closeBtn)
        close.setOnClickListener {
            noNetDialog.dismiss()
        }

        noNetDialog.setContentView(view)
        noNetDialog.setCancelable(true)
        noNetDialog.show()
    }

    fun startNightMode() {
        nightModeDialog = BottomSheetDialog(context)
        val view = layoutInflater.inflate(R.layout.dialog_night_mode, null)

        val close = view.findViewById<MaterialButton>(R.id.applyBtn)
        val nightRG = view.findViewById<RadioGroup>(R.id.nightRG)
        val stateSaver = StateSaver(context)

        var checked = stateSaver.getState("night")
        nightRG.check(nightRG.getChildAt(checked).id)
        nightRG.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.auto -> checked = 0
                R.id.night -> checked = 1
                R.id.light -> checked = 2
            }
        }

        close.setOnClickListener {
            stateSaver.setState("night", checked)
            close.isClickable = false
            when (checked) {
                0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            nightModeDialog.dismiss()

        }

        nightModeDialog.setContentView(view)
        nightModeDialog.setCancelable(true)
        nightModeDialog.show()
    }

    fun startSignOut(lifecycleScope: LifecycleCoroutineScope) {
        signoutDialog = BottomSheetDialog(context)
        val view = layoutInflater.inflate(R.layout.dialog_confirm_sign_out, null)


        val cancel = view.findViewById<MaterialButton>(R.id.closeBtn)
        val confirm = view.findViewById<MaterialButton>(R.id.signoutBtn)

        cancel.setOnClickListener {
            signoutDialog.dismiss()
        }

        confirm.setOnClickListener {
            startLoadingDialog()
            val stateSaver = StateSaver(context)
            lifecycleScope.launch {
                try {
                    DB().signOut()
                    stateSaver.setText("student", "null")
                    stateSaver.setText("level", "null")
                    val intent = Intent(context, ActivityLogin::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    context.startActivity(intent)
                    (context as Activity).finish()
                } catch (e: Exception) {
                    stopLoadingDialog()
                    Toast.makeText(context, context.getString(R.string.error), Toast.LENGTH_SHORT)
                        .show()
                    Log.d("Fares error is here :", e.message.toString())
                }
            }
        }

        signoutDialog.setContentView(view)
        signoutDialog.setCancelable(true)
        signoutDialog.show()
    }

    fun startLanguage() {
        langDialog = BottomSheetDialog(context)
        val view = layoutInflater.inflate(R.layout.dialog_language, null)
        val nightRG = view.findViewById<RadioGroup>(R.id.nightRG)
        val apply = view.findViewById<MaterialButton>(R.id.applyBtn)

        val stateSaver = StateSaver(context)
        var checked = stateSaver.getState("lang")
        nightRG.check(nightRG.getChildAt(checked).id)
        nightRG.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.auto -> checked = 0
                R.id.en -> checked = 1
                R.id.fr -> checked = 2
                R.id.ar -> checked = 3
            }
        }

        apply.setOnClickListener {
            stateSaver.setState("lang", checked)
            val intent = Intent(context, ActivityOnBoarding::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            (context as Activity).finish()
        }

        langDialog.setContentView(view)
        langDialog.setCancelable(true)
        langDialog.show()
    }

    fun startPasswordDialog(lifecycleScope: LifecycleCoroutineScope) {
        passwordDialog = BottomSheetDialog(context)
        val view = layoutInflater.inflate(R.layout.dialog_change_password, null)

        val saveBtn = view.findViewById<MaterialButton>(R.id.closeBtn)
        val oldPassword = view.findViewById<EditText>(R.id.oldPasswordET)
        val newPassword = view.findViewById<EditText>(R.id.newPasswordET)
        val cNewPassword = view.findViewById<EditText>(R.id.cNewPasswordET)

        saveBtn.setOnClickListener {
            if (NetworkChecker(context).isConnectedWithDialog() && validatePasswordFields(
                    oldPassword,
                    newPassword,
                    cNewPassword
                )
            ) {
                val old = oldPassword.text.toString()
                val new = newPassword.text.toString()
                changePassword(old, new, lifecycleScope)
            }
        }

        passwordDialog.setContentView(view)
        passwordDialog.setCancelable(true)
        passwordDialog.show()
    }

    private fun changePassword(old: String, new: String, lifecycleScope: LifecycleCoroutineScope) {
        startLoadingDialog()
        lifecycleScope.launch {
            try {
                DB().changePassword(context,old, new)
                Toast.makeText(context, context.getString(R.string.done), Toast.LENGTH_SHORT).show()
                loadingDialog.dismiss()
                passwordDialog.dismiss()
            } catch (e: Exception) {
                Toast.makeText(context, context.getString(R.string.error), Toast.LENGTH_SHORT)
                    .show()
                Log.d("Fares error is here :", e.message.toString())
                stopLoadingDialog()
            }
        }
    }

    fun validatePasswordFields(old: EditText, new: EditText, confirm: EditText): Boolean {
        return validatePassword(old) && validatePassword(new) && validateCNewPassword(new, confirm)
    }

    private fun validateCNewPassword(new: EditText, confirm: EditText): Boolean {
        val p = new.text.toString()
        val cP = confirm.text.toString()
        if (cP != p) {
            confirm.error = context.getString(R.string.no_match)
            confirm.requestFocus()
            return false
        } else {
            confirm.error = null
            return true
        }
    }

    private fun validatePassword(editText: EditText): Boolean {
        val p = editText.text.toString()
        if (p.isEmpty()) {
            editText.error = context.getString(R.string.password_error1)
            editText.requestFocus()
            return false
        } else if (p.length < 6) {
            editText.error = context.getString(R.string.password_error2)
            editText.requestFocus()
            return false
        } else {
            editText.error = null
            return true
        }
    }

}