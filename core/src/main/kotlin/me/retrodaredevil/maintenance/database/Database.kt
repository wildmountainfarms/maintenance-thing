package me.retrodaredevil.maintenance.database

import me.retrodaredevil.maintenance.domain.Thing
import me.retrodaredevil.maintenance.domain.ThingLink
import me.retrodaredevil.maintenance.reference.Reference


interface Database {
    fun <T : Any> lookup(reference: Reference<T>): T

    fun findThingLinks(thingReference: Reference<Thing>): List<ThingLink>
}
