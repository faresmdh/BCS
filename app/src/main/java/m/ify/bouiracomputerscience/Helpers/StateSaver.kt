package m.ify.computersciencebouira.Helpers

import android.content.Context
import android.content.SharedPreferences

class StateSaver(val context: Context) {

    private val sp: SharedPreferences = context.getSharedPreferences("bcs", Context.MODE_PRIVATE)

    fun setState(key: String, value: Int) {
        val editor = sp.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getState(key: String): Int {
        return sp.getInt(key, 0)
    }

    fun setText(key: String, value: String) {
        val editor = sp.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getText(key: String): String? {
        return sp.getString(key, "null")
    }


}