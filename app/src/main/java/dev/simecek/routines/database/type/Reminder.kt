package dev.simecek.routines.database.type

class Reminder(val hour: Int = 12, val minute: Int = 0) {

    @Override
    override fun toString(): String {
        return "$hour:${formatMinutes()}"
    }

    private fun formatMinutes():String {
        return if(minute < 10) "0$minute" else minute.toString()
    }
}