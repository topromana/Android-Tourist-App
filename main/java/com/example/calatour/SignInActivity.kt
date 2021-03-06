package com.example.calatour

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.calatour.Model.UserInfoSingleton
import com.example.calatour.Model.chatAPI.AuthenticationRequest
import com.example.calatour.Model.chatAPI.AuthenticationResponse
import com.example.calatour.Model.chatAPI.ErrorDetails
import com.example.calatour.rest_api.ChatAPI
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

class SignInActivity : AppCompatActivity(), View.OnClickListener {

    val chatAPI = ChatAPI.create()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val signInButton = findViewById<Button>(R.id.signInButton)
        signInButton.setOnClickListener(this)

        val globalLogoutButton = findViewById<Button>(R.id.globalLogoutButton)
        globalLogoutButton.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {

        val username = findViewById<EditText>(R.id.usernameEditText).text.toString()
        val errorUsername = findViewById(R.id.usernameErrorTextView) as TextView
        val password = findViewById<EditText>(R.id.passwordEditText).text.toString()
        val errorPassword = findViewById(R.id.passwordErrorTextView) as TextView
        val loginMessage = findViewById(R.id.loginMessageTextView) as TextView
        var okUser = 0
        var okPass = 0
        if(p0!!.id ==R.id.globalLogoutButton){
            chatAPI.globalLogout(  AuthenticationRequest(username,password) ).enqueue(object: retrofit2.Callback<Void>{
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(applicationContext,"No internet connection",Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                  if(response.isSuccessful){
                      Toast.makeText(applicationContext,"global logout succesful",Toast.LENGTH_SHORT).show()
                  }
                    else{
                      Toast.makeText(applicationContext,"global logout error",Toast.LENGTH_SHORT).show()
                  }

                }
            })
        }

        else {

            chatAPI.authenticate(AuthenticationRequest(username, password))
                .enqueue(object : retrofit2.Callback<AuthenticationResponse> {
                    override fun onFailure(call: Call<AuthenticationResponse>, t: Throwable) {
                        val errorTextView = findViewById<TextView>(R.id.globalErrorTextView)
                        errorTextView.text = "please check your internet connection"
                        errorTextView.setTextColor(getColor(R.color.myRed))
                    }

                    override fun onResponse(
                        call: Call<AuthenticationResponse>,
                        response: Response<AuthenticationResponse>
                    ) {
                        if (response.isSuccessful) {
                            UserInfoSingleton.userId=response.body()!!.id
                            UserInfoSingleton.token=response.body()!!.token
                            UserInfoSingleton.displayName=response.body()!!.display
                            loginMessage.setText("login succesful")
                            val intent = Intent(this@SignInActivity, OffersActivity::class.java)
                            startActivity(intent)
                        } else {
                            var errorDetails = ErrorDetails("No details available")
                            try {
                                if (response.errorBody() != null) {
                                    val rawErrorDetails = response.errorBody()!!.string()
                                    val parser = Gson()
                                    errorDetails =
                                        parser.fromJson(rawErrorDetails, ErrorDetails::class.java)
                                }
                            } catch (e: Exception) {
                                errorDetails = ErrorDetails("cannot retrieve error details")
                            }
                            val errorTextView = findViewById<TextView>(R.id.globalErrorTextView)
                            errorTextView.text = errorDetails.message
                            errorTextView.setTextColor(getColor(R.color.myRed))
                        }
                    }

                })
        }

    }
}