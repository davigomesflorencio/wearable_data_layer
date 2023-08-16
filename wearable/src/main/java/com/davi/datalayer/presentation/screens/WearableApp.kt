package com.davi.datalayer.presentation.screens

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.rememberScalingLazyListState
import com.davi.datalayer.R
import com.davi.datalayer.presentation.theme.orange
import com.davi.datalayer.presentation.viewmodels.Event

@Composable
fun WearableApp(
    events: List<Event>,
    image: Bitmap?,
    onQueryOtherDevicesClicked: () -> Unit,
    onQueryMobileCameraClicked: () -> Unit
) {
    val scalingLazyListState = rememberScalingLazyListState()

    Scaffold(
        vignette = { Vignette(vignettePosition = VignettePosition.TopAndBottom) },
        positionIndicator = { PositionIndicator(scalingLazyListState = scalingLazyListState) },
        timeText = { TimeText() }
    ) {
        ScalingLazyColumn(
            state = scalingLazyListState,
            contentPadding = PaddingValues(
                horizontal = 8.dp,
                vertical = 32.dp
            ),
            modifier = Modifier.background(androidx.compose.ui.graphics.Color.White)
        ) {
            item {
                Button(
                    onClick = onQueryOtherDevicesClicked,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.query_other_devices),
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center,
                        color = androidx.compose.ui.graphics.Color.White
                    )
                }
            }

            item {
                Button(
                    onClick = onQueryMobileCameraClicked,
                    colors = ButtonDefaults.buttonColors(containerColor = orange),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                ) {
                    Text(
                        text = stringResource(id = R.string.query_mobile_camera),
                        fontSize = 10.sp,
                        textAlign = TextAlign.Center,
                        color = androidx.compose.ui.graphics.Color.Black
                    )
                }
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .padding(32.dp)
                ) {
                    if (image == null) {
                        Image(
                            painterResource(id = R.drawable.photo_placeholder),
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

//            if (events.isEmpty()) {
//                item {
//                    Text(
//                        stringResource(id = R.string.waiting),
//                        modifier = Modifier.fillMaxWidth(),
//                        textAlign = TextAlign.Center
//                    )
//                }
//            } else {
//                items(events) { event ->
//                    Card(
//                        onClick = {},
//                        enabled = false
//                    ) {
//                        Column {
//                            Text(
//                                stringResource(id = event.title),
//                                style = MaterialTheme.typography.title3
//                            )
//                            Text(
//                                event.text,
//                                style = MaterialTheme.typography.body2
//                            )
//                        }
//                    }
//                }
//            }
        }
    }
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun MainAppPreviewEvents() {
    WearableApp(
        events = listOf(
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
                title = R.string.message,
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
        image = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888).apply {
            eraseColor(Color.WHITE)
        },
        onQueryOtherDevicesClicked = {},
        onQueryMobileCameraClicked = {}
    )
}

@Preview(device = Devices.WEAR_OS_LARGE_ROUND, showSystemUi = true)
@Composable
fun MainAppPreviewEmpty() {
    WearableApp(
        events = emptyList(),
        image = null,
        onQueryOtherDevicesClicked = {},
        onQueryMobileCameraClicked = {}
    )
}