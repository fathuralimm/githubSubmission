package com.example.mysubmission.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class UserResponse(

    @field:SerializedName("total_count")
    val totalCount: Int,

    @field:SerializedName("incomplete_results")
    val incompleteResults: Boolean,

    @field:SerializedName("items")
    val items: List<ItemsItem>
)

@Parcelize
@Entity(tableName = "users")
data class ItemsItem(

    @ColumnInfo(name = "login")
    @field:SerializedName("login")
    val login: String,

    @ColumnInfo(name = "avatar_url")
    @field:SerializedName("avatar_url")
    val avatarUrl: String,

    @PrimaryKey
    @field:SerializedName("id")
    val id: Int,
) : Parcelable
