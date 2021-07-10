package me.retrodaredevil.maintenance

import com.fasterxml.jackson.databind.json.JsonMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.SingletonSupport

fun buildDefaultMapper(builder: JsonMapper.Builder): JsonMapper.Builder =
        builder.addModule(KotlinModule(strictNullChecks = true, singletonSupport = SingletonSupport.CANONICALIZE))

fun createDefaultMapper(): JsonMapper = buildDefaultMapper(JsonMapper.builder()).build()
