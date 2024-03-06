package hr.foi.rampu.project.eventbuddy.database

import android.util.Log
import hr.foi.rampu.project.eventbuddy.entities.Role
import hr.foi.rampu.project.eventbuddy.entities.User

class UsersDao {

    fun getUserByUsername(username: String): User? {
        val sql = "SELECT * FROM korisnik WHERE korime = '${username}'"
        val set = Database.execute(sql)
        while (set.next()){
            return User(
                id = set.getString("ID").toInt(),
                username = set.getString("korime"),
                name = set.getString("ime"),
                surname = set.getString("prezime"),
                password = set.getString("lozinka"),
                warnings = set.getString("upozorenja").toInt(),
            )
        }
        return null
    }

    fun sendOrganizerRequest(user: User, reason: String) {
        val sql = "INSERT INTO zahtjev_organizator(ID_korisnik, opis) VALUES (${user.id}, '$reason');"
        Database.executeUpdate(sql)
    }

    fun hasUserOrganizerRequest(user: User): Boolean {
        val sql = """SELECT * FROM
                        zahtjev_organizator
                    WHERE
                        prihvacen = 0 AND
                        ID_korisnik = ${user.id}
                """.trimMargin()
        val set = Database.execute(sql)
        var rows = 0
        while(set.next()){
            rows++
        }
        return rows > 0
    }

    fun getRoles(id: Int): List<Role> {
        val sql = """
            SELECT uloga.ID, uloga.Naziv
            FROM uloga, uloge, korisnik
            WHERE
                uloge.ID_korisnik = korisnik.ID AND
                uloge.ID_uloga = uloga.ID AND
                korisnik.ID = ${id}
        """.trimIndent()

        val set = Database.execute(sql)
        val roles: MutableList<Role> = mutableListOf()

        while(set.next()){
            val role = Role(
                id = set.getString("ID").toInt(),
                name = set.getString("naziv")
            )
            roles += role
        }
        return roles
    }

    fun kickUser(id_korisnik: Int, id_dogadaj: Int){
        val sql = "DELETE FROM sudionici WHERE id_korisnik = '${id_korisnik}' AND id_dogadaj = '${id_dogadaj}'"
        Database.executeUpdate(sql)
    }

    fun banUser(id_korisnik: Int, id_dogadaj: Int){
        val sql = "DELETE FROM sudionici WHERE id_korisnik = '${id_korisnik}' AND id_dogadaj = '${id_dogadaj}'"
        Log.e("SQL",sql)
        Database.executeUpdate(sql)
        val sql2 = "INSERT INTO sudionici_zabrana (id_korisnik, id_dogadaj) VALUES ('${id_korisnik}', '${id_dogadaj}')"
        Log.e("SQL",sql2)
        Database.executeUpdate(sql2)
    }

    fun addUser(user: User){
        val sql = """
            INSERT INTO korisnik (korime, ime, prezime, lozinka)
            VALUES ('${user.username}', '${user.name}', '${user.surname}', '${user.password}')
        """.trimIndent()
        Database.executeUpdate(sql)
    }

    fun updateUser(user: User, ime: String, prezime: String, lozinka: String){
        val sql = """
            UPDATE korisnik
            SET
                ime = '${ime}',
                prezime = '${prezime}',
                lozinka = '${lozinka}'
            WHERE
                ID = ${user.id}
        """.trimIndent()
        Database.executeUpdate(sql)
    }

    fun getRequest(id: Int): Boolean {
        val sql = "SELECT * FROM zahtjev_organizator WHERE ID_korisnik = ${id}".trimIndent()

        val set = Database.execute(sql)
        Log.e("SQL", sql);
        while(set.next()){
            var odgovor = set.getBoolean("prihvacen")
            Log.e("PRIHVACEN", odgovor.toString());
            return odgovor
        }
        return false
    }

    fun deleteRequest(id: Int) {
        val sql = "DELETE FROM zahtjev_organizator WHERE ID_korisnik = ${id}".trimIndent()
        Database.executeUpdate(sql)
    }
}