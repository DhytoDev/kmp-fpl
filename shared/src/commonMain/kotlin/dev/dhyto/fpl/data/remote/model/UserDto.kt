package dev.dhyto.fpl.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("date_of_birth")
    val dateOfBirth: String?,
    @SerialName("default_event")
    val defaultEvent: Int?,
    val dirty: Boolean?,
    val email: String?,
    val entry: Int?,
    @SerialName("entry_email")
    val entryEmail: Boolean?,
    @SerialName("entry_language")
    val entryLanguage: String?,
    @SerialName("first_name")
    val firstName: String?,
    val gender: String?,
    val id: Int?,
    @SerialName("last_name")
    val lastName: String?,
    val region: Int?
)