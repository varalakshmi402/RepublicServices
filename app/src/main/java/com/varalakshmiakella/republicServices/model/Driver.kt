package com.varalakshmiakella.republicServices.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "driver")
data class Driver(
   @PrimaryKey(autoGenerate = true)
   val generatedID:Int?=null,
    val id: String,
    val name: String
):Serializable{
override fun hashCode(): Int {
    var result = id.hashCode()
    if(generatedID == null) {
        result += generatedID.hashCode()
    }
    return result
}
}