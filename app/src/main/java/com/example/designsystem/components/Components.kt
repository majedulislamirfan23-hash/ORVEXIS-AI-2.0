package com.example.designsystem.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
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

/**
 * 1. Primary Button with Violet accent, smooth border glow, and interactive feedback
 */
@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    leadingIcon: ImageVector? = null,
    testTag: String = "primary_button"
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val shape = RoundedCornerShape(13.dp)

    Box(
        modifier = modifier
            .testTag(testTag)
            .height(48.dp)
            .clip(shape)
            .background(
                if (enabled) {
                    Brush.horizontalGradient(listOf(primaryColor, primaryColor.copy(alpha = 0.85f)))
                } else {
                    Brush.horizontalGradient(listOf(DeepGraphite, DeepGraphite))
                }
            )
            .border(
                1.dp,
                if (enabled) primaryColor.copy(alpha = 0.6f) else BorderSubtle,
                shape
            )
            .clickable(
                enabled = enabled && !isLoading,
                role = Role.Button,
                onClick = onClick
            )
            .padding(horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = SoftWhite,
                strokeWidth = 2.dp
            )
        } else {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                if (leadingIcon != null) {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        tint = if (enabled) SoftWhite else MutedGrey,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = if (enabled) SoftWhite else MutedGrey
                    )
                )
            }
        }
    }
}

/**
 * 2. Secondary Button with subtle glass border and graphite surface
 */
@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null,
    testTag: String = "secondary_button"
) {
    val shape = RoundedCornerShape(13.dp)

    Box(
        modifier = modifier
            .testTag(testTag)
            .height(48.dp)
            .clip(shape)
            .background(SurfaceContainer)
            .border(1.dp, BorderGlow, shape)
            .clickable(enabled = enabled, role = Role.Button, onClick = onClick)
            .padding(horizontal = 18.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (enabled) SoftWhite else MutedGrey,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            Text(
                text = text,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Medium,
                    color = if (enabled) SoftWhite else MutedGrey
                )
            )
        }
    }
}

/**
 * 3. Custom Styled Icon Button
 */
@Composable
fun OrvexisIconButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    tint: Color = SoftWhite,
    backgroundColor: Color = SurfaceContainer,
    badgeCount: Int? = null,
    testTag: String = "icon_button"
) {
    val shape = RoundedCornerShape(12.dp)
    Box(
        modifier = modifier
            .testTag(testTag)
            .size(48.dp)
            .clip(shape)
            .background(backgroundColor)
            .border(1.dp, BorderGlow, shape)
            .clickable(onClick = onClick, role = Role.Button),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = tint,
            modifier = Modifier.size(20.dp)
        )
        if (badgeCount != null && badgeCount > 0) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .size(16.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = badgeCount.toString(),
                    color = SoftWhite,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

/**
 * 4. Search Bar with glass glow border and quick clear
 */
@Composable
fun OrvexisSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    placeholder: String = "Search tools, prompts, or models...",
    modifier: Modifier = Modifier,
    testTag: String = "search_bar"
) {
    val shape = RoundedCornerShape(14.dp)

    Box(
        modifier = modifier
            .testTag(testTag)
            .fillMaxWidth()
            .height(50.dp)
            .clip(shape)
            .background(DeepGraphite)
            .border(1.dp, BorderGlow, shape)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                tint = CoolGrey,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Box(modifier = Modifier.weight(1f)) {
                if (query.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.bodyMedium.copy(color = MutedGrey)
                    )
                }
                androidx.compose.foundation.text.BasicTextField(
                    value = query,
                    onValueChange = onQueryChange,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyMedium.copy(color = SoftWhite),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            if (query.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Clear search",
                    tint = CoolGrey,
                    modifier = Modifier
                        .size(18.dp)
                        .clickable { onQueryChange("") }
                )
            }
        }
    }
}

/**
 * 5. Dashboard Card Container with restrained gradient glow border
 */
@Composable
fun DashboardCard(
    modifier: Modifier = Modifier,
    borderColor: Color = BorderGlow,
    backgroundColor: Color = DeepGraphite,
    cornerRadius: Dp = 16.dp,
    onClick: (() -> Unit)? = null,
    testTag: String = "dashboard_card",
    content: @Composable () -> Unit
) {
    val shape = RoundedCornerShape(cornerRadius)
    val cardModifier = modifier
        .testTag(testTag)
        .clip(shape)
        .background(backgroundColor)
        .border(1.dp, borderColor, shape)
        .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
        .padding(16.dp)

    Box(modifier = cardModifier) {
        content()
    }
}

