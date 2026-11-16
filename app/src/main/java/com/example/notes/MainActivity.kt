package com.example.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.notes.feature.presentation.edit.EditNoteScreen
import com.example.notes.feature.presentation.list.NotesScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "notes") {
                        composable("notes") {
                            NotesScreen(
                                onOpenNote = { id -> navController.navigate("edit/$id") },
                                onCreateNote = { navController.navigate("edit/0") },
                            )
                        }
                        composable(
                            route = "edit/{noteId}",
                            arguments = listOf(
                                navArgument("noteId") { type = NavType.LongType },
                            ),
                        ) { backStackEntry ->
                            EditNoteScreen(
                                noteId = backStackEntry.arguments?.getLong("noteId") ?: 0L,
                                onBack = { navController.popBackStack() },
                            )
                        }
                    }
                }
            }
        }
    }
}
