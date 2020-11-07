package com.example.practica14

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class WidgetConfig: Activity() {
    private var widgetId = 0

    private lateinit var txtMensaje: EditText
    private lateinit var btnAceptar: Button
    private lateinit var btnCancelar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.widget_config)

        // Obtenemos la refernecia a los controles de la pantalla
        txtMensaje = findViewById(R.id.txtMensaje)
        btnAceptar = findViewById(R.id.btnAceptar)
        btnCancelar = findViewById(R.id.btnCancelar)

        //Obtenemos el intent que ha lanzado esta ventana
        //y recuperamos sus parametros
        val intentOrigen = intent
        val params = intentOrigen.extras

        widgetId = params!!.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        )


        // implementacion del boton 'Cancelar'
        btnCancelar.setOnClickListener {
            finish()
        }

        // implementacion del boton 'Aceptar'
        btnAceptar.setOnClickListener {
            // Guardamos el mensaje personalizado en las preferencias
            val prefs = getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("msg_$widgetId", txtMensaje.text.toString())
            editor.commit()

            //Actualizamos el widget tras la configuracion
            val appWidgetManager = AppWidgetManager.getInstance(this@WidgetConfig)
            PrimerWidget.actualizarWidget(this, appWidgetManager, widgetId)

            // Devolvemos el resultado: ACEPTAR (RESULT_OK)
            val resultado = Intent()
            resultado.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId)
            setResult(RESULT_OK, resultado)
            finish()

        }

        // Establecemos el resultado por defecto (si se pulsa el boton 'Atras'
        // del telefono sera este el resultado devuelto)
        setResult(RESULT_CANCELED)
    }
}