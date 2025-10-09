package com.example.motionlayoutdemo.compose

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.example.motionlayoutdemo.R

@OptIn(ExperimentalMotionApi::class)
@Composable
fun ExpandingSearchBar() {
    val context = LocalContext.current
    val motionScene = remember {
        context.resources.openRawResource(R.raw.motion_scene_search)
            .bufferedReader().use { it.readText() }
    }

    var isSearching by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    val progress by animateFloatAsState(
        targetValue = if (isSearching) 1f else 0f,
        animationSpec = tween(500)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        MotionLayout(
            motionScene = MotionScene(motionScene),
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(72.dp)
                .background(Color.White)
                .padding(horizontal = 8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black,
                modifier = Modifier
                    .layoutId("backIcon")
                    .clickable {
                        if (isSearching) {
                            searchText = ""
                            isSearching = false
                        }
                    }
            )

            Text(
                text = "Header Title",
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier.layoutId("titleText")
            )

            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = Color.Black,
                modifier = Modifier
                    .layoutId("searchIcon")
                    .clickable {
                        isSearching = true
                    }
            )

            OutlinedTextField(
                modifier = Modifier
                    .height(52.dp)
                    .background(Color.White)
                    .layoutId("searchField"),

                shape = RoundedCornerShape(40),
                value = searchText,
                onValueChange = { searchText = it },
                singleLine = true,
                placeholder = {
                    if (searchText.isEmpty()) Text("Search…", color = Color.Gray)
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    focusedContainerColor = Color(0xFFF2F2F2),
                    unfocusedContainerColor = Color(0xFFF2F2F2),
                    cursorColor = Color.Black
                )
            )
        }

        HorizontalDivider()
    }
}