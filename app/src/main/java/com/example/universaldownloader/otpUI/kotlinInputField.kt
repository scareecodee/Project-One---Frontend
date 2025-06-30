package com.example.universaldownloader.otpUI

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.universaldownloader.ViewModels.OtpViewModel

@Composable
fun OtpInput(
    length: Int = 6,
    onOtpComplete: (String) -> Unit,
    modifier: Modifier=Modifier,
    viewModel: OtpViewModel
) {

    val btnColor=Color(0xFF636AE8)
    var otpValues by remember { mutableStateOf(List(length) { "" }) }
    val focusRequesters = List(length) { FocusRequester() }

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {

        otpValues.forEachIndexed { index, value ->
            OutlinedTextField(
                value = value,
                onValueChange ={ newValue ->
                    val oldValue = otpValues[index]
                    if (newValue.length <= 1) {
                        otpValues = otpValues.toMutableList().apply {
                            this[index] = newValue
                        }

                        // ✅ Move focus forward
                        if (newValue.length == 1 && index < length - 1) {
                            focusRequesters[index + 1].requestFocus()
                        }

                        // ✅ Move focus backward if value deleted
                        if (newValue.isEmpty() && oldValue.isNotEmpty() && index > 0) {
                            focusRequesters[index - 1].requestFocus()

                        }

                        // ✅ Trigger callback when all fields are filled
                        if (otpValues.all { it.isNotEmpty() }) {
                            onOtpComplete(otpValues.joinToString(""))
                            viewModel.setAllFilled(true)

                        }
                        else{
                            viewModel.setAllFilled(false)
                        }
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor =btnColor,
                    unfocusedIndicatorColor = btnColor,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    unfocusedTextColor = Color.White,
                    focusedTextColor = Color.White

                ),
                modifier = Modifier
                    .width(43.dp)
                    .focusRequester(focusRequesters[index])
                ,
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
            )
        }
    }
}
