package m.ify.computersciencebouira.API

import kotlinx.serialization.Serializable

@Serializable
data class Student(
    var id:String?=null,
    var name:String,
    var email:String,
    var image:String?=null
)
