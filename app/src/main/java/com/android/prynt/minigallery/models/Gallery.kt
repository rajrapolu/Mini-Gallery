package com.android.prynt.minigallery.models

import java.io.Serializable

data class Gallery(val id: Int, val imageURL: String, val videoURL: String):Serializable {
}