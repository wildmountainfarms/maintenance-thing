package me.retrodaredevil.maintenance.database

import me.retrodaredevil.maintenance.domain.Thing
import me.retrodaredevil.maintenance.domain.ThingLink
import me.retrodaredevil.maintenance.domain.attachment.FileAttachment
import me.retrodaredevil.maintenance.domain.attachment.FileAttachmentInfo
import me.retrodaredevil.maintenance.domain.attachment.FileAttachmentTuple
import me.retrodaredevil.maintenance.reference.Reference

enum class FindThingLinkOption {
    ONLY_LINKS,
    LINKS_AND_RELATING_THINGS,
    LINKS_AND_RELATING_THINGS_AND_EXACT_THING
}


interface MaintenanceThingDatabase {
    @Throws(DatabaseException::class)
    fun <T : Any> lookup(reference: Reference<T>): DatabaseEntry<T>
    @Throws(DatabaseException::class)
    fun <T : Any> createNew(reference: Reference<T>, data: T)
    @Throws(DatabaseException::class)
    fun <T : Any> update(reference: Reference<T>, data: T, updateToken: UpdateToken)

    @Throws(DatabaseException::class)
    fun lookupWithAttachments(reference: Reference<Thing>): FileAttachmentTuple<Thing>

    @Throws(DatabaseException::class)
    fun findThingLinks(thingReference: Reference<Thing>, findThingLinkOption: FindThingLinkOption): List<ThingLink>

    @Throws(DatabaseException::class)
    fun getAttachments(thingReference: Reference<Thing>): List<FileAttachmentInfo>

    @Throws(DatabaseException::class)
    fun getAttachment(thingReference: Reference<Thing>, attachmentName: String): FileAttachment
}
