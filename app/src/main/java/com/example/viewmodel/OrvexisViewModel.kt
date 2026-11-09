package com.example.viewmodel

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.AIModelInfo
import com.example.model.AIPersona
import com.example.model.ChatMessage
import com.example.model.PlanTier
import com.example.model.SavedItem
import com.example.model.SavedItemType
import com.example.model.ScreenDestination
import com.example.model.ToolCategory
import com.example.model.ToolItem
import com.example.model.UserProfile
import com.example.model.UserSettings
import com.example.ui.theme.ChampagneGold
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.EmeraldAura
import com.example.ui.theme.NeonCyan
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

class OrvexisViewModel : ViewModel() {

    private val _currentDestination = MutableStateFlow(ScreenDestination.DASHBOARD)
    val currentDestination: StateFlow<ScreenDestination> = _currentDestination.asStateFlow()

    private val _navigationHistory = MutableStateFlow(listOf(ScreenDestination.DASHBOARD))

    // Profile State
    private val _userProfile = MutableStateFlow(
        UserProfile(
            displayName = "Esther Howard",
            handle = "@esther.ai",
            title = "Lead AI Architect",
            avatarPreset = 0,
            statusText = "Systems Nominal",
            statusColorHex = "#42D6A4",
            bio = "Engineering luxury autonomous intelligence pipelines with frontier reasoning models."
        )
    )
    val userProfile: StateFlow<UserProfile> = _userProfile.asStateFlow()

    // Free Mode & Credits State
    private val _isFreeModeActive = MutableStateFlow(true)
    val isFreeModeActive: StateFlow<Boolean> = _isFreeModeActive.asStateFlow()

    private val _userCredits = MutableStateFlow(100)
    val userCredits: StateFlow<Int> = _userCredits.asStateFlow()

    private val _settings = MutableStateFlow(UserSettings())
    val settings: StateFlow<UserSettings> = _settings.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    // Universal Saved Vault State
    private val _savedItems = MutableStateFlow<List<SavedItem>>(
        listOf(
            SavedItem(
                id = "save_1",
                title = "Jetpack Compose Glassmorphic Modifier",
                content = "fun Modifier.orvexisGlassCard(): Modifier = this.clip(RoundedCornerShape(16.dp)).background(Color(0xFF111421)).border(1.dp, Color(0xFF1F2438), RoundedCornerShape(16.dp)).padding(16.dp)",
                type = SavedItemType.CODE,
                timestamp = "Today, 09:32 AM",
                categoryTag = "Compose Architecture"
            ),
            SavedItem(
                id = "save_2",
                title = "Executive Frontier Telemetry Brief",
                content = "ORVEXIS AI captures the elite frontier of cognitive productivity with autonomous multi-step reasoning, low-latency token dispatching, and sustained 99.8% precision.",
                type = SavedItemType.RESPONSE,
                timestamp = "Today, 09:30 AM",
                categoryTag = "Executive"
            ),
            SavedItem(
                id = "save_3",
                title = "Midjourney Cybernetic Operating Console",
                content = "/imagine prompt: obsidian dark executive workspace console, violet volumetric neon aura, champagne gold accents, 8k resolution, raytracing --ar 16:9 --v 6.0",
                type = SavedItemType.PROMPT,
                timestamp = "Yesterday",
                categoryTag = "Visual Prompts"
            )
        )
    )
    val savedItems: StateFlow<List<SavedItem>> = _savedItems.asStateFlow()

    // AI Models
    val availableModels = listOf(
        AIModelInfo("orvexis_ultra", "Orvexis Ultra 4.5", "Flagship", "Frontier multi-step reasoning and deep analysis", "240 t/s"),
        AIModelInfo("orvexis_flash", "Orvexis Flash", "Instant", "Ultra-low latency for swift conversational flows", "520 t/s"),
        AIModelInfo("spectrion_pro", "Spectrion Pro", "Autonomous", "Auto-routes agents and manages MCP tools", "310 t/s"),
        AIModelInfo("vision_4k", "Vision Multimodal 4K", "Visual", "Pixel-level precision for UI, charts & images", "180 t/s")
    )

