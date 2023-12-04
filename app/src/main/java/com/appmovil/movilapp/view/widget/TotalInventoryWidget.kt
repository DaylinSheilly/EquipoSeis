package com.appmovil.movilapp.view.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.appmovil.movilapp.R
import com.appmovil.movilapp.repository.ArticuloRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale
import javax.inject.Inject
@AndroidEntryPoint
class TotalInventoryWidget : AppWidgetProvider() {
    @Inject
    lateinit var  articuloRepository: ArticuloRepository
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
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    articuloRepository: ArticuloRepository
) {
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.total_inventory_widget)
    views.setTextViewText(R.id.totalPrice, "$120.000.00")
    appWidgetManager.updateAppWidget(appWidgetId, views)
 /*   CoroutineScope(Dispatchers.IO).launch {
        val total = articuloRepository.getTotalArticulos()
        val formato = NumberFormat.getNumberInstance(Locale("es", "ES"))
        formato.minimumFractionDigits = 2
        val totalFormateado = formato.format(total)
        CoroutineScope(Dispatchers.Main).launch{
            views.setTextViewText(R.id.totalPrice, totalFormateado)
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }*/

}