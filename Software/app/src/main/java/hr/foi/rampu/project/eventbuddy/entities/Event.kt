package hr.foi.rampu.project.eventbuddy.entities

import hr.foi.rampu.project.eventbuddy.database.CategoryDao
import hr.foi.rampu.project.eventbuddy.database.EventsDao
import java.util.Date

class Event (
    val id: Int,
    val name: String,
    val overview: String,
    val date: Date,
    val time: Date,
    val location: String,
    val statusId: Int,
    val userId: Int,
    val categoryId: Int
) {
    private var participantsInstance: Int = -1
    private var categoryInstance: Category? = null

    private var categoryDao: CategoryDao = CategoryDao()
    private var eventsDao: EventsDao = EventsDao()
    val category: Category
        get() {
            if (categoryInstance == null) {
                categoryInstance = categoryDao.getCategoryById(categoryId)
            }
            return categoryInstance!!
        }

    val participants: Int
        get() {
            if (participantsInstance == -1) {
                participantsInstance = eventsDao.getNumberOfParticipants(id)
            }
            return participantsInstance
        }
}