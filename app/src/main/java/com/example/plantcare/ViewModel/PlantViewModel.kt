package com.example.plantcare.ViewModel

import android.graphics.Bitmap
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plantcare.Constant
import com.example.plantcare.Utils.formatText
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlantViewModel():ViewModel() {
    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = Constant.apiKey
    )

    private val _plantDiseaseResult = MutableStateFlow<AnnotatedString?>(null)
    val plantDiseaseResult = _plantDiseaseResult.asStateFlow()

    fun plantDiseaseResult(imageBitmap: Bitmap) {
        val prompt: String =
            "Analyze the provided image and determine if the plant has any diseases, deficiencies, or health issues. If a disease or problem is detected, provide a detailed diagnosis along with possible causes. Suggest effective treatment solutions, such as organic or chemical remedies, soil improvements, watering adjustments, or sunlight requirements. Additionally, offer general plant care tips, including ideal environmental conditions, pest control strategies, and fertilization recommendations to maintain long-term plant health."
        _plantDiseaseResult.value = AnnotatedString("Generating ...")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = generativeModel.generateContent(
                    content {
                        text(prompt)
                        image(imageBitmap)
                    }
                )
                // Update the UI with the generated and formatted text
                _plantDiseaseResult.value = result.text?.let { formatText(it) }
            } catch (e: Exception) {
                _plantDiseaseResult.value = AnnotatedString("Error: ${e.localizedMessage}")
            }
        }
    }
}