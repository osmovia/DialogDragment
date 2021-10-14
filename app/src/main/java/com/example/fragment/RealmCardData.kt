package com.example.fragment

import com.google.gson.Gson
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import org.bson.types.ObjectId


open class RealmCardData(
    @PrimaryKey
    var id: String = ObjectId().toHexString(),
    @Required
    var word: String? = null,
    var translate: String? = null
) : RealmObject() {
    override fun toString(): String {
        return Gson().toJson(this)
    }
}