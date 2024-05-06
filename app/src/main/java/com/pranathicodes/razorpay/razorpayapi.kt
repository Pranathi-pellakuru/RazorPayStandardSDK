package com.pranathicodes.razorpay

import com.pranathicodes.razorpay.model.OrderRequest
import com.pranathicodes.razorpay.model.OrderResponse
import okhttp3.Credentials
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface Razorpayapi {

    @POST("orders")
    suspend fun getOrderId( @Header("Authorization") header : String = Credentials.basic(Constants.RAZORPAY_KEY,Constants.RAZORPAY_SECRET),@Body orderBody : OrderRequest):Response<OrderResponse>
}