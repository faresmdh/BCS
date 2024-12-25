package m.ify.bouiracomputerscience.API

import kotlinx.serialization.Serializable

@Serializable
data class DevPost(
    val created_at:String,
    val description:String
)