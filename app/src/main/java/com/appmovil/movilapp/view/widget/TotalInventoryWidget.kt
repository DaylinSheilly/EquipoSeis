package com.appmovil.movilapp.view.widget

import android.app.Application
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import com.appmovil.movilapp.R
import com.appmovil.movilapp.repository.ArticuloRepository
import com.appmovil.movilapp.view.HomeActivity
import com.appmovil.movilapp.view.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject


@AndroidEntryPoint
class TotalInventoryWidget: AppWidgetProvider() {

    @Inject
    lateinit var articuloRepository: ArticuloRepository;

    private val PREFS_NAME = "WidgetPrefs"
    private val PREFS_LOGIN = "shared"
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, articuloRepository)
        }
    }

    private fun sesion(context: Context): Boolean{
        val loginPrefs = context?.getSharedPreferences(PREFS_LOGIN, Context.MODE_PRIVATE)
        val email = loginPrefs?.getString("email",null)
        Log.i("widget", "sesion $email")
        return email != null
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        val prefs = context?.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val totalVisible = prefs?.getBoolean("totalVisible", false)
        if (intent?.action == "TOGGLE_VISIBILITY") {
            Log.i("widget", "ojo pulsado")

//false es porque esta cerrado el ojo, osea que al presionarlo se quiere ocultar el contenido, si es true esta abierto, osea que al presionarlo se quiere mostrar el contenido
            if(totalVisible == true){
                Log.i("widget", "ojo pulsado true")

                if(sesion(context)){
                        Log.i("widget", "sesion valida")

                        val editor = prefs?.edit()
                        if (totalVisible != null) {
                            editor?.putBoolean("totalVisible", !totalVisible)
                        }
                        editor?.apply()

                        AppWidgetManager.getInstance(context).let { appWidgetManager ->
                            val thisWidget = ComponentName(context!!, TotalInventoryWidget::class.java)
                            val appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget)
                            for (appWidgetId in appWidgetIds) {
                                updateAppWidget(context, appWidgetManager, appWidgetId, articuloRepository)
                            }
                        }
                    }else{
                    Log.i("widget", "sesion invalida")

                        val loginIntent = Intent(context, LoginActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            putExtra("OnLoginRedirectToWidget", true)
                        }
                        context.startActivity(loginIntent)
                    }
                }else{
                Log.i("widget", "ojo pulsado false")

                val editor = prefs?.edit()
                if (totalVisible != null) {
                    editor?.putBoolean("totalVisible", !totalVisible)
                }
                editor?.apply()

                AppWidgetManager.getInstance(context).let { appWidgetManager ->
                    val thisWidget = ComponentName(context!!, TotalInventoryWidget::class.java)
                    val appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget)
                    for (appWidgetId in appWidgetIds) {
                        updateAppWidget(context, appWidgetManager, appWidgetId, articuloRepository)
                    }
                }
            }
            }else if (intent?.action == "GO_INVENTORY") {
            Log.i("widget", "GO INVENTORY")
                if(sesion(context!!)){
                    Log.i("widget", "GO INVENTORY TRU")

                    val inventoryIntent = Intent(context, HomeActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                    context.startActivity(inventoryIntent)
                 }else{
                    Log.i("widget", "GO INVENTORY FOLS")
                    val loginIntent = Intent(context, LoginActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        putExtra("OnLoginRedirectToIventory", true)
                    }
                    context.startActivity(loginIntent)
                }
        }else if (intent?.action == "UPDATE_WIDGET") {
            Log.i("widget", "update")
            AppWidgetManager.getInstance(context).let { appWidgetManager ->
                val thisWidget = ComponentName(context!!, TotalInventoryWidget::class.java)
                val appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget)
                for (appWidgetId in appWidgetIds) {
                    updateAppWidget(context, appWidgetManager, appWidgetId, articuloRepository)
                }
            }
        }
    }

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    articuloRepository: ArticuloRepository
) {
    val prefs = context.getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE)
    val totalVisible = prefs.getBoolean("totalVisible", false)
    val imageResource = if (totalVisible) R.drawable.eye_open else R.drawable.eye_close

    val views = RemoteViews(context.packageName, R.layout.total_inventory_widget)
    views.setImageViewResource(R.id.totalVisibility, imageResource)

    val intent = Intent(context, TotalInventoryWidget::class.java)
    intent.action = "TOGGLE_VISIBILITY"

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    views.setOnClickPendingIntent(R.id.totalVisibility, pendingIntent)

    val intentGoInventory = Intent(context, TotalInventoryWidget::class.java)
    intentGoInventory.action = "GO_INVENTORY"

    val pendingIntentGoInventory = PendingIntent.getBroadcast(
        context,
        1,
        intentGoInventory,
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    views.setOnClickPendingIntent(R.id.goIventory, pendingIntentGoInventory)

    if (totalVisible) {
        views.setTextViewText(R.id.totalPrice, "$****")
        appWidgetManager.updateAppWidget(appWidgetId, views)
    } else {
        CoroutineScope(Dispatchers.IO).launch {
            val total = articuloRepository.getTotalArticulos()
            val formato = NumberFormat.getNumberInstance(Locale("es", "ES"))
            formato.minimumFractionDigits = 2
            val totalFormateado = formato.format(total)
            CoroutineScope(Dispatchers.Main).launch {
                views.setTextViewText(R.id.totalPrice, "$$totalFormateado")
                appWidgetManager.updateAppWidget(appWidgetId, views)
            }
        }
    }
}

}