package dev.simecek.routines.state

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import dev.simecek.routines.database.entity.User.Companion.ANONYMOUS_USERNAME
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StateManager @Inject constructor(@ApplicationContext val context: Context) {

    companion object {
        const val STATE_STORE = "settings"
        val SIGNED_IN_USER = stringPreferencesKey("SIGNED_IN_USER")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(STATE_STORE)

    /*
     DOC: https://developer.android.com/topic/libraries/architecture/datastore#synchronous
     This way, DataStore asynchronously reads the data and caches it in memory.
     Later synchronous reads using runBlocking() may be faster or may avoid a disk
     I/O operation altogether if the initial read has completed
    */
    suspend fun cache(): Preferences {
        return context.dataStore.data.first()
    }

    suspend fun signIn(username: String) {
        context.dataStore.edit {
            it[SIGNED_IN_USER] = username
        }
    }

    suspend fun signOut() {
        context.dataStore.edit {
            it[SIGNED_IN_USER] = ANONYMOUS_USERNAME
        }
    }

    fun getSignedInUser():String {
        return runBlocking {
            context.dataStore.data.map {
                it[SIGNED_IN_USER] ?: ANONYMOUS_USERNAME
            }.first()
        }
    }

}
