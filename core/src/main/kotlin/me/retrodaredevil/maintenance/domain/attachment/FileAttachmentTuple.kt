package me.retrodaredevil.maintenance.domain.attachment

import me.retrodaredevil.maintenance.database.DatabaseEntry

class FileAttachmentTuple<T : Any>(
        val fileAttachmentInfoList: List<FileAttachmentInfo>,
        val databaseEntry: DatabaseEntry<T>
)
