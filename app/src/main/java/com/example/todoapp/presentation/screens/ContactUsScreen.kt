package com.example.todoapp.presentation.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionResult
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.todoapp.R
import com.example.todoapp.presentation.utils.fontFamily

@Composable
fun ContactUs(modifier: Modifier = Modifier, navController: NavHostController) {
    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(resId = R.raw.a)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceVariant),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Contact Us",
            fontFamily = fontFamily,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        val context = LocalContext.current
        Row(
            modifier = Modifier.padding(2.dp).fillMaxWidth().border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(5.dp)).padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = painterResource(id = R.drawable.twitter),
                contentDescription = "twitter",
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        val intent =
                            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitter.com"))
                        context.startActivity(intent)
                    })
            Image(
                painter = painterResource(id = R.drawable.facebook),
                contentDescription = "facebook",
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        val intent =
                            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com"))
                        context.startActivity(intent)
                    })
            Image(
                painter = painterResource(id = R.drawable.whatsapp),
                contentDescription = "whatsapp",
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        val intent =
                            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.whatsapp.com"))
                        context.startActivity(intent)
                    })
            Image(
                painter = painterResource(id = R.drawable.linkedin),
                contentDescription = "linkedin",
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        val intent =
                            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com"))
                        context.startActivity(intent)
                    })
            Image(
                painter = painterResource(id = R.drawable.instagram),
                contentDescription = "instagram",
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        val intent =
                            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com"))
                        context.startActivity(intent)
                    })
            Image(
                painter = painterResource(id = R.drawable.telegram),
                contentDescription = "telegram",
                modifier = Modifier
                    .size(50.dp)
                    .clickable {
                        val intent =
                            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.telegram.com"))
                        context.startActivity(intent)
                    })

        }
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.size(400.dp)
        )

    }
}