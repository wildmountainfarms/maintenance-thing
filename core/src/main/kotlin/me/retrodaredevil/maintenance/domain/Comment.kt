package me.retrodaredevil.maintenance.domain

import me.retrodaredevil.maintenance.reference.Reference
import java.time.Instant

class Comment(
        val userReference: Reference<User>,
        val postTime: Instant,
        val contentMarkdown: String, // TODO possible make a Content interface and a MarkdownContent class
)
