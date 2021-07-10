package me.retrodaredevil.maintenance.domain

import me.retrodaredevil.maintenance.reference.Reference
import java.time.Instant

class UpdateInfo(
        val created: Instant,
        val createdBy: Reference<User>,
        val updated: Instant,
        val updatedBy: Reference<User>,
)
