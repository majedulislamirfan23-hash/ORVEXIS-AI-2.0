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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.components.DashboardCard
import com.example.designsystem.components.OrvexisAvatar
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
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SurfaceContainer
import com.example.viewmodel.OrvexisViewModel

@Composable
fun ProfileScreen(
    viewModel: OrvexisViewModel,
    modifier: Modifier = Modifier
) {
    val profile by viewModel.userProfile.collectAsState()
    val isFreeMode by viewModel.isFreeModeActive.collectAsState()
    val userCredits by viewModel.userCredits.collectAsState()
    val primaryColor = MaterialTheme.colorScheme.primary

    var nameInput by remember(profile.displayName) { mutableStateOf(profile.displayName) }
    var handleInput by remember(profile.handle) { mutableStateOf(profile.handle) }
    var titleInput by remember(profile.title) { mutableStateOf(profile.title) }
    var bioInput by remember(profile.bio) { mutableStateOf(profile.bio) }
    var selectedAvatarIndex by remember(profile.avatarPreset) { mutableStateOf(profile.avatarPreset) }
    var selectedStatus by remember(profile.statusText) { mutableStateOf(profile.statusText) }
    var selectedStatusColor by remember(profile.statusColorHex) { mutableStateOf(profile.statusColorHex) }

    val initials = remember(nameInput) {
        val parts = nameInput.trim().split(" ").filter { it.isNotBlank() }
        if (parts.size >= 2) "${parts[0].first()}${parts[1].first()}".uppercase()
        else if (parts.isNotEmpty()) parts[0].take(2).uppercase()
        else "AI"
    }

    LazyColumn(
        modifier = modifier
            .testTag("profile_screen")
            .fillMaxSize()
            .background(ObsidianBlack)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Profile Customization",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = SoftWhite
                )
            )
            Text(
                text = "Configure your executive digital persona, status aura, and unmetered tier access.",
                style = MaterialTheme.typography.bodyMedium.copy(color = CoolGrey)
            )
        }

        // 1. Live Identity Preview Card
        item {
            DashboardCard(
                borderColor = BorderGlow,
                backgroundColor = DeepGraphite
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OrvexisAvatar(
                        initials = initials,
                        presetIndex = selectedAvatarIndex,
                        size = 64.dp,
                        statusColor = Color(android.graphics.Color.parseColor(selectedStatusColor))
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = nameInput.ifEmpty { "Esther Howard" },
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = SoftWhite
                                )
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .background(ChampagneGold.copy(alpha = 0.2f), RoundedCornerShape(6.dp))
                                    .border(0.5.dp, ChampagneGold.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = if (isFreeMode) "UNLIMITED" else "PRO",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isFreeMode) NeonCyan else ChampagneGold
                                )
                            }
                        }
                        Text(
                            text = handleInput.ifEmpty { "@esther.ai" },
                            fontSize = 12.sp,
                            color = primaryColor
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = titleInput.ifEmpty { "Lead AI Architect" },
                            fontSize = 13.sp,
                            color = CoolGrey
                        )
                    }
                }
            }
        }

        // 2. Avatar Preset Selector
        item {
            Column {
                Text(
                    text = "SELECT AVATAR PRESET",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = CoolGrey,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(6) { idx ->
                        val isSelected = idx == selectedAvatarIndex
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .border(
                                    2.dp,
                                    if (isSelected) primaryColor else Color.Transparent,
                                    CircleShape
                                )
                                .padding(3.dp)
                                .clickable { selectedAvatarIndex = idx }
                        ) {
                            OrvexisAvatar(
                                initials = initials,
                                presetIndex = idx,
                                size = 52.dp
                            )
                        }
                    }
                }
            }
        }

        // 3. User Details Inputs
        item {
            SettingsSection(title = "Profile Attributes") {
                // Name Input
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                    Text(
                        text = "Display Name",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = CoolGrey
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainer)
                            .border(1.dp, BorderSubtle, RoundedCornerShape(10.dp))
                            .padding(12.dp)
                    ) {
                        androidx.compose.foundation.text.BasicTextField(
                            value = nameInput,
                            onValueChange = { nameInput = it },
                            textStyle = MaterialTheme.typography.bodyMedium.copy(color = SoftWhite),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // Handle Input
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                    Text(
                        text = "User Handle",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = CoolGrey
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainer)
                            .border(1.dp, BorderSubtle, RoundedCornerShape(10.dp))
                            .padding(12.dp)
                    ) {
                        androidx.compose.foundation.text.BasicTextField(
                            value = handleInput,
                            onValueChange = { handleInput = it },
                            textStyle = MaterialTheme.typography.bodyMedium.copy(color = SoftWhite),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // Title Input
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                    Text(
                        text = "Role / Title",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = CoolGrey
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainer)
                            .border(1.dp, BorderSubtle, RoundedCornerShape(10.dp))
                            .padding(12.dp)
                    ) {
                        androidx.compose.foundation.text.BasicTextField(
                            value = titleInput,
                            onValueChange = { titleInput = it },
                            textStyle = MaterialTheme.typography.bodyMedium.copy(color = SoftWhite),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // Bio Input
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)) {
                    Text(
                        text = "Bio & Focus Directive",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = CoolGrey
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(72.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainer)
                            .border(1.dp, BorderSubtle, RoundedCornerShape(10.dp))
                            .padding(12.dp)
                    ) {
                        androidx.compose.foundation.text.BasicTextField(
                            value = bioInput,
                            onValueChange = { bioInput = it },
                            textStyle = MaterialTheme.typography.bodyMedium.copy(color = SoftWhite),
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }

        // 4. Status Aura Selector
        item {
            Column {
                Text(
                    text = "STATUS AURA",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = CoolGrey,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                val statuses = listOf(
                    Pair("Systems Nominal", "#42D6A4"),
                    Pair("Deep Thinking", "#895BFF"),
                    Pair("Autonomous Mode", "#35DFFF"),
                    Pair("Creative Flow", "#D9B66F")
                )

                DashboardCard(
                    borderColor = BorderGlow,
                    backgroundColor = DeepGraphite
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        statuses.forEach { (status, hex) ->
                            val isSelected = selectedStatus == status
                            val statusColor = Color(android.graphics.Color.parseColor(hex))
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSelected) SurfaceContainer else DeepGraphite)
                                    .border(
                                        1.dp,
                                        if (isSelected) statusColor else BorderSubtle,
                                        RoundedCornerShape(10.dp)
                                    )
                                    .clickable {
                                        selectedStatus = status
                                        selectedStatusColor = hex
                                    }
                                    .padding(horizontal = 14.dp, vertical = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Box(
                                        modifier = Modifier
                                            .size(10.dp)
                                            .background(statusColor, CircleShape)
                                    )
                                    Spacer(modifier = Modifier.width(10.dp))
                                    Text(
                                        text = status,
                                        fontSize = 13.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = SoftWhite
                                    )
                                }
                                if (isSelected) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Selected",
                                        tint = statusColor,
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // 5. Unmetered Free Use Mode & Credits
        item {
            SettingsSection(title = "Free Access & Usage Controls") {
                SettingsRow(
                    title = "Unmetered Free Mode",
                    subtitle = "Bypass all token and credit limits for continuous unconstrained creation",
                    icon = Icons.Default.Shield,
                    trailingContent = {
                        OrvexisToggleSwitch(
                            checked = isFreeMode,
                            onCheckedChange = { viewModel.toggleFreeMode() }
                        )
                    }
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "$userCredits Credits Available",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = SoftWhite
                        )
                        Text(
                            text = if (isFreeMode) "Deductions paused (Free Mode)" else "Standard consumption",
                            fontSize = 11.sp,
                            color = if (isFreeMode) NeonCyan else CoolGrey
                        )
                    }

                    SecondaryButton(
                        text = "+50 Free Credits",
                        onClick = { viewModel.topUpCredits(50) },
                        icon = Icons.Default.Token
                    )
                }
            }
        }

        // 6. Save Profile Action
        item {
            PrimaryButton(
                text = "Save Profile Persona",
                onClick = {
                    viewModel.updateProfile(
                        displayName = nameInput.ifBlank { "Esther Howard" },
                        handle = handleInput.ifBlank { "@esther.ai" },
                        title = titleInput.ifBlank { "Lead AI Architect" },
                        avatarPreset = selectedAvatarIndex,
                        statusText = selectedStatus,
                        statusColorHex = selectedStatusColor,
                        bio = bioInput
                    )
                },
                leadingIcon = Icons.Default.Check,
                modifier = Modifier.fillMaxWidth(),
                testTag = "save_profile_button"
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
