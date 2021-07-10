package me.retrodaredevil.maintenance.domain

import me.retrodaredevil.maintenance.reference.Reference
import java.time.Instant

sealed class ConfirmReason

class ManualConfirm(
        val confirmingUserReference: Reference<User>,
        val time: Instant
) : ConfirmReason()

object AssignConfirm : ConfirmReason()
