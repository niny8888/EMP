package fri.emp.odmevko.data

import android.content.Context
import androidx.room.Room

object DatabaseInstance {
    private var instance: SongDatabase? = null

    fun getDatabase(context: Context): SongDatabase {
        if (instance == null) {
            instance = Room.databaseBuilder(
                context.applicationContext,
                SongDatabase::class.java,
                "song_database"
            ).build()
        }
        return instance!!
    }
}