package dev.simecek.routines.hilt

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.simecek.routines.list.RoutineListAdapter

@Module
@InstallIn(ActivityComponent::class)
class RoutineListAdapterModule {

    @Provides
    fun providesRoutineListAdapter(@ApplicationContext context: Context): RoutineListAdapter {
        return RoutineListAdapter(context)
    }

}