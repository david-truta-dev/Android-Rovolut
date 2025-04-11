package com.tdavidc.dev.ui.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionResult
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.tdavidc.dev.R
import com.tdavidc.dev.ui.common.views.SetStatusBarStyle
import com.tdavidc.dev.ui.screens.welcome.views.StoryBarView


@Composable
fun WelcomeScreen(
    viewModel: WelcomeViewModel = hiltViewModel()
) {
    val initScreens = rememberSaveable {
        arrayListOf(
            WelcomeScreen(
                title = R.string.welcome_title_1,
                background = R.raw.anim_welcome_rocket,
                dark = true
            ),
            WelcomeScreen(
                title = R.string.welcome_title_2,
                description = R.string.welcome_description_2,
                background = R.raw.anim_welcome_card,
                dark = false
            ),
            WelcomeScreen(
                title = R.string.welcome_title_3,
                description = R.string.welcome_description_3,
                background = R.raw.anim_welcome_world,
                dark = true
            ),
            WelcomeScreen(
                title = R.string.welcome_title_4,
                description = R.string.welcome_description_4,
                background = R.raw.anim_welcome_coin,
                dark = true
            ),
            WelcomeScreen(
                title = R.string.welcome_title_5,
                background = R.raw.anim_welcome_secure,
                dark = false
            ),
            WelcomeScreen(
                title = R.string.welcome_title_6,
                background = R.raw.anim_welcome_support,
                dark = true,
                repeatAnimation = true
            )
        )
    }

    val currentIndex by viewModel.currentScreenIndex.observeAsState(0)
    val screens by viewModel.welcomeScreens.observeAsState(arrayListOf())
    val currentScreenProgress by viewModel.currentScreenProgress.observeAsState()

    val currentScreen = screens.getOrNull(currentIndex)
    val surfaceColor = if (currentScreen?.dark == true) Color.Black else Color.White
    val onSurfaceColor = if (currentScreen?.dark == true) Color.White else Color.Black
    val rawComposition: LottieCompositionResult? = currentScreen?.background?.let {
        rememberLottieComposition(spec = LottieCompositionSpec.RawRes(it))
    }

    SetStatusBarStyle(useDarkIcons = currentScreen?.dark == false)

    LaunchedEffect(Unit) {
        // init screens
        viewModel.setWelcomeScreens(initScreens)
    }

    Surface(color = surfaceColor) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
                .pointerInput(Unit) {
                    detectTapGestures { event ->
                        val isLeftHalf = event.x < this.size.width / 2

                        if (isLeftHalf) {
                            viewModel.goToPreviousScreen()
                        } else {
                            viewModel.goToNextScreen()
                        }
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            if (rawComposition != null)
                LottieAnimation(
                    composition = rawComposition.value,
                    progress = { currentScreenProgress ?: 0f },
                    contentScale = ContentScale.Fit,
                )
            Column(
                modifier = Modifier
                    .background(Color.Transparent)
                    .fillMaxHeight()
            ) {
                StoryBarView(
                    screens.count(),
                    currentIndex,
                    currentScreenProgress ?: 0f,
                    currentScreen?.dark ?: false,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 6.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_home),
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp),
                        colorFilter = ColorFilter.tint(onSurfaceColor)
                    )
                    Text(
                        stringResource(R.string.welcome_to_rovolut),
                        color = onSurfaceColor,
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}