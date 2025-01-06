package fri.emp.odmevko

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fri.emp.odmevko.ui.theme.OdmevkoTheme
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.Image
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import fri.emp.odmevko.data.DatabaseInstance
import fri.emp.odmevko.data.SongEntity
import fri.emp.odmevko.ui.theme.OdmevkoTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


data class Song(val id: Int, val name: String, val artist: String, val imageUrl: String)


class PlaylistActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OdmevkoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PlaylistScreen(context = this)
                }
            }
        }
    }
    /*
    private fun odpriPredvajalnik() {
        val intent = Intent(this, SongActivity::class.java)
        startActivity(intent)
    }

     */
}

@Composable
fun PlaylistScreen(context:Context) {
    var songs by remember { mutableStateOf<List<SongEntity>>(emptyList()) }

    // Load songs from the database
    LaunchedEffect(Unit) {
        val database = DatabaseInstance.getDatabase(context)
        val songDao = database.songDao()
        songs = withContext(Dispatchers.IO) { songDao.getAllSongs() }
    }

    var selectedSong by remember { mutableStateOf<Song?>(null) }
    var isPlaying by remember { mutableStateOf(false) }
    var showMiniPlayer by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {if (songs.isEmpty()) {
        Text("No songs in the playlist.", modifier = Modifier.padding(16.dp))
    }else {
        // Song list above the BottomOverlay
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = if (showMiniPlayer) 80.dp else 16.dp)
        ) {
            items(songs) { song ->
                SongItem(
                    song = Song(
                        id = song.id,
                        name = song.name,
                        artist = song.artist,
                        imageUrl = song.imageUrl
                    ),
                    isSelected = false,
                    onClick = {}
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        // Spacer ensures BottomOverlay stays below LazyColumn
        if (showMiniPlayer && selectedSong != null) {
            BottomOverlay(
                songTitle = selectedSong?.name ?: "Unknown",
                artistName = selectedSong?.artist ?: "Unknown Artist",
                isPlaying = isPlaying,
                onPlayPauseClick = {
                    isPlaying = !isPlaying
                    if (!isPlaying) {
                        showMiniPlayer = false
                    }
                },
                onDoubleClick = {
                    // Navigate to SongActivity on double-click
                    val intent = Intent(context, SongActivity::class.java).apply {
                        putExtra("song_title", selectedSong?.name)
                        putExtra("song_artist", selectedSong?.artist)
                        putExtra("song_image_url", selectedSong?.imageUrl)
                    }
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
    }
}


@Composable
fun BottomOverlay(
    songTitle: String,
    artistName: String,
    isPlaying: Boolean,
    onPlayPauseClick: () -> Unit,
    onDoubleClick: () -> Unit,
    modifier: Modifier
) {
    var lastClickTime by remember { mutableStateOf(0L) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastClickTime < 300) {
                    onDoubleClick()
                }
                lastClickTime = currentTime
            }
            .background(
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = MaterialTheme.shapes.large
            )
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = songTitle,
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = artistName,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(onClick = onPlayPauseClick) {
                Image(
                    painter = painterResource(
                        id = if (isPlaying) R.drawable.pauza else R.drawable.skipnaprej
                    ),
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    modifier = Modifier.size(48.dp)
                )
            }
        }
        //Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier
            .align(Alignment.BottomCenter)) {
            var progress by remember { mutableStateOf(0.5f)}
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 16.dp),
            )
        }
    }
}


@Composable
fun SongItem(song: Song, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) Color.LightGray else MaterialTheme.colorScheme.surface

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = rememberAsyncImagePainter(song.imageUrl),
            contentDescription = "Album art for ${song.name}",
            modifier = Modifier
                .size(50.dp)
                .background(Color.Gray)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = song.name, style = MaterialTheme.typography.bodyLarge)
            Text(text = song.artist, style = MaterialTheme.typography.bodyMedium)
        }
    }
}