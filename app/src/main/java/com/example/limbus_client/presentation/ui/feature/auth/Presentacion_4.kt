package com.example.limbus_client.presentation.ui.feature.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.limbus_client2.R

@Composable
fun Presentacion_4(
    onFinishClicked: () -> Unit,
    onSkipClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.TopCenter // 👈 Alineado arriba como los demás
    ) {
        // Tarjeta principal
        Card(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.95f)
                .padding(top = 48.dp), // 👈 Se sube la tarjeta visualmente
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Imagen ilustrativa
                Image(
                    painter = painterResource(id = R.drawable.imgen4),
                    contentDescription = "Funcionalidades adicionales",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.height(200.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ⬇ Puntitos de progreso (último activo)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Punto 1
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF3F51B5))
                    )
                    Spacer(modifier = Modifier.width(12.dp))

                    // Punto 2
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF3F51B5))
                    )
                    Spacer(modifier = Modifier.width(12.dp))

                    // Punto 3
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF3F51B5))
                    )
                    Spacer(modifier = Modifier.width(12.dp))

                    // Punto 4 (actual)
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF3F51B5))
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Título
                Text(
                    text = "Comienza tu viaje",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Descripción
                Text(
                    text = "Estás listo para comenzar tu camino hacia una mejor salud. Personaliza tu experiencia y disfruta de todas las funciones que Limbus tiene para ti.",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Botones inferiores
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = onSkipClicked,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        modifier = Modifier.width(120.dp)
                    ) {
                        Text("Saltar")
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = onFinishClicked,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF3F51B5)
                        ),
                        modifier = Modifier.width(120.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Comenzar")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
