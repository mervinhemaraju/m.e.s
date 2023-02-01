package com.th3pl4gu3.mauritius_emergency_services.models

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Keep
@Serializable
@Entity(tableName = "service_table")
data class Service(
    @PrimaryKey(autoGenerate = false) @ColumnInfo(name = "identifier")  val identifier: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "icon") val icon: String,
    @ColumnInfo(name = "number") val number: Int
)