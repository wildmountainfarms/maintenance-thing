package me.retrodaredevil.maintenance.reference

interface ReferenceCreator<T : Any> {
    fun createReference(simpleReference: SimpleReference): Reference<T>
}
