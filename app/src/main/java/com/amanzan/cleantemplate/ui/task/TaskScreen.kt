package com.amanzan.cleantemplate.ui.task

import com.amanzan.cleantemplate.ui.theme.MyApplicationTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun TaskScreen(modifier: Modifier = Modifier, viewModel: TaskViewModel = hiltViewModel()) {
    val items by viewModel.uiState.collectAsStateWithLifecycle()

    when (items) {
        is TaskUiState.Success -> {
            TaskScreen(
                items = (items as TaskUiState.Success).data,
                onSave = viewModel::addTask,
                modifier = modifier
            )
        }
        is TaskUiState.Loading -> {
            // Show loading indicator
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is TaskUiState.Error -> {
            // Show error message
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Error loading tasks")
            }
        }
        else -> {
            // Initial or unknown state, show something minimal
            Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "No data yet")
            }
        }
    }
}


@Composable
internal fun TaskScreen(
    items: List<String>,
    onSave: (name: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        var nameTask by remember { mutableStateOf("Compose") }
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                value = nameTask,
                onValueChange = { nameTask = it }
            )

            Button(modifier = Modifier.width(96.dp), onClick = { onSave(nameTask) }) {
                Text("Save")
            }
        }
        items.forEach {
            Text("Saved item: $it")
        }
    }
}

// Previews

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    MyApplicationTheme {
        TaskScreen(listOf("Compose", "Room", "Kotlin"), onSave = {})
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    MyApplicationTheme {
        TaskScreen(listOf("Compose", "Room", "Kotlin"), onSave = {})
    }
}
