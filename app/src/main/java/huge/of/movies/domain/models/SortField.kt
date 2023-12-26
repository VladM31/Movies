package huge.of.movies.domain.models

enum class SortField {
    TITLE, RELEASED_DATE;

    fun toTitleText(): String {
        return when (this) {
            TITLE -> "Title"
            RELEASED_DATE -> "Released Date"
        }
    }
}