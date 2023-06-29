package com.example.module_3_lesson_3_hw_2_compose

import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.vector.ImageVector

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
    val prefs = LocalPrefs.current

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
                Button(
                    onClick = {
                        context.startService(Intent(context, AudioService::class.java))
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
                Button(onClick = navigateToPlayer) {
                    Text(text = stringResource(id = R.string.player))
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
                Text(text = stringResource(id = R.string.player))
                Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

                Row() {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_skip_previous_40),
                        contentDescription = "Icon previous"
                    )
                    Image(
                        painter = painterResource(id = R.drawable.baseline_play_circle_40),
                        contentDescription = "Icon play"
                    )
                    Image(
                        painter = painterResource(id = R.drawable.baseline_pause_circle_40),
                        contentDescription = "Icon pause"
                    )
                    Image(
                        painter = painterResource(id = R.drawable.baseline_stop_circle_40),
                        contentDescription = "Icon stop"
                    )
                    Image(
                        painter = painterResource(id = R.drawable.baseline_skip_next_40),
                        contentDescription = "Icon next"
                    )
                }
                Row() {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_volume_down_40),
                        contentDescription = "Icon volume down"
                    )
                    Image(
                        painter = painterResource(id = R.drawable.baseline_volume_up_40),
                        contentDescription = "Icon volume up"
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