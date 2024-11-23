package m.ify.computersciencebouira.API

import kotlinx.serialization.Serializable

@Serializable
data class PDF(
    var id:Int,
    var type:String,
    var title:String,
    var url:String,
    var module:Int
)