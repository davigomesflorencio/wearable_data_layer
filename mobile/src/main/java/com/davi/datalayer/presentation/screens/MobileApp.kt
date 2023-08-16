package com.davi.datalayer.presentation.screens

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davi.datalayer.R
import com.davi.datalayer.domain.model.Event
import com.davi.datalayer.presentation.theme.Pink40
import com.davi.datalayer.presentation.theme.Purple40

/**
 * The UI affording the actions the user can take, along with a list of the events and the image
 * to be sent to the wearable devices.
 */
@Composable
fun MobileApp(
    eventsList: List<Event>,
    image: Bitmap?,
    isCameraSupported: Boolean,
    onTakePhotoClick: () -> Unit,
    onSendPhotoClick: () -> Unit,
    onStartWearableActivityClick: () -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp)
            ) {
                Box(modifier = Modifier.size(200.dp)) {
                    if (image == null) {
                        Image(
                            painterResource(id = R.drawable.ic_content_picture),
                            contentDescription = stringResource(
                                id = R.string.photo_placeholder
                            ),
                            modifier = Modifier.fillMaxSize()
                        )
                    } else {
                        Image(
                            image.asImageBitmap(),
                            contentDescription = stringResource(
                                id = R.string.captured_photo
                            ),
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
        item {
            Divider(modifier = Modifier.padding(top = 10.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp)
            ) {
                Button(
                    onClick = onTakePhotoClick,
                    enabled = isCameraSupported
                ) {
                    Text(stringResource(id = R.string.take_photo))
                }
                Spacer(modifier = Modifier.width(30.dp))
                Button(
                    onClick = onSendPhotoClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Purple40),
                    enabled = image != null
                ) {
                    Text(stringResource(id = R.string.send_photo), color = Color.White)
                }
            }
        }
        item {
            Divider()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            ) {
                Button(
                    onClick = onStartWearableActivityClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Pink40),
                ) {
                    Text(stringResource(id = R.string.start_wearable_activity), color = Color.White)
                }
            }
        }
//        items(eventsList.size) { index ->
//            Column {
//                Text(
//                    stringResource(id = eventsList[index].title),
//                    style = MaterialTheme.typography.bodySmall
//                )
//                Text(
//                    eventsList[index].text,
//                    style = MaterialTheme.typography.bodySmall
//                )
//            }
//            Divider()
//        }
    }
}

@Preview
@Composable
fun MainAppPreview() {
    MobileApp(
        eventsList = listOf(
            Event(
                title = R.string.data_item_changed,
                text = "Event 1"
            ),
            Event(
                title = R.string.data_item_deleted,
                text = "Event 2"
            ),
            Event(
                title = R.string.data_item_unknown,
                text = "Event 3"
            ),
            Event(
                title = R.string.message_from_watch,
                text = "Event 4"
            ),
            Event(
                title = R.string.data_item_changed,
                text = "Event 5"
            ),
            Event(
                title = R.string.data_item_deleted,
                text = "Event 6"
            )
        ),
        image = null,
        isCameraSupported = true,
        onTakePhotoClick = {},
        onSendPhotoClick = {},
        onStartWearableActivityClick = {}
    )
}