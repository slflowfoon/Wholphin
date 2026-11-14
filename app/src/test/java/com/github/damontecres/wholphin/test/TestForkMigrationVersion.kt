package com.github.damontecres.wholphin.test

import com.github.damontecres.wholphin.util.Version
import com.github.damontecres.wholphin.util.upstreamMigrationBaseline
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TestForkMigrationVersion {
    @Test
    fun `Installed fork keeps old preferences but receives newer migrations`() {
        val baseline = Version.fromString("0.1.6-0-g4d643904").upstreamMigrationBaseline()
        assertFalse(baseline.isEqualOrBefore(Version.fromString("0.3.4-4-g0")))
        assertFalse(baseline.isEqualOrBefore(Version.fromString("0.6.2-18-g0")))
        assertTrue(baseline.isEqualOrBefore(Version.fromString("1.0.1-2-g0")))
        assertTrue(baseline.isEqualOrBefore(Version.fromString("1.0.8-0-g0")))
    }

    @Test
    fun `Every published legacy fork commit has the same upstream baseline`() {
        val hashes = listOf("ba4a0cf4", "70b0b375", "0318bf67", "18bdea88", "cd9d16bb", "4d643904")
        hashes.forEachIndexed { index, hash ->
            assertEquals(
                Version(0, 6, 3, 3, "d538b8d4"),
                Version(0, 1, index, 0, hash).upstreamMigrationBaseline(),
            )
        }
    }

    @Test
    fun `Genuine older upstream and unknown builds keep their migrations`() {
        listOf("0.1.6", "0.1.6-0-g12345678", "0.6.3", "1.1.1-0-g159edc87").forEach { name ->
            val original = Version.fromString(name)
            assertEquals(original, original.upstreamMigrationBaseline())
        }
    }
}
