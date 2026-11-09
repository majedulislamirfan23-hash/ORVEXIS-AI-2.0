package com.example

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class ExampleAppTest {

  @Test
  fun testAppCoreModules() {
    val destinations = com.example.model.ScreenDestination.values()
    assertEquals(16, destinations.size)
    assertNotNull(com.example.model.ScreenDestination.DASHBOARD)
    assertNotNull(com.example.model.ScreenDestination.AI_CHAT)
    assertNotNull(com.example.model.ScreenDestination.TOOLS_HUB)
  }

  @Test
  fun testToolCategories() {
    val categories = com.example.model.ToolCategory.values()
    assertEquals(11, categories.size)
  }
}
