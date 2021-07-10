package me.retrodaredevil.maintenance.domain

class User(
        val username: String,
        val displayName: String,
) {
    object Constants {
        val USER_GUEST = User("guest", "Guest")
        val USER_SYSTEM = User("system", "System")
    }
}
