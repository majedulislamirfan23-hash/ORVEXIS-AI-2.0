package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Accessibility
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.components.ConfirmationDialog
import com.example.designsystem.components.DashboardCard
import com.example.designsystem.components.OrvexisSearchBar
import com.example.designsystem.components.OrvexisToggleSwitch
import com.example.designsystem.components.PrimaryButton
import com.example.designsystem.components.SecondaryButton
import com.example.designsystem.components.SettingsRow
import com.example.designsystem.components.SettingsSection
import com.example.model.ScreenDestination
import com.example.ui.theme.BorderGlow
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.ChampagneGold
import com.example.ui.theme.CoolGrey
import com.example.ui.theme.DeepGraphite
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.EmeraldAura
import com.example.ui.theme.ErrorRose
import com.example.ui.theme.MutedGrey
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.SoftWhite
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SurfaceContainer
import com.example.viewmodel.OrvexisViewModel

enum class SettingsCategory(val displayName: String) {
    GENERAL("General"),
    ACCOUNT("Account"),
    FEATURES("Features"),
    AGENT("Agent"),
    EXTENSIONS("Extensions"),
    STORE("Store"),
    PERMISSIONS("Permissions"),
    ADVANCED("Advanced")
}

@Composable
fun SettingsScreen(
    viewModel: OrvexisViewModel,
    modifier: Modifier = Modifier
) {
    val settings by viewModel.settings.collectAsState()
    var selectedCategory by remember { mutableStateOf(SettingsCategory.FEATURES) }
    var searchQuery by remember { mutableStateOf("") }
    var showResetDialog by remember { mutableStateOf(false) }
    val primaryColor = MaterialTheme.colorScheme.primary

    LazyColumn(
        modifier = modifier
            .testTag("settings_screen")
            .fillMaxSize()
            .background(ObsidianBlack)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Settings Center",
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = SoftWhite
                        )
                    )
                    Text(
                        text = "Complete executive control over telemetry, models, and interface parameters.",
                        style = MaterialTheme.typography.bodyMedium.copy(color = CoolGrey)
                    )
                }
            }
        }

        // Category Navigation Tabs (General, Account, Features, Agent, Extensions, Store, Permissions, Advanced)
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(SettingsCategory.values()) { cat ->
                    val isSelected = cat == selectedCategory
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) primaryColor else DeepGraphite)
                            .border(
                                1.dp,
                                if (isSelected) primaryColor else BorderGlow,
                                RoundedCornerShape(10.dp)
                            )
                            .clickable { selectedCategory = cat }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = cat.displayName,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) SoftWhite else CoolGrey
                            )
                        )
                    }
                }
            }
        }

        // 1. Active Provider Card (Spectrion Pro - Unlimited)
        item {
            Column {
                Text(
                    text = "ACTIVE PROVIDER",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = CoolGrey,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                DashboardCard(
                    borderColor = BorderGlow,
                    backgroundColor = DeepGraphite
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(primaryColor),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("S", fontWeight = FontWeight.Bold, fontSize = 20.sp, color = SoftWhite)
                            }
                            Spacer(modifier = Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = "Spectrion Pro",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = SoftWhite
                                    )
                                )
                                Text(
                                    text = "Automatic model routing • Unlimited capacity",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontSize = 12.sp,
                                        color = CoolGrey
                                    )
                                )
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(SuccessGreen, CircleShape)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Active",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = SuccessGreen
                            )
                        }
                    }
                }
            }
        }

        // 2. Cognitive Features Section (Long-term Memory, Extensions Hub, Show Tool Steps, Auto-Route, Heartbeat)
        item {
            SettingsSection(title = "Cognitive Features & Tooling") {
                SettingsRow(
                    title = "Long-term Memory",
                    subtitle = "Agent remembers key user preferences and project context across sessions",
                    icon = Icons.Default.Memory,
                    trailingContent = {
                        OrvexisToggleSwitch(
                            checked = settings.longTermMemory,
                            onCheckedChange = { checked ->
                                viewModel.updateSettings { it.copy(longTermMemory = checked) }
                            }
                        )
                    }
                )

                SettingsRow(
                    title = "Extensions Hub",
                    subtitle = "MCP servers, autonomous skills, and connected tools integration",
                    icon = Icons.Default.Tune,
                    trailingContent = {
                        OrvexisToggleSwitch(
                            checked = settings.extensionsHub,
                            onCheckedChange = { checked ->
                                viewModel.updateSettings { it.copy(extensionsHub = checked) }
                            }
                        )
                    }
                )

                SettingsRow(
                    title = "Show Tool & Thinking Steps",
                    subtitle = "Render live reasoning trace and step-by-step thinking in responses",
                    icon = Icons.Default.Psychology,
                    trailingContent = {
                        OrvexisToggleSwitch(
                            checked = settings.showToolSteps,
                            onCheckedChange = { checked ->
                                viewModel.updateSettings { it.copy(showToolSteps = checked) }
                            }
                        )
                    }
                )

                SettingsRow(
                    title = "Auto-Route Messages",
                    subtitle = "Automatically select the optimal specialized model for each prompt",
                    icon = Icons.Default.AutoAwesome,
                    trailingContent = {
                        OrvexisToggleSwitch(
                            checked = settings.autoRouteMessages,
                            onCheckedChange = { checked ->
                                viewModel.updateSettings { it.copy(autoRouteMessages = checked) }
                            }
                        )
                    }
                )

                SettingsRow(
                    title = "Heartbeat Sync",
                    subtitle = "Autonomous background checks every 30 minutes for task state updates",
                    icon = Icons.Default.Favorite,
                    showDivider = false,
                    trailingContent = {
                        OrvexisToggleSwitch(
                            checked = settings.heartbeat,
                            onCheckedChange = { checked ->
                                viewModel.updateSettings { it.copy(heartbeat = checked) }
                            }
                        )
                    }
                )
            }
        }

        // 3. Extended Thinking Depth
        item {
            Column {
                Text(
                    text = "EXTENDED THINKING",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = CoolGrey,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                DashboardCard(
                    borderColor = BorderGlow,
                    backgroundColor = DeepGraphite
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Default Reasoning Level",
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Medium,
                                        color = SoftWhite
                                    )
                                )
                                Text(
                                    text = "Depth of cognitive deduction before responding",
                                    fontSize = 12.sp,
                                    color = CoolGrey
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .background(SurfaceContainer, RoundedCornerShape(8.dp))
                                    .border(0.5.dp, BorderSubtle, RoundedCornerShape(8.dp))
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = settings.defaultThinkingLevel,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold,
                                    color = ChampagneGold
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            listOf("Quick (1K)", "Moderate (4K)", "Extreme (16K)").forEach { level ->
                                val isSelected = settings.defaultThinkingLevel == level
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(if (isSelected) primaryColor else SurfaceContainer)
                                        .border(1.dp, if (isSelected) primaryColor else BorderSubtle, RoundedCornerShape(10.dp))
                                        .clickable {
                                            viewModel.updateSettings { it.copy(defaultThinkingLevel = level) }
                                        }
                                        .padding(vertical = 10.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = level,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = if (isSelected) SoftWhite else CoolGrey
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 4. Quick Customization Shortcut
        item {
            SettingsSection(title = "Profile & Vault Repository") {
                SettingsRow(
                    title = "Profile Persona",
                    subtitle = "Customize name, title, status aura, and avatar style",
                    icon = Icons.Default.Person,
                    onClick = { viewModel.navigateTo(ScreenDestination.PROFILE) },
                    trailingContent = {
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Open",
                            tint = CoolGrey
                        )
                    }
                )

                SettingsRow(
                    title = "Saved Vault Repository",
                    subtitle = "Manage bookmarked prompts, code snippets, and responses",
                    icon = Icons.Default.Bookmark,
                    onClick = { viewModel.navigateTo(ScreenDestination.SAVED_VAULT) },
                    trailingContent = {
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Open",
                            tint = CoolGrey
                        )
                    }
                )

                SettingsRow(
                    title = "Customization Center",
                    subtitle = "Customize accent colors, layout density, and live UI preview",
                    icon = Icons.Default.Palette,
                    onClick = { viewModel.navigateTo(ScreenDestination.CUSTOMIZATION) },
                    trailingContent = {
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Open",
                            tint = CoolGrey
                        )
                    }
                )

                SettingsRow(
                    title = "Subscription & Usage",
                    subtitle = "Manage Orvexis Pro tier, billing cycle, and token limits",
                    icon = Icons.Default.Security,
                    showDivider = false,
                    onClick = { viewModel.navigateTo(ScreenDestination.UPGRADE) },
                    trailingContent = {
                        Icon(
                            imageVector = Icons.Default.ChevronRight,
                            contentDescription = "Open",
                            tint = CoolGrey
                        )
                    }
                )
            }
        }

        // 5. Reset to Default Button
        item {
            Spacer(modifier = Modifier.height(8.dp))
            SecondaryButton(
                text = "Reset All Settings to Factory Default",
                onClick = { showResetDialog = true },
                icon = Icons.Default.RestartAlt,
                modifier = Modifier.fillMaxWidth()
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    if (showResetDialog) {
        ConfirmationDialog(
            title = "Reset All Settings?",
            message = "This will restore theme accents, cognitive features, reasoning depths, and layout density back to standard factory presets.",
            confirmText = "Reset",
            cancelText = "Cancel",
            onConfirm = {
                viewModel.resetSettingsToDefault()
            },
            onDismiss = { showResetDialog = false }
        )
    }
}
