package com.example.taskoneweeklyeight.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.taskoneweeklyeight.databinding.ActivityHeroInfoBinding


class HeroInfoActivity: AppCompatActivity() {
    lateinit var binding: ActivityHeroInfoBinding
    var heroInfo = MainActivity.heroInfo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeroInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        actionBarSetting()
        refreshActivity()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun actionBarSetting() {
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }

    companion object{
        const val KEY_ID  = "id"
    }

    private fun refreshActivity() = with(binding){
        val count = intent.getIntExtra(KEY_ID,0)
        val base_URl = "https://api.opendota.com${heroInfo[count].img}"
        ivImage.load(base_URl)
        tvName.text = heroInfo[count].localized_name
        tvAgi.text = heroInfo[count].agi_gain.toString()
        tvStr.text = heroInfo[count].str_gain.toString()
        tvInt.text = heroInfo[count].int_gain.toString()
        tvSpeed.text = heroInfo[count].move_speed.toString()
        tvRange.text = heroInfo[count].attack_range.toString()
        tvHealth.text = heroInfo[count].base_health.toString()
        tvMana.text = heroInfo[count].base_mana.toString()
        val attack = (heroInfo[count].base_attack_min + heroInfo[count].base_attack_max)/2
        tvBaseAttack.text = attack.toString()
        tvPrimaryAttr.text = when(heroInfo[count].primary_attr) {
            "str" -> "Сила"
            "agi" -> "Ловкость"
            "int" -> "Интеллект"
            else -> {
                ""
            }
        }
    }
}