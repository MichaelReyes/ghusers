package com.technologies.ghusers.core.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.technologies.ghusers.core.data.database.AppDatabase

@Entity(tableName = AppDatabase.USERS)
data class User(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @SerializedName("avatar_url")
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String,
    @SerializedName("events_url")
    @ColumnInfo(name = "events_url")
    val eventsUrl: String,
    @SerializedName("followers_url")
    @ColumnInfo(name = "followers_url")
    val followersUrl: String,
    @SerializedName("following_url")
    @ColumnInfo(name = "following_url")
    val followingUrl: String,
    @SerializedName("gists_url")
    @ColumnInfo(name = "gists_url")
    val gistsUrl: String,
    @SerializedName("gravatar_id")
    @ColumnInfo(name = "gravatar_id")
    val gravatarId: String,
    @SerializedName("html_url")
    @ColumnInfo(name = "html_url")
    val htmlUrl: String,
    @ColumnInfo(name = "login")
    val login: String,
    @SerializedName("node_id")
    @ColumnInfo(name = "node_id")
    val nodeId: String,
    @SerializedName("organizations_url")
    @ColumnInfo(name = "organizations_url")
    val organizationsUrl: String,
    @SerializedName("received_events_url")
    @ColumnInfo(name = "received_events_url")
    val receivedEventsUrl: String,
    @SerializedName("repos_url")
    @ColumnInfo(name = "repos_url")
    val reposUrl: String,
    @SerializedName("site_admin")
    @ColumnInfo(name = "site_admin")
    val siteAdmin: Boolean,
    @SerializedName("starred_url")
    @ColumnInfo(name = "starred_url")
    val starredUrl: String,
    @SerializedName("subscriptions_url")
    @ColumnInfo(name = "subscriptions_url")
    val subscriptionsUrl: String,
    @ColumnInfo(name = "type")
    val type: String,
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "company")
    val company: String?,
    @ColumnInfo(name = "blog")
    val blog: String?,
    @ColumnInfo(name = "followers")
    val followers: Int?,
    @ColumnInfo(name = "following")
    val following: Int?,
    @ColumnInfo(name = "notes")
    var notes: String?,
) {
    @Ignore
    var identifier: Int = 0

    @Ignore
    var hasNotes: Boolean = false
}