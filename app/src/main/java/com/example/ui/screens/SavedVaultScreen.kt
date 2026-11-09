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
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.DeleteOutline
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.components.CopyIconButton
import com.example.designsystem.components.DashboardCard
import com.example.designsystem.components.EmptyState
import com.example.designsystem.components.OrvexisSearchBar
import com.example.designsystem.components.SecondaryButton
import com.example.model.SavedItem
import com.example.model.SavedItemType
import com.example.model.ScreenDestination
import com.example.ui.theme.BorderGlow
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.ChampagneGold
import com.example.ui.theme.CoolGrey
import com.example.ui.theme.DeepGraphite
import com.example.ui.theme.ErrorRose
import com.example.ui.theme.MutedGrey
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.SoftWhite
import com.example.ui.theme.SurfaceContainer
import com.example.viewmodel.OrvexisViewModel

@Composable
fun SavedVaultScreen(
    viewModel: OrvexisViewModel,
    modifier: Modifier = Modifier
) {
    val savedItems by viewModel.savedItems.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf(SavedItemType.ALL) }
    val primaryColor = MaterialTheme.colorScheme.primary

    val filteredItems = remember(savedItems, searchQuery, selectedFilter) {
        savedItems.filter { item ->
            val matchesType = selectedFilter == SavedItemType.ALL || item.type == selectedFilter
            val matchesQuery = searchQuery.isBlank() ||
                    item.title.contains(searchQuery, ignoreCase = true) ||
                    item.content.contains(searchQuery, ignoreCase = true) ||
                    item.categoryTag.contains(searchQuery, ignoreCase = true)
            matchesType && matchesQuery
        }
    }

    LazyColumn(
        modifier = modifier
            .testTag("saved_vault_screen")
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
                        text = "Saved Vault",
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = SoftWhite
                        )
                    )
                    Text(
                        text = "Your centralized repository of saved prompts, synthesized code, and breakthrough responses.",
                        style = MaterialTheme.typography.bodyMedium.copy(color = CoolGrey)
                    )
                }
            }
        }

        // Search Bar
        item {
            OrvexisSearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                placeholder = "Search saved code, prompts, notes..."
            )
        }

        // Filter Pills (All, Code, Responses, Prompts, Tools)
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(SavedItemType.values()) { type ->
                    val isSelected = type == selectedFilter
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) primaryColor else DeepGraphite)
                            .border(
                                1.dp,
                                if (isSelected) primaryColor else BorderGlow,
                                RoundedCornerShape(10.dp)
                            )
                            .clickable { selectedFilter = type }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = type.displayName,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) SoftWhite else CoolGrey
                            )
                        )
                    }
                }
            }
        }

        // Count indicator
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${filteredItems.size} SAVED ITEMS",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = CoolGrey,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                )
            }
        }

        // Items List or Empty State
        if (filteredItems.isEmpty()) {
            item {
                EmptyState(
                    title = if (searchQuery.isNotBlank()) "No items match '$searchQuery'" else "Your Saved Vault is Empty",
                    subtitle = if (searchQuery.isNotBlank()) "Try searching for a different keyword or resetting filters." else "Tap the bookmark icon on any AI chat response, generated code snippet, or tool output to save it here for instant one-tap recall.",
                    icon = Icons.Default.BookmarkBorder,
                    actionText = if (searchQuery.isNotBlank()) "Clear Filter" else "Explore AI Chat",
                    onAction = {
                        if (searchQuery.isNotBlank()) {
                            searchQuery = ""
                            selectedFilter = SavedItemType.ALL
                        } else {
                            viewModel.navigateTo(ScreenDestination.AI_CHAT)
                        }
                    }
                )
            }
        } else {
            items(filteredItems, key = { it.id }) { item ->
                SavedVaultCard(
                    item = item,
                    primaryColor = primaryColor,
                    onSendToChat = {
                        viewModel.sendMessage("Review this saved note: ${item.title}\n\n${item.content}")
                        viewModel.navigateTo(ScreenDestination.AI_CHAT)
                    },
                    onDelete = { viewModel.deleteSavedItem(item.id) },
                    onCopied = { viewModel.showToast("Copied to clipboard!") }
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun SavedVaultCard(
    item: SavedItem,
    primaryColor: androidx.compose.ui.graphics.Color,
    onSendToChat: () -> Unit,
    onDelete: () -> Unit,
    onCopied: () -> Unit
) {
    val isCode = item.type == SavedItemType.CODE

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
                    Box(
                        modifier = Modifier
                            .background(SurfaceContainer, RoundedCornerShape(6.dp))
                            .border(0.5.dp, BorderSubtle, RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = item.type.displayName,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = when (item.type) {
                                SavedItemType.CODE -> NeonCyan
                                SavedItemType.RESPONSE -> ChampagneGold
                                SavedItemType.PROMPT -> primaryColor
                                else -> SoftWhite
                            }
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = item.categoryTag,
                        fontSize = 11.sp,
                        color = CoolGrey
                    )
                }

                Text(
                    text = item.timestamp,
                    fontSize = 10.sp,
                    color = MutedGrey
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = SoftWhite
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(6.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(SurfaceContainer)
                    .border(0.5.dp, BorderSubtle, RoundedCornerShape(10.dp))
                    .padding(12.dp)
            ) {
                Text(
                    text = item.content,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = if (isCode) FontFamily.Monospace else FontFamily.SansSerif,
                        fontSize = if (isCode) 12.sp else 13.sp,
                        lineHeight = 18.sp,
                        color = SoftWhite
                    ),
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Send to chat action
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainer)
                            .border(0.5.dp, BorderSubtle, RoundedCornerShape(8.dp))
                            .clickable(onClick = onSendToChat)
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ChatBubbleOutline,
                            contentDescription = "Send to Chat",
                            tint = primaryColor,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Send to Chat",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = SoftWhite
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // 1-tap Copy Button
                    CopyIconButton(
                        textToCopy = item.content,
                        onCopied = onCopied
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Delete Button
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainer)
                            .border(0.5.dp, BorderSubtle, RoundedCornerShape(8.dp))
                            .clickable(onClick = onDelete),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.DeleteOutline,
                            contentDescription = "Delete from vault",
                            tint = ErrorRose,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}