/**
 * 6. Interactive Tool Card with category badge, star favorite toggle, and execute action
 */
@Composable
fun ToolCard(
    tool: ToolItem,
    onOpen: (ToolItem) -> Unit,
    onToggleFavorite: (String) -> Unit,
    modifier: Modifier = Modifier,
    testTag: String = "tool_card"
) {
    val shape = RoundedCornerShape(16.dp)
    val isFav = tool.isFavorite

    Box(
        modifier = modifier
            .testTag(testTag)
            .clip(shape)
            .background(DeepGraphite)
            .border(1.dp, if (isFav) ChampagneGold.copy(alpha = 0.4f) else BorderGlow, shape)
            .clickable { onOpen(tool) }
            .padding(14.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Category pill
                Box(
                    modifier = Modifier
                        .background(SurfaceContainer, RoundedCornerShape(6.dp))
                        .border(0.5.dp, BorderSubtle, RoundedCornerShape(6.dp))
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Text(
                        text = tool.category.displayName,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                        color = NeonCyan
                    )
                }

                // Favorite Star
                Icon(
                    imageVector = if (isFav) Icons.Filled.Star else Icons.Outlined.StarOutline,
                    contentDescription = if (isFav) "Favorited" else "Add to favorites",
                    tint = if (isFav) ChampagneGold else MutedGrey,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { onToggleFavorite(tool.id) }
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = tool.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    color = SoftWhite
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = tool.description,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 13.sp,
                    color = CoolGrey,
                    lineHeight = 18.sp
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${tool.executionCount} runs",
                    fontSize = 11.sp,
                    color = MutedGrey
                )
                Text(
                    text = "Launch →",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

/**
 * 7. Settings Section Header
 */
@Composable
fun SettingsSection(
    title: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title.uppercase(),
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = CoolGrey,
                letterSpacing = 1.sp
            ),
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(DeepGraphite)
                .border(1.dp, BorderGlow, RoundedCornerShape(16.dp))
        ) {
            Column {
                content()
            }
        }
    }
}

/**
 * 8. Settings Row item with label, description, and trailing control
 */
@Composable
fun SettingsRow(
    title: String,
    subtitle: String? = null,
    icon: ImageVector? = null,
    trailingContent: @Composable () -> Unit,
    onClick: (() -> Unit)? = null,
    showDivider: Boolean = true,
    testTag: String = "settings_row"
) {
    Column(
        modifier = Modifier
            .testTag(testTag)
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(14.dp))
                }
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Medium,
                            color = SoftWhite
                        )
                    )
                    if (subtitle != null) {
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = subtitle,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 12.sp,
                                color = CoolGrey
                            )
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            trailingContent()
        }
    }
    if (showDivider) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp)
                .background(BorderSubtle)
        )
    }
}

/**
 * 9. Custom styled Toggle Switch
 */
@Composable
fun OrvexisToggleSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    testTag: String = "toggle_switch"
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange,
        modifier = modifier.testTag(testTag),
        colors = SwitchDefaults.colors(
            checkedThumbColor = SoftWhite,
            checkedTrackColor = primaryColor,
            uncheckedThumbColor = CoolGrey,
            uncheckedTrackColor = SurfaceContainer,
            uncheckedBorderColor = BorderGlow
        )
    )
}

/**
 * 10. Usage Indicator Bar with Quota percentage
 */
@Composable
fun UsageIndicator(
    label: String,
    current: Int,
    max: Int,
    unit: String = "",
    color: Color = NeonCyan,
    modifier: Modifier = Modifier
) {
    val progress = (current.toFloat() / max.toFloat()).coerceIn(0f, 1f)
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(600, easing = FastOutSlowInEasing),
        label = "usage_progress"
    )

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp, color = CoolGrey)
            )
            Text(
                text = "$current / $max $unit",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = SoftWhite
                )
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(SurfaceContainer)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(animatedProgress)
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp))
                    .background(color)
            )
        }
    }
}

/**
 * 11. Telemetry Sparkline Chart (Custom Canvas)
 */
