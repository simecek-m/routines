package dev.simecek.routines.listener

import dev.simecek.routines.database.entity.Routine

interface ClickRoutineListener {
    fun onClick(routine: Routine)
}