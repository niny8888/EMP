package fri.emp.odmevko

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DB(val context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_PLAYLIST_TABLE.playlist)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun exectueQuery(sql:String):Boolean {
        try {
            val database = this.writableDatabase
            database.execSQL(sql)
        } catch (e: Exception){
            e.printStackTrace()
            return false
        }
        return true
    }

    fun getData(){
        var cursor:Cursor?=null
        try {
            val database = this.readableDatabase
            cursor = database.rawQuery("SELECT * FROM PLAYLIST", null)
            cursor?.moveToFirst()
            do {
                var songtitle = cursor.getColumnIndex("SONGTITLE")
                var songartist = cursor.getColumnIndex("SONGARTIST")
                var songalbum = cursor.getColumnIndex("SONGALBUM")
                var songimage = cursor.getColumnIndex("SONGIMAGE")
                var songplay = cursor.getColumnIndex("PLAY")
                Log.d("Playlist", "Title $songtitle, Artist $songartist, Album $songalbum, Image $songimage, Playing $songplay")
            }while (cursor.moveToNext())
        } catch (e:Exception) {
            e.printStackTrace()
        } finally {
            cursor?.close()
        }
    }

    companion object{
        private const val DB_VERSION = 1
        private const val DB_NAME = "Playlist.DB"

    }
}