package me.retrodaredevil.maintenance.database.couchdb

import me.retrodaredevil.maintenance.database.UpdateToken

data class RevisionUpdateToken(
        val revision: String
) : UpdateToken
