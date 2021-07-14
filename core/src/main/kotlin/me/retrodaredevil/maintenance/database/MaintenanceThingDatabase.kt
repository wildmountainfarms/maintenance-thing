package me.retrodaredevil.maintenance.database

import me.retrodaredevil.maintenance.domain.Thing
import me.retrodaredevil.maintenance.domain.ThingLink
import me.retrodaredevil.maintenance.reference.Reference

enum class FindThingLinkOption {
    ONLY_LINKS,
    LINKS_AND_RELATING_THINGS,
    LINKS_AND_RELATING_THINGS_AND_EXACT_THING
}


interface MaintenanceThingDatabase {
    fun <T : Any> lookup(reference: Reference<T>): T

    fun findThingLinks(thingReference: Reference<Thing>, findThingLinkOption: FindThingLinkOption): List<ThingLink>
}
