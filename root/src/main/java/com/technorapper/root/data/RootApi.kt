package com.technorapper.root.data


import com.technorapper.root.data.data_model.lablist.LabsListModel
import retrofit2.http.GET
import retrofit2.http.Query

interface RootApi {

    @GET("api/v1/getAllLabs")
    suspend fun getAllLabs(
        @Query("idToken") idToken: String,
        @Query("uid") uid: String
    ): LabsListModel

}