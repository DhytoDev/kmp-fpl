package dev.dhyto.fpl.data.remote.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Classic(
    @SerialName("admin_entry")
    val adminEntry: Int?,
    val closed: Boolean,
    val created: String,
    @SerialName("cup_league")
    val cupLeague: Int?,
    @SerialName("cup_qualified")
    val cupQualified: Boolean?,
    @SerialName("entry_can_admin")
    val entryCanAdmin: Boolean,
    @SerialName("entry_can_invite")
    val entryCanInvite: Boolean,
    @SerialName("entry_can_leave")
    val entryCanLeave: Boolean,
    @SerialName("entry_last_rank")
    val entryLastRank: Int,
    @SerialName("entry_rank")
    val entryRank: Int,
    @SerialName("has_cup")
    val hasCup: Boolean,
    val id: Int,
    @SerialName("league_type")
    val leagueType: String,
    @SerialName("max_entries")
    val maxEntries: Int?,
    val name: String,
    val rank: Int?,
    @SerialName("rank_count")
    val rankCount: Int?,
    val scoring: String,
    @SerialName("short_name")
    val shortName: String?,
    @SerialName("start_event")
    val startEvent: Int
)