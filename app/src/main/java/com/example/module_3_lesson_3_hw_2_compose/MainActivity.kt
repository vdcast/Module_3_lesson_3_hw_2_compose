package com.example.module_3_lesson_3_hw_2_compose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.module_3_lesson_3_hw_2_compose.ui.theme.Module_3_Lesson_3_hw_2_ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Module_3_Lesson_3_hw_2_ComposeTheme {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Column {
                        Button(
                            onClick = {
                                startService(Intent(this@MainActivity, AudioService::class.java))
                            }
                        ) {
                            Text(text = stringResource(id = R.string.startService))
                        }
                        Button(
                            onClick = {
                                stopService(Intent(this@MainActivity, AudioService::class.java))
                            }
                        ) {
                            Text(text = stringResource(id = R.string.stopService))
                        }
                    }
                }

            }
        }
    }
}