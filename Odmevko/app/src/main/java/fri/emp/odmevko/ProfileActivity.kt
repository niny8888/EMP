package fri.emp.odmevko

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fri.emp.odmevko.repository.MusicRepository
import fri.emp.odmevko.ui.screens.MusicSearchScreen
import fri.emp.odmevko.viewmodel.MusicViewModel

class ProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProfileScreen()
        }
    }

    @Composable
    fun ProfileScreen() {
        // Safely initialize MusicViewModel
        val musicViewModel by remember { mutableStateOf(MusicViewModel(MusicRepository())) }
        var currentScreen by remember { mutableStateOf<Screen>(Screen.Profile) }

        // Screen navigation logic
        when (currentScreen) {
            Screen.Profile -> ProfileLayout(
                onSearchMusicClick = { currentScreen = Screen.MusicSearch },
                onViewPlaylistClick = { currentScreen = Screen.Playlist }
            )
            Screen.MusicSearch -> MusicSearchScreen(viewModel = musicViewModel)
            Screen.Playlist -> PlaylistScreen() // Replace with actual playlist screen
        }
    }

    @Composable
    fun ProfileLayout(
        onSearchMusicClick: () -> Unit,
        onViewPlaylistClick: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = onSearchMusicClick) {
                Text("Search Music")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onViewPlaylistClick) {
                Text("View Playlist")
            }
        }
    }
}

sealed class Screen {
    object Profile : Screen()
    object MusicSearch : Screen()
    object Playlist : Screen()
}
@Composable
fun ProfileScreenWithoutViewModel() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { /* Navigate to Playlist */ }) {
            Text("View Playlist")
        }
    }
}
