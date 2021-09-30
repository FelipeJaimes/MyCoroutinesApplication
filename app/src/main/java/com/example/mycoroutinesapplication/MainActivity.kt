package com.example.mycoroutinesapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        submitButton.setOnClickListener {
            /**
             * GlobalScope esta ligado al ciclo de vida de la aplicacion
             * si la aplicacion se destruye antes de que la corrutina termine
             * lanza una excepcion. Por esa razón se aconseja NO usarlo para crear coroutines.
             * Si se cierra el Activity la coroutine seguirá en ejecución,
             * y seguirá en ejecución hasta que se cierre la aplicación.
             *
             * El Dispatcher es el hilo donde queremos que se ejecute la tarea
             * de la corrutina:
             * -Main, para el hilo principal
             * -IO, para tareas de entrada y salida.
             * -Default, para tareas de alta carga en CPU.
             * -Unconfined, para ciertas tareas de testing.
             *
             * Al agregar CoroutineScope y setear el coroutineContext ya no es necesario colocar
             * GlobalScope.launch (Dispatchers.Main)
             */
            lifecycleScope.launch {
                val success1 = async(Dispatchers.IO){
                    validateLogin1(userEditText.text.toString(), passwordEditText.text.toString())
                }
                val success2 = async(Dispatchers.IO){
                    validateLogin2(userEditText.text.toString(), passwordEditText.text.toString())
                }
                toast(if (success1.await() && success2.await()) "Success" else "Failure")
            }
        }
    }


    private fun validateLogin1(username: String, password: String): Boolean {
        Thread.sleep(2000)
        return username.isNotEmpty() && password.isNotEmpty()
    }

    private fun validateLogin2(username: String, password: String): Boolean {
        Thread.sleep(2000)
        return username.isNotEmpty() && password.isNotEmpty()
    }

    private fun Context.toast(message: String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

}