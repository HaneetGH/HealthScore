package com.technorapper.onboarding.proto

import androidx.datastore.core.DataStore
import com.technorapper.onboarding.UserStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class ProtoUserRepoImpl(private val protoDataStore: DataStore<UserStore>) : ProtoUserRepo {
    override suspend fun saveUserID(state: String) {
        protoDataStore.updateData { store ->
            store.toBuilder()
                .setUserId(state)
                .build()
        }
    }

    override suspend fun getUserID(): Flow<String> {
        return protoDataStore.data.catch {
            emit(UserStore.getDefaultInstance())
        }.map { it.userId }
    }

    override suspend fun saveTokenID(state: String) {
        protoDataStore.updateData { store ->
            store.toBuilder()
                .setSessionTokenId(state)
                .build()
        }
    }

    override suspend fun getTokenID(): Flow<String> {
        return protoDataStore.data.catch {
            emit(UserStore.getDefaultInstance())
        }.map { it.sessionTokenId }
    }
}

