package fri.emp.odmevko

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import fri.emp.odmevko.ui.theme.OdmevkoTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Console

class MainActivity : ComponentActivity() {

    private var test = kotlin.String()

    fun testing() {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://deezerdevs-deezer.p.rapidapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getData("eminem")

        retrofitData.enqueue(object : Callback<MyData?> {

            override fun onResponse(p0: Call<MyData?>, p1: Response<MyData?>) {
                test = p1.body()?.next.toString()
                Log.d("TAG: onResponse", "onResponse: " + p1.body())
            }

            override fun onFailure(p0: Call<MyData?>, p1: Throwable) {
                // Log.d("TAG: onFaliure", "onFaliure: " + p1.message)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        testing()

        enableEdgeToEdge()
        setContent {
            OdmevkoTheme {
                Surface (modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Greeting(name = "Odmevko")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Column (
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = " $name",
            modifier = Modifier.background(Color.Blue).padding()
        )
        Text(
            text = " $name",
            modifier = Modifier.background(Color.Blue).padding()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OdmevkoTheme {
        Greeting("Android")
    }
}