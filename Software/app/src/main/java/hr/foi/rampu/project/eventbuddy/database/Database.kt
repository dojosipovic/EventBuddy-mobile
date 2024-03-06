package hr.foi.rampu.project.eventbuddy.database

import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import android.util.Log
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

object Database {

    private var con: Connection? = null

    fun execute(sql:String): ResultSet{
        return this.connectToDatabase()!!.createStatement().executeQuery(sql)
    }

    fun executeUpdate(sql: String){
        this.connectToDatabase()!!.createStatement().executeUpdate(sql)
    }

    fun connectToDatabase(): Connection? {
        val ip = "31.147.206.65"
        val db = "RPP2324_T02_DB"
        val username = "RPP2324_T02_User"
        val password = "AIF1mn#s"
        val a = ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(a)
        var connectURL: String? = null
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver")
            connectURL = "jdbc:jtds:sqlserver://$ip/$db"
            con = DriverManager.getConnection(connectURL, username, password)
        } catch (e: Exception) {
            Log.e("Error: ", e.message!!)
        }
        return con
    }
}
