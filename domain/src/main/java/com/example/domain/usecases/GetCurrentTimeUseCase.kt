package com.example.domain.usecases

import java.text.SimpleDateFormat
import java.util.Date

class GetCurrentTimeUseCase {

    fun execute(): String
    {
        val formatter = SimpleDateFormat("hh:MM:ss dd/mm/yyyy")
        val date = Date()
        return formatter.format(date)
    }
}