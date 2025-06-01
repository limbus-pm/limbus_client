package com.example.limbus_client.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * A reusable step indicator for multi-step forms
 * Updated version with connecting lines between steps
 *
 * @param currentStep The current active step (1-based)
 * @param totalSteps Total number of steps in the flow
 * @param modifier Optional modifier for customizing the layout
 * @param activeColor The color for active step indicators
 * @param inactiveColor The color for inactive step indicators
 * @param showConnectingLines Whether to show lines connecting the steps
 * @param stepSize Size of each step indicator circle
 */
@Composable
fun StepIndicator(
    currentStep: Int,
    totalSteps: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = Color(0xFF3F51B5),
    inactiveColor: Color = Color.LightGray,
    showConnectingLines: Boolean = true,
    stepSize: Int = 12
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(totalSteps) { step ->
            val stepNumber = step + 1
            val isActive = stepNumber <= currentStep
            val isCompleted = stepNumber < currentStep

            // Step indicator circle
            Box(
                modifier = Modifier
                    .size(stepSize.dp)
                    .clip(CircleShape)
                    .background(
                        color = when {
                            isCompleted -> activeColor
                            isActive -> activeColor
                            else -> inactiveColor
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                // You can add a check icon for completed steps if needed
                // For now, keeping it simple with just colored circles
            }

            // Add connecting line between steps (except after the last step)
            if (showConnectingLines && step < totalSteps - 1) {
                Box(
                    modifier = Modifier
                        .width(24.dp)
                        .height(2.dp)
                        .background(
                            color = if (stepNumber < currentStep) activeColor else inactiveColor
                        )
                )
            } else if (!showConnectingLines && step < totalSteps - 1) {
                // Add spacing between dots when not showing lines
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}

/**
 * Alternative version that maintains your original simple dot style
 * This version is closer to your existing implementation
 */
@Composable
fun SimpleStepIndicator(
    currentStep: Int,
    totalSteps: Int,
    modifier: Modifier = Modifier,
    activeColor: Color = Color(0xFF3F51B5),
    inactiveColor: Color = Color.LightGray
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..totalSteps) {
            val isActive = i <= currentStep
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(8.dp)
                    .background(
                        color = if (isActive) activeColor else inactiveColor,
                        shape = CircleShape
                    )
            )
        }
    }
}

/**
 * Enhanced version with numbered steps and optional labels
 */
@Composable
fun NumberedStepIndicator(
    currentStep: Int,
    totalSteps: Int,
    stepLabels: List<String> = emptyList(),
    modifier: Modifier = Modifier,
    activeColor: Color = Color(0xFF3F51B5),
    inactiveColor: Color = Color.LightGray,
    completedColor: Color = Color(0xFF4CAF50)
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Step indicators row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(totalSteps) { step ->
                val stepNumber = step + 1
                val isActive = stepNumber == currentStep
                val isCompleted = stepNumber < currentStep
                val isUpcoming = stepNumber > currentStep

                // Step circle with number
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(
                            color = when {
                                isCompleted -> completedColor
                                isActive -> activeColor
                                else -> inactiveColor
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.material3.Text(
                        text = stepNumber.toString(),
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                // Connecting line
                if (step < totalSteps - 1) {
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(2.dp)
                            .background(
                                color = if (stepNumber < currentStep) completedColor else inactiveColor
                            )
                    )
                }
            }
        }

        // Step labels (if provided)
        if (stepLabels.isNotEmpty() && stepLabels.size >= totalSteps) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                stepLabels.take(totalSteps).forEachIndexed { index, label ->
                    val stepNumber = index + 1
                    val isActive = stepNumber <= currentStep

                    androidx.compose.material3.Text(
                        text = label,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (isActive) activeColor else inactiveColor,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}