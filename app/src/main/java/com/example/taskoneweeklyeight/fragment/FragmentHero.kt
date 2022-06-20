package com.example.taskoneweeklyeight.fragment

import android.content.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskoneweeklyeight.R
import com.example.taskoneweeklyeight.activity.HeroInfoActivity
import com.example.taskoneweeklyeight.activity.MainActivity.Companion.heroInfo
import com.example.taskoneweeklyeight.adapter.HeroAdapter
import com.example.taskoneweeklyeight.databinding.FragmentHeroBinding
import com.example.taskoneweeklyeight.json.HeroInfo
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import okhttp3.*
import java.io.*


class FragmentHero : Fragment(), HeroAdapter.Listener {

    private var json: String = ""
    lateinit var binding: FragmentHeroBinding
    private val URL_HEROINFO = "https://api.opendota.com/api/heroStats"
    private val okHttpClient = OkHttpClient()
    private val file: String = "DotsHero"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHeroBinding
            .inflate(inflater, container, false)
        readJson()
        initRcV()
        return binding.root
    }

    private fun getHeroInfo() {
        val request = Request.Builder()
            .url(URL_HEROINFO)
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                json = response.body.string()
                val moshi = Moshi.Builder().build()
                val listType = Types.newParameterizedType(List::class.java, HeroInfo::class.java)
                val adapter: JsonAdapter<List<HeroInfo>> = moshi.adapter(listType)
                heroInfo = adapter.fromJson(json)!!
            }

            override fun onFailure(call: Call, e: IOException) {
            }
        })
    }

    private fun saveJson() {
        try {
            // отрываем поток для записи
            val bw = BufferedWriter(
                OutputStreamWriter(
                    activity?.openFileOutput(file, AppCompatActivity.MODE_PRIVATE)
                )
            )
            // пишем данные
            bw.write(json)
            // закрываем поток
            bw.close()
            Log.d("MyLog", "Файл записан")
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun readJson() {
        try {
            // открываем поток для чтения
            val br = BufferedReader(
                InputStreamReader(
                    activity?.openFileInput(file)
                )
            )
            var str = ""
            str = br.readLine()
            // читаем содержимое
            if ((str != null) || (str != "")) {
                val moshi = Moshi.Builder().build()
                val listType = Types.newParameterizedType(List::class.java, HeroInfo::class.java)
                val adapter: JsonAdapter<List<HeroInfo>> = moshi.adapter(listType)
                heroInfo = adapter.fromJson(str)!!
            } else {
                getHeroInfo()
                while (heroInfo.isEmpty()) {
                    continue
                }
                saveJson()
            }
        } catch (e: FileNotFoundException) {
            getHeroInfo()
            while (heroInfo.isEmpty()) {
                continue
            }
            saveJson()
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun initRcV() = with(binding) {
        rcView.layoutManager = LinearLayoutManager(activity)
        rcView.adapter = HeroAdapter(this@FragmentHero, heroInfo)
    }

    companion object {
        fun newInstance() = FragmentHero()
    }

    override fun onClickItem(heroInfo: List<HeroInfo>, position: Int) {
        val intentHero = Intent(activity, HeroInfoActivity::class.java)
        intentHero.putExtra(HeroInfoActivity.KEY_ID, position)
        startActivity(intentHero)
    }
}