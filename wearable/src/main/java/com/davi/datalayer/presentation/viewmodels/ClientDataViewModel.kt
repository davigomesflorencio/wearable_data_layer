package com.davi.datalayer.presentation.viewmodels

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.annotation.StringRes
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.davi.datalayer.R
import com.davi.datalayer.services.DataLayerListenerService
import com.google.android.gms.wearable.Asset
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.CapabilityInfo
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.Wearable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ClientDataViewModel(application: Application) : AndroidViewModel(application),
    DataClient.OnDataChangedListener,
    MessageClient.OnMessageReceivedListener,
    CapabilityClient.OnCapabilityChangedListener {

    private val _events = mutableStateListOf<Event>()

    /**
     * The list of events from the clients.
     */
    val events: List<Event> = _events

    /**
     * The currently received image (if any), available to display.
     */
    var image by mutableStateOf<Bitmap?>(null)
        private set

    private var loadPhotoJob: Job = Job().apply { complete() }


    override fun onDataChanged(p0: DataEventBuffer) {
// Add all events to the event log
        _events.addAll(
            p0.map { dataEvent ->
                val title = when (dataEvent.type) {
                    DataEvent.TYPE_CHANGED -> R.string.data_item_changed
                    DataEvent.TYPE_DELETED -> R.string.data_item_deleted
                    else -> R.string.data_item_unknown
                }

                Event(
                    title = title,
                    text = dataEvent.dataItem.toString()
                )
            }
        )

        // Do additional work for specific events
        p0.forEach { dataEvent ->
            when (dataEvent.type) {
                DataEvent.TYPE_CHANGED -> {
                    when (dataEvent.dataItem.uri.path) {
                        DataLayerListenerService.IMAGE_PATH -> {
                            loadPhotoJob.cancel()
                            loadPhotoJob = viewModelScope.launch {
                                image = loadBitmap(
                                    DataMapItem.fromDataItem(dataEvent.dataItem)
                                        .dataMap
                                        .getAsset(DataLayerListenerService.IMAGE_KEY)
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onMessageReceived(p0: MessageEvent) {
        _events.add(
            Event(
                title = R.string.message,
                text = p0.toString()
            )
        )
    }

    override fun onCapabilityChanged(p0: CapabilityInfo) {
        _events.add(
            Event(
                title = R.string.capability_changed,
                text = p0.toString()
            )
        )
    }

    private suspend fun loadBitmap(asset: Asset?): Bitmap? {
        if (asset == null) return null
        val response =
            Wearable.getDataClient(getApplication<Application>()).getFdForAsset(asset).await()
        return response.inputStream.use { inputStream ->
            withContext(Dispatchers.IO) {
                BitmapFactory.decodeStream(inputStream)
            }
        }
    }

}

/**
 * A data holder describing a client event.
 */
data class Event(
    @StringRes val title: Int,
    val text: String
)