package com.example.plantcare.Utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

// Function to format text using AnnotatedString for Jetpack Compose
fun formatText(input: String): AnnotatedString {
    val builder = AnnotatedString.Builder()

    var currentIndex = 0
    val boldPattern = Regex("\\*\\*(.*?)\\*\\*")
    val italicPattern = Regex("\\*\\*\\*(.*?)\\*\\*\\*")

    // Handle **bold** formatting
    val boldMatches = boldPattern.findAll(input)
    for (match in boldMatches) {
        // Add non-bold text before the match
        if (match.range.first > currentIndex) {
            builder.append(input.substring(currentIndex, match.range.first))
        }
        // Add bold text
        builder.withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
            append(match.groupValues[1])
        }
        currentIndex = match.range.last + 1
    }

    // Continue for other matches and the remaining text
    val italicMatches = italicPattern.findAll(input)
    for (match in italicMatches) {
        // Add non-italic text before the match
        if (match.range.first > currentIndex) {
            builder.append(input.substring(currentIndex, match.range.first))
        }
        // Add italic text
        builder.withStyle(style = SpanStyle(fontStyle = FontStyle.Italic)) {
            append(match.groupValues[1])
        }
        currentIndex = match.range.last + 1
    }

    // Append remaining non-formatted text
    if (currentIndex < input.length) {
        builder.append(input.substring(currentIndex))
    }

    return builder.toAnnotatedString()
}