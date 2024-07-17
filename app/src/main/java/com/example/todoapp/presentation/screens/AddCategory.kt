package com.example.todoapp.presentation.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.todoapp.presentation.utils.CategoryItem
import com.example.todoapp.presentation.utils.CustomText
import com.example.todoapp.presentation.viewmodels.TodosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCategory(
    modifier: Modifier = Modifier,
    viewModel: TodosViewModel,
    navController: NavHostController
) {
    val showAddCategory = remember {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        text = "New Category",
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(horizontal = 2.dp),
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "back",
                        tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.clickable {
                            navController.navigateUp()
                        }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary),
                actions = {
                    IconButton(onClick = {
                        showAddCategory.value = !showAddCategory.value
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "new category",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                })
        },
    ) {
        val categories = viewModel.state.collectAsState().value.categories

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(it),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            item {
                AnimatedVisibility(
                    visible = showAddCategory.value,
                    modifier = Modifier.fillMaxHeight(.2f)
                ) {
                    Column {
                        CustomText(text = "List Name")
                        TextField(
                            value = viewModel.state.collectAsState().value.category.value,
                            onValueChange = { viewModel.state.value.category.value = it },
                            label = {
                                Text(text = "Enter the list name")
                            },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                            modifier = Modifier
                                .padding(vertical = 10.dp)
                                .fillMaxWidth()
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {

                            OutlinedButton(onClick = {
                                showAddCategory.value = !showAddCategory.value
                                viewModel.addCategory()

                            }, modifier = Modifier) {
                                Icon(
                                    imageVector = Icons.Default.Done,
                                    contentDescription = "add",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }

                        }
                    }
                }

            }

            items(categories) { cat ->
                CategoryItem(cat, viewModel = viewModel) {
                    viewModel.setCategoryValues(cat)
                    showAddCategory.value = true


                }

            }
        }
    }
}

