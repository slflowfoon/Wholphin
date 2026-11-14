package com.github.damontecres.wholphin.util

/** The fork's 0.1.x tags all contain upstream 0.6.3 plus three commits. */
internal fun Version.upstreamMigrationBaseline(): Version =
    if (
        major == 0 && minor == 1 && patch in 0..6 &&
        hash?.take(8) in LEGACY_FORK_COMMITS
    ) {
        Version(0, 6, 3, 3, "d538b8d4")
    } else {
        this
    }

private val LEGACY_FORK_COMMITS =
    setOf("ba4a0cf4", "70b0b375", "0318bf67", "18bdea88", "cd9d16bb", "4d643904")
