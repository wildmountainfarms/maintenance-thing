package me.retrodaredevil.maintenance.reference

interface ReferenceCreator<T : Any> {
    val databaseName: String
    val typeName: String
    fun createReference(simpleReference: SimpleReference): Reference<T>
}
