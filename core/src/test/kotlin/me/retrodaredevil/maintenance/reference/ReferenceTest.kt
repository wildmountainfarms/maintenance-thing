package me.retrodaredevil.maintenance.reference

import me.retrodaredevil.maintenance.createDefaultMapper
import me.retrodaredevil.maintenance.domain.Thing
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.*

internal class ReferenceTest {
    @Test
    fun testJson() {
        val uuid = UUID.randomUUID()
        val simpleReference = SimpleReference(uuid)
        val mapper = createDefaultMapper()
        val json = mapper.writeValueAsString(simpleReference)
        println(json)
        val parsed = mapper.readValue(json, BaseReference::class.java)
        assertTrue(parsed is SimpleReference)
        assertEquals(uuid, parsed.uuid)

        val reference = simpleReference.toReference("meaningless_value", "another_meaningless_value", Thing::class.java)
        val json2 = mapper.writeValueAsString(reference)
        assertEquals(json, json2)
    }
}
