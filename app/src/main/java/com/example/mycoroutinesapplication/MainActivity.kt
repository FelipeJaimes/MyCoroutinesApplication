package com.example.mycoroutinesapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mycoroutinesapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // Binding class for activity_main.xml
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        //Progress bar visibility is setup with dataBinding for demo purposes
        with(binding){
            viewModel?.loginResult?.observe(this@MainActivity, Observer { loginResult ->
                toast(loginResult)
            })

            submitButton.setOnClickListener {
                //Here we still use viewBinding
                viewModel?.onSubmitClicked(userEditText.editText?.text.toString(), passwordEditText.editText?.text.toString())
                hideKeyboard()
            }
        }
    }

    private fun Context.toast(message: String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    private fun hideKeyboard(){
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}