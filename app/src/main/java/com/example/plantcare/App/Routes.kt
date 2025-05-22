package com.example.plantcare.App

import kotlinx.serialization.Serializable

sealed class Routes{
    @Serializable
    object Login:Routes()

    @Serializable
    object SignUp:Routes()

    @Serializable
    object NewsUI:Routes()

    @Serializable
    object PlantDisease:Routes()

    @Serializable
    object BottomBar:Routes()

    @Serializable
    object Profile:Routes()
}