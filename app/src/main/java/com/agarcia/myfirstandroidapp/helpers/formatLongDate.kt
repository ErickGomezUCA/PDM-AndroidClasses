package com.agarcia.myfirstandroidapp.helpers

import android.os.Build
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


fun formatLongDate(originalDate: String): String {
  return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    // API 26 o superior: usamos LocalDate y DateTimeFormatter
    val parsedDate = LocalDate.parse(originalDate)
    val formatter = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", Locale("es", "ES"))
    parsedDate.format(formatter)
  } else {
    // API menor a 26: usamos SimpleDateFormat
    try {
      val parser = SimpleDateFormat("yyyy-MM-dd", Locale.US)
      val formatter = SimpleDateFormat("d 'de' MMMM 'de' yyyy", Locale("es", "ES"))
      val date = parser.parse(originalDate)
      if (date != null) formatter.format(date) else originalDate
    } catch (e: Exception) {
      originalDate // En caso de error, se devuelve la fecha original
    }
  }
}