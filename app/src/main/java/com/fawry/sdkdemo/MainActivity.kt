package com.fawry.sdkdemo
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fawry.fawrypay.domain.models.BillItems
import com.fawry.fawrypay.domain.models.ChargeItemAccount
import com.fawry.fawrypay.domain.models.CreatePayRefNoResponse
import com.fawry.fawrypay.domain.models.FawryLaunchModel
import com.fawry.fawrypay.domain.models.FawryPayError
import com.fawry.fawrypay.domain.models.LaunchCustomerModel
import com.fawry.fawrypay.domain.models.LaunchMerchantModel
import com.fawry.fawrypay.utils.CardManagerCallbacks
import com.fawry.fawrypay.utils.FawrySdkCallbacks
import com.fawry.fawrypay.utils.fawrySdk.LaunchFawrySdk
import com.fawry.fawrypay.utils.fawrySdk.enums.Languages
import com.fawry.fawrypay.utils.fawrySdk.enums.PaymentMethods
import com.fawry.fawrypay.utils.fawrySdk.enums.PaymentStatus
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {
    //Replace this with Testing/Staging/Production URL
    var baseUrl = "https://atfawry.fawrystaging.com/"

    //customer info
    var customerName = "testName"
    var customerMobile = "01234567899"
    var customerEmail =
        "test@test.com" //required in saving cards for payment with card tokenization
    var customerProfileId = "7117" //required in saving cards for payment with card tokenization

    //merchant info
    var merchantCode = "770000020169"
    var merchantSecretCode = "57e05132-63c3-41f6-83ed-164640d5e98d"

    val chargeItems = ArrayList<BillItems>()
    val accounts = arrayListOf(
                ChargeItemAccount(
                    accountCode= "770000017819",
                amount= 500.00
            ),
                ChargeItemAccount(
                    accountCode= "770000017942",
                    amount= 500.00
                ))
    val billItem = BillItems(
        itemId = "testId",
        description = "",
        quantity = "1",
        price = "10.00",
//        chargeItemAccounts = accounts //use it for split payment flow
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
        LaunchFawrySdk.launchAnonymousSDK(
            activity = this,
            _languages = Languages.ENGLISH,
            _baseUrl = baseUrl,
            _fawryLaunchModel = FawryLaunchModel(
                launchCustomerModel = LaunchCustomerModel(
                    customerName = customerName,
                    customerEmail = customerEmail,
                    customerMobile = customerMobile,
                    customerProfileId = customerProfileId
                ),
                launchMerchantModel = LaunchMerchantModel(
                    merchantCode = merchantCode,
                    secretCode = merchantSecretCode,
                    merchantRefNum = "${System.currentTimeMillis()}"
                ),
                allowVoucher = true,
                allow3DPayment = true,
                chargeItems = chargeItems,
                skipReceipt = false,
                payWithCardToken = true,
                paymentMethods = PaymentMethods.ALL,
                authCaptureMode = false,
            ),
            _callback = object : FawrySdkCallbacks {
                override fun onSuccess(
                    paymentStatus: PaymentStatus,
                    data: CreatePayRefNoResponse?
                ) {
                    Toast.makeText(this@MainActivity, "on success ${paymentStatus}", Toast.LENGTH_SHORT)
                        .show()
                    Log.d("SDKTeam", "onSuccess data: ${data}")
                    Log.d("SDKTeam", "onSuccess paymentStatus: ${paymentStatus}")
                }

                override fun onPaymentCompleted(
                    paymentStatus: PaymentStatus,
                    data: CreatePayRefNoResponse?,
                    error: FawryPayError?
                ) {
                    Toast.makeText(this@MainActivity, "onPaymentCompleted ${paymentStatus}", Toast.LENGTH_SHORT)
                        .show()
                    Log.d("SDKTeam", "onPaymentCompleted data: ${data}")
                    Log.d("SDKTeam", "onPaymentCompleted paymentStatus: ${paymentStatus}")
                    Log.d("SDKTeam", "onPaymentCompleted error: ${error}")
                }

                override fun onFailure(error: FawryPayError) {
                    Log.d("SDKTeam",  "onFailure error: ${error}")
                }
            })
    }

    private fun manageCards() {
        LaunchFawrySdk.launchCardManagerFlow(
            activity = this,
            _languages = Languages.ENGLISH,
            _baseUrl = baseUrl,
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
            ), _callback = object : CardManagerCallbacks {

                override fun onSuccess(message: String) {
                    Log.d("SDKTeam", "onSuccess message: ${message}")
                }

                override fun onFailure(error: String) {
                    Log.d("SDKTeam",  "onFailure error: ${error}")
                }
            })
    }
}