package m.ify.computersciencebouira.Helpers

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import m.ify.computersciencebouira.R

class NetworkChecker(private val context: Context) {


    //check if there is internet and show dialog if is not connected
    fun isConnectedWithDialog(): Boolean{
        return if (isConnected()){
            true
        }else{
            //Show no internet dialog
            Dialogs(context).startNoInternet()
            false
        }
    }

    fun isConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

        return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }

}