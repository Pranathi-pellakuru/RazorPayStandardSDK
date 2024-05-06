package com.pranathicodes.razorpay

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import org.json.JSONObject

class PaymentActivity : ComponentActivity(), PaymentResultListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val amount = intent.getStringExtra("amount")
        Log.d("amount",amount.toString())
        val orderId = intent.getStringExtra("orderId")
        Checkout.preload(applicationContext)
        val co = Checkout()
        co.setKeyID(Constants.RAZORPAY_KEY)
        if (amount != null) {
            val options = JSONObject()
            options.put("name","Pranathi Corp")
            //You can omit the image option to fetch the image from the dashboard
            options.put("currency","INR")
            options.put("order_id", orderId)
            options.put("amount",(amount.toInt() * 100).toString())//pass amount in currency subunits

            val retryObj =JSONObject()
            retryObj.put("enabled", true)
            retryObj.put("max_count", 4)
            options.put("retry", retryObj)
            co.open(this, options)
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "payment success", Toast.LENGTH_SHORT).show()
        this.finish()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "payment failed", Toast.LENGTH_SHORT).show()
        this.finish()
    }

    override fun onDestroy() {
        Checkout.clearUserData(applicationContext)
        super.onDestroy()
    }
}
