package bz.micro.tehuur.models

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.babbel.mobile.android.commons.okhttpawssigner.OkHttpAwsV4Signer
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class TeModel : ViewModel() {

    private val okClient: OkHttpClient
    val listAmount = MutableStateFlow(10)
    private var _loading = MutableStateFlow(false)
    val loading  = _loading.asStateFlow()

    private var _offsetList = mutableStateOf(0)
    private var theEnd = mutableStateOf(false)

    private val _elementList = mutableStateListOf<Record>()// mutableListOf<
    private val _elementListHolder = MutableStateFlow(listOf<Record>())
    val elementList  = _elementListHolder.asStateFlow()

    init {
        val signer = OkHttpAwsV4Signer("us-east-1", "execute-api")
        okClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val signed = signer.sign(original, "AKIA3V3KQQAXAL4FX7PB", "op+e6RpEXTNiUlb8mhnyzzPoziu8w8mKr7y+Ftrk")
                chain.proceed(signed)
            }
            .build()
        refreshList()
        }

    fun nextPage() {
        getList(_offsetList.value)
    }

    fun refreshList() {
        _elementList.clear()
        _offsetList.value = 0
        theEnd.value= false
        getList(_offsetList.value)
    }

      private fun getList(offset:Int) {
         if (!_loading.value && !theEnd.value) {
             _loading.value = true
             val jsonObject = JSONObject()
             jsonObject.put("city", "amsterdam")
             jsonObject.put("count", listAmount.value)
             jsonObject.put("offset", offset)

             val formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'")
             val current = LocalDateTime.now().format(formatter)
             val request = Request.Builder()
                 .url("https://g89gmy5d5b.execute-api.eu-central-1.amazonaws.com/list")
                 .header("x-amz-date", current)
                 .post(jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull()))
                 .build()

             okClient.newCall(request).enqueue(object : Callback {
                 override fun onFailure(call: Call, e: IOException) {
                     e.localizedMessage?.let { Log.e("okHTTP", it) } // redirect to error page
                 }
                 override fun onResponse(call: Call, response: Response) {
                     response.use {
                         if (!response.isSuccessful) {
                             Log.e("okHttp", response.toString())
                         }
                         val x = JSONArray(response.body!!.string())
                         if (x.length()<10) {theEnd.value=true}
                             (0 until x.length()).forEach {
                                 val el = x.getJSONObject(it)
                                 val r = Record(
                                     id = el["id"].toString(),
                                     date = el["date"].toString(),
                                     provider = el["provider"].toString(),
                                     globalid = el["globalid"].toString().toInt(),
                                     country = el["country"].toString(),
                                     county = el["county"].toString(),
                                     city = el["city"].toString(),
                                     street = el["street"].toString(),
                                     house = el["house"].toString(),
                                     index1 = el["index1"].toString(),
                                     index2 = el["index2"].toString(),
                                     img = el["img"].toString(),
                                     rooms = el["rooms"].toString(),
                                     price = el["price"].toString(),
                                     metr = el["metr"].toString(),
                                     link = el["link"].toString(),
                                     description = el["description"].toString(),
                                     pathcity = el["pathcity"].toString(),
                                     telegramchatid = el["telegramchatid"].toString(),
                                     telegramid = el["telegramid"].toString(),
                                     whatsupid = el["whatsupid"].toString(),
                                     facebookid = el["facebookid"].toString(),
                                     instagramid = el["instagramid"].toString(),
                                 )
                                 _elementList.add(r)
                             }

                         Log.d("array2", _elementListHolder.value.size.toString())
                             _elementListHolder.value = _elementList
                             _offsetList.value+=10
                             _loading.value = false

                             Log.d("array1", _elementList.size.toString())
                             Log.d("array2", _elementListHolder.value.size.toString())
                     }
                 }
             })
         }
        }
     }