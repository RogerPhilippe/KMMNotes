package br.com.phs.kmmnotes.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.phs.kmmnotes.android.note.details.NoteDetailScreen
import br.com.phs.kmmnotes.android.note.list.NoteListScreen
import br.com.phs.kmmnotes.presentation.NOTE_DETAILS
import br.com.phs.kmmnotes.presentation.NOTE_LIST
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = NOTE_LIST) {
                    composable(route = NOTE_LIST) {
                        NoteListScreen(navController = navController)
                    }
                    composable(
                        route = "${NOTE_DETAILS}/{noteId}",
                        arguments = listOf(
                            navArgument(name = "noteId") {
                                type = NavType.LongType
                                defaultValue = -1L
                            }
                        )
                    ) { backStackEntry ->
                        val noteId = backStackEntry.arguments?.getLong("noteId")?: -1L
                        NoteDetailScreen(noteId = noteId, navController = navController)
                    }
                }
            }
        }
    }
}

