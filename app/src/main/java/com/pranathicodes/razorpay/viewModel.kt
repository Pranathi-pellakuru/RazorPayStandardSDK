package com.pranathicodes.razorpay

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pranathicodes.razorpay.model.OrderRequest
import com.pranathicodes.razorpay.model.OrderResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel: ViewModel() {
    private  val repository: RazorpayRepository = RazorpayRepository
    var orderResponse : MutableLiveData<OrderResponse> = MutableLiveData()

    fun getOrderResponse(orderBody : OrderRequest){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                orderResponse.postValue(repository.getOrderId(orderBody))
            }
        }
    }
}