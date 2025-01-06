package fri.emp.odmevko.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SongDao {

    @Insert
    suspend fun insertSong(song: SongEntity)

    @Query("SELECT * FROM songs")
    suspend fun getAllSongs(): List<SongEntity>
}


