package com.example.model

enum class ScreenDestination(val title: String, val isPrimaryNav: Boolean = false) {
    DASHBOARD("Dashboard", isPrimaryNav = true),
    AI_CHAT("AI Chat", isPrimaryNav = true),
    TOOLS_HUB("Tools Hub", isPrimaryNav = true),
    CREATE_STUDIO("Create", isPrimaryNav = true),
    SETTINGS("Settings", isPrimaryNav = true),
    
    // More menu / Extended sidebar
    SAVED_VAULT("Saved Vault"),
    PROFILE("Profile"),
    EXPLORE("Explore"),
    DOCUMENTS("Documents"),
    STUDY_MODE("Study Mode"),
    CODE_STUDIO("Code Studio"),
    VISION("Vision"),
    VOICE("Voice"),
    HISTORY("History"),
    CUSTOMIZATION("Customization"),
    UPGRADE("Upgrade Center")
}

data class UserProfile(
    val id: String = "usr_001",
    val displayName: String = "Esther Howard",
    val handle: String = "@esther.ai",
    val title: String = "Lead AI Architect",
    val avatarPreset: Int = 0, // 0 to 5
    val statusText: String = "Systems Nominal",
    val statusColorHex: String = "#42D6A4", // Emerald, Violet, Cyan, Gold
    val bio: String = "Engineering luxury autonomous intelligence pipelines with frontier reasoning models.",
    val isFreeModeActive: Boolean = false
)

enum class SavedItemType(val displayName: String) {
    ALL("All"),
    CODE("Code Snippets"),
    RESPONSE("AI Responses"),
    PROMPT("Prompts"),
    TOOL("Tool Runs")
}

data class SavedItem(
    val id: String,
    val title: String,
    val content: String,
    val type: SavedItemType,
    val timestamp: String,
    val modelName: String = "Orvexis Ultra",
    val categoryTag: String = "General"
)

data class ChatMessage(
    val id: String,
    val isUser: Boolean,
    val text: String,
    val timestamp: String,
    val modelName: String = "Orvexis Ultra",
    val thinkingProcess: String? = null,
    val isStreaming: Boolean = false,
    val isSaved: Boolean = false
)

data class AIModelInfo(
    val id: String,
    val name: String,
    val badge: String,
    val description: String,
    val speed: String
)

data class AIPersona(
    val id: String,
    val name: String,
    val subtitle: String,
    val specialty: String,
    val greeting: String
)

enum class ToolCategory(val displayName: String) {
    ALL("All"),
    WRITING("Writing"),
    CODING("Coding"),
    DESIGN("Design"),
    PROMPTS("Image Prompts"),
    VIDEO("Video Planning"),
    STUDY("Study"),
    BUSINESS("Business"),
    RESEARCH("Research"),
    PRODUCTIVITY("Productivity"),
    APP_DEV("App Development")
}

data class ToolItem(
    val id: String,
    val name: String,
    val category: ToolCategory,
    val description: String,
    val isFavorite: Boolean = false,
    val executionCount: Int = 0,
    val defaultPrompt: String = "",
    val sampleResponse: String = ""
)

data class UserSettings(
    val accentPreset: String = "VIOLET", // VIOLET, CYAN, GOLD, EMERALD
    val isCompactLayout: Boolean = false,
    val fontScale: Float = 1.0f,
    val reducedMotion: Boolean = false,
    val longTermMemory: Boolean = true,
    val extensionsHub: Boolean = true,
    val showToolSteps: Boolean = true,
    val autoRouteMessages: Boolean = true,
    val heartbeat: Boolean = false,
    val defaultThinkingLevel: String = "Moderate (4K)",
    val responseLength: String = "Auto",
    val replyTone: String = "Professional"
)

data class PlanTier(
    val id: String,
    val name: String,
    val priceMonthly: String,
    val priceAnnual: String,
    val badge: String?,
    val isCurrent: Boolean,
    val isFeatured: Boolean,
    val tokenLimit: String,
    val features: List<String>
)
