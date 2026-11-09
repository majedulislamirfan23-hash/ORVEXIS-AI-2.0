package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.components.BookmarkIconButton
import com.example.designsystem.components.CopyIconButton
import com.example.designsystem.components.DashboardCard
import com.example.designsystem.components.PrimaryButton
import com.example.designsystem.components.SecondaryButton
import com.example.model.SavedItemType
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

enum class StudioTab(val label: String) {
    CODE_STUDIO("Code Studio"),
    WRITING_STUDIO("Writing Studio"),
    PROMPT_LAB("Prompt Lab")
}

@Composable
fun CreateStudioScreen(
    viewModel: OrvexisViewModel,
    modifier: Modifier = Modifier
) {
    var activeTab by remember { mutableStateOf(StudioTab.CODE_STUDIO) }
    var promptInput by remember { mutableStateOf("Build a production Kotlin state machine for Jetpack Compose with sealed UI states.") }
    var isSynthesizing by remember { mutableStateOf(false) }
    var generatedCode by remember {
        mutableStateOf(
            "// ORVEXIS Generated State Pattern\n" +
            "sealed interface UiState {\n" +
            "    data object Idle : UiState\n" +
            "    data class Loading(val progress: Float = 0f) : UiState\n" +
            "    data class Success<T>(val payload: T) : UiState\n" +
            "    data class Error(val cause: String) : UiState\n" +
            "}"
        )
    }

    val context = LocalContext.current
    val primaryColor = MaterialTheme.colorScheme.primary

    LazyColumn(
        modifier = modifier
            .testTag("create_studio_screen")
            .fillMaxSize()
            .background(ObsidianBlack)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Create Studio",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = SoftWhite
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Autonomous generation workstation for code, prose, and prompt architecture.",
                style = MaterialTheme.typography.bodyMedium.copy(color = CoolGrey)
            )
        }

        // Tab Selector
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(DeepGraphite)
                    .border(1.dp, BorderGlow, RoundedCornerShape(12.dp))
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                StudioTab.values().forEach { tab ->
                    val isSelected = tab == activeTab
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (isSelected) primaryColor else SurfaceContainer)
                            .clickable {
                                activeTab = tab
                                when (tab) {
                                    StudioTab.CODE_STUDIO -> {
                                        promptInput = "Build a production Kotlin state machine for Jetpack Compose with sealed UI states."
                                        generatedCode = "// ORVEXIS Generated State Pattern\nsealed interface UiState {\n    data object Idle : UiState\n    data class Success<T>(val payload: T) : UiState\n}"
                                    }
                                    StudioTab.WRITING_STUDIO -> {
                                        promptInput = "Draft an executive keynote script introducing the ORVEXIS neural platform."
                                        generatedCode = "\"Ladies and gentlemen, today we introduce ORVEXIS AI—an operating system where intelligence is not an assistant, but the fundamental fabric of human leverage.\""
                                    }
                                    StudioTab.PROMPT_LAB -> {
                                        promptInput = "Synthesize an ultra-realistic Midjourney v6 prompt for a luxury futuristic console."
                                        generatedCode = "/imagine prompt: obsidian dark executive workspace console, violet volumetric neon aura, champagne gold accents, 8k resolution, raytracing, minimalistic hyper-clean aesthetic --ar 16:9 --v 6.0"
                                    }
                                }
                            }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = tab.label,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) SoftWhite else CoolGrey
                        )
                    }
                }
            }
        }

        // Prompt Input Card
        item {
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
                        Text(
                            text = "Generation Directive",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = SoftWhite
                            )
                        )
                        Box(
                            modifier = Modifier
                                .background(SurfaceContainer, RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "Spectrion Core",
                                fontSize = 11.sp,
                                color = NeonCyan
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(84.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainer)
                            .border(1.dp, BorderSubtle, RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        androidx.compose.foundation.text.BasicTextField(
                            value = promptInput,
                            onValueChange = { promptInput = it },
                            textStyle = MaterialTheme.typography.bodyMedium.copy(color = SoftWhite),
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        PrimaryButton(
                            text = if (isSynthesizing) "Synthesizing..." else "Generate Output",
                            onClick = {
                                isSynthesizing = true
                                android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                                    isSynthesizing = false
                                    generatedCode = when (activeTab) {
                                        StudioTab.CODE_STUDIO ->
                                            "// Synthesized Jetpack Compose Architecture\n@Composable\nfun OrvexisDashboardSurface() {\n    Box(\n        modifier = Modifier\n            .fillMaxWidth()\n            .clip(RoundedCornerShape(16.dp))\n            .background(Color(0xFF080910))\n            .border(1.dp, Color(0xFF1F2438), RoundedCornerShape(16.dp))\n            .padding(16.dp)\n    ) {\n        Text(\"Autonomous Systems Active\", color = Color(0xFFF5F5FA))\n    }\n}"
                                        StudioTab.WRITING_STUDIO ->
                                            "EXECUTIVE REPORT:\n\n1. Market Positioning: ORVEXIS captures the elite frontier of cognitive productivity.\n2. Throughput Multipliers: Autonomous dispatching reduces operational drag by 68%.\n3. Strategic Runway: Sustained 99.8% precision across multi-turn reasoning."
                                        StudioTab.PROMPT_LAB ->
                                            "/imagine prompt: futuristic high-tech laboratory, obsidian glass panels, holographic neon violet charts, cybernetic aesthetic, ultra realistic, cinematic lighting, 8k --v 6.0"
                                    }
                                    viewModel.showToast("Synthesized with ${activeTab.label}")
                                }, 1000)
                            },
                            isLoading = isSynthesizing,
                            leadingIcon = Icons.Default.AutoAwesome
                        )
                    }
                }
            }
        }

        // Synthesized Output Terminal Card
        item {
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
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Terminal,
                                contentDescription = null,
                                tint = primaryColor,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Engine Output",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = SoftWhite
                                )
                            )
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            val isSaved = viewModel.isItemSaved(generatedCode)
                            BookmarkIconButton(
                                isBookmarked = isSaved,
                                onToggle = {
                                    if (isSaved) {
                                        val item = viewModel.savedItems.value.find { it.content.trim() == generatedCode.trim() }
                                        if (item != null) viewModel.deleteSavedItem(item.id)
                                    } else {
                                        viewModel.saveItem(
                                            title = "${activeTab.label} Synthesis",
                                            content = generatedCode,
                                            type = when (activeTab) {
                                                StudioTab.CODE_STUDIO -> SavedItemType.CODE
                                                StudioTab.WRITING_STUDIO -> SavedItemType.RESPONSE
                                                StudioTab.PROMPT_LAB -> SavedItemType.PROMPT
                                            },
                                            categoryTag = activeTab.label
                                        )
                                    }
                                }
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            CopyIconButton(
                                textToCopy = generatedCode
                            )

                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Send to Chat →",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = NeonCyan,
                                modifier = Modifier.clickable {
                                    viewModel.sendMessage("Review this studio output:\n\n$generatedCode")
                                    viewModel.navigateTo(com.example.model.ScreenDestination.AI_CHAT)
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainer)
                            .border(1.dp, BorderSubtle, RoundedCornerShape(12.dp))
                            .padding(14.dp)
                    ) {
                        Text(
                            text = generatedCode,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 12.sp,
                                lineHeight = 18.sp,
                                color = SoftWhite
                            )
                        )
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
