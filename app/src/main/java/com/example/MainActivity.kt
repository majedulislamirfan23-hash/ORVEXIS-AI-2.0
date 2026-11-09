package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Token
import androidx.compose.material.icons.filled.Upgrade
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Person
import com.example.designsystem.components.OrvexisAvatar
import com.example.model.ScreenDestination
import com.example.ui.screens.ChatScreen
import com.example.ui.screens.CreateStudioScreen
import com.example.ui.screens.CustomizationScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.SavedVaultScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.ToolsHubScreen
import com.example.ui.screens.UpgradeScreen
import com.example.ui.theme.BorderGlow
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.ChampagneGold
import com.example.ui.theme.CoolGrey
import com.example.ui.theme.DeepGraphite
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.OrvexisTheme
import com.example.ui.theme.SoftWhite
import com.example.ui.theme.SurfaceContainer
import com.example.viewmodel.OrvexisViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: OrvexisViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val settings by viewModel.settings.collectAsState()
            val currentDestination by viewModel.currentDestination.collectAsState()
            val toastMessage by viewModel.toastMessage.collectAsState()

            BackHandler(enabled = currentDestination != ScreenDestination.DASHBOARD) {
                if (!viewModel.handleBack()) {
                    viewModel.navigateTo(ScreenDestination.DASHBOARD)
                }
            }

            OrvexisTheme(
                customPrimaryAccent = viewModel.getActivePrimaryColor()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(ObsidianBlack)
                ) {
                    OrvexisAppContent(
                        viewModel = viewModel,
                        currentDestination = currentDestination
                    )

                    // Floating Toast Notification
                    AnimatedVisibility(
                        visible = toastMessage != null,
                        enter = slideInVertically { it / 2 } + fadeIn(),
                        exit = slideOutVertically { it / 2 } + fadeOut(),
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 90.dp)
                    ) {
                        if (toastMessage != null) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(DeepGraphite)
                                    .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(20.dp))
                                    .padding(horizontal = 20.dp, vertical = 10.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.AutoAwesome,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = toastMessage ?: "",
                                        style = MaterialTheme.typography.bodyMedium.copy(
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
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrvexisAppContent(
    viewModel: OrvexisViewModel,
    currentDestination: ScreenDestination
) {
    var showMoreModal by remember { mutableStateOf(false) }
    val primaryColor = MaterialTheme.colorScheme.primary

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val isTablet = maxWidth >= 600.dp

        if (isTablet) {
            // Tablet / Desktop Layout with Navigation Rail
            Row(modifier = Modifier.fillMaxSize()) {
                NavigationRail(
                    modifier = Modifier
                        .fillMaxHeight()
                        .background(DeepGraphite)
                        .border(1.dp, BorderGlow),
                    containerColor = DeepGraphite,
                    header = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .statusBarsPadding()
                                .padding(vertical = 12.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(primaryColor),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("O", color = SoftWhite, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "ORVEXIS",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = ChampagneGold,
                                letterSpacing = 1.sp
                            )
                        }
                    }
                ) {
                    val allItems = listOf(
                        Pair(ScreenDestination.DASHBOARD, Icons.Default.Home),
                        Pair(ScreenDestination.AI_CHAT, Icons.AutoMirrored.Filled.Chat),
                        Pair(ScreenDestination.SAVED_VAULT, Icons.Default.Bookmark),
                        Pair(ScreenDestination.PROFILE, Icons.Default.Person),
                        Pair(ScreenDestination.TOOLS_HUB, Icons.Default.Build),
                        Pair(ScreenDestination.CREATE_STUDIO, Icons.Default.Code),
                        Pair(ScreenDestination.SETTINGS, Icons.Default.Settings),
                        Pair(ScreenDestination.CUSTOMIZATION, Icons.Default.Palette),
                        Pair(ScreenDestination.UPGRADE, Icons.Default.Upgrade)
                    )

                    allItems.forEach { (dest, icon) ->
                        val selected = currentDestination == dest
                        NavigationRailItem(
                            selected = selected,
                            onClick = { viewModel.navigateTo(dest) },
                            icon = {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = dest.title,
                                    tint = if (selected) primaryColor else CoolGrey
                                )
                            },
                            label = {
                                Text(
                                    text = dest.title,
                                    fontSize = 10.sp,
                                    color = if (selected) SoftWhite else CoolGrey
                                )
                            },
                            colors = NavigationRailItemDefaults.colors(
                                indicatorColor = primaryColor.copy(alpha = 0.2f)
                            )
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .statusBarsPadding()
                ) {
                    ScreenContent(viewModel = viewModel, destination = currentDestination)
                }
            }
        } else {
            // Mobile Compact Layout with Top Header & Bottom Navigation Bar
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ObsidianBlack),
                topBar = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(ObsidianBlack)
                            .statusBarsPadding()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(primaryColor),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("O", color = SoftWhite, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "ORVEXIS",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            letterSpacing = 1.sp,
                                            color = SoftWhite
                                        )
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "AI",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = NeonCyan
                                        )
                                    )
                                }
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            // Saved Vault shortcut
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SurfaceContainer)
                                    .border(1.dp, BorderGlow, RoundedCornerShape(8.dp))
                                    .clickable { viewModel.navigateTo(ScreenDestination.SAVED_VAULT) }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Bookmark,
                                        contentDescription = "Saved Vault",
                                        tint = ChampagneGold,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Vault", fontSize = 12.sp, color = ChampagneGold, fontWeight = FontWeight.SemiBold)
                                }
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            // More button
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SurfaceContainer)
                                    .border(1.dp, BorderGlow, RoundedCornerShape(8.dp))
                                    .clickable { showMoreModal = true }
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.MoreHoriz,
                                        contentDescription = "More features",
                                        tint = SoftWhite,
                                        modifier = Modifier.size(18.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("More", fontSize = 12.sp, color = SoftWhite)
                                }
                            }
                        }
                    }
                },
                bottomBar = {
                    NavigationBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(DeepGraphite)
                            .border(1.dp, BorderGlow)
                            .navigationBarsPadding(),
                        containerColor = DeepGraphite,
                        tonalElevation = 0.dp
                    ) {
                        val mobileDestinations = listOf(
                            Pair(ScreenDestination.DASHBOARD, Icons.Default.Home),
                            Pair(ScreenDestination.AI_CHAT, Icons.AutoMirrored.Filled.Chat),
                            Pair(ScreenDestination.TOOLS_HUB, Icons.Default.Build),
                            Pair(ScreenDestination.CREATE_STUDIO, Icons.Default.Code),
                            Pair(ScreenDestination.SETTINGS, Icons.Default.Settings)
                        )

                        mobileDestinations.forEach { (dest, icon) ->
                            val selected = currentDestination == dest
                            NavigationBarItem(
                                selected = selected,
                                onClick = { viewModel.navigateTo(dest) },
                                icon = {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = dest.title,
                                        tint = if (selected) primaryColor else CoolGrey
                                    )
                                },
                                label = {
                                    Text(
                                        text = dest.title,
                                        fontSize = 11.sp,
                                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (selected) SoftWhite else CoolGrey
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = primaryColor,
                                    indicatorColor = primaryColor.copy(alpha = 0.15f)
                                )
                            )
                        }
                    }
                }
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    ScreenContent(viewModel = viewModel, destination = currentDestination)
                }
            }
        }
    }

    // Extended More Features Bottom Sheet
    if (showMoreModal) {
        ModalBottomSheet(
            onDismissRequest = { showMoreModal = false },
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
                    .navigationBarsPadding()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Extended Workspaces",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = SoftWhite
                        )
                    )
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = CoolGrey,
                        modifier = Modifier.clickable { showMoreModal = false }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                val extendedModules = listOf(
                    Triple(ScreenDestination.SAVED_VAULT, Icons.Default.Bookmark, "Saved Repository"),
                    Triple(ScreenDestination.PROFILE, Icons.Default.Person, "Profile Persona"),
                    Triple(ScreenDestination.CUSTOMIZATION, Icons.Default.Palette, "Theme & Accents"),
                    Triple(ScreenDestination.UPGRADE, Icons.Default.Upgrade, "Upgrade Center"),
                    Triple(ScreenDestination.EXPLORE, Icons.Default.Explore, "Model Exploration"),
                    Triple(ScreenDestination.DOCUMENTS, Icons.Default.Description, "Document Vault"),
                    Triple(ScreenDestination.STUDY_MODE, Icons.Default.School, "Cognitive Tutor"),
                    Triple(ScreenDestination.VISION, Icons.Default.Visibility, "Vision Analysis"),
                    Triple(ScreenDestination.VOICE, Icons.Default.Mic, "Voice Synthesis"),
                    Triple(ScreenDestination.HISTORY, Icons.Default.History, "Audit Logs")
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.height(280.dp)
                ) {
                    items(extendedModules) { (dest, icon, desc) ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .background(SurfaceContainer)
                                .border(1.dp, BorderSubtle, RoundedCornerShape(14.dp))
                                .clickable {
                                    showMoreModal = false
                                    viewModel.navigateTo(dest)
                                }
                                .padding(12.dp)
                        ) {
                            Column {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = dest.title,
                                    tint = primaryColor,
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = dest.title,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = SoftWhite
                                    )
                                )
                                Text(
                                    text = desc,
                                    fontSize = 11.sp,
                                    color = CoolGrey
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ScreenContent(
    viewModel: OrvexisViewModel,
    destination: ScreenDestination
) {
    when (destination) {
        ScreenDestination.DASHBOARD -> DashboardScreen(viewModel = viewModel)
        ScreenDestination.AI_CHAT -> ChatScreen(viewModel = viewModel)
        ScreenDestination.TOOLS_HUB -> ToolsHubScreen(viewModel = viewModel)
        ScreenDestination.CREATE_STUDIO,
        ScreenDestination.CODE_STUDIO -> CreateStudioScreen(viewModel = viewModel)
        ScreenDestination.SETTINGS -> SettingsScreen(viewModel = viewModel)
        ScreenDestination.CUSTOMIZATION -> CustomizationScreen(viewModel = viewModel)
        ScreenDestination.UPGRADE -> UpgradeScreen(viewModel = viewModel)
        ScreenDestination.PROFILE -> ProfileScreen(viewModel = viewModel)
        ScreenDestination.SAVED_VAULT -> SavedVaultScreen(viewModel = viewModel)
        ScreenDestination.EXPLORE -> {
            viewModel.setCategory(com.example.model.ToolCategory.ALL)
            ToolsHubScreen(viewModel = viewModel)
        }
        ScreenDestination.DOCUMENTS -> CreateStudioScreen(viewModel = viewModel)
        ScreenDestination.STUDY_MODE -> {
            viewModel.setCategory(com.example.model.ToolCategory.STUDY)
            ToolsHubScreen(viewModel = viewModel)
        }
        ScreenDestination.VISION,
        ScreenDestination.VOICE -> ChatScreen(viewModel = viewModel)
        ScreenDestination.HISTORY -> DashboardScreen(viewModel = viewModel)
    }
}
