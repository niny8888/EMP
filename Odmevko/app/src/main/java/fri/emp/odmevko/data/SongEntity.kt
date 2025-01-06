package fri.emp.odmevko.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "songs")
data class SongEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val artist: String,
    val imageUrl: String
)


