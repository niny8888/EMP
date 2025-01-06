package fri.emp.odmevko.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import fri.emp.odmevko.viewmodel.MusicViewModel

@Composable
fun MusicSearchScreen(viewModel: MusicViewModel) {
    val musicData = viewModel.musicData.collectAsState().value
    val error = viewModel.error.collectAsState().value
    var query by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            label = { Text("Search for music") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { viewModel.searchMusic(query) }) {
            Text("Search")
        }
        Spacer(modifier = Modifier.height(16.dp))

        when {
            error != null -> Text("Error: $error", color = Color.Red)
            musicData != null -> {
                LazyColumn {
                    items(musicData.data) { song ->
                        SongItem(
                            artist = song.artist.name,
                            title = song.title
                        )
                    }
                }

            }
            else -> Text("Enter a query and hit search")
        }
    }
}
@Composable
fun SongItem(artist: String, title: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = "$artist - $title",
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

