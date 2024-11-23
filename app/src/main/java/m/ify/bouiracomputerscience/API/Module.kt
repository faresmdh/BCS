package m.ify.computersciencebouira.API

import kotlinx.serialization.Serializable

@Serializable
data class Module(
    var id:Int,
    var name:String,
    var desc:String,
    var image:String,
    var level:String,
    var semestre:String
)