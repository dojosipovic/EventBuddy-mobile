package hr.foi.rampu.project.eventbuddy.database

import android.util.Log
import hr.foi.rampu.project.eventbuddy.entities.Event
import hr.foi.rampu.project.eventbuddy.entities.User
import java.text.SimpleDateFormat
import java.util.Locale

class EventsDao {
    private val sdfDate: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    private val sdfTime: SimpleDateFormat = SimpleDateFormat("HH:mm:ss", Locale.ENGLISH)

    fun getAllEvents(includePast: Boolean = false): List<Event> {
        // TODO("Napraviti da se ne prikazuju događaji ulogiranog korisnika")
        var sql = "SELECT * FROM dogadaj"

        if (!includePast) {
            sql = """
                SELECT * FROM dogadaj
                WHERE
                    CAST(datum as date) >= CAST(GETDATE() as date) AND ID_status = 1
                ORDER BY
                    datum
            """.trimIndent()
        }

        val set = Database.execute(sql)
        val list: MutableList<Event> = mutableListOf()
        while(set.next()){
            val event = Event(
                id = set.getString("ID").toInt(),
                name = set.getString("naziv"),
                overview = set.getString("opis"),
                date = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH)
                    .parse(set.getString("datum"))!!,
                time = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH)
                    .parse(set.getString("datum"))!!,
                location = set.getString("mjesto"),
                categoryId = set.getString("ID_kategorija").toInt(),
                statusId = set.getString("ID_status").toInt(),
                userId = set.getString("ID_korisnik").toInt()
            )
            list += event
            Log.i("CATEGORY", "${event.category.id} - ${event.category.name}")
        }
        return list
    }

    fun getNumberOfParticipants(id: Int): Int {
        val sql = """
            SELECT COUNT(*) as broj_sudionika
            FROM sudionici
            LEFT JOIN korisnik
            ON ID = ID_korisnik
            WHERE ID_dogadaj = ${id}
        """.trimIndent()
        val set = Database.execute(sql)
        while(set.next()){
            return set.getString("broj_sudionika").toInt()
        }
        return 0
    }

    fun getEventByName(eventName: String): Event? {
        val sql = "SELECT * FROM dogadaj WHERE naziv='${eventName}'"
        Log.e("SQL",sql)
        val set = Database.execute(sql)
        var eventDobiven: Event? = null
        while(set.next()){
            eventDobiven = Event(
                id = set.getString("ID").toInt(),
                name = set.getString("naziv"),
                overview = set.getString("opis"),
                date = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH)
                    .parse(set.getString("datum"))!!,
                time = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH)
                    .parse(set.getString("datum"))!!,
                location = set.getString("mjesto"),
                categoryId = set.getString("ID_kategorija").toInt(),
                statusId = set.getString("ID_status").toInt(),
                userId = set.getString("ID_korisnik").toInt()
            )
            return eventDobiven
        }
        return eventDobiven
    }

    fun getEventById(eventId: Int): Event? {
        val sql = "SELECT * FROM dogadaj WHERE ID=${eventId}"
        Log.e("SQL",sql)
        val set = Database.execute(sql)
        var eventDobiven: Event? = null
        while(set.next()){
            eventDobiven = Event(
                id = set.getString("ID").toInt(),
                name = set.getString("naziv"),
                overview = set.getString("opis"),
                date = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH)
                    .parse(set.getString("datum"))!!,
                time = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH)
                    .parse(set.getString("datum"))!!,
                location = set.getString("mjesto"),
                categoryId = set.getString("ID_kategorija").toInt(),
                statusId = set.getString("ID_status").toInt(),
                userId = set.getString("ID_korisnik").toInt()
            )
            return eventDobiven
        }
        return eventDobiven
    }

    fun updateEvent(event: Event){
        val sql = """
            UPDATE dogadaj SET
                naziv = '${event.name}',
                opis = '${event.overview}',
                mjesto = '${event.location}',
                datum = '${sdfDate.format(event.date)} ${sdfTime.format(event.time)}'
                WHERE
                id = ${event.id}
        """.trimIndent()
        Log.e("SQL",sql)
        Database.executeUpdate(sql)
    }
    fun cancelEvent(event: Event){
        val sql = """
            UPDATE dogadaj SET
                ID_status = '4'
                WHERE
                id = ${event.id}
        """.trimIndent()
        Log.e("SQL",sql)
        Database.executeUpdate(sql)
    }


    fun getUserEvents(user: User): List<Event> {
        val sql = "SELECT * FROM dogadaj WHERE ID_korisnik = ${user.id} AND ID_status = 1  ORDER BY datum DESC"
        val set = Database.execute(sql)
        val list: MutableList<Event> = mutableListOf()

        while(set.next()){
            val event = Event(
                id = set.getString("ID").toInt(),
                name = set.getString("naziv"),
                overview = set.getString("opis"),
                date = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH)
                    .parse(set.getString("datum"))!!,
                time = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH)
                    .parse(set.getString("datum"))!!,
                location = set.getString("mjesto"),
                categoryId = set.getString("ID_kategorija").toInt(),
                statusId = set.getString("ID_status").toInt(),
                userId = set.getString("ID_korisnik").toInt()
            )
            list += event
        }
        return list
    }

    fun getUserSubscribedHistoryEvents(user: User): List<Event> {
        val sql = """
            SELECT * FROM
                dogadaj
            RIGHT JOIN sudionici
            ON
                dogadaj.ID = sudionici.ID_dogadaj
            WHERE
                sudionici.ID_korisnik = ${user.id} AND
                CAST(datum as date) < CAST(GETDATE() as date) AND ID_status = 1
        """.trimIndent()
        val set = Database.execute(sql)
        val list: MutableList<Event> = mutableListOf()

        while(set.next()){
            val event = Event(
                id = set.getString("ID").toInt(),
                name = set.getString("naziv"),
                overview = set.getString("opis"),
                date = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH)
                    .parse(set.getString("datum"))!!,
                time = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH)
                    .parse(set.getString("datum"))!!,
                location = set.getString("mjesto"),
                categoryId = set.getString("ID_kategorija").toInt(),
                statusId = set.getString("ID_status").toInt(),
                userId = set.getString("ID_korisnik").toInt()
            )
            list += event
        }
        return list
    }

    fun getUserSubscribedUpcomingEvents(user: User): List<Event> {
        val sql = """
            SELECT * FROM
                dogadaj
            RIGHT JOIN sudionici
            ON
                dogadaj.ID = sudionici.ID_dogadaj
            WHERE
                sudionici.ID_korisnik = ${user.id} AND
                CAST(datum as date) >= CAST(GETDATE() as date) AND ID_status = 1
        """.trimIndent()
        val set = Database.execute(sql)
        val list: MutableList<Event> = mutableListOf()

        while(set.next()){
            val event = Event(
                id = set.getString("ID").toInt(),
                name = set.getString("naziv"),
                overview = set.getString("opis"),
                date = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH)
                    .parse(set.getString("datum"))!!,
                time = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH)
                    .parse(set.getString("datum"))!!,
                location = set.getString("mjesto"),
                categoryId = set.getString("ID_kategorija").toInt(),
                statusId = set.getString("ID_status").toInt(),
                userId = set.getString("ID_korisnik").toInt()
            )
            list += event
        }
        return list
    }

    fun getAllParticipantsOnEvent(eventID: String?): List<User> {
        val sql = """select 
                    ID, korime, ime, prezime, lozinka, upozorenja 
                    from sudionici 
                    LEFT JOIN korisnik ON ID = ID_korisnik
                    WHERE 
                    ID_dogadaj = ${eventID}"""
        val set = Database.execute(sql)
        val list: MutableList<User> = mutableListOf()

        while(set.next()){
            val user = User(
                id = set.getString("ID").toInt(),
                username = set.getString("korime"),
                name = set.getString("ime"),
                surname = set.getString("prezime"),
                password = set.getString("lozinka"),
                warnings = set.getString("upozorenja").toInt(),
            )
            list += user
        }
        return list
    }

    fun isUserSubscribedOnEvent(idUser: Int, idEvent: Int): Boolean {
        val sql = """SELECT * FROM
                        sudionici
                    WHERE
                        ID_dogadaj = ${idEvent} AND
                        ID_korisnik = ${idUser}
                """.trimMargin()
        val set = Database.execute(sql)
        var rows: Int = 0
        while(set.next()){
            rows++
        }
        return rows > 0
    }

    fun subscribeUserOnEvent(user: User, event: Event) {
        Database.executeUpdate("INSERT INTO sudionici VALUES(${event.id}, ${user.id})")
    }

    fun unsubscribeUserOnEvent(user: User, event: Event) {
        Database.executeUpdate("DELETE FROM sudionici WHERE ID_dogadaj=${event.id} AND ID_korisnik = ${user.id}")
    }

    fun addEvent(newEvent: Event) {
        val sql = """INSERT INTO 
                dogadaj 
                    (naziv, opis, mjesto, datum, ID_korisnik, ID_kategorija)
                VALUES
                    (
                    '${newEvent.name}', 
                    '${newEvent.overview}',
                    '${newEvent.location}',
                    '${sdfDate.format(newEvent.date)} ${sdfTime.format(newEvent.time)}',
                    ${newEvent.userId},
                    ${newEvent.categoryId}
                    )
                    """.trimMargin()
        Log.e("SQL",sql)
        Database.executeUpdate(sql)
    }

    fun isUserBanned(user: User, event: Event): Boolean {
        val sql = """SELECT * FROM
                        sudionici_zabrana
                    WHERE
                        ID_dogadaj = ${event.id} AND
                        ID_korisnik = ${user.id}
                """.trimMargin()
        val set = Database.execute(sql)
        var rows: Int = 0
        while(set.next()){
            rows++
        }
        return rows > 0
    }
}
