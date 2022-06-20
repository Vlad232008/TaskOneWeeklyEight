package com.example.taskoneweeklyeight.json

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HeroInfo(
    val id: Int,
    val name: String,
    val localized_name: String,
    val primary_attr: String,
    val attack_type: String,
    val roles: List<String>,
    val img: String,
    val icon: String,
    val agi_gain: Double,
    val str_gain: Double,
    val int_gain: Double,
    val base_health: Int,
    val base_mana: Int,
    val base_attack_min: Int,
    val base_attack_max: Int,
    val base_str: Int,
    val base_agi: Int,
    val base_int: Int,
    val attack_range: Int,
    val attack_rate: Double,
    val move_speed: Int,
)