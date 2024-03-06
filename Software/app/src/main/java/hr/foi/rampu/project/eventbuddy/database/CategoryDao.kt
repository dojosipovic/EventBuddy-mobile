package hr.foi.rampu.project.eventbuddy.database

import hr.foi.rampu.project.eventbuddy.entities.Category

class CategoryDao {
    fun getCategoryById(id: Int): Category? {
        val sql = "SELECT * FROM kategorija WHERE ID = ${id}"
        val set = Database.execute(sql)
        while(set.next()){
            return Category(
                id = set.getString("ID").toInt(),
                name = set.getString("naziv")
            )
        }
        return null
    }
}