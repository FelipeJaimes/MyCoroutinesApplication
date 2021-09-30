package com.example.mycoroutinesapplication

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job : Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /**
         * La diferencia entre Job() y SupervisorJob(), es que si en el Job(), una
         * de las corrutinas lanza una excepcion, todas las corrutinas se cancelan.
         * Mientras que con el SupervisorJob() esto no sucede, y las otras corrutinas
         * siguen ejecutandoce.
         */
        job = SupervisorJob()

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
            launch {
                val success = withContext(Dispatchers.IO){
                    validateLogin(userEditText.text.toString(), passwordEditText.text.toString())
                }
                toast(if (success) "Success" else "Failure")
            }
        }
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    private fun validateLogin(username: String, password: String): Boolean {
        Thread.sleep(2000)
        return username.isNotEmpty() && password.isNotEmpty()
    }

    private fun Context.toast(message: String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

}