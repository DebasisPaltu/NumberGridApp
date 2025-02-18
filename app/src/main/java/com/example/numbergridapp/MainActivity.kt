package com.example.numbergridapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.numbergridapp.ui.theme.NumberGridAppTheme
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumberGridApp()
        }
    }
}

@Composable
fun NumberGridApp() {
    var selectedRule by remember { mutableStateOf("Odd Numbers") }
    val rules = listOf("Odd Numbers", "Even Numbers", "Prime Numbers", "Fibonacci Numbers")

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        var expanded by remember { mutableStateOf(false) }

        Box(modifier = Modifier.fillMaxWidth()) {
            Button(onClick = { expanded = true }) {
                Text(text = selectedRule, modifier = Modifier.padding(end = 8.dp))
                Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Dropdown Arrow")
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                rules.forEach { rule ->
                    DropdownMenuItem(text = { Text(rule) }, onClick = {
                        selectedRule = rule
                        expanded = false
                    })
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Number Grid
        LazyVerticalGrid(columns = GridCells.Fixed(10), modifier = Modifier.fillMaxSize()) {
            items(100) { index ->
                val number = index + 1
                val highlight = shouldHighlight(number, selectedRule)
                NumberBox(number, highlight)
            }
        }
    }
}


@Composable
fun NumberBox(number: Int, highlight: Boolean) {
    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(40.dp)
            .background(if (highlight) Color.Red else Color.LightGray, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

fun shouldHighlight(number: Int, rule: String): Boolean {
    return when (rule) {
        "Odd Numbers" -> number % 2 != 0
        "Even Numbers" -> number % 2 == 0
        "Prime Numbers" -> isPrime(number)
        "Fibonacci Numbers" -> isFibonacci(number)
        else -> false
    }
}

fun isPrime(num: Int): Boolean {
    if (num < 2) return false
    for (i in 2..sqrt(num.toDouble()).toInt()) {
        if (num % i == 0) return false
    }
    return true
}

fun isFibonacci(num: Int): Boolean {
    var a = 0
    var b = 1
    while (b < num) {
        val temp = a + b
        a = b
        b = temp
    }
    return b == num || num == 0
}