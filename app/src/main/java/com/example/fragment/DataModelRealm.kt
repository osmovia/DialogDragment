package com.example.fragment

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import java.lang.IllegalArgumentException

enum class DataModelRealm(val displayName: String) {
    Open("Open"),
    InProgress("In Progress"),
    Complete("Complete"),
}

open class Task() : RealmObject() {
    @PrimaryKey
    var name: String = "task"

    @Required
    var status = DataModelRealm.Open.name
    var statusEnum: DataModelRealm
    get() {
        return try {
            DataModelRealm.valueOf(status)
        } catch (e: IllegalArgumentException) {
            DataModelRealm.Open
        }
    }
    set(value) { status = value.name}
}