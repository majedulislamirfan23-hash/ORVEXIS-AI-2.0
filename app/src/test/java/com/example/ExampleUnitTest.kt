package com.example

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun testViewModelInitialState() {
    val vm = com.example.viewmodel.OrvexisViewModel()
    assertEquals(com.example.model.ScreenDestination.DASHBOARD, vm.currentDestination.value)
    assertEquals(100, vm.userCredits.value)
    assertTrue(vm.isFreeModeActive.value)
    assertEquals(4, vm.availableModels.size)
    assertEquals(10, vm.tools.value.size)
    assertEquals("Esther Howard", vm.userProfile.value.displayName)
    assertEquals(3, vm.savedItems.value.size)
  }

  @Test
  fun testProfileAndSaveSystem() {
    val vm = com.example.viewmodel.OrvexisViewModel()
    vm.updateProfile(
      displayName = "Alex Vance",
      handle = "@alex.ai",
      title = "Executive Strategist",
      avatarPreset = 2,
      statusText = "Autonomous Mode",
      statusColorHex = "#35DFFF",
      bio = "Custom Bio"
    )
    assertEquals("Alex Vance", vm.userProfile.value.displayName)
    assertEquals(2, vm.userProfile.value.avatarPreset)

    // Save item
    vm.saveItem(
      title = "Test Snippet",
      content = "val x = 42",
      type = com.example.model.SavedItemType.CODE,
      categoryTag = "Unit Test"
    )
    assertTrue(vm.isItemSaved("val x = 42"))
    assertEquals(4, vm.savedItems.value.size)

    val saved = vm.savedItems.value.first()
    vm.deleteSavedItem(saved.id)
    assertFalse(vm.isItemSaved("val x = 42"))
  }

  @Test
  fun testFreeModeAndTopUp() {
    val vm = com.example.viewmodel.OrvexisViewModel()
    assertTrue(vm.isFreeModeActive.value)
    vm.toggleFreeMode()
    assertFalse(vm.isFreeModeActive.value)

    val currentCredits = vm.userCredits.value
    vm.topUpCredits(50)
    assertEquals(currentCredits + 50, vm.userCredits.value)
  }
}
