package me.retrodaredevil.maintenance

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class MaintenanceThingApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(MaintenanceThingApplication::class.java, *args)
        }
    }
}