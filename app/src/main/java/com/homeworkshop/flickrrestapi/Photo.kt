package com.homeworkshop.flickrrestapi

data class Photo(
    val farm: Int,
    val height_s: Int,
    val id: String,
    val isfamily: Int,
    val isfriend: Int,
    val ispublic: Int,
    val owner: String,
    val secret: String,
    val server: String,
    val title: String,
    val url_s: String,
    val width_s: Int


) {
    override fun toString(): String {
        return "Photo(farm=$farm, height_s=$height_s, id='$id', isfamily=$isfamily, isfriend=$isfriend, ispublic=$ispublic, owner='$owner', secret='$secret', server='$server', title='$title', url_s='$url_s', width_s=$width_s)"
    }
}