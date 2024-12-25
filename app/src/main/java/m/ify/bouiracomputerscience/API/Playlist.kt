package m.ify.bouiracomputerscience.API

import kotlinx.serialization.Serializable


@Serializable
data class Playlist(
    val id:Int,
    val title:String,
    val module: Int,
    val link:String
)