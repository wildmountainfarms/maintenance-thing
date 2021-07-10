package me.retrodaredevil.maintenance.domain

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.JsonTypeName


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
sealed class ThingLinkData {
    open val ignoreOrderForEquality: Boolean = false
}

@JsonTypeName("plain")
object PlainThingLinkData : ThingLinkData() {
    override val ignoreOrderForEquality: Boolean
        get() = true
}

/**
 * Represents that thingA contains thingB. ex: thingA:refrigerator contains thingB:milk
 */
@JsonTypeName("container")
object ContainerThingLinkData : ThingLinkData()

/**
 * Represents that thingA is a type of thingB. ex: thingA:fluffy is a type of thingB:dog
 */
@JsonTypeName("type")
object TypeThingLinkData : ThingLinkData()

/**
 * Represents that thingA contains an amount of thingB(s). Ex: thingA:cabinet has 3 thingB:boxes of cheez-its
 */
@JsonTypeName("inventory")
class InventoryThingLinkData(
        val inventoryCountOfThingB: Int
) : ThingLinkData()
