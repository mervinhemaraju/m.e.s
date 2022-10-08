package com.th3pl4gu3.mes.api

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "service_table")
data class Service(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "identifier") val identifier: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "icon") val icon: String,
    @ColumnInfo(name = "number") val number: Long
) : Parcelable