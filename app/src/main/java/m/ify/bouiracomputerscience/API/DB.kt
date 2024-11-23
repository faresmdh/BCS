package m.ify.computersciencebouira.API

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.SignOutScope
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserInfo
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import m.ify.computersciencebouira.Helpers.StateSaver

class DB {

    val supabase = createSupabaseClient(
        supabaseUrl = "",
        supabaseKey = ""
    ) {
        install(Postgrest)
        install(Auth)
    }

    suspend fun signIn(emailStr:String,passwordStr:String): List<Student> {
        supabase.auth.signInWith(Email){
            email = emailStr
            password = passwordStr
        }
        return supabase.postgrest.from("students").select(Columns.ALL).decodeList<Student>()
    }

    suspend fun signUp(nameStr:String,emailStr:String,passwordStr:String): List<Student> {
        supabase.auth.signUpWith(Email){
            email = emailStr
            password = passwordStr
        }
        val student = Student(name = nameStr, email = emailStr)
        return supabase.postgrest.from("students").insert(student){
            select(Columns.ALL)
        }.decodeList<Student>()
    }

    suspend fun saveStudentInfo(emailStr: String,nameStr: String): List<Student> {
        val student = Student(name = nameStr, email = emailStr)
        return supabase.postgrest.from("students").insert(student){
            select(Columns.ALL)
        }.decodeList<Student>()
    }

    suspend fun getUSer(refreshSession:Boolean = false): UserInfo {
        return supabase.auth.retrieveUserForCurrentSession(refreshSession)
    }

    suspend fun getModules(): List<Module> {
        return supabase.postgrest.from("modules").select(Columns.ALL){
            order("id",Order.ASCENDING)
        }.decodeList<Module>()
    }

    suspend fun getPDFs(): List<PDF> {
        return supabase.postgrest.from("pdfs").select(Columns.ALL){
            order("id",Order.ASCENDING)
        }.decodeList<PDF>()
    }

    suspend fun signOut(){
        supabase.auth.signOut(SignOutScope.GLOBAL)
    }

    suspend fun changePassword(context: Context, old:String, new:String){
        val emailStr = Gson().fromJson(StateSaver(context).getText("student"),Student::class.java).email
        Log.d("Fares email is here :",emailStr)
        supabase.auth.signInWith(Email){
            email = emailStr
            password = old
        }

        Log.d("Fares email is signed in? :","yes")

        supabase.auth.updateUser {
            password = new
        }

        Log.d("Fares password updated? :","yes")
    }

}