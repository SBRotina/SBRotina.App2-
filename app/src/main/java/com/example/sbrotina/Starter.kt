package com.example.sbrotina

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sbrotina.api.Endpoint
import com.example.sbrotina.databinding.ActivityStarterBinding
import com.example.sbrotina.model.LoginModel
import com.example.sbrotina.model.Usuario
import com.example.sbrotina.util.NetworkUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Starter : AppCompatActivity() {

    private val Base_Url = "http://sbrotina.somee.com/"
    private val Tag: String = "CHECK_RESPONSE"

    private lateinit var binding: ActivityStarterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStarterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        window.statusBarColor = Color.parseColor("#30C7AE")

        binding.textnavegartela.setOnClickListener{
            val navegar = Intent(this,Register::class.java)
            startActivity(navegar)
        }

        binding.blogin.setOnClickListener{
            val email = binding.etlogin.text.toString().trim()
            val senha = binding.etPassword.text.toString().trim()

            if (email.isEmpty()){
                binding.etlogin.error = "Email required"
                binding.etlogin.requestFocus()
                return@setOnClickListener
            }

            else if (senha.isEmpty()){
                binding.etPassword.error = "Senha required"
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }

            else {
                login()
            }
        }


    }

    fun login(){
        val retrofitClient = NetworkUtils.getRetrofitInstance("http://sbrotina.somee.com")
        val endpoint = retrofitClient.create(Endpoint::class.java)

        var payload = LoginModel(binding.etlogin.text.toString(), binding.etPassword.text.toString());
        val  callback = endpoint.authenticate(payload);

        callback.enqueue(object : Callback<Usuario>{
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {

                if (response.isSuccessful == true) {
                    Toast.makeText(baseContext,"Deu Bom!!", Toast.LENGTH_LONG).show()
                    val navegar = Intent(this@Starter,Home::class.java)
                    startActivity(navegar)
                }

            else {
                    Toast.makeText(baseContext,"deu ruim!!", Toast.LENGTH_LONG).show()
                    println(response)
            }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {

            }

        })

    }

}