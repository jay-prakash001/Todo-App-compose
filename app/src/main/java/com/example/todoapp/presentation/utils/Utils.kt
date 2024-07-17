package com.example.todoapp.presentation.utils

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.R
import com.example.todoapp.data.model.Category
import com.example.todoapp.data.model.Todo
import com.example.todoapp.presentation.screens.NavScreenItems
import com.example.todoapp.presentation.viewmodels.TodosViewModel
import java.sql.Date
import java.util.Locale

val fontFamily = FontFamily(Font(R.font.customfont))

@Composable
fun CustomText(text: String, modifier: Modifier = Modifier.padding(vertical = 5.dp)) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.labelMedium,
        fontFamily = fontFamily,
        fontSize = 16.sp
    )
}

//@Preview(showSystemUi = true)
@Composable
fun CategoryItem(
    category: Category = Category(0, "Default"),
    viewModel: TodosViewModel,
    showEditBar: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = category.desc.capitalize(), style = MaterialTheme.typography.labelSmall,
                fontSize = 20.sp, color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Row {

                IconButton(onClick = {
                    viewModel.state.value.category.value = category.desc
                    showEditBar()
                }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "delete",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                IconButton(onClick = {
                    viewModel.deleteCategory(category)
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "delete",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}

@Composable
fun TodoItem(
    todo: Todo = Todo(
        0,
        "title",
        "desc", 0,
        0,
        "Default", 5,
        "incomplete"
    ),
    navController: NavController = rememberNavController(),
    viewModel: TodosViewModel
) {
    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
            .clickable {

                navController.navigate(NavScreenItems.AddTodo.name)
                viewModel.setTodoValues(todo)
            },
        elevation = CardDefaults.elevatedCardElevation(5.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val date = convertDateMillis(todo.dueDate)
                val color = if (todo.category.equals("Completed", ignoreCase = true)) {
                    MaterialTheme.colorScheme.secondary
                } else {
                    MaterialTheme.colorScheme.onTertiary
                }
                CustomText(
                    text = todo.category.capitalize(),
                    modifier = Modifier
                        .background(color)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onErrorContainer,
                            RectangleShape
                        )
                        .padding(10.dp)
                )

                Text(
                    text = date, style = MaterialTheme.typography.labelSmall,
                    fontSize = 12.sp, color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Row {

                    IconButton(onClick = {
                        viewModel.updateTodoStatus(todo)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = "delete",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    IconButton(onClick = {
                        viewModel.deleteTodo(todo)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "delete",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
            Text(
                text = todo.title.capitalize(),
                fontSize = 30.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.SemiBold,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = todo.desc.capitalize(),
                fontSize = 20.sp,
                fontFamily = fontFamily,
                fontWeight = FontWeight.Light,
                style = MaterialTheme.typography.bodySmall
            )


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                for (i in 1..5) {
                    if (i <= todo.priority) {
                        Image(
                            painter = painterResource(id = R.drawable.star_filled),
                            contentDescription = "rating star",
                            modifier = Modifier.size(30.dp)
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.star_unfilled),
                            contentDescription = "rating star",
                            modifier = Modifier.size(30.dp)
                        )

                    }
                }
            }
        }

    }
}


fun convertDateMillis(millis: Long): String {
    val date = Date(millis)
    val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    return dateFormat.format(date)
}