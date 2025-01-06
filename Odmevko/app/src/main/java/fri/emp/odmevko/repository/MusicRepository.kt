package fri.emp.odmevko.repository

import fri.emp.odmevko.MyData
import fri.emp.odmevko.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MusicRepository {

    fun searchMusic(query: String, onSuccess: (MyData) -> Unit, onError: (String) -> Unit) {
        val call = RetrofitInstance.api.getData(query)
        call.enqueue(object : Callback<MyData> {
            override fun onResponse(call: Call<MyData>, response: Response<MyData>) {
                if (response.isSuccessful && response.body() != null) {
                    onSuccess(response.body()!!)
                } else {
                    onError("Failed to fetch data: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MyData>, t: Throwable) {
                onError("Error: ${t.message}")
            }
        })
    }
}