@Composable
fun SparklineChart(
    data: List<Float>,
    lineColor: Color = NeonCyan,
    modifier: Modifier = Modifier.size(width = 80.dp, height = 32.dp)
) {
    Canvas(modifier = modifier) {
        if (data.size < 2) return@Canvas
        val maxVal = (data.maxOrNull() ?: 1f).coerceAtLeast(1f)
        val minVal = (data.minOrNull() ?: 0f).coerceAtMost(maxVal - 0.1f)
        val range = (maxVal - minVal).coerceAtLeast(0.01f)

        val w = size.width
        val h = size.height
        val stepX = w / (data.size - 1)

        val path = Path()
        data.forEachIndexed { i, value ->
            val x = i * stepX
            val normalized = (value - minVal) / range
            val y = h - (normalized * (h - 4.dp.toPx())) - 2.dp.toPx()
            if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
        }

        drawPath(
            path = path,
            color = lineColor,
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
        )
    }
}

/**
 * 12. Circular AI Confidence Gauge (82.5 Overall Score)
 */
@Composable
fun ConfidenceGauge(
    score: Float = 82.5f,
    modifier: Modifier = Modifier.size(160.dp)
) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(160.dp)) {
            val strokeWidth = 14.dp.toPx()
            val diameter = size.minDimension - strokeWidth
            val radius = diameter / 2
            val center = Offset(size.width / 2, size.height / 2)

            // Background arc (240 degrees sweep starting at 150 deg)
            drawArc(
                color = SurfaceContainer,
                startAngle = 150f,
                sweepAngle = 240f,
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(diameter, diameter),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Glowing score sweep
            val sweep = (score / 100f) * 240f
            drawArc(
                brush = Brush.sweepGradient(
                    0.0f to ChampagneGold,
                    0.5f to NeonCyan,
                    1.0f to ElectricViolet
                ),
                startAngle = 150f,
                sweepAngle = sweep,
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(diameter, diameter),
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "%.1f".format(score),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = SoftWhite
            )
            Text(
                text = "Overall Score",
                fontSize = 11.sp,
                color = CoolGrey,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

/**
 * 13. Upgrade Tier Card
 */
@Composable
fun UpgradeCard(
    tierName: String,
    price: String,
    period: String,
    badge: String?,
    isCurrent: Boolean,
    features: List<String>,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String = "upgrade_card"
) {
    val shape = RoundedCornerShape(20.dp)
    val isPro = badge != null
    val primaryAccent = MaterialTheme.colorScheme.primary

    Box(
        modifier = modifier
            .testTag(testTag)
            .fillMaxWidth()
            .clip(shape)
            .background(
                brush = if (isPro) {
                    Brush.verticalGradient(listOf(DeepGraphite, SurfaceContainer))
                } else {
                    Brush.linearGradient(listOf(DeepGraphite, DeepGraphite))
                }
            )
            .border(
                width = if (isPro) 1.5.dp else 1.dp,
                brush = if (isPro) {
                    Brush.linearGradient(listOf(primaryAccent, ChampagneGold))
                } else {
                    Brush.linearGradient(listOf(BorderGlow, BorderGlow))
                },
                shape = shape
            )
            .padding(20.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = tierName,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = SoftWhite
                    )
                )
                if (badge != null) {
                    Box(
                        modifier = Modifier
                            .background(
                                Brush.horizontalGradient(listOf(primaryAccent, ChampagneGold)),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = badge,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = ObsidianBlack
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = price,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isPro) ChampagneGold else SoftWhite
                )
                Text(
                    text = " / $period",
                    fontSize = 14.sp,
                    color = CoolGrey,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Features checklist
            features.forEach { feature ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = if (isPro) NeonCyan else SuccessGreen,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = feature,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 13.sp,
                            color = SoftWhite
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            if (isCurrent) {
                SecondaryButton(
                    text = "Current Active Plan",
                    onClick = {},
                    enabled = false,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                PrimaryButton(
                    text = "Upgrade to $tierName",
                    onClick = onSelect,
                    modifier = Modifier.fillMaxWidth(),
                    testTag = "select_tier_$tierName"
                )
            }
        }
    }
}

/**
 * 14. Confirmation Dialog
 */
@Composable
fun ConfirmationDialog(
    title: String,
    message: String,
    confirmText: String = "Confirm",
    cancelText: String = "Cancel",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(DeepGraphite)
                .border(1.dp, BorderGlow, RoundedCornerShape(20.dp))
                .padding(24.dp)
        ) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = SoftWhite
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium.copy(color = CoolGrey)
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    SecondaryButton(
                        text = cancelText,
                        onClick = onDismiss
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    PrimaryButton(
                        text = confirmText,
                        onClick = {
                            onConfirm()
                            onDismiss()
                        }
                    )
                }
            }
        }
    }
}

/**
 * 15. Empty State display
 */
@Composable
fun EmptyState(
    title: String,
    subtitle: String,
    icon: ImageVector,
    actionText: String? = null,
    onAction: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(64.dp)
                .background(SurfaceContainer, CircleShape)
                .border(1.dp, BorderGlow, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = CoolGrey,
                modifier = Modifier.size(28.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                color = SoftWhite
            ),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium.copy(color = CoolGrey),
            textAlign = TextAlign.Center
        )
        if (actionText != null && onAction != null) {
            Spacer(modifier = Modifier.height(18.dp))
            PrimaryButton(
                text = actionText,
                onClick = onAction
            )
        }
    }
}

