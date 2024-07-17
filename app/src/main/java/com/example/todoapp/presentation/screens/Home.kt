package com.example.todoapp.presentation.screens

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition

import com.example.todoapp.R
import com.example.todoapp.presentation.utils.CustomText
import com.example.todoapp.presentation.utils.TodoItem
import com.example.todoapp.presentation.viewmodels.TodosViewModel

//@Preview(showSystemUi = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    modifier: Modifier = Modifier,
    viewModel: TodosViewModel,
    navController: NavHostController
) {
    val showCategory = remember {
        mutableStateOf(false)
    }
    Scaffold(modifier = modifier.fillMaxSize(), floatingActionButton = {
        SmallFloatingActionButton(onClick = {
            navController.navigate(NavScreenItems.AddTodo.name)
        }, containerColor = MaterialTheme.colorScheme.primary) {
        val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(resId = R.raw.add))
            LottieAnimation(composition = composition, iterations = LottieConstants.IterateForever, modifier = Modifier.size(80.dp))
        }
    },
        topBar = {
            SmallTopAppBar(
                title = {

                    Text(
                        text = "All Todos",
                        color = MaterialTheme.colorScheme.onPrimary
                    )


                },

                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
                modifier = Modifier,
                actions = {
                    IconButton(onClick = {
                        viewModel.sortTodo()
                    }) {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "change order", modifier = Modifier,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )

                    }
                    IconButton(onClick = {
                        showCategory.value = !showCategory.value

                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "drop down Icon", modifier = Modifier,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )

                    }
                    DropdownMenu(expanded = showCategory.value, onDismissRequest = {
                        showCategory.value = false
                    }) {

                        Divider()

                        val context = LocalContext.current
                        viewModel.state.collectAsState().value.categories.forEach { cat ->
                            DropdownMenuItem(
                                text = { CustomText(text = cat.desc.capitalize()) },
                                onClick = {
//                                    viewModel.state.value.category.value = cat.desc
                                    if (cat.desc.contains("default", ignoreCase = true)) {
                                        viewModel.selectedCategory.value = ""

                                    } else {
                                        viewModel.selectedCategory.value = cat.desc

                                    }
//                                    viewModel.updateTempTodos()
                                    showCategory.value = false
                                    Toast.makeText(
                                        context,
//                                        viewModel.state.value.category.value
                                        viewModel.selectedCategory.value,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                })
                            Divider()
                        }
                    }
                    val showMoreOptions = remember {
                        mutableStateOf(false)
                    }

                    val context = LocalContext.current
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Options",
                        tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.clickable {
                            showMoreOptions.value = !showMoreOptions.value
                        }


                    )
                    DropdownMenu(expanded = showMoreOptions.value, onDismissRequest = {
                        showMoreOptions.value = false
                    }) {
                        DropdownMenuItem(
                            text = { CustomText(text = "Task Lists") },
                            onClick = {
                                navController.navigate(NavScreenItems.AddCategory.name)
                            })
                        Divider()

                        DropdownMenuItem(
                            text = { CustomText(text = "More apps from us") },
                            onClick = {
                                val url = "https://github.com/jay-prakash001"
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                context.startActivity(intent)


                            })
                        Divider()

                        DropdownMenuItem(
                            text = { CustomText(text = "Contact us") },
                            onClick = {
                                navController.navigate(NavScreenItems.ContactUs.name)

                            })

                        Divider()

                    }


                }, navigationIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "logo",
                        modifier = Modifier
                            .padding(horizontal = 10.dp)
                            .size(50.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )

                })
        }) { innerPadding ->
        val context = LocalContext.current


        val todoList = viewModel.state.collectAsState().value.todos

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
        ) {

            if (todoList.isEmpty()) {

                item {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val composition by rememberLottieComposition(
                            spec = LottieCompositionSpec.RawRes(resId = R.raw.b)
                        )
                        LottieAnimation(
                            composition = composition,
                            iterations = LottieConstants.IterateForever
                        )


                        Text(
                            text = "Write your first task.",
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }



            items(todoList) {

                if (it.category.toString().contains(
                        viewModel.selectedCategory.collectAsState().value,
                        ignoreCase = true
                    )
                ) {
                    TodoItem(it, viewModel = viewModel, navController = navController)

                }


            }


        }

    }
}

