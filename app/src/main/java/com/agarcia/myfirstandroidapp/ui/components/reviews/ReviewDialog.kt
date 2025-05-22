package com.agarcia.myfirstandroidapp.ui.components.reviews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.agarcia.myfirstandroidapp.data.model.Review

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewDialog(
    movieId: Int,
    existingReview: Review? = null, // null for create, Review object for edit
    onDismiss: () -> Unit,
    onSubmit: (Review) -> Unit,
    modifier: Modifier = Modifier
) {
    val isEditing = existingReview != null

    var author by remember { mutableStateOf("") }
    var rating by remember { mutableDoubleStateOf(0.0) }
    var isRatingDropdownExpanded by remember { mutableStateOf(false) }
    var description by remember { mutableStateOf("") }
    var authorError by remember { mutableStateOf(false) }
    var descriptionError by remember { mutableStateOf(false) }

    // Initialize fields with existing review data when editing
    LaunchedEffect(existingReview) {
        existingReview?.let { review ->
            author = review.author
            rating = review.rating
            description = review.description
        }
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Title
                Text(
                    text = if (isEditing) "Editar Reseña" else "Agregar Reseña",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                // Author field
                OutlinedTextField(
                    value = author,
                    onValueChange = {
                        author = it
                        authorError = false
                    },
                    label = { Text("Autor") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = authorError,
                    supportingText = if (authorError) {
                        { Text("El nombre del autor es obligatorio") }
                    } else null
                )

                // Rating dropdown section
                Column {
                    Text(
                        text = "Calificación",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    ExposedDropdownMenuBox(
                        expanded = isRatingDropdownExpanded,
                        onExpandedChange = { isRatingDropdownExpanded = !isRatingDropdownExpanded }
                    ) {
                        OutlinedTextField(
                            value = if (rating == 0.0) "Seleccionar calificación" else "$rating/5.0",
                            onValueChange = {},
                            readOnly = true,
                            label = { Text("Calificación") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isRatingDropdownExpanded) },
                            modifier = Modifier
                                .menuAnchor()
                                .fillMaxWidth()
                        )

                        ExposedDropdownMenu(
                            expanded = isRatingDropdownExpanded,
                            onDismissRequest = { isRatingDropdownExpanded = false }
                        ) {
                            val ratingOptions = listOf(0.0, 0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0)

                            ratingOptions.forEach { ratingOption ->
                                DropdownMenuItem(
                                    text = {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Text("$ratingOption/5.0")
                                        }
                                    },
                                    onClick = {
                                        rating = ratingOption
                                        isRatingDropdownExpanded = false
                                    }
                                )
                            }
                        }
                    }
                }

                // Description field
                OutlinedTextField(
                    value = description,
                    onValueChange = {
                        description = it
                        descriptionError = false
                    },
                    label = { Text("Descripción") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    maxLines = 5,
                    isError = descriptionError,
                    supportingText = if (descriptionError) {
                        { Text("La descripción es obligatoria") }
                    } else null
                )

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row {
                        TextButton(onClick = onDismiss) {
                            Text("Cancelar")
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Button(
                            onClick = {
                                // Validate fields
                                authorError = author.isBlank()
                                descriptionError = description.isBlank()

                                if (!authorError && !descriptionError && rating > 0) {
                                    val review = if (isEditing && existingReview != null) {
                                        // Update existing review
                                        existingReview.copy(
                                            author = author.trim(),
                                            rating = rating,
                                            description = description.trim()
                                        )
                                    } else {
                                        // Create new review
                                        Review(
                                            id = 0, // This will be generated by your data layer
                                            author = author.trim(),
                                            rating = rating,
                                            description = description.trim(),
                                            movieId = movieId
                                        )
                                    }
                                    onSubmit(review)
                                }
                            }
                        ) {
                            Text(if (isEditing) "Actualizar" else "Guardar")
                        }
                    }
                }
            }
        }
    }
}