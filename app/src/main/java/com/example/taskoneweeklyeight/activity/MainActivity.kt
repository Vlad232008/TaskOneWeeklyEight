package com.example.taskoneweeklyeight.activity

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.taskoneweeklyeight.R
import com.example.taskoneweeklyeight.adapter.HeroAdapter
import com.example.taskoneweeklyeight.databinding.ActivityMainBinding
import com.example.taskoneweeklyeight.fragment.FragmentHero
import com.example.taskoneweeklyeight.fragment.FragmentProgram
import com.example.taskoneweeklyeight.json.HeroInfo
import com.example.taskoneweeklyeight.manager.FragmentManager

class MainActivity : AppCompatActivity(), HeroAdapter.Listener {
    lateinit var binding: ActivityMainBinding
    private var currentMenuItemId = R.id.hero


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE ) == -1)
            ||(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE ) == -1)) {
            ActivityCompat.requestPermissions(
                this,arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),0)
        }
        setBottomNavListener()
    }
    private fun setBottomNavListener(){
        binding.bNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.hero->{
                    currentMenuItemId = R.id.hero
                    title = "Герои"
                    FragmentManager.setFragment(FragmentHero.newInstance(), this)
                }
                R.id.program->{
                    currentMenuItemId = R.id.program
                    title = "О программе"
                    FragmentManager.setFragment(FragmentProgram.newInstance(), this)
                }
            }
            true
        }
    }
    override fun onResume() {
        super.onResume()
        binding.bNav.selectedItemId = currentMenuItemId
    }

    override fun onClickItem(heroInfo: List<HeroInfo>, position: Int) {
        Log.d("MyLog", "Name: $position")
    }
    companion object{
        var heroInfo = listOf<HeroInfo>() // Bundle()
    }
}