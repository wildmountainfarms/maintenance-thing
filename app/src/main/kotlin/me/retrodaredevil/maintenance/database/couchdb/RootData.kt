package me.retrodaredevil.maintenance.database.couchdb

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import me.retrodaredevil.couchdbjava.response.Attachment

@JsonInclude(JsonInclude.Include.NON_NULL)
class RootData<T : Any>(
        @JsonProperty("_id")
        @get:JsonProperty("_id")
        val id: String?,
        @JsonProperty("_rev")
        @get:JsonProperty("_rev")
        val rev: String?,
        @JsonProperty("_attachments")
        @get:JsonProperty("_attachments")
        val attachments: Map<String, Attachment>?,
        @JsonProperty("data")
        @get:JsonProperty("data")
        val data: BasicData<T>
)
