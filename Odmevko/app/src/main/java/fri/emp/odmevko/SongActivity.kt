package fri.emp.odmevko

import android.app.Activity
import android.database.Cursor
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.Image
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import fri.emp.odmevko.ui.theme.OdmevkoTheme


class SongActivity : ComponentActivity() {
    var db:DB?=null
    // val songPlaybackStorage = songPlaybackUrl
    private var mediaPlayer: MediaPlayer? = null
    var songPlaybackUrl = ""
    var songTitle = ""
    var songArtist = ""
    var songAlbum = ""
    var songImageUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = DB(this)


        songTitle = intent.getStringExtra("song_title") ?: "Unknown Title"
        songArtist = intent.getStringExtra("song_artist") ?: "Unknown Artist"
        songAlbum = intent.getStringExtra("song_album") ?: "Unknown Album"
        songImageUrl = intent.getStringExtra("song_image_url") ?: ""
        songPlaybackUrl = intent.getStringExtra("song_playback_url") ?: ""
        Log.d("SongActivity", "Playback URL: $songPlaybackUrl")

        try {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(songPlaybackUrl)
                prepareAsync() // Asynchronous preparation
                setOnPreparedListener { start() } // Start playback when ready
            }
        } catch (e: Exception) {
            Log.e("SongActivity", "Error initializing MediaPlayer: ${e.message}")
            e.printStackTrace()
        }


        setContent {
            MusicPlayerScreen(
                songTitle = songTitle,
                songArtist = songArtist,
                songAlbum = songAlbum,
                songImageUrl = songImageUrl,
                onPlayPauseClick = { togglePlayPause() },
                insertData = {insertData()}
            )
        }

    }

    private fun togglePlayPause() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
            } else {
                it.start()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release() // Release resources when activity is destroyed
        mediaPlayer = null
    }

    private fun insertData(){
        val sqlQueryDodaj = "INSERT INTO PLAYLIST(SONGTITLE, SONGARTIST, SONGALBUM, SONGIMAGE, PLAY) VALUES('$songTitle', '$songArtist', '$songAlbum', '$songImageUrl', '$songPlaybackUrl')"
        db?.exectueQuery(sqlQueryDodaj)
        db?.getData()
    }
}

private val Icons.Filled.SkipNext: ImageVector
    get() {
        TODO("Not yet implemented")
    }
private val Icons.Filled.SkipPrevious: ImageVector
    get() {
        TODO("Not yet implemented")
    }
private val Icons.Filled.Pause: ImageVector
    get() {
        TODO("Not yet implemented")
    }

@Composable
fun MusicPlayerScreen(songTitle: String, songArtist: String, songAlbum: String, songImageUrl: String, onPlayPauseClick: () -> Unit, insertData: () -> Unit) {
    var isPlaying by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0.5f) }

    Scaffold(
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(8.dp)
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceBetween) {
                    AsyncImage(
                        model = songImageUrl,
                        contentDescription = "Album cover",
                        modifier = Modifier
                            .width(280.dp)
                            .height(280.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = songTitle,
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = songAlbum,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = songArtist,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.height(48.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "1:50", style = MaterialTheme.typography.bodySmall)
                        Text(text = "3:45", style = MaterialTheme.typography.bodySmall)
                    }
                }
                Spacer(modifier = Modifier.height(48.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { /* Handle previous */ },
                        modifier = Modifier.size(64.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.skipnazaj),
                            contentDescription = "Previous song",
                            modifier = Modifier.width(64.dp).height(64.dp)
                        )
                    }
                    IconButton(onClick = {
                        isPlaying = !isPlaying
                        onPlayPauseClick()
                    },
                        modifier = Modifier.size(64.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.pauza),
                            contentDescription = if (isPlaying) "Pause" else "Play",
                            modifier = Modifier.width(64.dp).height(64.dp)
                        )
                    }
                    IconButton(onClick = { },
                        modifier = Modifier.size(64.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.skipnaprej),
                            contentDescription = "Next song",
                            modifier = Modifier.width(64.dp).height(64.dp)
                        )
                    }
                    IconButton(onClick = { insertData() },
                        modifier = Modifier.size(64.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.skipnaprej),
                            contentDescription = "Next song",
                            modifier = Modifier.width(64.dp).height(64.dp)
                        )
                    }
                }
            }
        }
    )
}
