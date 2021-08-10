package me.retrodaredevil.maintenance.reference

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import me.retrodaredevil.maintenance.annotations.JsonExplicit
import java.util.*

@JsonDeserialize(`as` = SimpleReference::class)
@JsonExplicit
sealed class BaseReference(
        @get:JsonProperty
        val uuid: UUID
)
class SimpleReference(uuid: UUID) : BaseReference(uuid) {
    fun <T : Any>toReference(databaseName: String, typeName: String, dataClass: Class<T>): Reference<T> = Reference(uuid, databaseName, typeName, dataClass)
}

// ": Any" makes it non-null
class Reference<T : Any>(
        uuid: UUID,
        val databaseName: String,
        val typeName: String,
        val dataClass: Class<T>,
) : BaseReference(uuid)
