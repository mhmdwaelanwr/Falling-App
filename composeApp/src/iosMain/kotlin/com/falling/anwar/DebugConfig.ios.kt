package com.falling.anwar

import kotlin.native.Platform

actual val isDebugBuild: Boolean = Platform.isDebugBinary
