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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Restore
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.components.DashboardCard
import com.example.designsystem.components.SecondaryButton
import com.example.designsystem.components.UpgradeCard
import com.example.designsystem.components.UsageIndicator
import com.example.ui.theme.BorderGlow
import com.example.ui.theme.BorderSubtle
import com.example.ui.theme.ChampagneGold
import com.example.ui.theme.CoolGrey
import com.example.ui.theme.DeepGraphite
import com.example.ui.theme.MutedGrey
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.SoftWhite
import com.example.ui.theme.SuccessGreen
import com.example.ui.theme.SurfaceContainer
import com.example.viewmodel.OrvexisViewModel

@Composable
fun UpgradeScreen(
    viewModel: OrvexisViewModel,
    modifier: Modifier = Modifier
) {
    val isAnnual by viewModel.isAnnualBilling.collectAsState()
    val planTiers = viewModel.planTiers
    val primaryColor = MaterialTheme.colorScheme.primary

    LazyColumn(
        modifier = modifier
            .testTag("upgrade_screen")
            .fillMaxSize()
            .background(ObsidianBlack)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Upgrade Center",
                style = MaterialTheme.typography.displayLarge.copy(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = SoftWhite
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Scale your cognitive compute. Transparent pricing with instant cancellation anytime.",
                style = MaterialTheme.typography.bodyMedium.copy(color = CoolGrey)
            )
        }

        // 1. Billing Cycle Switcher (Monthly vs Annual 20% Off)
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(DeepGraphite)
                    .border(1.dp, BorderGlow, RoundedCornerShape(14.dp))
                    .padding(6.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    // Monthly
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (!isAnnual) primaryColor else SurfaceContainer)
                            .clickable { viewModel.toggleBillingPeriod(false) }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Monthly Billing",
                            fontSize = 13.sp,
                            fontWeight = if (!isAnnual) FontWeight.Bold else FontWeight.Medium,
                            color = if (!isAnnual) SoftWhite else CoolGrey
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    // Annual
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isAnnual) primaryColor else SurfaceContainer)
                            .clickable { viewModel.toggleBillingPeriod(true) }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Annual Billing",
                                fontSize = 13.sp,
                                fontWeight = if (isAnnual) FontWeight.Bold else FontWeight.Medium,
                                color = if (isAnnual) SoftWhite else CoolGrey
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .background(ChampagneGold, RoundedCornerShape(6.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "-20%",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = ObsidianBlack
                                )
                            }
                        }
                    }
                }
            }
        }

        // Free Mode & Instant Top-Up Card
        item {
            val isFreeMode by viewModel.isFreeModeActive.collectAsState()
            val userCredits by viewModel.userCredits.collectAsState()
            DashboardCard(
                borderColor = if (isFreeMode) NeonCyan.copy(alpha = 0.4f) else BorderGlow,
                backgroundColor = DeepGraphite
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Unmetered Free Mode",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = SoftWhite
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .background(if (isFreeMode) NeonCyan.copy(alpha = 0.2f) else SurfaceContainer, RoundedCornerShape(6.dp))
                                    .border(0.5.dp, if (isFreeMode) NeonCyan else BorderSubtle, RoundedCornerShape(6.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = if (isFreeMode) "ACTIVE" else "OFF",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isFreeMode) NeonCyan else CoolGrey
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = if (isFreeMode) "Token deduction is paused. Experiment completely free!" else "Standard consumption: $userCredits credits left.",
                            fontSize = 12.sp,
                            color = CoolGrey
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    SecondaryButton(
                        text = if (isFreeMode) "Disable Free" else "Enable Free",
                        onClick = { viewModel.toggleFreeMode() }
                    )
                }
            }
        }

        // 2. Current Usage Indicators
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
                            text = "Active Quota Utilization",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = SoftWhite
                            )
                        )
                        Box(
                            modifier = Modifier
                                .background(SuccessGreen.copy(alpha = 0.2f), RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "Healthy",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = SuccessGreen
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    UsageIndicator(
                        label = "High-Reasoning Tokens",
                        current = 7420,
                        max = 10000,
                        unit = "tokens",
                        color = primaryColor
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    UsageIndicator(
                        label = "Autonomous Tool Executions",
                        current = 148,
                        max = 200,
                        unit = "runs",
                        color = NeonCyan
                    )
                }
            }
        }

        // 3. Plan Tier Cards
        items(planTiers) { plan ->
            val price = if (isAnnual) plan.priceAnnual else plan.priceMonthly
            val period = if (isAnnual) "mo, billed annually" else "month"
            UpgradeCard(
                tierName = plan.name,
                price = price,
                period = period,
                badge = plan.badge,
                isCurrent = plan.isCurrent,
                features = plan.features,
                onSelect = {
                    viewModel.upgradePlan(plan.name)
                }
            )
        }

        // 4. Restore Purchases & Transparent Terms
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .clickable { viewModel.showToast("Purchases restored successfully.") }
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Restore,
                        contentDescription = null,
                        tint = CoolGrey,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Restore Purchases",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = CoolGrey
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Subscriptions renew automatically unless canceled at least 24 hours before the end of the current billing period. Manage or cancel anytime via account settings.",
                    fontSize = 11.sp,
                    color = MutedGrey,
                    lineHeight = 16.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
