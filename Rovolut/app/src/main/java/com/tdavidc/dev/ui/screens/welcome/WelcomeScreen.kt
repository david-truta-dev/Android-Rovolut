package com.tdavidc.dev.ui.screens.welcome

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionResult
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.tdavidc.dev.R
import com.tdavidc.dev.ui.screens.welcome.views.StoryBarView
import com.tdavidc.dev.ui.theme.black
import com.tdavidc.dev.ui.theme.white
import com.tdavidc.dev.ui.views.RoundedTextButton
import com.tdavidc.dev.ui.views.SetStatusBarStyle


@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    onClickCreateAccount: () -> Unit,
    onClickLogin: () -> Unit,
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

    Surface(color = surfaceColor, modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = modifier
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
            contentAlignment = Alignment.Center,
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
                if (currentScreen?.title != null)
                    Text(
                        stringResource(currentScreen.title).uppercase(),
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold),
                        color = onSurfaceColor
                    )
                if (currentScreen?.description != null)
                    Text(
                        stringResource(currentScreen.description),
                        modifier = Modifier.padding(horizontal = 16.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        color = onSurfaceColor
                    )
                Spacer(modifier = Modifier.weight(1f))
                RoundedTextButton(
                    onClickCreateAccount,
                    stringResource(R.string.welcome_top_button),
                    containerColor = white,
                    contentColor = black,
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.size(16.dp))
                RoundedTextButton(
                    onClickLogin,
                    stringResource(R.string.welcome_bottom_button),
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
                Spacer(modifier = Modifier.size(40.dp))
            }
        }
    }
}