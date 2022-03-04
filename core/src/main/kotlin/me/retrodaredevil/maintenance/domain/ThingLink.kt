package me.retrodaredevil.maintenance.domain

import me.retrodaredevil.maintenance.reference.SimpleReference
import java.util.*

private fun thingLinkThingsEquals(thingLink1: ThingLink, thingLink2: ThingLink, ignoreOrder: Boolean = false): Boolean {
    return (thingLink1.thingA == thingLink2.thingA && thingLink1.thingB == thingLink2.thingB) ||
            (ignoreOrder && thingLink1.thingA == thingLink2.thingB && thingLink1.thingB == thingLink2.thingA)
}

class ThingLink(
        thingA: SimpleReference,
        thingB: SimpleReference,
        val data: ThingLinkData
) {
    val thingA = Thing.createReference(thingA)
    val thingB = Thing.createReference(thingB)
    fun isCompatibleWith(other: ThingLink): Boolean {
//        return when (data) {
//            is PlainThingLinkData -> other.data !is PlainThingLinkData ||
//        }
        return !equals(other)
    }
    override fun equals(other: Any?): Boolean {
        if (other !is ThingLink) {
            return false
        }
        if (!thingLinkThingsEquals(this, other, data.ignoreOrderForEquality)) {
            return false
        }
        return data == other.data
    }

    override fun hashCode(): Int {
        val thingHashCode = Objects.hash(thingA.uuid, thingB.uuid) + if (data.ignoreOrderForEquality) Objects.hash(thingB.uuid, thingA.uuid) else 0
        return thingHashCode
    }
}
