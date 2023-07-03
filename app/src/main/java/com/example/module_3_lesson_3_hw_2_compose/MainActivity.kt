package com.example.module_3_lesson_3_hw_2_compose

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.drawable.Icon
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore.Audio
import android.provider.OpenableColumns
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.module_3_lesson_3_hw_2_compose.ui.theme.Module_3_Lesson_3_hw_2_ComposeTheme
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.example.module_3_lesson_3_hw_2_compose.ui.theme.Mint10
import java.time.format.TextStyle
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.module_3_lesson_3_hw_2_compose.ui.theme.FileData

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Module_3_Lesson_3_hw_2_ComposeTheme {

                MyApp()


            }
        }
    }
}


val LocalPrefs = compositionLocalOf<SharedPrefs> { error(" No SharedPrefs provided") }

@Composable
fun MyApp() {
    val prefs = SharedPrefs(LocalContext.current)
    CompositionLocalProvider(LocalPrefs provides prefs) {

        val navController = rememberNavController()
        val skin = prefs.skinFlow.collectAsState()

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            Image(
                painter = painterResource(
                    id = when (skin.value) {
                        "Light" -> R.drawable.light
                        "Dark" -> R.drawable.dark_mahadev
                        "Ocean" -> R.drawable.ocean
                        "Moto" -> R.drawable.motocross
                        "Nature" -> R.drawable.nature
                        else -> R.drawable.light
                    }
                ),
                contentDescription = "Image background Main Screen",
                contentScale = ContentScale.FillBounds
            )

            NavHost(
                navController = navController,
                startDestination = ScreenRoutes.ScreenMain.route
            ) {
                composable(ScreenRoutes.ScreenMain.route) {
                    ScreenMain(
                        navigateToPlayer = {
                            navController.navigate(ScreenRoutes.ScreenPlayer.route)
                        },
                        navigateToSettings = {
                            navController.navigate(ScreenRoutes.ScreenSettings.route)
                        }
                    )
                }
                composable(ScreenRoutes.ScreenSettings.route) {
                    ScreenSettings(
                        navigateBack = {
                            navController.popBackStack()
                        }
                    )
                }
                composable(ScreenRoutes.ScreenPlayer.route) {
                    ScreenPlayer(
                        navigateBack = {
                            navController.popBackStack()
                        }
                    )
                }

            }
        }


    }
}

@Composable
fun ScreenMain(
    navigateToPlayer: () -> Unit,
    navigateToSettings: () -> Unit
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Card(
            modifier = Modifier,
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(Mint10)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(all = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Screen Main",
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
                Button(onClick = navigateToPlayer) {
                    Text(text = stringResource(id = R.string.player))
                }
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
                Button(
                    onClick = {
                        val intent = Intent(context, AudioService::class.java)
                        val myUri = Uri.parse("content://com.android.providers.downloads.documents/document/raw%3A%2Fstorage%2Femulated%2F0%2FDownload%2FGorillaz%20-%20Empire%20Ants.mp3")
                        intent.putExtra("audio_uri", myUri)
                        context.startService(intent)
                    }
                ) {
                    Text(text = stringResource(id = R.string.startService))
                }
                Button(
                    onClick = {
                        context.stopService(Intent(context, AudioService::class.java))
                    }
                ) {
                    Text(text = stringResource(id = R.string.stopService))
                }
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
                Button(onClick = navigateToSettings) {
                    Text(text = stringResource(id = R.string.settings))
                }
            }
        }
    }

}


