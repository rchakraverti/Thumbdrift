package hu.ait.thumbdrift.data

data class Ride(
    var uid: String? = "",
    var authorUID: String = "",
    var from: String = "",
    var to: String = "",
    var date: String = "",
    var seats: Int = 0
)