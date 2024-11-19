package fri.emp.odmevko

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
import androidx.compose.ui.text.style.TextOverflow
import fri.emp.odmevko.ui.theme.OdmevkoTheme


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
                    PlaylistScreen()
                }
            }
        }
    }
}
@Preview
@Composable
fun PlaylistScreen() {
    // Sample song data
    val songs = listOf(
        Song(1, "Lose Yourself", "Eminem", "https://open.spotify.com/track/6atDVsk39X4LS1C3hdgX5l"),
        Song(2, "Shape of You", "Ed Sheeran", "https://example.com/ed_sheeran_shape_of_you.jpg"),
        Song(3, "Blinding Lights", "The Weeknd", "https://example.com/ed_sheeran_shape_of_you.jpg") ,
        Song(4, "Muzik", "Jz", "https://example.com/ed_sheeran_shape_of_you.jpg"),
        Song(5, "Dela", "jz", "https://example.com/ed_sheeran_shape_of_you.jpg") ,
        Song(6, "gugu", "gaga", "https://example.com/ed_sheeran_shape_of_you.jpg") ,
        Song(7, "Blinding ", " Weeknd", "https://example.com/ed_sheeran_shape_of_you.jpg") ,
        Song(8, "Blinding Lights", "The Weeknd", "https://example.com/ed_sheeran_shape_of_you.jpg") ,
        Song(9, "Blinding Lights", "The Weeknd", "https://example.com/ed_sheeran_shape_of_you.jpg") ,
        Song(10, "Blinding Lights", "The Weeknd", "https://example.com/ed_sheeran_shape_of_you.jpg") ,
        Song(11, "Blinding Lights", "The Weeknd", "https://example.com/ed_sheeran_shape_of_you.jpg") ,
        Song(12, "Blinding Lights", "The Weeknd", "https://example.com/ed_sheeran_shape_of_you.jpg") ,
    )

    var selectedSongId by remember { mutableStateOf<Int?>(null) }
    var isPlaying by remember { mutableStateOf(false) }
    var currentSong by remember { mutableStateOf<Song?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(songs) { song ->
            SongItem(
                song = song,
                isSelected = song.id == selectedSongId,
                onClick = {
                    selectedSongId = song.id
                    currentSong = song
                    isPlaying = true
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
    currentSong?.let {
        BottomOverlay(song = it, isPlaying = isPlaying, onPlayPauseClicked = {
            isPlaying = !isPlaying // Toggle play/pause
        })
    }
}

@Composable
fun BottomOverlay(song: Song, isPlaying: Boolean, onPlayPauseClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Color.Black.copy(alpha = 0.7f))
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Album Art
            Image(
                painter = rememberAsyncImagePainter(song.imageUrl),
                contentDescription = "Album art for ${song.name}",
                modifier = Modifier
                    .size(50.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = song.name,
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = song.artist,
                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }


            IconButton(
                onClick = onPlayPauseClicked,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Filled.Call else Icons.Filled.PlayArrow,
                    contentDescription = if (isPlaying) "Pause" else "Play",
                    tint = Color.White
                )
            }
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