    private val _selectedModel = MutableStateFlow(availableModels[0])
    val selectedModel: StateFlow<AIModelInfo> = _selectedModel.asStateFlow()

    // Personas
    val personas = listOf(
        AIPersona("alex", "Orvexis Core", "Default Operating Intelligence", "General Intelligence", "Systems nominal. How may I accelerate your workflow today?"),
        AIPersona("python_bot", "Python AI Chat", "Senior Systems Architect", "Code & Architecture", "Hello! I am Python AI Chat, your versatile conversational companion that turns logic into production code."),
        AIPersona("buffett", "Buffett Intelligence", "Market & Strategic Analyst", "Strategy & Forecasting", "Ready to dissect market mechanics, unit economics, and capital allocation."),
        AIPersona("seller", "Growth Catalyst", "Product & Conversion Specialist", "Commerce & Conversion", "Welcome to the peak optimization studio. Let's engineer high-converting experiences."),
        AIPersona("resume", "Executive Resume Architect", "Career Milestone Strategist", "Career & Narrative", "I craft compelling career narratives that resonate with high-tier leadership.")
    )

    private val _selectedPersona = MutableStateFlow(personas[0])
    val selectedPersona: StateFlow<AIPersona> = _selectedPersona.asStateFlow()

    // Chat thread
    private val _chatMessages = MutableStateFlow<List<ChatMessage>>(
        listOf(
            ChatMessage(
                id = "msg_0",
                isUser = false,
                text = "Welcome to ORVEXIS AI. All cognitive telemetry is online. How can I assist you with synthesis, code architecture, or creative generation?",
                timestamp = "09:30 AM",
                modelName = "Orvexis Ultra"
            ),
            ChatMessage(
                id = "msg_1",
                isUser = true,
                text = "Give me an executive architectural summary for a luxury futuristic AI platform with Jetpack Compose.",
                timestamp = "09:31 AM"
            ),
            ChatMessage(
                id = "msg_2",
                isUser = false,
                text = "ORVEXIS AI incorporates an obsidian dark visual foundation (#080910), restrained electric violet glow boundaries, and modular Clean MVVM architecture. Every UI surface leverages 60fps edge-to-edge Compose rendering with custom glassmorphic canvas shaders.",
                timestamp = "09:31 AM",
                modelName = "Orvexis Ultra",
                thinkingProcess = "Synthesizing M3 color tokens, obsidian layout hierarchy, and responsive tablet navigation structures..."
            )
        )
    )
    val chatMessages: StateFlow<List<ChatMessage>> = _chatMessages.asStateFlow()

    private val _isGenerating = MutableStateFlow(false)
    val isGenerating: StateFlow<Boolean> = _isGenerating.asStateFlow()

    // Tools Hub
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow(ToolCategory.ALL)
    val selectedCategory: StateFlow<ToolCategory> = _selectedCategory.asStateFlow()

