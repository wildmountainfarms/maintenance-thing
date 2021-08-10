package me.retrodaredevil.maintenance.database.couchdb

class BasicData<T : Any>(
        val type: String,
        val content: T
)
