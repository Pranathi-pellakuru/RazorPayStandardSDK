package com.pranathicodes.razorpay

import com.pranathicodes.razorpay.model.OrderRequest
import com.pranathicodes.razorpay.model.OrderResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

object RazorpayRepository {
    private val service : Razorpayapi? = RetrofitApi.retrofitServ

    suspend fun getOrderId(orderRequest: OrderRequest):OrderResponse?{
        return  try{
            var data : Response<OrderResponse>?
            withContext(Dispatchers.IO){
                data = service?.getOrderId(orderBody = orderRequest)
            }
            if(data?.isSuccessful == true){
                data?.body()
            }
            else{
                null
            }
        }
        catch (e : Exception){
            null
        }
    }
}