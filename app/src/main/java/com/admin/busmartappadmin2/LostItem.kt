package com.admin.busmartappadmin2

import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
data class LostItem(
    val description: String? = null,
    val downloadUri: String? = null,
    var documentId: String? = null

) {

    companion object {
        const val def_decs = ""
        const val def_uri = ""
    }
}
