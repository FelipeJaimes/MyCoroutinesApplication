package com.example.mycoroutinesapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var vm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vm = ViewModelProvider(this)[MainViewModel::class.java]

        vm.loginResult.observe(this, Observer { success ->
            toast(if (success) "Success" else "Failure")
        })
        vm.progressBarVisible.observe(this, Observer { visible ->
            progressWidget.visibility = if (visible) View.VISIBLE else View.GONE
        })

        submitButton.setOnClickListener {
                vm.onSubmitClicked(userEditText.text.toString(), passwordEditText.text.toString())
            this.currentFocus?.let { view ->
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }
    }

    private fun Context.toast(message: String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

}