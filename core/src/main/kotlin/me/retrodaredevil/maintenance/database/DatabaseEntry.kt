package me.retrodaredevil.maintenance.database

import me.retrodaredevil.maintenance.reference.Reference

class DatabaseEntry<T : Any>(
        val reference: Reference<T>,
        val content: T,
        val updateToken: UpdateToken,
)
