package com.fawry.sdkdemo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fawry.fawrypay.FawrySdk
import com.fawry.fawrypay.interfaces.FawryPreLaunch
import com.fawry.fawrypay.interfaces.FawrySdkCallbacks
import com.fawry.fawrypay.models.*
import com.fawry.fawrypay.utils.AppConstants
import com.fawry.fawrypay.utils.FawryUtils
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {
    //Replace this with Testing/Staging/Production URL
    var baseUrl = "https://atfawry.fawrystaging.com/"

    //customer info
    var customerName = "testName"
    var customerMobile = ""
    var customerEmail =
        "test@test.com" //required in saving cards for payment with card tokenization
    var customerProfileId = "7117" //required in saving cards for payment with card tokenization

    //merchant info
    var merchantCode = "+/IAAY2notgLsdUB9VeTFg=="
    var merchantSecretCode = "69826c87-963d-47b7-8beb-869f7461fd93"

    val chargeItems = ArrayList<PayableItem>()
    val billItem = BillItems(
        itemId = "testId",
        description = "",
        quantity = "1",
        price = "10.00"
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        chargeItems.add(billItem)
        val btnPayment = findViewById<Button>(R.id.btn_payment)
        val btnManageCards = findViewById<Button>(R.id.btn_manage_cards)
        btnPayment.setOnClickListener {
            startPayment()
        }

        btnManageCards.setOnClickListener {
            manageCards()
        }
    }

    private fun startPayment() {
        FawrySdk.launchAnonymousSDK(
            this, FawrySdk.Languages.ENGLISH, baseUrl,
            FawryLaunchModel(
                launchCustomerModel = LaunchCustomerModel(
                    customerName = customerName,
                    customerEmail = null,
                    customerMobile = null,
                    customerProfileId = customerProfileId
                ),
                launchMerchantModel = LaunchMerchantModel(
                    merchantCode = merchantCode,
                    secretCode = merchantSecretCode,
                    merchantRefNum = "${System.currentTimeMillis()}"
                ),
                allow3DPayment = true,
                chargeItems = chargeItems,
                skipReceipt = false,
                skipLogin = true,
                payWithCardToken = true,
                authCaptureMode = false,
                allowVoucher = false,
                signature = null,
                paymentMethods = FawrySdk.PaymentMethods.ALL
            ),
            object : FawrySdkCallbacks {
                override fun onPreLaunch(onPreLaunch: FawryPreLaunch) {
                    onPreLaunch.onContinue()
                }

                override fun onInit() {

                }

                override fun onSuccess(msg: String, data: Any?) {
                    Log.d("SDK_Team","$data")

                    var gson = Gson()
                    var parseResponse = gson.fromJson(data.toString(), CreatePayRefNoResponse::class.java)


                    Toast.makeText(this@MainActivity, "on success ${msg}", Toast.LENGTH_LONG)
                        .show()
                }

                override fun onPaymentCompleted(msg: String, data: Any?) {
                    Log.d("SDK_Team","$data")

                    var gson = Gson()
                    var parseResponse = gson.fromJson(data.toString(), CreatePayRefNoResponse::class.java)

                    Toast.makeText(
                        this@MainActivity,
                        "on payment completed $data",
                        Toast.LENGTH_LONG
                    ).show()
                }

                override fun onFailure(error: String) {
                    Log.d("SDK_Team","$error")
                    Toast.makeText(
                        this@MainActivity,
                        "on failure ${error}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    private fun manageCards() {
        FawrySdk.launchCardManager(
            this,
            FawrySdk.Languages.ENGLISH,
            baseUrl,
            FawryLaunchModel(
                LaunchCustomerModel(
                    customerName = customerName,
                    customerEmail = customerEmail,
                    customerMobile = customerMobile,
                    customerProfileId = customerProfileId
                ),
                LaunchMerchantModel(
                    merchantCode = merchantCode,
                    secretCode = merchantSecretCode
                )
            ), object : FawrySdkCallbacks {
                override fun onPreLaunch(onPreLaunch: FawryPreLaunch) {
                    onPreLaunch.onContinue()

                }

                override fun onInit() {  }

                override fun onPaymentCompleted(msg: String, data: Any?) {
                    //won't be called in this flow
                }

                override fun onSuccess(msg: String, data: Any?) {
                    //won't be called in this flow
                }

                override fun onFailure(error: String) {
                    Toast.makeText(this@MainActivity, error, Toast.LENGTH_SHORT).show()
                }
            })
    }
}