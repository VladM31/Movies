package huge.of.movies.databases.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import org.json.JSONArray

@ProvidedTypeConverter
class StringListConverter {

    @TypeConverter
    fun fromStringListToString(stringList: List<String>): String {
        val array = JSONArray()

        stringList.forEach {
            array.put(it)
        }

        return array.toString()
    }

    @TypeConverter
    fun fromStringToStringList(string: String): List<String> {
        val array = JSONArray(string)

        return mutableListOf<String>().apply {
            for (i in 0 until array.length()) {
                add(array.getString(i))
            }
        }
    }
}