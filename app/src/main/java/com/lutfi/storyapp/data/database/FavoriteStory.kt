package com.lutfi.storyapp.data.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite_stories")
@Parcelize
data class FavoriteStory(
    @field:SerializedName("photoUrl")
    var photoUrl: String? = null,

    @field:SerializedName("createdAt")
    var createdAt: String? = null,

    @field:SerializedName("name")
    var name: String? = null,

    @field:SerializedName("description")
    var description: String? = null,

    @field:SerializedName("lon")
    var lon: Double? = 0.0,

    @PrimaryKey
    @field:SerializedName("id")
    var id: String = "",

    @field:SerializedName("lat")
    var lat: Double? = 0.0

) : Parcelable