    private val _tools = MutableStateFlow(
        listOf(
            ToolItem("tool_1", "Text Generator", ToolCategory.WRITING, "High-precision prose, essays, and technical briefs.", isFavorite = true, executionCount = 384, defaultPrompt = "Generate an executive summary on AI workflow acceleration."),
            ToolItem("tool_2", "Code Assistant", ToolCategory.CODING, "Generate, fix, and optimize Kotlin & Compose architectures.", isFavorite = true, executionCount = 512, defaultPrompt = "Write a Jetpack Compose glassmorphic card modifier."),
            ToolItem("tool_3", "AI Image Generator", ToolCategory.DESIGN, "Create luxury aesthetic visual assets with prompt refinement.", isFavorite = true, executionCount = 740, defaultPrompt = "Futuristic obsidian console with violet holographic display."),
            ToolItem("tool_4", "Data Analyzer", ToolCategory.BUSINESS, "Analyze complex tabular data and synthesize executive KPIs.", isFavorite = false, executionCount = 189, defaultPrompt = "Extract key variance drivers from quarterly AI inference costs."),
            ToolItem("tool_5", "Study Mode", ToolCategory.STUDY, "Deep multi-turn tutoring and cognitive mastery drills.", isFavorite = true, executionCount = 265, defaultPrompt = "Explain quantum state coherence with mathematical intuition."),
            ToolItem("tool_6", "App Development", ToolCategory.APP_DEV, "Scaffold production Android screens and Compose UI states.", isFavorite = false, executionCount = 430, defaultPrompt = "Generate a responsive list-detail pane in Jetpack Compose."),
            ToolItem("tool_7", "Video Planning", ToolCategory.VIDEO, "Generate visual storyboards, scene beats, and cinematic hooks.", isFavorite = false, executionCount = 120, defaultPrompt = "Create a 60-second teaser script for a futuristic operating system."),
            ToolItem("tool_8", "Image Prompts", ToolCategory.PROMPTS, "Engineer ultra-detailed Midjourney v6 and DALL-E parameters.", isFavorite = false, executionCount = 310, defaultPrompt = "Generate a prompt for cinematic luxury cybernetic interface."),
            ToolItem("tool_9", "Research Digest", ToolCategory.RESEARCH, "Synthesize scientific papers and extract empirical methodologies.", isFavorite = false, executionCount = 98, defaultPrompt = "Summarize latest trends in speculative decoding for LLMs."),
            ToolItem("tool_10", "Productivity Engine", ToolCategory.PRODUCTIVITY, "Convert fragmented notes into actionable Jira & Notion items.", isFavorite = true, executionCount = 340, defaultPrompt = "Turn my stream-of-consciousness brainstorm into structured sprint tasks.")
        )
    )
    val tools: StateFlow<List<ToolItem>> = _tools.asStateFlow()

    // Upgrade tiers
    val planTiers = listOf(
        PlanTier(
            id = "free",
            name = "Explorer Free",
            priceMonthly = "$0",
            priceAnnual = "$0",
            badge = "FREE FOREVER",
            isCurrent = false,
            isFeatured = false,
            tokenLimit = "Unlimited in Free Mode",
            features = listOf("Standard Orvexis Flash & Ultra models", "Unmetered Free Mode available", "Full Tools Hub access", "Universal Save Vault", "Daily +50 bonus top-ups")
        ),
        PlanTier(
            id = "pro",
            name = "Orvexis Pro",
            priceMonthly = "$19",
            priceAnnual = "$15",
            badge = "RECOMMENDED",
            isCurrent = true,
            isFeatured = true,
            tokenLimit = "Unlimited tokens",
            features = listOf("Full access to Orvexis Ultra 4.5", "Spectrion Pro autonomous auto-routing", "Extended Thinking (up to 16K)", "Custom design themes & accents", "Priority high-throughput generation")
        ),
        PlanTier(
            id = "enterprise",
            name = "Enterprise Ultra",
            priceMonthly = "$49",
            priceAnnual = "$39",
            badge = "ULTIMATE",
            isCurrent = false,
            isFeatured = false,
            tokenLimit = "Dedicated cluster",
            features = listOf("All Pro tier privileges", "Private model fine-tuning", "Dedicated neural cluster", "Unlimited team workspaces", "Direct 24/7 engineering SLA")
        )
    )

    private val _isAnnualBilling = MutableStateFlow(true)
    val isAnnualBilling: StateFlow<Boolean> = _isAnnualBilling.asStateFlow()

    // Active tool run modal
    private val _activeToolForRun = MutableStateFlow<ToolItem?>(null)
    val activeToolForRun: StateFlow<ToolItem?> = _activeToolForRun.asStateFlow()

    fun navigateTo(destination: ScreenDestination) {
        if (_currentDestination.value != destination) {
            _navigationHistory.update { it + destination }
            _currentDestination.value = destination
        }
    }

    fun handleBack(): Boolean {
        val history = _navigationHistory.value
        return if (history.size > 1) {
            val updated = history.dropLast(1)
            _navigationHistory.value = updated
            _currentDestination.value = updated.last()
            true
        } else {
            false
        }
    }

