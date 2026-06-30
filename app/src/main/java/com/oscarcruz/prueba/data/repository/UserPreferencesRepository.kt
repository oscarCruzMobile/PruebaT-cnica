package com.oscarcruz.prueba.data.repository
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesRepository @Inject constructor(
    private val dataStore: DataStore<Preferences> // Hilt inyecta el DataStore que creamos arriba
) {

    // ── Escritura Genérica ───────────────────────────────────────────────────

    suspend fun saveString(key: String, value: String) {
        dataStore.edit { it[stringPreferencesKey(key)] = value }
    }

    suspend fun saveInt(key: String, value: Int) {
        dataStore.edit { it[intPreferencesKey(key)] = value }
    }

    suspend fun saveBoolean(key: String, value: Boolean) {
        dataStore.edit { it[booleanPreferencesKey(key)] = value }
    }

    // ── Lectura Reactiva (Flow) ──────────────────────────────────────────────

    fun getString(key: String, default: String = ""): Flow<String> =
        dataStore.data.map { it[stringPreferencesKey(key)] ?: default }
            .catch { emit(default) }

    fun getBoolean(key: String, default: Boolean = false): Flow<Boolean> =
        dataStore.data.map { it[booleanPreferencesKey(key)] ?: default }
            .catch { emit(default) }

    // ── Lectura Síncrona ─────────────────────────────────────────────────────

    suspend fun getStringSync(key: String, default: String = ""): String =
        getString(key, default).first()

    // ── Utilidades ──────────────────────────────────────────────────────────

    suspend fun remove(key: String) {
        dataStore.edit { prefs ->
            prefs.remove(stringPreferencesKey(key))
            prefs.remove(intPreferencesKey(key))
            prefs.remove(booleanPreferencesKey(key))
        }
    }

    suspend fun clearAll() {
        dataStore.edit { it.clear() }
    }
}