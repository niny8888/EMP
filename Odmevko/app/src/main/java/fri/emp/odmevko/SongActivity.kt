package fri.emp.odmevko

import android.os.Bundle
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
import fri.emp.odmevko.ui.theme.OdmevkoTheme

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
@Preview
@Composable
fun MusicPlayerScreen() {
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
                    Image(
                        painter = painterResource(id = R.drawable._96872555059),
                        contentDescription = "slikca albuma :3",
                        modifier = Modifier.width(280.dp).height(280.dp)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "NOID",
                        style = MaterialTheme.typography.headlineLarge,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "CHROMAKOPIA",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Tyler, The Creator",
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
                    IconButton(onClick = {  },
                        modifier = Modifier.size(64.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.skipnazaj),
                            contentDescription = "slikca albuma :3",
                            modifier = Modifier.width(64.dp).height(64.dp)
                        )
                    }
                    IconButton(onClick = {
                        isPlaying = !isPlaying
                    },
                        modifier = Modifier.size(64.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.pauza),
                            contentDescription = "slikca albuma :3",
                            modifier = Modifier.width(64.dp).height(64.dp)
                        )
                    }
                    IconButton(onClick = { },
                        modifier = Modifier.size(64.dp)) {
                        Image(
                            painter = painterResource(id = R.drawable.skipnaprej),
                            contentDescription = "slikca albuma :3",
                            modifier = Modifier.width(64.dp).height(64.dp)
                        )
                    }
                }
            }
        }
    )
}
