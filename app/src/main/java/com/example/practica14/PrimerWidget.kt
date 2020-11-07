package com.example.practica14

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.util.*

class PrimerWidget: AppWidgetProvider() {
    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
        // Actualizar widget

        println("Actualizando")
        // Iteramos la lista de widgets en ejecucion
        for( i in appWidgetIds!!.indices){
            // Id del widget actual
            val widgetId = appWidgetIds[i]

            actualizarWidget(context, appWidgetManager, widgetId)
        }
    }

    companion object{
        fun actualizarWidget(
            context: Context?,
            appWidgetManager: AppWidgetManager?,
            widgetId: Int
        ){
            Log.d(null, "Actualiza")
            // Recuperamos el mensaje personalizado para el widget actual
            val prefs = context?.getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE)
            val mensaje = prefs?.getString("msg_$widgetId", "Hora actual: ")

            val rnd = Random()
            val color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))

            //Obtenemos la lista de controles del widget actual
            val controles = RemoteViews(context?.packageName, R.layout.primer_widget)

            //Actualizamos el mensaje en el control del widget
            controles.setTextViewText(R.id.lblMensaje, mensaje)
            controles.setInt(R.id.frmWidgetMarco, "setBackgroundColor", color)
            //Obtenemos la hora actual
            val date = Calendar.getInstance().time
            val formatter = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault())
            val hora = formatter.format(date)

            //Actualizamos la hora en el widget
            controles.setTextViewText(R.id.lblHora, hora)

            //Notificamos al manager de la actualizacion del widget actual
            appWidgetManager?.updateAppWidget(widgetId, controles)
        }
    }
}