    // Profile Management
    fun updateProfile(
        displayName: String,
        handle: String,
        title: String,
        avatarPreset: Int,
        statusText: String,
        statusColorHex: String,
        bio: String
    ) {
        _userProfile.value = _userProfile.value.copy(
            displayName = displayName,
            handle = handle,
            title = title,
            avatarPreset = avatarPreset,
            statusText = statusText,
            statusColorHex = statusColorHex,
            bio = bio
        )
        showToast("Profile updated successfully")
    }

    // Free Mode & Credit Actions
    fun toggleFreeMode() {
        val newState = !_isFreeModeActive.value
        _isFreeModeActive.value = newState
        if (newState) {
            showToast("Unmetered Free Mode Activated! Zero credit deductions.")
        } else {
            showToast("Standard Metered Mode Active.")
        }
    }

    fun topUpCredits(amount: Int = 50) {
        _userCredits.update { it + amount }
        showToast("Added +$amount Free Credits! Total: ${_userCredits.value}")
    }

    // Universal Save Vault Actions
    fun saveItem(title: String, content: String, type: SavedItemType, categoryTag: String = "General") {
        if (isItemSaved(content)) {
            showToast("Already in your Saved Vault")
            return
        }
        val newItem = SavedItem(
            id = UUID.randomUUID().toString(),
            title = title,
            content = content,
            type = type,
            timestamp = "Just now",
            modelName = _selectedModel.value.name,
            categoryTag = categoryTag
        )
        _savedItems.update { listOf(newItem) + it }
        showToast("Saved to Vault: $title")
    }

    fun deleteSavedItem(id: String) {
        _savedItems.update { list -> list.filterNot { it.id == id } }
        showToast("Removed from Saved Vault")
    }

    fun isItemSaved(content: String): Boolean {
        return _savedItems.value.any { it.content.trim() == content.trim() }
    }

    fun toggleSaveChatMessage(messageId: String) {
        val msg = _chatMessages.value.find { it.id == messageId } ?: return
        if (isItemSaved(msg.text)) {
            val existing = _savedItems.value.find { it.content.trim() == msg.text.trim() }
            if (existing != null) {
                deleteSavedItem(existing.id)
            }
        } else {
            val title = if (msg.isUser) "My Prompt" else "AI Synthesis (${msg.modelName})"
            saveItem(
                title = title,
                content = msg.text,
                type = if (msg.isUser) SavedItemType.PROMPT else SavedItemType.RESPONSE,
                categoryTag = "Chat Studio"
            )
        }
    }

    // Simplified 1-Tap Quick Make
    fun quickMake(actionKey: String) {
        when (actionKey) {
            "fast_code" -> {
                sendMessage("Generate a high-performance Kotlin caching helper for Jetpack Compose.")
                navigateTo(ScreenDestination.AI_CHAT)
            }
            "fast_idea" -> {
                sendMessage("Generate 3 breakthrough product ideas combining spatial computing and autonomous LLM agents.")
                navigateTo(ScreenDestination.AI_CHAT)
            }
            "fast_summary" -> {
                sendMessage("Synthesize an executive briefing on AI cognitive productivity and return on compute.")
                navigateTo(ScreenDestination.AI_CHAT)
            }
            "fast_prompt" -> {
                sendMessage("Write an ultra-detailed cinematic Midjourney v6 prompt for a luxury obsidian user interface.")
                navigateTo(ScreenDestination.AI_CHAT)
            }
        }
    }

    fun selectModel(model: AIModelInfo) {
        _selectedModel.value = model
        showToast("Switched to ${model.name}")
    }

    fun selectPersona(persona: AIPersona) {
        _selectedPersona.value = persona
        showToast("Active persona: ${persona.name}")
    }

    fun updateSettings(transform: (UserSettings) -> UserSettings) {
        _settings.update(transform)
    }

    fun resetSettingsToDefault() {
        _settings.value = UserSettings()
        showToast("Settings reset to factory defaults")
    }

