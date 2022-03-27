package com.technorapper.healthscore.proto

import kotlinx.coroutines.flow.Flow

interface ProtoUserRepo {
    suspend fun saveUserID(state:String)
    suspend fun getUserID(): Flow<String>

    suspend fun saveTokenID(state:String)
    suspend fun getTokenID(): Flow<String>
}