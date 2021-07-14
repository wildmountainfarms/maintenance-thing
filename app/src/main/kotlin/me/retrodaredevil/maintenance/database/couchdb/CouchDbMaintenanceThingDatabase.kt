package me.retrodaredevil.maintenance.database.couchdb

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.IntNode
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.fasterxml.jackson.databind.node.TextNode
import me.retrodaredevil.couchdbjava.CouchDbInstance
import me.retrodaredevil.couchdbjava.json.JsonData
import me.retrodaredevil.couchdbjava.json.jackson.CouchDbJacksonUtil
import me.retrodaredevil.couchdbjava.json.jackson.JacksonJsonData
import me.retrodaredevil.couchdbjava.request.ViewQueryParamsBuilder
import me.retrodaredevil.couchdbjava.response.ViewResponse
import me.retrodaredevil.maintenance.MaintenanceThingConstants
import me.retrodaredevil.maintenance.database.FindThingLinkOption
import me.retrodaredevil.maintenance.database.MaintenanceThingDatabase
import me.retrodaredevil.maintenance.domain.Thing
import me.retrodaredevil.maintenance.domain.ThingLink
import me.retrodaredevil.maintenance.reference.Reference

class CouchDbMaintenanceThingDatabase(
        private val instance: CouchDbInstance,
        private val mapper: ObjectMapper
) : MaintenanceThingDatabase {
    private val thingDatabase = instance.getDatabase(MaintenanceThingConstants.DATABASE_THINGS)
    private val jsonNodeFactory = JsonNodeFactory.instance

    override fun <T : Any> lookup(reference: Reference<T>): T {
        val documentData = instance.getDatabase(reference.databaseName).getDocument(reference.uuid.toString())
        return CouchDbJacksonUtil.readValue(mapper, documentData.jsonData, reference.dataClass)
    }

    override fun findThingLinks(thingReference: Reference<Thing>, findThingLinkOption: FindThingLinkOption): List<ThingLink> {
        val uuidString = thingReference.uuid.toString()
        val startNumber = when (findThingLinkOption) {
            FindThingLinkOption.ONLY_LINKS -> 3
            FindThingLinkOption.LINKS_AND_RELATING_THINGS -> 2
            FindThingLinkOption.LINKS_AND_RELATING_THINGS_AND_EXACT_THING -> 1
        }

        val startKey = JacksonJsonData(ArrayNode(jsonNodeFactory, listOf(TextNode(uuidString), IntNode(startNumber))))
        val endKey = JacksonJsonData(ArrayNode(jsonNodeFactory, listOf(TextNode(uuidString), IntNode(3))))

        val response = thingDatabase.queryView(
                "mainViews",
                "thingLinkWithThing",
                ViewQueryParamsBuilder()
                        .includeDocs(true)
                        .startKeyJson(startKey)
                        .endKeyJson(endKey)
                        .build()
        )
        val rows: List<ViewResponse.DocumentEntry> = response.rows
        TODO("finish this function and change return type to a new class I haven't made yet")
    }
}
