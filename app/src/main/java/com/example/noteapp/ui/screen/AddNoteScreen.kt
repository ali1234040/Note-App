package com.example.noteapp.ui.screen

import android.provider.ContactsContract.CommonDataKinds.Note
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.noteapp.R
import com.example.noteapp.data.local.db.NoteEntity
import com.example.noteapp.navigation.Screens
import com.example.noteapp.viewModel.NotesViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddNoteScreen(
    viewModel: NotesViewModel = hiltViewModel(),
    navController: NavHostController,
    id: Int = 0
) {
    var titleText by remember { mutableStateOf("") }
    var bodyText by remember { mutableStateOf("") }
    var buttonText by remember { mutableStateOf("add note") }

    LaunchedEffect(Unit) {

        if (id > 0) {
            viewModel.getNoteById(id) {note ->
                titleText = note.title
                bodyText = note.desc
                buttonText = "update note"
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.dark_navy_blue))
            .padding(WindowInsets.navigationBars.asPaddingValues())
            .padding(bottom = 16.dp, start = 10.dp, end = 10.dp)
    ) {


        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(
                modifier = Modifier
                    .windowInsetsTopHeight(WindowInsets.statusBars)
            )
            TextField(
                value = titleText,
                onValueChange = { titleText = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Title",
                        color = Color.Gray,
                        fontSize = 20.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.White
                ),
            )

            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = bodyText,
                onValueChange = { bodyText = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = "Start typing",
                        color = Color.Gray,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.White
                ),
            )
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {

            Column {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                        .clip(RoundedCornerShape(14.dp)),
                    onClick = {
                        if (id > 0) {

                            viewModel.updateNote(
                                NoteEntity(
                                    title = titleText,
                                    desc = bodyText,
                                    date = "noValue",
                                    id = id
                                )
                            )

                        } else {
                            viewModel.addNote(
                                NoteEntity(
                                    title = titleText,
                                    desc = bodyText,
                                    date = "noValue"
                                )
                            )
                        }
                        navController.navigate(Screens.Home.route){
                            popUpTo(Screens.Home.route) {
                                inclusive = true
                            }
                        }
                    }
                ) {
                    Text(
                        text = buttonText,
                        fontSize = 16.sp
                    )
                }
//                Spacer(
//                    modifier = Modifier
//                        .height( WindowInsets.navigationBars.getBottom(density = density).dp)
//                )
            }
        }
    }
}

