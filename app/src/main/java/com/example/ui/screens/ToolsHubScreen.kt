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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.designsystem.components.BookmarkIconButton
import com.example.designsystem.components.CopyIconButton
import com.example.designsystem.components.EmptyState
import com.example.designsystem.components.OrvexisSearchBar
import com.example.designsystem.components.PrimaryButton
import com.example.designsystem.components.SecondaryButton
import com.example.designsystem.components.ToolCard
import com.example.model.SavedItemType
import com.example.model.ToolCategory
import com.example.model.ToolItem
import com.example.ui.theme.BorderGlow
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.ChampagneGold
import com.example.ui.theme.CoolGrey
import com.example.ui.theme.DeepGraphite
import com.example.ui.theme.MutedGrey
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.SoftWhite
import com.example.ui.theme.SurfaceContainer
import com.example.viewmodel.OrvexisViewModel

@Composable
fun ToolsHubScreen(
    viewModel: OrvexisViewModel,
    modifier: Modifier = Modifier
) {
    val tools by viewModel.tools.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val activeToolForRun by viewModel.activeToolForRun.collectAsState()
    val primaryColor = MaterialTheme.colorScheme.primary

    val filteredTools = remember(tools, searchQuery, selectedCategory) {
        tools.filter { tool ->
            val matchesCategory = selectedCategory == ToolCategory.ALL || tool.category == selectedCategory
            val matchesQuery = searchQuery.isBlank() ||
                    tool.name.contains(searchQuery, ignoreCase = true) ||
                    tool.description.contains(searchQuery, ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }

    LazyColumn(
        modifier = modifier
            .testTag("tools_hub_screen")
            .fillMaxSize()
            .background(ObsidianBlack)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Tools Hub",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = SoftWhite
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Autonomous intelligent engines categorized for executive workflows.",
                style = MaterialTheme.typography.bodyMedium.copy(color = CoolGrey)
            )
        }

        // Search Bar
        item {
            OrvexisSearchBar(
                query = searchQuery,
                onQueryChange = { viewModel.setSearchQuery(it) }
            )
        }

        // Category Filter Chips
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(ToolCategory.values()) { category ->
                    val isSelected = category == selectedCategory
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) primaryColor else DeepGraphite)
                            .border(
                                1.dp,
                                if (isSelected) primaryColor else BorderGlow,
                                RoundedCornerShape(10.dp)
                            )
                            .clickable { viewModel.setCategory(category) }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = category.displayName,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) SoftWhite else CoolGrey
                            )
                        )
                    }
                }
            }
        }

        // Tools Count
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${filteredTools.size} Tools Available",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = CoolGrey,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                )
            }
        }

        // Tools Grid
        if (filteredTools.isEmpty()) {
            item {
                EmptyState(
                    title = "No tools matched '$searchQuery'",
                    subtitle = "Try adjusting your search terms or filter to another category.",
                    icon = Icons.Default.SearchOff,
                    actionText = "Reset Filters",
                    onAction = {
                        viewModel.setSearchQuery("")
                        viewModel.setCategory(ToolCategory.ALL)
                    }
                )
            }
        } else {
            items(filteredTools, key = { it.id }) { tool ->
                ToolCard(
                    tool = tool,
                    onOpen = { viewModel.openToolRunner(tool) },
                    onToggleFavorite = { viewModel.toggleFavorite(it) }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    // Interactive Tool Execution Modal
    if (activeToolForRun != null) {
        ToolRunnerDialog(
            tool = activeToolForRun!!,
            viewModel = viewModel,
            onClose = { viewModel.closeToolRunner() },
            onSendToChat = { prompt ->
                viewModel.closeToolRunner()
                viewModel.sendMessage(prompt)
                viewModel.navigateTo(com.example.model.ScreenDestination.AI_CHAT)
            }
        )
    }
}

@Composable
fun ToolRunnerDialog(
    tool: ToolItem,
    viewModel: OrvexisViewModel,
    onClose: () -> Unit,
    onSendToChat: (String) -> Unit
) {
    var promptInput by remember { mutableStateOf(tool.defaultPrompt) }
    var isRunning by remember { mutableStateOf(false) }
    var executionResult by remember { mutableStateOf<String?>(null) }
    val primaryColor = MaterialTheme.colorScheme.primary

    Dialog(onDismissRequest = onClose) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(DeepGraphite)
                .border(1.dp, BorderGlow, RoundedCornerShape(20.dp))
                .padding(20.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = tool.name,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = SoftWhite
                            )
                        )
                        Text(
                            text = tool.category.displayName,
                            fontSize = 11.sp,
                            color = NeonCyan
                        )
                    }
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = CoolGrey,
                        modifier = Modifier.clickable { onClose() }
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "Prompt Input:",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = CoolGrey
                )
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(90.dp)
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

                if (isRunning) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = primaryColor,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Synthesizing with ORVEXIS engine...",
                            fontSize = 12.sp,
                            color = CoolGrey
                        )
                    }
                } else if (executionResult != null) {
                    val resultText = executionResult!!
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Engine Output:",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = ChampagneGold
                        )

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            val isSaved = viewModel.isItemSaved(resultText)
                            BookmarkIconButton(
                                isBookmarked = isSaved,
                                onToggle = {
                                    if (isSaved) {
                                        val existing = viewModel.savedItems.value.find { it.content.trim() == resultText.trim() }
                                        if (existing != null) viewModel.deleteSavedItem(existing.id)
                                    } else {
                                        viewModel.saveItem(
                                            title = "${tool.name} Run",
                                            content = resultText,
                                            type = SavedItemType.TOOL,
                                            categoryTag = tool.category.displayName
                                        )
                                    }
                                }
                            )

                            Spacer(modifier = Modifier.width(6.dp))

                            CopyIconButton(
                                textToCopy = resultText
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainer)
                            .border(1.dp, BorderGlow, RoundedCornerShape(12.dp))
                            .padding(12.dp)
                    ) {
                        Text(
                            text = resultText,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 13.sp,
                                color = SoftWhite
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    SecondaryButton(
                        text = "Transfer to Chat",
                        onClick = { onSendToChat(promptInput) }
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    PrimaryButton(
                        text = "Run Engine",
                        onClick = {
                            isRunning = true
                            executionResult = null
                            // Simulate AI synthesis
                            android.os.Handler(android.os.Looper.getMainLooper()).postDelayed({
                                isRunning = false
                                executionResult = "Output synthesized for '${tool.name}':\n\n1. Parameters verified against frontier telemetry.\n2. Optimal throughput achieved with 99.4% precision.\n3. Ready for deployment into production stream."
                            }, 1200)
                        },
                        leadingIcon = Icons.Default.PlayArrow
                    )
                }
            }
        }
    }
}
