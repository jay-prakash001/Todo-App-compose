package com.example.todoapp.presentation.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.todoapp.R
import com.example.todoapp.presentation.utils.CustomText
import com.example.todoapp.presentation.utils.convertDateMillis
import com.example.todoapp.presentation.utils.fontFamily
import com.example.todoapp.presentation.viewmodels.TodosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTodo(
    modifier: Modifier = Modifier,
    viewModel: TodosViewModel,
    navController: NavHostController
) {

    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "New Task",
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.padding(horizontal = 2.dp),
                    style = MaterialTheme.typography.titleLarge,
                )
            }, navigationIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "back",
                    tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.clickable {
                        navController.navigateUp()
                    }
                )
            }, colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary))
        }, floatingActionButton = {
            FloatingActionButton(containerColor = MaterialTheme.colorScheme.primary, onClick = {
                viewModel.saveTodo()

                navController.navigateUp()

            }) {

                Icon(
                    imageVector = Icons.Default.Done,
                    contentDescription = "add todo",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(it)
                .padding(horizontal = 10.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            CustomText(text = "What is to be done?")
            TextField(
                value = viewModel.state.collectAsState().value.title.value,
                onValueChange = { viewModel.state.value.title.value = it },
                label = {
                    Text(text = "Title")
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
            )
            CustomText("Describe the detail of the task.")
            TextField(
                value = viewModel.state.collectAsState().value.desc.value,
                onValueChange = { viewModel.state.value.desc.value = it },
                label = {
                    Text(text = "Description")

                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                val showDateDialog = remember {
                    mutableStateOf(false)
                }
                CustomText(text = "Due Date?")
                if (!showDateDialog.value) {
                    if (viewModel.state.collectAsState().value.dueDate.value != 0L) {
                        val date =
                            convertDateMillis(viewModel.state.collectAsState().value.dueDate.value)

                        CustomText(
                            text = date,
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .border(1.dp, MaterialTheme.colorScheme.primary, RectangleShape)
                                .padding(10.dp)
                        )
                    }
                }
                val composition = rememberLottieComposition(spec =
                    LottieCompositionSpec.RawRes(resId = R.raw.date)
                )
                LottieAnimation(composition = composition.value, iterations = LottieConstants.IterateForever,modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        showDateDialog.value = !showDateDialog.value
                    })
//                Icon(
//                    imageVector = Icons.Default.DateRange,
//                    contentDescription = "date time picker",
//                    modifier = Modifier.clickable {
//                        showDateDialog.value = !showDateDialog.value
//                    })
                val datePickerState = rememberDatePickerState()
                val context = LocalContext.current
                if (showDateDialog.value) {
                    Dialog(onDismissRequest = { showDateDialog.value = false }) {
                        Card {
                            DatePicker(state = datePickerState)
                            Button(onClick = {
                                viewModel.state.value.dueDate.value =
                                    datePickerState.selectedDateMillis ?: 0
                                Toast.makeText(
                                    context,
                                    viewModel.state.value.dueDate.value.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                                showDateDialog.value = false
                            }, modifier = Modifier.offset(240.dp, 0.dp)) {

                                Icon(imageVector = Icons.Default.Done, contentDescription = "done")
                            }
                        }
                    }

                }

            }
            Text(
                text = "Note : we are working on the notification and reminder service.",
                fontSize = 10.sp
            )
            //////////////////////////////////////////////////////////////
            CustomText(text = "Set Priority : ${viewModel.state.collectAsState().value.priority.value}")
            Row (modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp), horizontalArrangement = Arrangement.SpaceAround){
                for (i in 1..5){
                    if (i <= viewModel.state.collectAsState().value.priority.value ){
                    Image(painter = painterResource(id = R.drawable.star_filled), contentDescription ="set rating",
                        Modifier
                            .size(40.dp)
                            .clickable {
                                viewModel.state.value.priority.value = i
                            } )
                }else{
                        Image(painter = painterResource(id = R.drawable.star_unfilled), contentDescription ="set rating",
                            Modifier
                                .size(40.dp)
                                .clickable {
                                    viewModel.state.value.priority.value = i
                                } )
                    }
                    }
            }
            val isExpanded = remember {
                mutableStateOf(false)
            }
            CustomText(text = "Add to List")
            Row(
                horizontalArrangement = Arrangement.spacedBy(40.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                CustomText(text = viewModel.state.collectAsState().value.category.value)
                IconButton(onClick = {
                    isExpanded.value = !isExpanded.value
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "select category from drop down menu.",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                val context = LocalContext.current
                val categoriesList = viewModel.state.collectAsState().value.categories
//                Text(text = categoriesList.toString())
                DropdownMenu(expanded = isExpanded.value, onDismissRequest = {
                    isExpanded.value = !isExpanded.value
                }) {
                    categoriesList.forEach { cat ->

                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = cat.desc.capitalize(),
                                    fontFamily = fontFamily
                                )
                            },
                            onClick = {
                                viewModel.state.value.category.value = cat.desc.lowercase()
                            })
                    }
                }
            }
        }


    }

}



