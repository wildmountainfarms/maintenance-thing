package me.retrodaredevil.maintenance.database.couchdb

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.IntNode
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.fasterxml.jackson.databind.node.TextNode
import me.retrodaredevil.couchdbjava.CouchDbInstance
import me.retrodaredevil.couchdbjava.attachment.AttachmentGet
import me.retrodaredevil.couchdbjava.json.jackson.CouchDbJacksonUtil
import me.retrodaredevil.couchdbjava.json.jackson.JacksonJsonData
import me.retrodaredevil.couchdbjava.request.ViewQueryParamsBuilder
import me.retrodaredevil.couchdbjava.response.ViewResponse
import me.retrodaredevil.maintenance.MaintenanceThingConstants
import me.retrodaredevil.maintenance.database.*
import me.retrodaredevil.maintenance.domain.Thing
import me.retrodaredevil.maintenance.domain.ThingLink
import me.retrodaredevil.maintenance.domain.attachment.FileAttachment
import me.retrodaredevil.maintenance.domain.attachment.FileAttachmentInfo
import me.retrodaredevil.maintenance.domain.attachment.FileAttachmentTuple
import me.retrodaredevil.maintenance.reference.Reference

private fun Reference<*>.getDocumentId(): String {
    return "$typeName:$uuid"
}

class CouchDbMaintenanceThingDatabase(
        private val instance: CouchDbInstance,
        private val mapper: ObjectMapper
) : MaintenanceThingDatabase {
    private val thingDatabase = instance.getDatabase(MaintenanceThingConstants.DATABASE_MAIN)
    private val jsonNodeFactory = JsonNodeFactory.instance

    @Throws(DatabaseException::class)
    private fun <T : Any> lookupRaw(reference: Reference<T>): RootData<T> {
        val documentData = instance.getDatabase(reference.databaseName).getDocument(reference.getDocumentId())

        val type = mapper.typeFactory.constructSimpleType(BasicData::class.java, arrayOf(mapper.typeFactory.constructType(reference.dataClass)))
        return mapper.readValue(documentData.jsonData.json, type)
    }
    @Throws(DatabaseException::class)
    private fun <T : Any> updateRaw(reference: Reference<T>, rootData: RootData<T>) {
        val jsonData = JacksonJsonData(mapper.valueToTree(rootData))
        instance.getDatabase(reference.databaseName).putDocument(reference.getDocumentId(), jsonData)
    }

    @Throws(DatabaseException::class)
    private fun <T : Any>lookupWithAttachmentsRaw(reference: Reference<T>): FileAttachmentTuple<T> {
        val documentData = instance.getDatabase(reference.databaseName).getDocument(reference.getDocumentId())
        val rootData = lookupRaw(reference)
        val databaseEntry = DatabaseEntry(reference, rootData.data.content, RevisionUpdateToken(documentData.revision))
        val attachments = rootData.attachments ?: emptyMap()
        return FileAttachmentTuple(attachments.map { FileAttachmentInfo(it.key, it.value.contentType) }, databaseEntry)
    }

    @Throws(DatabaseException::class)
    override fun <T : Any> lookup(reference: Reference<T>): DatabaseEntry<T> {
        return lookupWithAttachmentsRaw(reference).databaseEntry
    }
    @Throws(DatabaseException::class)
    override fun lookupWithAttachments(reference: Reference<Thing>): FileAttachmentTuple<Thing> = lookupWithAttachmentsRaw(reference)


    @Throws(DatabaseException::class)
    override fun <T : Any> createNew(reference: Reference<T>, data: T) {
        updateRaw(reference, RootData(null, null, null, BasicData(reference.typeName, data)))
    }

    @Throws(DatabaseException::class)
    override fun <T : Any> update(reference: Reference<T>, data: T, updateToken: UpdateToken) {
        val revisionUpdateToken = updateToken as RevisionUpdateToken
        updateRaw(reference, RootData(null, revisionUpdateToken.revision, null, BasicData(reference.typeName, data)))
    }

    @Throws(DatabaseException::class)
    override fun findThingLinks(thingReference: Reference<Thing>, findThingLinkOption: FindThingLinkOption): List<ThingLink> {
        val documentId = thingReference.getDocumentId()
        val startNumber = when (findThingLinkOption) {
            FindThingLinkOption.ONLY_LINKS -> 3
            FindThingLinkOption.LINKS_AND_RELATING_THINGS -> 2
            FindThingLinkOption.LINKS_AND_RELATING_THINGS_AND_EXACT_THING -> 1
        }

        val startKey = JacksonJsonData(ArrayNode(jsonNodeFactory, listOf(TextNode(documentId), IntNode(startNumber))))
        val endKey = JacksonJsonData(ArrayNode(jsonNodeFactory, listOf(TextNode(documentId), IntNode(3))))

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

    @Throws(DatabaseException::class)
    override fun getAttachments(thingReference: Reference<Thing>): List<FileAttachmentInfo> {
        return lookupWithAttachmentsRaw(thingReference).fileAttachmentInfoList
    }

    @Throws(DatabaseException::class)
    override fun getAttachment(thingReference: Reference<Thing>, attachmentName: String): FileAttachment {
        val documentId = thingReference.getDocumentId()
        val database = instance.getDatabase(thingReference.databaseName)
        val attachmentData = database.getAttachment(AttachmentGet.get(documentId, attachmentName))

        return FileAttachment(attachmentData.dataSource, FileAttachmentInfo(attachmentName, attachmentData.contentType))
    }
}
