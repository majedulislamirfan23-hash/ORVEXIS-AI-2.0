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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CardGiftcard
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material.icons.filled.Token
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.components.ConfidenceGauge
import com.example.designsystem.components.CopyIconButton
import com.example.designsystem.components.DashboardCard
import com.example.designsystem.components.OrvexisAvatar
import com.example.designsystem.components.PrimaryButton
import com.example.designsystem.components.SecondaryButton
import com.example.designsystem.components.SparklineChart
import com.example.designsystem.components.ToolCard
import com.example.model.ScreenDestination
import com.example.model.ToolItem
import com.example.ui.theme.BorderGlow
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.ChampagneGold
import com.example.ui.theme.CoolGrey
import com.example.ui.theme.DeepGraphite
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.MutedGrey
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.SoftWhite
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SurfaceContainer
import com.example.viewmodel.OrvexisViewModel

@Composable
fun DashboardScreen(
    viewModel: OrvexisViewModel,
    modifier: Modifier = Modifier
) {
    val profile by viewModel.userProfile.collectAsState()
    val isFreeMode by viewModel.isFreeModeActive.collectAsState()
    val userCredits by viewModel.userCredits.collectAsState()
    val tools by viewModel.tools.collectAsState()
    val savedItems by viewModel.savedItems.collectAsState()
    val favoriteTools = remember(tools) { tools.filter { it.isFavorite } }
    var quickPromptText by remember { mutableStateOf("") }
    val primaryColor = MaterialTheme.colorScheme.primary

    val initials = remember(profile.displayName) {
        val parts = profile.displayName.trim().split(" ").filter { it.isNotBlank() }
        if (parts.size >= 2) "${parts[0].first()}${parts[1].first()}".uppercase()
        else if (parts.isNotEmpty()) parts[0].take(2).uppercase()
        else "AI"
    }

    LazyColumn(
        modifier = modifier
            .testTag("dashboard_screen")
            .fillMaxSize()
            .background(ObsidianBlack)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
            // 1. User Header: Avatar, Name, Handle, Free Mode Switch, Credits Pill
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.clickable { viewModel.navigateTo(ScreenDestination.PROFILE) }
                ) {
                    // Customizable Avatar with Status Aura
                    OrvexisAvatar(
                        initials = initials,
                        presetIndex = profile.avatarPreset,
                        size = 48.dp,
                        statusColor = Color(android.graphics.Color.parseColor(profile.statusColorHex))
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = profile.displayName,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SoftWhite
                                )
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .background(
                                        if (isFreeMode) NeonCyan.copy(alpha = 0.2f) else ChampagneGold.copy(alpha = 0.2f),
                                        RoundedCornerShape(6.dp)
                                    )
                                    .border(
                                        0.5.dp,
                                        if (isFreeMode) NeonCyan.copy(alpha = 0.5f) else ChampagneGold.copy(alpha = 0.4f),
                                        RoundedCornerShape(6.dp)
                                    )
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = if (isFreeMode) "FREE MODE" else "PRO",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isFreeMode) NeonCyan else ChampagneGold
                                )
                            }
                        }
                        Text(
                            text = "${profile.title} • Edit Profile",
                            style = MaterialTheme.typography.labelSmall.copy(color = CoolGrey)
                        )
                    }
                }

                // Free Mode Quick Switch & Credits Pill
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Quick Free Mode Toggle
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(if (isFreeMode) NeonCyan.copy(alpha = 0.15f) else SurfaceContainer)
                            .border(
                                1.dp,
                                if (isFreeMode) NeonCyan.copy(alpha = 0.6f) else BorderGlow,
                                RoundedCornerShape(20.dp)
                            )
                            .clickable { viewModel.toggleFreeMode() }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(if (isFreeMode) NeonCyan else CoolGrey, CircleShape)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = if (isFreeMode) "Free Use: ON" else "Free: OFF",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isFreeMode) NeonCyan else CoolGrey
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Credits / Top-Up Pill
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(SurfaceContainer)
                            .border(1.dp, BorderGlow, RoundedCornerShape(20.dp))
                            .clickable { viewModel.topUpCredits(50) }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Token,
                                contentDescription = "Credits",
                                tint = primaryColor,
                                modifier = Modifier.size(15.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = if (isFreeMode) "∞ Free" else "$userCredits",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = SoftWhite
                                )
                            )
                        }
                    }
                }
            }
        }

        // 2. 1-TAP QUICK-MAKE CAROUSEL ("make it easy, simple make")
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "SIMPLE QUICK-MAKE (1-TAP FREE CREATION)",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = CoolGrey,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )
                    Text(
                        text = "Instant Run",
                        fontSize = 11.sp,
                        color = NeonCyan
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    val quickMakeItems = listOf(
                        Triple("Instant Code", "Generate Kotlin cache helper", "fast_code"),
                        Triple("Fast Brainstorm", "3 spatial AI breakthrough ideas", "fast_idea"),
                        Triple("Executive Memo", "Boardroom cognitive brief", "fast_summary"),
                        Triple("Cinematic Prompt", "Luxury obsidian console 8k", "fast_prompt")
                    )

                    items(quickMakeItems) { (title, subtitle, actionKey) ->
                        Box(
                            modifier = Modifier
                                .width(170.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(DeepGraphite)
                                .border(1.dp, BorderGlow, RoundedCornerShape(14.dp))
                                .clickable { viewModel.quickMake(actionKey) }
                                .padding(12.dp)
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(28.dp)
                                            .background(primaryColor.copy(alpha = 0.2f), RoundedCornerShape(8.dp)),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Bolt,
                                            contentDescription = null,
                                            tint = primaryColor,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                    Text(
                                        text = "FREE",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SuccessGreen
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Text(
                                    text = title,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SoftWhite
                                )

                                Text(
                                    text = subtitle,
                                    fontSize = 11.sp,
                                    color = CoolGrey,
                                    lineHeight = 15.sp,
                                    maxLines = 2
                                )
                            }
                        }
                    }
                }
            }
        }

        // 3. Saved Vault Shortcut Card
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                SurfaceContainer,
                                DeepGraphite
                            )
                        )
                    )
                    .border(1.dp, BorderGlow, RoundedCornerShape(16.dp))
                    .clickable { viewModel.navigateTo(ScreenDestination.SAVED_VAULT) }
                    .padding(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .background(ChampagneGold.copy(alpha = 0.15f), RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Bookmark,
                                contentDescription = "Saved Vault",
                                tint = ChampagneGold,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Saved Vault Repository",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SoftWhite
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Box(
                                    modifier = Modifier
                                        .background(primaryColor.copy(alpha = 0.2f), RoundedCornerShape(6.dp))
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "${savedItems.size} items",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = primaryColor
                                    )
                                }
                            }
                            Text(
                                text = "Access saved code snippets, prompts, and 1-tap copy tools",
                                fontSize = 11.sp,
                                color = CoolGrey
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Open Saved Vault",
                        tint = ChampagneGold,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        // 4. Quick AI Prompt Bar
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
                            text = "Quick Dispatch AI",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = SoftWhite
                            )
                        )
                        if (isFreeMode) {
                            Box(
                                modifier = Modifier
                                    .background(SuccessGreen.copy(alpha = 0.2f), RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text("0 Credits (Free)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = SuccessGreen)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainer)
                            .border(1.dp, BorderSubtle, RoundedCornerShape(12.dp))
                            .padding(horizontal = 14.dp, vertical = 10.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Box(modifier = Modifier.weight(1f)) {
                                if (quickPromptText.isEmpty()) {
                                    Text(
                                        text = "Ask anything, build code, or analyze...",
                                        style = MaterialTheme.typography.bodyMedium.copy(color = MutedGrey)
                                    )
                                }
                                androidx.compose.foundation.text.BasicTextField(
                                    value = quickPromptText,
                                    onValueChange = { quickPromptText = it },
                                    singleLine = true,
                                    textStyle = MaterialTheme.typography.bodyMedium.copy(color = SoftWhite),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(primaryColor)
                                    .clickable {
                                        if (quickPromptText.isNotBlank()) {
                                            viewModel.sendMessage(quickPromptText)
                                            quickPromptText = ""
                                            viewModel.navigateTo(ScreenDestination.AI_CHAT)
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.Send,
                                    contentDescription = "Send prompt",
                                    tint = SoftWhite,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // 5. Quick Action Chips (Ask AI, Generate Ideas, Write Content, Build App, Analyze Image, Study)
        item {
            Column {
                Text(
                    text = "QUICK ACTIONS",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = CoolGrey,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                )
                Spacer(modifier = Modifier.height(10.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    val quickActions = listOf(
                        Pair("Ask AI", Icons.Default.AutoAwesome),
                        Pair("Generate Ideas", Icons.Default.Speed),
                        Pair("Write Content", Icons.Default.Create),
                        Pair("Build App", Icons.Default.Code),
                        Pair("Analyze Image", Icons.Default.Image),
                        Pair("Study Something", Icons.Default.School)
                    )
                    items(quickActions) { (label, icon) ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(DeepGraphite)
                                .border(1.dp, BorderGlow, RoundedCornerShape(12.dp))
                                .clickable {
                                    when (label) {
                                        "Ask AI" -> viewModel.navigateTo(ScreenDestination.AI_CHAT)
                                        "Build App" -> viewModel.navigateTo(ScreenDestination.CREATE_STUDIO)
                                        "Write Content" -> {
                                            viewModel.setCategory(com.example.model.ToolCategory.WRITING)
                                            viewModel.navigateTo(ScreenDestination.TOOLS_HUB)
                                        }
                                        "Analyze Image" -> viewModel.navigateTo(ScreenDestination.AI_CHAT)
                                        "Study Something" -> {
                                            viewModel.setCategory(com.example.model.ToolCategory.STUDY)
                                            viewModel.navigateTo(ScreenDestination.TOOLS_HUB)
                                        }
                                        else -> viewModel.navigateTo(ScreenDestination.AI_CHAT)
                                    }
                                }
                                .padding(horizontal = 14.dp, vertical = 10.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    tint = primaryColor,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = label,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Medium,
                                        color = SoftWhite
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }

        // 6. Telemetry Metrics Row (AI Usage 24.5K, Outputs 1,847, Precision 98.5%, Active Models 12)
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "COGNITIVE TELEMETRY",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = CoolGrey,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )
                    Text(
                        text = "Live Stream",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = SuccessGreen
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Metric 1: AI Usage
                    DashboardCard(
                        modifier = Modifier.weight(1f),
                        borderColor = BorderGlow,
                        cornerRadius = 14.dp
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("AI Usage", fontSize = 12.sp, color = CoolGrey)
                                SparklineChart(
                                    data = listOf(10f, 14f, 12f, 18f, 22f, 20f, 24.5f),
                                    lineColor = primaryColor
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("24.5K", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = SoftWhite)
                            Text("+12.5% this month", fontSize = 10.sp, color = SuccessGreen)
                        }
                    }

                    // Metric 2: Outputs
                    DashboardCard(
                        modifier = Modifier.weight(1f),
                        borderColor = BorderGlow,
                        cornerRadius = 14.dp
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Outputs", fontSize = 12.sp, color = CoolGrey)
                                SparklineChart(
                                    data = listOf(8f, 10f, 15f, 14f, 17f, 16f, 18.4f),
                                    lineColor = NeonCyan
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("1,847", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = SoftWhite)
                            Text("+8.2% this month", fontSize = 10.sp, color = SuccessGreen)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Metric 3: Precision
                    DashboardCard(
                        modifier = Modifier.weight(1f),
                        borderColor = BorderGlow,
                        cornerRadius = 14.dp
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Precision", fontSize = 12.sp, color = CoolGrey)
                                SparklineChart(
                                    data = listOf(95f, 96f, 97f, 96.5f, 98f, 98.5f),
                                    lineColor = ChampagneGold
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("98.5%", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = SoftWhite)
                            Text("+2.1% this month", fontSize = 10.sp, color = SuccessGreen)
                        }
                    }

                    // Metric 4: Models
                    DashboardCard(
                        modifier = Modifier.weight(1f),
                        borderColor = BorderGlow,
                        cornerRadius = 14.dp
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("Models", fontSize = 12.sp, color = CoolGrey)
                                SparklineChart(
                                    data = listOf(14f, 13f, 13f, 12f, 12f),
                                    lineColor = NeonCyan
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("12 Active", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = SoftWhite)
                            Text("Spectrion Routed", fontSize = 10.sp, color = CoolGrey)
                        }
                    }
                }
            }
        }

        // 7. AI Confidence Gauge Card (82.5 Overall Score)
        item {
            DashboardCard(
                borderColor = BorderGlow,
                backgroundColor = DeepGraphite
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "AI Confidence Index",
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
                            Text("Neural Audit", fontSize = 11.sp, color = NeonCyan)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    ConfidenceGauge(score = 82.5f)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Breakdown chips
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ConfidenceStatItem("Performance", "24%", primaryColor)
                        ConfidenceStatItem("Accuracy", "32%", NeonCyan)
                        ConfidenceStatItem("Efficiency", "27%", ChampagneGold)
                        ConfidenceStatItem("Reliability", "17%", SuccessGreen)
                    }
                }
            }
        }

        // 8. Favorite Tools Grid
        item {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "FAVORITE TOOLS",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = CoolGrey,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )
                    Text(
                        text = "See All (${tools.size})",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = primaryColor,
                        modifier = Modifier.clickable { viewModel.navigateTo(ScreenDestination.TOOLS_HUB) }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    favoriteTools.take(3).forEach { tool ->
                        ToolCard(
                            tool = tool,
                            onOpen = { viewModel.openToolRunner(tool) },
                            onToggleFavorite = { viewModel.toggleFavorite(it) }
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

@Composable
private fun ConfidenceStatItem(label: String, value: String, dotColor: Color) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .background(dotColor, CircleShape)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SoftWhite)
        }
        Text(text = label, fontSize = 11.sp, color = CoolGrey)
    }
}