@Composable
fun ScreenPlayer(
    navigateBack: () -> Unit
) {
    val context = LocalContext.current
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    val fileList = remember { mutableStateOf(listOf<FileData>()) }
    val mediaPlayer = remember { MediaPlayer() }
    val currentIndex = remember { mutableStateOf(0) }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.release()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Card(
            modifier = Modifier,
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(Mint10)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(all = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.player),
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

                FilePicker { uri, fileName ->
                    Log.d("MYLOG", "Selected file $fileName and uri: $uri")
                    fileList.value = fileList.value + FileData(fileName, uri)
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                LazyColumn {
                    items(fileList.value.size) { index ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.baseline_play_circle_24),
                                contentDescription = "Icon play from list",
                                modifier = Modifier
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                                    .clickable {
                                        val fileData = fileList.value[index]
                                        mediaPlayer.reset()
                                        mediaPlayer.setDataSource(context, fileData.uri)
                                        mediaPlayer.prepare()
                                        mediaPlayer.start()
                                    }
                            )
                            Text(text = fileList.value[index].name)
                        }
                    }
                }


                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
                Row() {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_skip_previous_40),
                        contentDescription = "Icon previous",
                        modifier = Modifier.clickable {
                            if (currentIndex.value > 0) {
                                currentIndex.value--
                                mediaPlayer.reset()
                                mediaPlayer.setDataSource(context, fileList.value[currentIndex.value].uri)
                                mediaPlayer.prepare()
                                mediaPlayer.start()
                            }
                        }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.baseline_play_circle_40),
                        contentDescription = "Icon play",
                        modifier = Modifier.clickable {
                            if (!mediaPlayer.isPlaying) {
                                mediaPlayer.start()
                            }
                        }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.baseline_pause_circle_40),
                        contentDescription = "Icon pause",
                        modifier = Modifier.clickable {
                            if (mediaPlayer.isPlaying) {
                                mediaPlayer.pause()
                            }
                        }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.baseline_stop_circle_40),
                        contentDescription = "Icon stop",
                        modifier = Modifier.clickable {
                            if (mediaPlayer.isPlaying) {
                                mediaPlayer.stop()
                                mediaPlayer.prepare()
                            }
                        }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.baseline_skip_next_40),
                        contentDescription = "Icon next",
                        modifier = Modifier.clickable {
                            if (currentIndex.value < fileList.value.size - 1) {
                                currentIndex.value++
                                mediaPlayer.reset()
                                mediaPlayer.setDataSource(context, fileList.value[currentIndex.value].uri)
                                mediaPlayer.prepare()
                                mediaPlayer.start()
                            }
                        }
                    )
                }
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))
                Row() {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_volume_down_40),
                        contentDescription = "Icon volume down",
                        modifier = Modifier.clickable {
                            audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.FLAG_PLAY_SOUND)
                        }
                    )
                    Image(
                        painter = painterResource(id = R.drawable.baseline_volume_up_40),
                        contentDescription = "Icon volume up",
                        modifier = Modifier.clickable {
                            audioManager.adjustVolume(AudioManager.ADJUST_RAISE, AudioManager.FLAG_PLAY_SOUND)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))
                Button(onClick = navigateBack) {
                    Text(text = "Back")
                }
            }
        }
    }
}


@Composable
fun ScreenSettings(
    navigateBack: () -> Unit
) {
    val prefs = LocalPrefs.current
    val skins = listOf("Light", "Dark", "Ocean", "Moto", "Nature")
    var selectedSkin by rememberSaveable() { mutableStateOf(prefs.getSkin()) }
    val (isCheckedRepeat, setCheckedRepeat) = remember { mutableStateOf(prefs.getRepeat()) }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Card(
            modifier = Modifier,
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(Mint10)
        ) {
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(all = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.settings),
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Choose background skin:")
                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_medium)))
                    Spinner(
                        modifier = Modifier.width(dimensionResource(id = R.dimen.button_width)),
                        itemList = skins,
                        selectedItem = selectedSkin,
                        onItemSelected = {
                            selectedSkin = it
                            prefs.setSkin(selectedSkin)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                Row() {
                    Text(text = "Selected skin:")
                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_small)))
                    Text(text = prefs.getSkin())
                }

                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_small)))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Repeat list when finished")
                    Spacer(modifier = Modifier.width(dimensionResource(R.dimen.padding_large)))
                    Switch(
                        checked = isCheckedRepeat,
                        onCheckedChange = { newValue ->
                            setCheckedRepeat(newValue)
                            if (newValue) {
                                prefs.setRepeat(true)
                            } else {
                                prefs.setRepeat(false)
                            }
                        }
                    )
                }


                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

                Button(onClick = navigateBack) {
                    Text(text = "Back")
                }
            }
        }
    }


}

@Composable
fun Spinner(
    modifier: Modifier,
    itemList: List<String>,
    selectedItem: String,
    onItemSelected: (selectedItem: String) -> Unit
) {
    var tempSelectedItem = selectedItem
    if (selectedItem.isBlank() && itemList.isNotEmpty()) {
        onItemSelected(itemList[0])
        tempSelectedItem = itemList[0]
    }
    var expanded by rememberSaveable() { mutableStateOf(false) }

    OutlinedButton(
        onClick = { expanded = true },
        modifier = modifier,
        enabled = itemList.isNotEmpty()
    ) {
        Text(
            text = tempSelectedItem,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            modifier = Modifier.weight(1f)
        )
        Icon(
            if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = "Icons for spinner"
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            itemList.forEach {
                DropdownMenuItem(
                    text = { Text(text = it) },
                    onClick = {
                        expanded = false
                        onItemSelected(it)
                    }
                )
            }
        }
    }
}


@Composable
fun FilePicker(onFilePicked: (Uri, String) -> Unit) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri: Uri? ->
            uri?.let {
                var result: String? = null
                if (uri.scheme.equals("content")) {
                    val cursor = context.contentResolver.query(uri, null, null, null, null)
                    cursor?.use {
                        if (it.moveToFirst()) {
                            val index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                            if (index != -1) {
                                result = cursor.getString(index)
                            }
                        }
                    }
                }

                result?.let { fileName ->
                    val cut = fileName.lastIndexOf('/')
                    if (cut != -1) {
                        result = fileName.substring(cut + 1)
                    }
                    onFilePicked(uri, result ?: "")
                }
            }
        }
    )
    Button(
        onClick = {
            launcher.launch(arrayOf("audio/*"))
        }
    ) {
        Text(text = "Add music")
    }
}