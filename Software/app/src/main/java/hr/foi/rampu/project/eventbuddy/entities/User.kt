package hr.foi.rampu.project.eventbuddy.entities

import hr.foi.rampu.project.eventbuddy.database.UsersDao

class User(
    val id: Int,
    val name: String,
    val surname: String,
    val username: String,
    val password: String,
    val warnings: Int
) {
    private var rolesInstance: List<Role>? = null
    private var usersDao: UsersDao = UsersDao()

    val roles: List<Role>
        get() {
            if (rolesInstance == null) {
                rolesInstance = usersDao.getRoles(id)
            }
            return rolesInstance!!.toList()
        }

    fun IsOrganizer(): Boolean = roles.find { it.id == 3 } != null

    fun checkForRequest(): Boolean {
        return usersDao.getRequest(id)
    }

    fun deleteRequest() {
        usersDao.deleteRequest(id)
    }
}