package dev.simecek.routines.listener

import dev.simecek.routines.database.entity.Routine

interface FinishRoutineListener {
    fun onFinishRoutine(routine: Routine)
}