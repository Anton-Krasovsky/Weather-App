package by.tigertosh.weatherretrofitpractice

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import by.tigertosh.weatherretrofitpractice.databinding.ActivityMainBinding
import by.tigertosh.weatherretrofitpractice.retrofit.MainApi
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainApi: MainApi
    private var city: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        initRetrofit()

        binding.button.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                hideKeyboard()
                city = binding.city.text.toString()
                if (city == "") {
                    runOnUiThread {
                        toast()
                    }
                } else {
                    val model = mainApi.getWeather(
                        "ec3b165bc5cb40bd9cf100502241904",
                        city,
                        "1",
                        "no",
                        "no"
                    )

                    runOnUiThread {
                        binding.progressBar.visibility = View.VISIBLE
                        Picasso.get().load("https:${model.current.condition.icon}")
                            .into(binding.icWeather)
                    }
                    delay(1500)

                    runOnUiThread {
                        binding.tempC.text = buildString {
                            append(model.current.temp_c)
                            append("C degrees")
                        }
                        binding.days.text = model.location.localtime
                        binding.progressBar.visibility = View.INVISIBLE
                        binding.days.visibility = View.VISIBLE
                        binding.tempC.visibility = View.VISIBLE
                        binding.icWeather.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun initRetrofit() {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/").client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mainApi = retrofit.create(MainApi::class.java)
    }

    private fun hideKeyboard() {
        val view = this.currentFocus
        if (view != null) {
            val hideMe = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hideMe.hideSoftInputFromWindow(view.windowToken, 0)
        } else
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
    }

    private fun toast() = Toast.makeText(
        this@MainActivity, "Enter the city",
        Toast.LENGTH_SHORT
    ).show()

}