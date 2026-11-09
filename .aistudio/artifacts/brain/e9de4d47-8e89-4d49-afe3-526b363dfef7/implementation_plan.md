# ORVEXIS AI — Profile Customization, Save & Copy System, and Free-Use Expansion

Enhance the ORVEXIS AI platform with full profile personalization, a universal save & bookmark vault, an instant one-tap copy system across all surfaces, expanded unmetered free-use capabilities, and streamlined quick-make workflows.

---

## User Review & Critical Decisions

> [!IMPORTANT]
> The enhancement maintains the obsidian dark aesthetic (`#080910`), electric violet (`#895BFF`), and neon cyan (`#35DFFF`) glows while dramatically simplifying access and personal control:
> - **Full Profile Customization**: Users can edit their display name, handle/title, status aura, and avatar selection.
> - **Universal Save System**: Store prompts, code snippets, chat responses, and tool outputs into a dedicated Saved Vault with category filters and instant recall.
> - **One-Tap Copy System**: Added to every chat bubble, code block, tool card, and saved note with smooth clipboard copying and visual checkmark + toast feedback.
> - **Free Use Expansion & Simple Mode**: Added "Unmetered Free Mode" toggle, instant "+50 Daily Credits" top-up action, and 1-tap "Quick Make" templates for effortless creation.

- **Confirmed Decision 1**: **Unified Saved Vault** — Accessible from the bottom navigation/more drawer, allowing users to organize saved prompts, code snippets, and AI responses with tag filtering.
- **Confirmed Decision 2**: **Zero-Friction Free Mode** — Users can toggle "Unmetered Free Mode" anytime in Settings or Dashboard to experiment without credit deductions.
- **Confirmed Decision 3**: **Immediate Profile Sync** — Changing profile details (name, title, avatar style) immediately updates the Dashboard header, Chat user bubbles, and Settings card.

---

## 1. Overview & Core Concept

- **What It Does**: Upgrades ORVEXIS AI with intuitive personal identity tools, a centralized knowledge vault for saving breakthrough ideas, universal clipboard copying, and friction-free generative access.
- **Target Audience / Persona**: Power users, creators, and developers who need instant access to generated outputs, personal branding, and unconstrained experimentation.
- **Key Value**: Delivers maximum leverage with minimum effort—every generated output can be saved or copied with a single tap, profiles are deeply customizable, and credit constraints can be bypassed in free mode.

---

## 2. User Experience & Visual Design

### Key User Flows

1. **Profile Customization Flow**:
   - User taps on their avatar or profile card on the Dashboard or Settings screen.
   - Opens the **Profile Customization Modal/Sheet**:
     - Avatar selector with 6 futuristic gradient presets and custom initials.
     - Display name field (e.g. "Esther Howard" / "Alex Vance").
     - Title / Role field (e.g. "Executive AI Architect", "Creative Director").
     - Status Indicator selector ("Autonomous Mode", "Deep Thinking", "Systems Nominal", "Creative Flow") with colored aura dots.
     - User Bio & Primary Focus field.
   - Tapping "Save Profile" immediately updates the app header, chat identity, and local preferences with a celebratory toast.

2. **Universal Save System & Saved Vault Flow**:
   - On any chat bubble, code generation box, or tool output, a bookmark/save icon (`BookmarkBorder` / `Bookmark`) lets the user save the item.
   - Opens the **Saved Vault**:
     - Filter tabs: *All*, *Code Snippets*, *AI Responses*, *Prompts*, *Tools*.
     - Search bar to locate saved items by keyword.
     - Each saved card has: Category badge, timestamp, preview text, 1-tap copy button, and delete action.
     - "Send to Chat" button to reload any saved prompt directly into active conversation.

3. **Universal One-Tap Copy System**:
   - Visual copy buttons present on all content blocks (chat messages, generated code, prompt templates, and saved cards).
   - Tapping copies plain text or formatted code to the Android clipboard.
   - Instant visual feedback: the copy icon transitions into a green checkmark (`Check`) for 1.5 seconds, accompanied by a clean toast notification (`"Copied to clipboard"`).

4. **Expanded Free Use & Simple-Make Mode**:
   - **Unmetered Free Mode Toggle**: Accessible in the header and settings; when active, token deductions are set to 0.
   - **Daily Top-Up Button**: A quick "+50 Free Credits" button in the credit badge and upgrade screen for instant replenishment.
   - **Quick-Make Carousel**: 1-tap shortcut cards on the Dashboard ("Fast Idea", "Instant Code", "Executive Summary", "Cinematic Prompt") that instantly generate results without manual prompt writing.

---

## 3. Key Product Decisions & Trade-Offs

- **Decision 1: Reactive State Integration in `OrvexisViewModel`**
  - *Chosen Approach*: Centralize profile data, saved items list, free-mode flag, and clipboard operations in `OrvexisViewModel`.
  - *Why*: Ensures reactive consistency—saving an item in Create Studio immediately updates the Saved Vault count and badges across all screens.
- **Decision 2: Non-Destructive Storage Structure**
  - *Chosen Approach*: Model saved items with unique IDs, categories, timestamps, and full text payloads.
  - *Why*: Allows easy filtering, deletion, and cross-screen sharing without schema conflicts.
- **Decision 3: Integrated Modal & Dedicated Screen Access**
  - *Chosen Approach*: Profile editing and item saving work seamlessly via modal bottom sheets and can also be navigated to via the navigation drawer.
  - *Why*: Keeps workflows fast (1 tap from current context) while providing a dedicated overview when managing larger collections.

---

## 4. Technical Architecture & Data Strategy

### System Component & Data Flow

```
┌─────────────────────────────────────────────────────────────────┐
│                       OrvexisViewModel                          │
├────────────────────────────────┬────────────────────────────────┤
│ - userProfile: UserProfile     │ - savedVault: List<SavedItem>  │
│ - isFreeModeActive: Boolean    │ - userCredits: StateFlow<Int>  │
│ - saveItem(item: SavedItem)    │ - updateProfile(profile)       │
│ - copyToClipboard(text: String)│ - topUpFreeCredits(amount)     │
└────────────────┬───────────────┴────────────────┬───────────────┘
                 │                                │
     ┌───────────┴───────────┐        ┌───────────┴───────────┐
     ▼                       ▼        ▼                       ▼
[ProfileEditorSheet]   [SavedVaultScreen]  [QuickMakeRow]   [UniversalCopy]
- Avatar Preset Grid    - Categorized List  - 1-Tap Actions  - Icon Animation
- Name & Handle Inputs  - Keyword Search    - Auto-Dispatch  - System Clipboard
- Status Aura Dots      - 1-Tap Copy/Delete - Zero-Friction  - Toast Trigger
```

### New Data Entities (`Models.kt`)

- `UserProfile`: id, displayName, handle, title, avatarPresetIndex, statusText, statusColorHex, bio
- `SavedItem`: id, title, content, type (PROMPT, CODE, RESPONSE, TOOL), timestamp, modelUsed

---

## Next Steps Upon Approval
Upon clicking **Proceed**, we will:
1. Extend `Models.kt` with `UserProfile` and `SavedItem` models.
2. Update `OrvexisViewModel.kt` with profile management, saved vault repository, top-up actions, and free-mode controls.
3. Build `ProfileEditDialog` and `SavedVaultScreen`.
4. Integrate universal 1-tap copy buttons with animated checkmark feedback across `ChatScreen`, `CreateStudioScreen`, `ToolsHubScreen`, and `DashboardScreen`.
5. Add the "Unmetered Free Mode" toggle and "Quick-Make" templates to the Dashboard.
6. Verify build compilation via `compile_applet`.
