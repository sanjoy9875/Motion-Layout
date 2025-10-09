package com.example.motionlayoutdemo.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ExperimentalMotionApi
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.example.motionlayoutdemo.R

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMotionApi::class)
@Composable
fun CollapsingHeaderList() {
    val context = LocalContext.current

    // Load MotionScene JSON
    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.motion_scene_list)
            .readBytes()
            .decodeToString()
    }

    // Scroll state for list
    val listState = rememberLazyListState()
    val maxOffset = 300f

    // ✅ Reverse the progress logic
    val progress by derivedStateOf {
        val offset = listState.firstVisibleItemScrollOffset.toFloat()
        val index = listState.firstVisibleItemIndex
        // Only animate while scrolling within header (index == 0)
        if (index == 0) {
            (offset / maxOffset).coerceIn(0f, 1f)
        } else {
            1f // fully collapsed once past header
        }
    }

    Column {
        MotionLayout(
            motionScene = MotionScene(content = motionScene),
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .layoutId("box")
                .background(Color(0xFF101010))
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                contentDescription = null,
                modifier = Modifier
                    .layoutId("profile_pic")
                    .clip(CircleShape)
                    .border(2.dp, Color.White, CircleShape)
            )

            Text(
                text = "Sanjoy Paul",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .layoutId("username")
            )
        }
        LazyColumn(state = listState) {

            // List items
            items(40) { index ->
                Text(
                    text = "Item #$index",
                    fontSize = 18.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .background(Color.LightGray.copy(alpha = 0.3f))
                        .padding(16.dp)
                )
            }
        }
    }
}

