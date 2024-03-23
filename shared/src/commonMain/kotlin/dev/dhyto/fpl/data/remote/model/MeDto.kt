package dev.dhyto.fpl.data.remote.model


import kotlinx.serialization.Serializable

@Serializable
data class MeDto(
    val player: UserDto?,
    val watched: List<Int?>?
)