package com.pranathicodes.razorpay

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.pranathicodes.razorpay.model.OrderRequest
import com.pranathicodes.razorpay.ui.theme.RazorPayTheme


class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // apart from setting it in AndroidManifest.xml, keyId can also be set
        // programmatically during runtime

        setContent {
            RazorPayTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(viewModel)
                }
            }
        }
    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var amount by remember { mutableStateOf("") }
    val orderResponse by viewModel.orderResponse.observeAsState()

    orderResponse?.let {
        keyboardController?.hide()
        val intent = Intent(
            context,
            PaymentActivity::class.java
        )
        intent.putExtra("amount",amount)
        intent.putExtra("orderId", it.id)
        context.startActivity(intent)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(value = amount, onValueChange = {
            amount = it
        }, modifier = Modifier.fillMaxWidth(0.9f))

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            if(amount.isNotEmpty()){
                viewModel.getOrderResponse(
                    OrderRequest(
                        amount = amount.toInt() * 100,
                        currency = "INR",
                        receipt = ""
                    )
                )
            }else{
                Toast.makeText(context,"enter the amount",Toast.LENGTH_SHORT).show()
            }
        }) { Text(text = "Checkout", modifier = Modifier.wrapContentWidth()) }
    }

}