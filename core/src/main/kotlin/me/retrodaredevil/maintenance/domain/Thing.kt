package me.retrodaredevil.maintenance.domain

import me.retrodaredevil.maintenance.MaintenanceThingConstants
import me.retrodaredevil.maintenance.reference.Reference
import me.retrodaredevil.maintenance.reference.ReferenceCreator
import me.retrodaredevil.maintenance.reference.SimpleReference

class Thing(
        val displayName: String,
        val description: String,
        val documentCollection: DocumentCollection,
        val commentSection: CommentSection,
) {
    companion object : ReferenceCreator<Thing> {
        override fun createReference(simpleReference: SimpleReference): Reference<Thing> {
            return simpleReference.toReference(MaintenanceThingConstants.DATABASE_THINGS, Thing::class.java)
        }
    }
}
