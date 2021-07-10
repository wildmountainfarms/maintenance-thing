package me.retrodaredevil.maintenance.domain

import me.retrodaredevil.maintenance.reference.Reference
import java.time.LocalDate

class ScheduledTask(
        val taskReference: Reference<Task>,
        val assignedUsers: List<Reference<User>>,
        val assignDate: LocalDate,
        val dueAfterDays: Int,
        val confirmReasons: List<ConfirmReason>
)
