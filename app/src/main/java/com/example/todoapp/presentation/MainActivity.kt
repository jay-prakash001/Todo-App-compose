package com.example.todoapp.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.todoapp.data.room.TodoDB
import com.example.todoapp.presentation.screens.AddCategory
import com.example.todoapp.presentation.screens.AddTodo
import com.example.todoapp.presentation.screens.ContactUs
import com.example.todoapp.presentation.screens.Home
import com.example.todoapp.presentation.screens.NavScreenItems
import com.example.todoapp.presentation.viewmodels.TodosViewModel
import com.example.todoapp.ui.theme.TodoAppTheme

class MainActivity : ComponentActivity() {


    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            TodoDB::class.java,
            "todo.db"
        ).build()
    }
//val context = applicationContext
//    private val dbStore = PreferenceDataStoreFactory.create(
//        corruptionHandler = ReplaceFileCorruptionHandler(produceNewData = { emptyPreferences() }), produceFile = {context.preferencesDataStoreFile("cat_data")
//
//        }
//    )

    //    private val viewModel by viewModels<TodoViewModel>(
//        factoryProducer = {
//            object : ViewModelProvider.Factory {
//                override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                    return TodoViewModel(db.dao) as T
//                }
//            }
//        }
//    )
    private val viewModel by viewModels<TodosViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return TodosViewModel(db.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {


            TodoAppTheme {
                NavApp(viewModel = viewModel)
//                Test(viewModel = viewModel)

            }
        }
    }
}



@Composable
fun Test(modifier: Modifier = Modifier,viewModel: TodosViewModel) {

Column(modifier = Modifier.fillMaxSize()) {
    val context = LocalContext.current
    val url = "https://github.com/jay-prakash001"

    Button(onClick = {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }) {
        Text(text = "open yt")
    }

}
}
@Composable
fun NavApp(modifier: Modifier = Modifier, viewModel: TodosViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavScreenItems.Home.name) {
        composable(NavScreenItems.Home.name) {
            Home(viewModel = viewModel, navController = navController)
        }
        composable(NavScreenItems.AddTodo.name) {
            AddTodo(viewModel = viewModel, navController = navController)
        }
        composable(NavScreenItems.AddCategory.name) {
            AddCategory(viewModel = viewModel, navController = navController)
        }
        composable(NavScreenItems.ContactUs.name) {
            ContactUs( navController = navController)
        }
    }
}


