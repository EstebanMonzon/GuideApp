package com.ort.guideapp.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Activity (
    var uid: String = "",
    var title: String = "",
    var city: String = "",
    var province: String = "",
    var country: String = "",
    var guideUid: String = "",
    var description: String = "",
    var activityPhoto: String = "",
    var rate: Int = 0 ,
    var lat: Double = 0.0,
    var long: Double = 0.0,
    var tags: MutableList<String> = mutableListOf()
) : Parcelable {
    constructor() : this("", "","","","","","","",0, 0.0, 0.0, mutableListOf())
    init {
        this.uid = uid
        this.title = title
        this.city = city
        this.province = province
        this.country = country
        this.guideUid = guideUid
        this.description = description
        this.activityPhoto = activityPhoto
        this.rate = rate
        this.lat = lat
        this.long = long
        this.tags = tags
    }
}