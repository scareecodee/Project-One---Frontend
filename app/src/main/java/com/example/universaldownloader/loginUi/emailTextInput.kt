package com.example.universaldownloader.loginUi

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.universaldownloader.R

import android.util.Patterns

fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches()
}


@Composable
fun EmailInput(
    email: String,
    onEmailChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val btnColor=Color(0xFF636AE8)
    androidx.compose.foundation.layout.Column(
        modifier = modifier.padding(top = 20.dp)
    ) {
        // Label
        Text(
            text = "Email",
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.poppinslight)),
            modifier = Modifier.padding(horizontal = 20.dp)
        )
        // Box with the TextField
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                .border(1.dp, Color.White, RoundedCornerShape(12.dp))
                .padding(18.dp) // Padding inside the box
        ) {
            if (email.isEmpty()) {
                Text(
                    text = "Enter your email",
                    color = Color.Gray,
                    fontFamily = FontFamily(Font(R.font.poppinslight))
                )
            }
            androidx.compose.foundation.text.BasicTextField(
                value = email,
                onValueChange = onEmailChange,
                modifier = Modifier.fillMaxWidth(),
                textStyle = androidx.compose.ui.text.TextStyle(
                    color = Color.White,
                    fontSize = 16.sp
                ),
                cursorBrush = SolidColor(btnColor),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                )
            )
        }
        if (!isValidEmail(email) && email.isNotEmpty()) {
            Text(
                text = "Invalid email address",
                color = Color.Red,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = FontFamily(Font(R.font.poppinslight)),
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }
    }
}
