package com.example.ui.screens

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.components.BookmarkIconButton
import com.example.designsystem.components.CopyIconButton
import com.example.designsystem.components.OrvexisAvatar
import com.example.designsystem.components.OrvexisToggleSwitch
import com.example.designsystem.components.PrimaryButton
import com.example.designsystem.components.SecondaryButton
import com.example.model.ChatMessage
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: OrvexisViewModel,
    modifier: Modifier = Modifier
) {
    val messages by viewModel.chatMessages.collectAsState()
    val isGenerating by viewModel.isGenerating.collectAsState()
    val selectedModel by viewModel.selectedModel.collectAsState()
    val selectedPersona by viewModel.selectedPersona.collectAsState()
    val settings by viewModel.settings.collectAsState()

    var inputPrompt by remember { mutableStateOf("") }
    var showSettingsSheet by remember { mutableStateOf(false) }
    var showModelSelector by remember { mutableStateOf(false) }
    var showPersonaSelector by remember { mutableStateOf(false) }

    val listState = rememberLazyListState()
    val context = LocalContext.current
    val primaryColor = MaterialTheme.colorScheme.primary

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Column(
        modifier = modifier
            .testTag("chat_screen")
            .fillMaxSize()
            .background(ObsidianBlack)
    ) {
        // Top Bar: Model Selector, Persona Switcher, Settings & Clear
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(DeepGraphite)
                .border(1.dp, BorderGlow)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Model Selector Pill
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .background(SurfaceContainer)
                    .border(1.dp, BorderGlow, RoundedCornerShape(10.dp))
                    .clickable { showModelSelector = !showModelSelector }
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(NeonCyan, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = selectedModel.name,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = SoftWhite
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("▾", color = CoolGrey, fontSize = 12.sp)
                }
            }

            // Right actions: Persona, Chat Settings & Clear
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Persona button
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(SurfaceContainer)
                        .border(1.dp, BorderGlow, RoundedCornerShape(10.dp))
                        .clickable { showPersonaSelector = !showPersonaSelector },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Switch persona",
                        tint = primaryColor,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Chat Settings button
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(SurfaceContainer)
                        .border(1.dp, BorderGlow, RoundedCornerShape(10.dp))
                        .clickable { showSettingsSheet = true },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = "Chat settings",
                        tint = SoftWhite,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Clear Chat button
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(SurfaceContainer)
                        .border(1.dp, BorderGlow, RoundedCornerShape(10.dp))
                        .clickable { viewModel.clearChat() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.DeleteOutline,
                        contentDescription = "Clear chat",
                        tint = CoolGrey,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        // Expandable Model Selector Bar
        AnimatedVisibility(visible = showModelSelector) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceContainer)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(viewModel.availableModels) { model ->
                    val isSelected = model.id == selectedModel.id
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) primaryColor else DeepGraphite)
                            .border(1.dp, if (isSelected) primaryColor else BorderSubtle, RoundedCornerShape(10.dp))
                            .clickable {
                                viewModel.selectModel(model)
                                showModelSelector = false
                            }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = model.name,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) SoftWhite else CoolGrey
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "• ${model.badge}",
                                    fontSize = 10.sp,
                                    color = if (isSelected) SoftWhite.copy(alpha = 0.8f) else ChampagneGold
                                )
                            }
                        }
                    }
                }
            }
        }

        // Expandable Persona Switcher Bar
        AnimatedVisibility(visible = showPersonaSelector) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SurfaceContainer)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(viewModel.personas) { persona ->
                    val isSelected = persona.id == selectedPersona.id
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) primaryColor else DeepGraphite)
                            .border(1.dp, if (isSelected) primaryColor else BorderSubtle, RoundedCornerShape(10.dp))
                            .clickable {
                                viewModel.selectPersona(persona)
                                showPersonaSelector = false
                            }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = persona.name,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (isSelected) SoftWhite else CoolGrey
                        )
                    }
                }
            }
        }

        // Active persona info bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Persona: ",
                fontSize = 11.sp,
                color = CoolGrey
            )
            Text(
                text = selectedPersona.name,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = NeonCyan
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = "Tone: ",
                fontSize = 11.sp,
                color = CoolGrey
            )
            Text(
                text = settings.replyTone,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = ChampagneGold
            )
        }

        // Messages Thread
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            items(messages, key = { it.id }) { msg ->
                ChatBubble(
                    message = msg,
                    primaryColor = primaryColor,
                    isSaved = viewModel.isItemSaved(msg.text),
                    onToggleSave = { viewModel.toggleSaveChatMessage(msg.id) },
                    onRegenerate = {
                        viewModel.regenerateLastMessage()
                    }
                )
            }

            if (isGenerating) {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = primaryColor,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Synthesizing with ${selectedModel.name}...",
                            style = MaterialTheme.typography.labelSmall.copy(color = CoolGrey)
                        )
                    }
                }
            }
        }

        // Quick Suggestion Chips
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val suggestions = listOf(
                "Optimize this architecture",
                "Generate executive brief",
                "Explain thinking steps",
                "Debug edge cases"
            )
            items(suggestions) { chip ->
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainer)
                        .border(0.5.dp, BorderSubtle, RoundedCornerShape(8.dp))
                        .clickable { viewModel.sendMessage(chip) }
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = chip,
                        fontSize = 11.sp,
                        color = CoolGrey
                    )
                }
            }
        }

        // Message Input Composer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(DeepGraphite)
                .border(1.dp, BorderGlow)
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Attachment simulation
                IconButton(
                    onClick = { viewModel.showToast("Attachment picker opened") },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.AttachFile,
                        contentDescription = "Attach file",
                        tint = CoolGrey,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Voice input simulation
                IconButton(
                    onClick = { viewModel.showToast("Voice listening initialized...") },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = "Voice input",
                        tint = CoolGrey,
                        modifier = Modifier.size(20.dp)
                    )
                }

                // Text field
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceContainer)
                        .border(1.dp, BorderSubtle, RoundedCornerShape(12.dp))
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                ) {
                    if (inputPrompt.isEmpty()) {
                        Text(
                            text = "Message ORVEXIS AI...",
                            style = MaterialTheme.typography.bodyMedium.copy(color = MutedGrey)
                        )
                    }
                    androidx.compose.foundation.text.BasicTextField(
                        value = inputPrompt,
                        onValueChange = { inputPrompt = it },
                        textStyle = MaterialTheme.typography.bodyMedium.copy(color = SoftWhite),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Send Button
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(
                            if (inputPrompt.isNotBlank()) primaryColor else SurfaceContainer
                        )
                        .clickable(enabled = inputPrompt.isNotBlank() && !isGenerating) {
                            val textToSend = inputPrompt
                            inputPrompt = ""
                            viewModel.sendMessage(textToSend)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send message",
                        tint = if (inputPrompt.isNotBlank()) SoftWhite else MutedGrey,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }

    // Chat Settings Modal Bottom Sheet
    if (showSettingsSheet) {
        ChatSettingsBottomSheet(
            viewModel = viewModel,
            onDismiss = { showSettingsSheet = false }
        )
    }
}

@Composable
fun ChatBubble(
    message: ChatMessage,
    primaryColor: androidx.compose.ui.graphics.Color,
    isSaved: Boolean,
    onToggleSave: () -> Unit,
    onRegenerate: () -> Unit
) {
    val isUser = message.isUser

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 4.dp)
        ) {
            Text(
                text = if (isUser) "You" else message.modelName,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = if (isUser) primaryColor else NeonCyan
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = message.timestamp,
                fontSize = 10.sp,
                color = MutedGrey
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth(if (isUser) 0.85f else 0.95f)
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (isUser) 16.dp else 4.dp,
                        bottomEnd = if (isUser) 4.dp else 16.dp
                    )
                )
                .background(
                    brush = if (isUser) {
                        Brush.linearGradient(
                            listOf(
                                primaryColor.copy(alpha = 0.25f),
                                DeepGraphite
                            )
                        )
                    } else {
                        Brush.linearGradient(listOf(DeepGraphite, DeepGraphite))
                    }
                )
                .border(
                    1.dp,
                    if (isUser) primaryColor.copy(alpha = 0.4f) else BorderGlow,
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (isUser) 16.dp else 4.dp,
                        bottomEnd = if (isUser) 4.dp else 16.dp
                    )
                )
                .padding(14.dp)
        ) {
            Column {
                if (message.thinkingProcess != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainer)
                            .border(0.5.dp, BorderSubtle, RoundedCornerShape(8.dp))
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "Thinking: ${message.thinkingProcess}",
                            fontSize = 11.sp,
                            color = CoolGrey,
                            lineHeight = 15.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }

                Text(
                    text = message.text,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontSize = 14.sp,
                        lineHeight = 21.sp,
                        color = SoftWhite
                    )
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Bookmark / Save Button
                    BookmarkIconButton(
                        isBookmarked = isSaved,
                        onToggle = onToggleSave
                    )

                    Spacer(modifier = Modifier.width(6.dp))

                    // 1-Tap Animated Copy Button
                    CopyIconButton(
                        textToCopy = message.text
                    )

                    if (!isUser) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(SurfaceContainer)
                                .border(0.5.dp, BorderSubtle, RoundedCornerShape(8.dp))
                                .clickable { onRegenerate() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Regenerate",
                                tint = CoolGrey,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatSettingsBottomSheet(
    viewModel: OrvexisViewModel,
    onDismiss: () -> Unit
) {
    val settings by viewModel.settings.collectAsState()
    val selectedModel by viewModel.selectedModel.collectAsState()
    val primaryColor = MaterialTheme.colorScheme.primary

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = DeepGraphite,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .size(width = 40.dp, height = 4.dp)
                    .background(BorderGlow, CircleShape)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Chat Settings",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = SoftWhite
                    )
                )
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = CoolGrey,
                    modifier = Modifier.clickable { onDismiss() }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Model Selection
            Text(
                text = "Model",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = CoolGrey
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                viewModel.availableModels.forEach { model ->
                    val isSelected = model.id == selectedModel.id
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) primaryColor else SurfaceContainer)
                            .border(1.dp, if (isSelected) primaryColor else BorderSubtle, RoundedCornerShape(10.dp))
                            .clickable { viewModel.selectModel(model) }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = model.name.split(" ").first(),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) SoftWhite else CoolGrey
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Response Length
            Text(
                text = "Response length",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = CoolGrey
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Auto", "Short", "Long").forEach { length ->
                    val isSelected = settings.responseLength == length
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) primaryColor else SurfaceContainer)
                            .border(1.dp, if (isSelected) primaryColor else BorderSubtle, RoundedCornerShape(10.dp))
                            .clickable { viewModel.updateSettings { it.copy(responseLength = length) } }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = length,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = if (isSelected) SoftWhite else CoolGrey
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Reply Tone Grid
            Text(
                text = "Reply tone",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = CoolGrey
            )
            Spacer(modifier = Modifier.height(8.dp))
            val tones = listOf(
                "Default", "Inspiring",
                "Professional", "Enthusiastic",
                "Friendly", "Romantic",
                "Joyful", "Critical"
            )
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                tones.chunked(2).forEach { rowTones ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowTones.forEach { tone ->
                            val isSelected = settings.replyTone == tone
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) primaryColor else SurfaceContainer)
                                    .border(1.dp, if (isSelected) primaryColor else BorderSubtle, RoundedCornerShape(10.dp))
                                    .clickable { viewModel.updateSettings { it.copy(replyTone = tone) } }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = tone,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = if (isSelected) SoftWhite else CoolGrey
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Show Tool Steps Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Show Tool & Thinking Steps",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = SoftWhite
                    )
                    Text(
                        text = "Render extended thoughts in chat bubbles",
                        fontSize = 11.sp,
                        color = CoolGrey
                    )
                }
                OrvexisToggleSwitch(
                    checked = settings.showToolSteps,
                    onCheckedChange = { checked ->
                        viewModel.updateSettings { it.copy(showToolSteps = checked) }
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