/**
 * 16. Universal One-Tap Copy Button with animated checkmark feedback
 */
@Composable
fun CopyIconButton(
    textToCopy: String,
    onCopied: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    tint: Color = CoolGrey,
    testTag: String = "copy_icon_button"
) {
    val context = LocalContext.current
    var isCopied by remember { mutableStateOf(false) }

    LaunchedEffect(isCopied) {
        if (isCopied) {
            delay(1500)
            isCopied = false
        }
    }

    Box(
        modifier = modifier
            .testTag(testTag)
            .size(36.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(SurfaceContainer)
            .border(0.5.dp, BorderSubtle, RoundedCornerShape(8.dp))
            .clickable {
                val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                clipboard.setPrimaryClip(ClipData.newPlainText("Copied Text", textToCopy))
                isCopied = true
                onCopied?.invoke()
            },
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(targetState = isCopied, label = "copy_anim") { copied ->
            if (copied) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Copied",
                    tint = SuccessGreen,
                    modifier = Modifier.size(16.dp)
                )
            } else {
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = "Copy to clipboard",
                    tint = tint,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

/**
 * 17. Universal Bookmark/Save Button for Saved Vault
 */
@Composable
fun BookmarkIconButton(
    isBookmarked: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String = "bookmark_icon_button"
) {
    Box(
        modifier = modifier
            .testTag(testTag)
            .size(36.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(SurfaceContainer)
            .border(0.5.dp, if (isBookmarked) ChampagneGold.copy(alpha = 0.5f) else BorderSubtle, RoundedCornerShape(8.dp))
            .clickable(onClick = onToggle),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = if (isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
            contentDescription = if (isBookmarked) "Saved to vault" else "Save to vault",
            tint = if (isBookmarked) ChampagneGold else CoolGrey,
            modifier = Modifier.size(18.dp)
        )
    }
}

/**
 * 18. Customizable Luxury User Avatar
 */
@Composable
fun OrvexisAvatar(
    initials: String,
    presetIndex: Int = 0,
    size: Dp = 46.dp,
    statusColor: Color? = null,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val gradients = listOf(
        listOf(ElectricViolet, NeonCyan),
        listOf(ChampagneGold, ElectricViolet),
        listOf(NeonCyan, SuccessGreen),
        listOf(Color(0xFFFF647C), ChampagneGold),
        listOf(ElectricViolet, Color(0xFF6B46C1)),
        listOf(Color(0xFF38B2AC), NeonCyan)
    )
    val currentGradient = gradients[presetIndex.coerceIn(0, gradients.lastIndex)]

    Box(
        modifier = modifier
            .size(size)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .background(Brush.linearGradient(currentGradient))
                .border(1.5.dp, ChampagneGold.copy(alpha = 0.5f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = initials,
                color = SoftWhite,
                fontWeight = FontWeight.Bold,
                fontSize = (size.value * 0.35f).sp
            )
        }

        if (statusColor != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(size * 0.28f)
                    .background(statusColor, CircleShape)
                    .border(1.5.dp, ObsidianBlack, CircleShape)
            )
        }
    }
}

