package com.example.todoapp.uix.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todoapp.R
import com.example.todoapp.uix.viewmodel.HomePageViewModel
import com.google.gson.Gson
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavController, homePageViewModel: HomePageViewModel){

    val isSearch = remember { mutableStateOf(false) }

    val tf = remember { mutableStateOf("") }

    val toDoList = homePageViewModel.toDoList.observeAsState(listOf())

    val scope = rememberCoroutineScope()

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {

        homePageViewModel.loadToDos()
    }
    
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    if (isSearch.value) {
                        TextField(
                            value = tf.value,
                            onValueChange = {
                                tf.value = it
                                homePageViewModel.search(it)
                            },
                            label = { Text(text = "Ara") },
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                focusedLabelColor = Color.White,
                                focusedIndicatorColor = Color.White,
                                unfocusedLabelColor = Color.Black,
                                unfocusedIndicatorColor = Color.White
                            )
                        )
                    } else {
                        Text(text = "ToDos")
                    }
                },

                actions = {
                    if(isSearch.value){
                        IconButton(onClick = {

                            isSearch.value = false
                            tf.value = ""
                        }) {
                            Icon(painter = painterResource(id = R.drawable.baseline_close_24), contentDescription = "")
                        }
                    }else{
                        IconButton(onClick = {
                            isSearch.value = true

                        }) {
                            Icon(painter = painterResource(id = R.drawable.baseline_search_24), contentDescription = "")
                        }
                    }
                }
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = {navController.navigate("addPage")},
                content = {
                    Icon(painter = painterResource(id = R.drawable.baseline_add_24), contentDescription = "")
                }
            )
        },

        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) }
    ){
        paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,

        ){
           items(
               count = toDoList.value.count(),
               itemContent = {
                   val toDo = toDoList.value[it]
                   Card(modifier = Modifier.padding(all = 5.dp)) {
                       Row(
                           modifier = Modifier
                               .fillMaxWidth()
                               .clickable {
                                   val toDoJson = Gson().toJson(toDo)
                                   navController.navigate("editPage/$toDoJson")
                               },

                           horizontalArrangement = Arrangement.SpaceBetween,
                           verticalAlignment = Alignment.CenterVertically
                       ) {
                           Column(modifier = Modifier.padding(all = 10.dp)) {

                               Text(text = toDo.todo_name, fontSize = 20.sp)

                           }

                           IconButton(onClick = {
                               scope.launch {
                                   val sb = snackbarHostState.showSnackbar(
                                       message = "${toDo.todo_name} silinsin mi?",
                                       actionLabel = "Evet"
                                   )

                                   if (sb == SnackbarResult.ActionPerformed) {
                                       homePageViewModel.delete(toDo.todo_id)
                                   }
                               }
                           }) {
                               Icon(
                                   painter = painterResource(id = R.drawable.baseline_close_24),
                                   contentDescription = "",
                                   tint = Color.Gray
                               )
                           }
                       }
                   }
               }
           )
        }
    }
}