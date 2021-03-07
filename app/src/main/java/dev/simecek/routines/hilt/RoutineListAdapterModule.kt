package dev.simecek.routines.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dev.simecek.routines.list.RoutineListAdapter

@Module
@InstallIn(ActivityComponent::class)
class RoutineListAdapterModule {

    @Provides
    fun providesRoutineListAdapter(): RoutineListAdapter {
        return RoutineListAdapter()
    }

}