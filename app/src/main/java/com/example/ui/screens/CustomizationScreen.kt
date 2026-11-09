package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.components.DashboardCard
import com.example.designsystem.components.OrvexisToggleSwitch
import com.example.designsystem.components.PrimaryButton
import com.example.designsystem.components.SecondaryButton
import com.example.designsystem.components.SettingsRow
import com.example.designsystem.components.SettingsSection
import com.example.ui.theme.BorderGlow
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.ChampagneGold
import com.example.ui.theme.CoolGrey
import com.example.ui.theme.DeepGraphite
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.EmeraldAura
import com.example.ui.theme.MutedGrey
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.SoftWhite
import com.example.ui.theme.SurfaceContainer
import com.example.viewmodel.OrvexisViewModel

@Composable
fun CustomizationScreen(
    viewModel: OrvexisViewModel,
    modifier: Modifier = Modifier
) {
    val settings by viewModel.settings.collectAsState()
    val activePrimary = viewModel.getActivePrimaryColor()

    LazyColumn(
        modifier = modifier
            .testTag("customization_screen")
            .fillMaxSize()
            .background(ObsidianBlack)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Customization Center",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = SoftWhite
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Personalize your visual identity with immediate live preview rendering.",
                style = MaterialTheme.typography.bodyMedium.copy(color = CoolGrey)
            )
        }

        // 1. LIVE PREVIEW CONTAINER
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "REAL-TIME LIVE PREVIEW",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = CoolGrey,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )
                    Text(
                        text = "Dynamic Tokens",
                        fontSize = 11.sp,
                        color = activePrimary
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(
                                    activePrimary.copy(alpha = 0.2f),
                                    DeepGraphite,
                                    SurfaceContainer
                                )
                            )
                        )
                        .border(
                            1.5.dp,
                            Brush.horizontalGradient(listOf(activePrimary, BorderGlow)),
                            RoundedCornerShape(20.dp)
                        )
                        .padding(if (settings.isCompactLayout) 12.dp else 20.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(36.dp)
                                        .clip(CircleShape)
                                        .background(activePrimary),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = null,
                                        tint = SoftWhite,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = "Orvexis Neural OS",
                                        fontSize = (15 * settings.fontScale).sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SoftWhite
                                    )
                                    Text(
                                        text = "Obsidian Dark • ${settings.accentPreset} Accent",
                                        fontSize = (11 * settings.fontScale).sp,
                                        color = CoolGrey
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .background(activePrimary.copy(alpha = 0.2f), RoundedCornerShape(8.dp))
                                    .border(1.dp, activePrimary.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = if (settings.isCompactLayout) "Compact" else "Comfortable",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = activePrimary
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = "This preview updates instantly with your chosen accent color, typography scaling factor, and layout density.",
                            fontSize = (13 * settings.fontScale).sp,
                            color = SoftWhite,
                            lineHeight = (18 * settings.fontScale).sp
                        )

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(activePrimary)
                                    .padding(horizontal = 14.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = "Primary Action",
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SoftWhite
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(SurfaceContainer)
                                    .border(1.dp, BorderGlow, RoundedCornerShape(10.dp))
                                    .padding(horizontal = 14.dp, vertical = 8.dp)
                            ) {
                                Text(
                                    text = "Glass Surface",
                                    fontSize = 12.sp,
                                    color = CoolGrey
                                )
                            }
                        }
                    }
                }
            }
        }

        // 2. ACCENT COLOR PICKER
        item {
            Column {
                Text(
                    text = "ACCENT COLOR PRESET",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = CoolGrey,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                val accentOptions = listOf(
                    Triple("VIOLET", "Electric Violet", ElectricViolet),
                    Triple("CYAN", "Neon Cyan", NeonCyan),
                    Triple("GOLD", "Champagne Gold", ChampagneGold),
                    Triple("EMERALD", "Emerald Aura", EmeraldAura)
                )

                DashboardCard(
                    borderColor = BorderGlow,
                    backgroundColor = DeepGraphite
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        accentOptions.forEach { (presetKey, label, color) ->
                            val isSelected = settings.accentPreset == presetKey
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (isSelected) SurfaceContainer else DeepGraphite)
                                    .border(
                                        1.dp,
                                        if (isSelected) color else BorderSubtle,
                                        RoundedCornerShape(12.dp)
                                    )
                                    .clickable {
                                        viewModel.updateSettings { it.copy(accentPreset = presetKey) }
                                    }
                                    .padding(horizontal = 14.dp, vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(20.dp)
                                            .background(color, CircleShape)
                                            .border(2.dp, SoftWhite.copy(alpha = 0.5f), CircleShape)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Text(
                                        text = label,
                                        fontSize = 14.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = SoftWhite
                                    )
                                }
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Selected",
                                        tint = color,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 3. LAYOUT DENSITY & ACCESSIBILITY
        item {
            SettingsSection(title = "Layout & Typography") {
                SettingsRow(
                    title = "Compact Mode",
                    subtitle = "Reduce padding and spacing for high-density information display",
                    trailingContent = {
                        OrvexisToggleSwitch(
                            checked = settings.isCompactLayout,
                            onCheckedChange = { checked ->
                                viewModel.updateSettings { it.copy(isCompactLayout = checked) }
                            }
                        )
                    }
                )

                SettingsRow(
                    title = "Reduced Motion",
                    subtitle = "Disable spring motion and visual micro-animations",
                    trailingContent = {
                        OrvexisToggleSwitch(
                            checked = settings.reducedMotion,
                            onCheckedChange = { checked ->
                                viewModel.updateSettings { it.copy(reducedMotion = checked) }
                            }
                        )
                    }
                )

                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Font Scale Multiplier",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium,
                            color = SoftWhite
                        )
                        Text(
                            text = "%.1fx".format(settings.fontScale),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = activePrimary
                        )
                    }
                    Slider(
                        value = settings.fontScale,
                        onValueChange = { scale ->
                            viewModel.updateSettings { it.copy(fontScale = scale) }
                        },
                        valueRange = 0.85f..1.25f,
                        steps = 3,
                        colors = SliderDefaults.colors(
                            thumbColor = activePrimary,
                            activeTrackColor = activePrimary,
                            inactiveTrackColor = SurfaceContainer
                        )
                    )
                }
            }
        }

        // 4. Save Changes & Reset Customization Actions
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                SecondaryButton(
                    text = "Reset Preset",
                    onClick = {
                        viewModel.updateSettings {
                            it.copy(
                                accentPreset = "VIOLET",
                                isCompactLayout = false,
                                fontScale = 1.0f,
                                reducedMotion = false
                            )
                        }
                        viewModel.showToast("Theme reset to Electric Violet")
                    },
                    modifier = Modifier.weight(1f)
                )

                PrimaryButton(
                    text = "Save Changes",
                    onClick = {
                        viewModel.showToast("Customization settings saved successfully!")
                    },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
