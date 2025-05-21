package com.amanzan.cleantemplate.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amanzan.cleantemplate.ui.task.AddTaskScreen
import com.amanzan.cleantemplate.ui.task.TaskScreen

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "main") {
        composable("main") { 
            TaskScreen(
                navController = navController
            )
        }
        composable("add_task") {
            AddTaskScreen(
                navController = navController
            )
        }
    }
}