    fun toggleFavorite(toolId: String) {
        _tools.update { list ->
            list.map { if (it.id == toolId) it.copy(isFavorite = !it.isFavorite) else it }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setCategory(category: ToolCategory) {
        _selectedCategory.value = category
    }

    fun openToolRunner(tool: ToolItem) {
        _activeToolForRun.value = tool
    }

    fun closeToolRunner() {
        _activeToolForRun.value = null
    }

    fun toggleBillingPeriod(annual: Boolean) {
        _isAnnualBilling.value = annual
    }

    fun sendMessage(userText: String) {
        if (userText.isBlank()) return

        val userMsg = ChatMessage(
            id = UUID.randomUUID().toString(),
            isUser = true,
            text = userText,
            timestamp = "Just now"
        )
        _chatMessages.update { it + userMsg }
        _isGenerating.value = true

        viewModelScope.launch {
            delay(800)
            val model = _selectedModel.value
            val tone = _settings.value.replyTone

            val aiResponseText = when {
                userText.contains("code", ignoreCase = true) || userText.contains("app", ignoreCase = true) ->
                    "```kotlin\n@Composable\nfun OrvexisGlassCard(modifier: Modifier = Modifier) {\n    Box(\n        modifier = modifier\n            .clip(RoundedCornerShape(16.dp))\n            .background(Color(0xFF111421))\n            .border(1.dp, Color(0xFF1F2438), RoundedCornerShape(16.dp))\n            .padding(16.dp)\n    )\n}\n```\nHere is your requested production Compose block, engineered strictly with the official ORVEXIS design tokens."
                userText.contains("idea", ignoreCase = true) || userText.contains("generate", ignoreCase = true) ->
                    "1. Cognitive Neural HUD: A spatial telemetry interface that tracks cognitive focus.\n2. Autonomous Code Architect: Self-assembling Compose component pipelines.\n3. Executive AI Summarizer: Zero-shot boardroom synthesis for multi-source reports."
                else ->
                    "Understood. Analyzing your request with ${_selectedPersona.value.name} in a $tone style.\n\nAll parameters have been synthesized with frontier accuracy. The neural pipeline executed cleanly with 0ms drop in throughput."
            }

            val aiMsg = ChatMessage(
                id = UUID.randomUUID().toString(),
                isUser = false,
                text = aiResponseText,
                timestamp = "Just now",
                modelName = model.name,
                thinkingProcess = if (_settings.value.showToolSteps) "Evaluated request against ${model.name} (${model.speed}). Tone set to $tone." else null
            )
            _chatMessages.update { it + aiMsg }
            _isGenerating.value = false

            // Deduct credits ONLY if Free Mode is NOT active
            if (!_isFreeModeActive.value && _userCredits.value > 0) {
                _userCredits.update { it - 1 }
            }
        }
    }

    fun regenerateLastMessage() {
        val messages = _chatMessages.value
        val lastUser = messages.lastOrNull { it.isUser }
        if (lastUser != null) {
            sendMessage(lastUser.text)
        }
    }

    fun clearChat() {
        _chatMessages.value = listOf(
            ChatMessage(
                id = "welcome",
                isUser = false,
                text = "Chat history cleared. Systems nominal. How can I assist you now?",
                timestamp = "Just now",
                modelName = _selectedModel.value.name
            )
        )
        showToast("Conversation cleared")
    }

    fun upgradePlan(tierName: String) {
        showToast("Switched to $tierName! Enjoy all premium benefits.")
    }

    fun showToast(msg: String) {
        _toastMessage.value = msg
        viewModelScope.launch {
            delay(2800)
            if (_toastMessage.value == msg) {
                _toastMessage.value = null
            }
        }
    }

    fun getActivePrimaryColor(): Color {
        return when (_settings.value.accentPreset) {
            "CYAN" -> NeonCyan
            "GOLD" -> ChampagneGold
            "EMERALD" -> EmeraldAura
            else -> ElectricViolet
        }
    }
}
