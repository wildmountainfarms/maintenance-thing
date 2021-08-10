package me.retrodaredevil.maintenance.domain.attachment

import okio.Source

class FileAttachment(
        val source: Source,
        val info: FileAttachmentInfo
)
