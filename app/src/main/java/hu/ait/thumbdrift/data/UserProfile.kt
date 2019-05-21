package hu.ait.thumbdrift.data

import java.io.Serializable

data class UserProfile(
    var uid: String? = "",
    var name: String = "",
    var gender: String = "",
    var age: Int = 0,
    var description: String = "",
    var canDrive: Boolean = false
) : Serializable