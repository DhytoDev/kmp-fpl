package dev.dhyto.fpl.shared.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Leagues(
    val classic: List<Classic>,
//    val cup: Cup,
    @SerialName("cup_matches")
    val cupMatches: List<CupMatchesDto>,
//    val h2h: List<Any>
)