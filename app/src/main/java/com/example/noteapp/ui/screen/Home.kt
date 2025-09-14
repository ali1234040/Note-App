package com.example.noteapp.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.noteapp.R
import com.example.noteapp.data.local.db.NoteEntity
import com.example.noteapp.navigation.Screens
import com.example.noteapp.viewModel.NotesViewModel

@Composable
fun HomeScreen(
    viewModel: NotesViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val notes = viewModel.notes.collectAsState()
    var showAlertDialog by remember { mutableStateOf(false) }
    val density = LocalDensity.current
    Box(
        contentAlignment = Alignment.BottomEnd,
        modifier = Modifier
            .padding(WindowInsets.navigationBars.asPaddingValues())
            .fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.dark_navy_blue))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,

                ) {

                Spacer(
                    modifier = Modifier
                        .windowInsetsTopHeight(WindowInsets.statusBars)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "All Notes",
                        fontSize = 24.sp,
                        color = colorResource(R.color.white)
                    )
                    TextButton(
                        onClick = {
                            showAlertDialog = true
                        },
                    ) {
                        Text(
                            text = "Clear",
                            fontSize = 16.sp,
                            color = colorResource(R.color.white),
                        )
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                ) {
                    items(notes.value.size) {
                        Notes(notes.value[it], viewModel, navController)
                    }
                }
            }
        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(50.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(Color.Cyan)
                .size(50.dp)
        ) {
            IconButton({
                navController.navigate(Screens.AddNotes.paramsWithArgs("0"))
            }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Note",
                    modifier = Modifier
                        .size(28.dp)
                )
            }
        }


        if (showAlertDialog) {
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                AlertDialog(
                    onDismissRequest = { showAlertDialog = false },
                    confirmButton = {
                        TextButton({
                            viewModel.deleteAllNote()
                            showAlertDialog = false
                        }) {
                            Text("Clear")
                        }
                    },
                    dismissButton = {
                        TextButton({ showAlertDialog = false }) {
                            Text("Cancel")
                        }
                    },
                    text = {
                        Text(
                            text = "Are you sure you want to delete all items?",
                            fontSize = 16.sp
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun Notes(note: NoteEntity, viewMode: NotesViewModel, navController: NavHostController) {
    Box(
        modifier = Modifier
            .padding(12.dp)
            .background(
                color = colorResource(R.color.blue),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navController.navigate(Screens.AddNotes.paramsWithArgs("${note.id}")) {
                        popUpTo(Screens.AddNotes.route) {
                            inclusive = true
                        }
                    }
                }
        ) {
            Text(
                text = note.title,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = note.desc,
                fontSize = 14.sp
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(
                    onClick = {
                        viewMode.deleteNoteById(note.id)
                    }) {
                    Text(
                        text = "delete",
                        fontSize = 12.sp
                    )
                }

                Text(
                    text = note.date,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }
        }
    }
}
