# Falling App 🛡️

**Falling App** is a high-performance, cross-platform fall detection and emergency response system built with **Kotlin Multiplatform (KMP)** and **Compose Multiplatform**. It is designed to provide immediate assistance to elderly or high-risk individuals by bridging the gap between hardware sensors and emergency responders.

Developed by **Anwar** (com.falling.anwar).

---

## 🚀 Overview

The system architecture follows a reactive flow:
1. **Hardware Sensors**: Collect movement data from an external AI-powered device.
2. **AI Engine**: Processes sensor data into a binary state: `FALL` or `NO_FALL`.
3. **Falling App**: Consumes these events. Upon a `FALL` detection, it triggers a critical alert. Unlike standard systems, a `NO_FALL` event after a `FALL` will **not** clear the alert. Manual acknowledgement is mandatory to ensure the user is truly safe.

---

## ✨ Features

- **Cross-Platform UI**: Shared high-end healthcare UI using Compose Multiplatform.
- **Critical Alert System**: High-intensity visual and audible alarms.
- **Android Foreground Service**: Ensures the siren and monitoring persist even if the app is in the background or killed.
- **Siren & Notifications**: Uses a dedicated notification channel `fall_alerts` with a loud siren sound (`alarm.mp3`).
- **Smart Logic**: Strictly follows the "I'M OK" protocol for clearing emergencies.
- **Senior-Friendly UX**: High-contrast, minimalist white UI with large, easy-to-tap buttons and clear feedback.
- **Hidden Debug Suite**: Integrated simulation tool for developers and demonstrators.

---

## 🔄 UX Flow

1. **NORMAL State**: Displayed in a calming Green theme with "SAFE" status. Monitoring is active in the background.
2. **FALL Event**: The AI hardware transmits a fall signal.
3. **ALERT State**: 
    - UI immediately switches to a tactical Red theme.
    - Full-volume siren (`alarm.mp3`) blasts continuously.
    - A persistent foreground notification appears in the `fall_alerts` channel.
    - A 30-second emergency countdown is initiated.
4. **I'M OK**: The user must tap the large green "I AM SAFE" button. This is the only way to silence the siren and return to `NORMAL`.
5. **Emergency Call**: If the user is unable to tap "I'M OK", a prominent "CALL EMERGENCY" button provides one-tap access to help.

---

## 🏗️ Architecture

The project leverages a clean **Kotlin Multiplatform** structure:
- **`shared/commonMain`**: Contains the core logic (`FallStore`), domain models, and the shared Compose UI (Screens & Components).
- **`composeApp/androidMain`**: Android-specific implementations, including the `AlertService` (Foreground Service), Notification Management, and MediaPlayer integration.
- **`iosApp`**: SwiftUI wrapper for the shared KMP core.

---

## 📂 Module & File Structure

- **`com.falling.anwar.domain`**:
    - `FallStore.kt`: The central state machine managing `NORMAL` vs `ALERT` states.
    - `AnwarSignature.kt`: Internal identifiers and metadata.
- **`com.falling.anwar.ui.screens`**:
    - `HomeScreen.kt`: High-contrast monitoring dashboard.
    - `AlertScreen.kt`: Tactical emergency interface with countdown and siren control.
- **`com.falling.anwar.android`**:
    - `AlertService.kt`: Foreground service for background siren and notifications.

---

## 🛠️ Setup & Run

### Android
1. Open the project in **Android Studio (Ladybug or newer)**.
2. Ensure you have the Android SDK 34+ installed.
3. Run `gradle sync`.
4. Run the `composeApp` module on a physical device or emulator.

### iOS
1. Open the `iosApp/iosApp.xcworkspace` in **Xcode**.
2. Build and run on a Simulator or iPhone.

---

## 🔑 Permissions

The app requires the following Android permissions:
- `android.permission.POST_NOTIFICATIONS`: Required for Android 13+ to show the alert notification.
- `android.permission.FOREGROUND_SERVICE`: To keep the siren active when the app is backgrounded.
- `android.permission.VIBRATE`: For haptic feedback during alerts.

---

## 🧪 How to Test (Hidden Debug)

To simulate a fall event without hardware:
1. Ensure the app is a **Debug Build**.
2. Navigate to the **Home Screen**.
3. **Tap the "FALLING APP" title 4 times** within 1.5 seconds.
4. The app will immediately trigger the `FALL` logic, starting the siren and alert UI.

---

## 🤖 Future AI Integration

The `FallStore` is designed to be fed by any `FallEventSource`. Current implementation uses a binary protocol:
```kotlin
enum class FallEvent { FALL, NO_FALL }
```
New AI models can be integrated by emitting these events into the `FallStore.onEvent()` stream via Bluetooth, MQTT, or local sensor processing.

---

## 🎬 Demo Script (90s)

1. **Intro (0-15s)**: Show the Home Screen. Highlight the clean "SAFE" UI and explain that it's monitoring via AI.
2. **Trigger (15-30s)**: Perform the **hidden 4-tap gesture**. Explain that this simulates the AI hardware detecting a fall.
3. **Alert (30-60s)**: The screen turns Red, siren starts, and notification appears. Point out that `NO_FALL` wouldn't stop this—it's a critical safety lock.
4. **Resolution (60-90s)**: Tap the large green **"I AM SAFE"** button. The siren stops, the notification disappears, and the UI returns to the Green "SAFE" state.

---
© 2024 Falling App - com.falling.anwar
