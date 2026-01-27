# Falling App
> **Emergency Fall Detection & Response System**

[![Platform](https://img.shields.io/badge/Platform-Android%20%7C%20iOS-brightgreen)](#)
[![Kotlin](https://img.shields.io/badge/Kotlin-Multiplatform-blue)](https://kotlinlang.org/docs/multiplatform.html)
[![Compose](https://img.shields.io/badge/Compose-Multiplatform-orange)](https://www.jetbrains.com/lp/compose-multiplatform/)

## 1. Concept & Problem Statement
Falling App is a healthcare-grade safety monitoring application designed to detect and respond to emergency fall events for seniors.

The system consumes events from an AI-powered sensor system that outputs exactly two states: **FALL** or **NO_FALL**. The app prioritizes safety and stability, ensuring that once a fall is detected, the alert remains active until manually cleared by the user.

**Core Logic:**
*   **Safety Lock:** A `FALL` detection triggers an irreversible **ALERT** state that ignores subsequent `NO_FALL` signals.
*   **Senior-Friendly UI:** Large typography, high-contrast elements, and big tap targets (>= 48dp) designed for accessibility.
*   **Manual Acknowledgment:** Only the "I'M OK" button can silence the siren and reset the app.

---

## 2. Features
- **Healthcare UI:** Clean, clinical design (mostly white) with teal/blue accents.
- **Shared UI (Compose Multiplatform):** Unified design and logic for both Android and iOS in `commonMain`.
- **Tasteful Animations:** Calm breathing pulses on status indicators and smooth screen transitions.
- **Hidden Debug Panel:** Access developer tools by tapping the "Falling App" title 4 times (Debug builds only).
- **Persistent State:** App state (Normal/Alert) is saved and restored even after app restarts.
- **Android Alert Mechanism:**
    - **Foreground Service:** Ensures the alarm continues even if the app is closed.
    - **High-Priority Notification:** Immediate visibility on the lock screen.
    - **Looping Siren:** High-decibel emergency audio (`siren.mp3`).

---

## 3. UI/UX Design

### Color Tokens
- **Background:** #FFFFFF (Clinical White)
- **Normal:** #009688 (Care Teal)
- **Alert:** #D32F2F (Alert Red)
- **Text:** #111827 (High Contrast)

### Screens
1. **NORMAL Screen:** Displays "Monitoring" status with a gentle breathing pulse. Shows the timestamp of the last detected event.
2. **ALERT Screen:** Soft red tint with a clear "FALL DETECTED" warning. Features two large action buttons: "I'M OK" and "Call Emergency".

---

## 4. Repository Structure

```
FallingApp/
├── composeApp/
│   ├── src/
│   │   ├── commonMain/                  # Shared Business Logic & UI
│   │   │   ├── kotlin/com/falling/anwar/
│   │   │   │   ├── domain/              # Store, State Management
│   │   │   │   ├── ui/
│   │   │   │   │   ├── theme/           # Healthcare Material3 Theme
│   │   │   │   │   ├── components/      # Reusable Senior-friendly UI
│   │   │   │   │   └── screens/         # Home & Alert Screens
│   │   │   │   └── debug/               # Hidden Debug Gesture & Panel
│   │   ├── androidMain/                 # Android Specific Implementations
│   │   │   ├── kotlin/com/falling/anwar/
│   │   │   │   ├── AlertService.kt      # Foreground Service & Siren
│   │   │   │   └── UiPreviews.kt        # Android Studio UI Previews
│   │   └── iosMain/                     # iOS Specific Implementations
├── iosApp/                              # iOS Xcode Project
└── README.md                            # You are here
```

---

## 5. Setup & Run

### Requirements
- Android Studio (Ladybug/Meerkat+)
- JDK 17
- Xcode (for iOS)

### Installation
1. Clone the repository.
2. **Android:** Run the `composeApp` configuration.
3. **iOS:** Run the `iosApp` configuration from Android Studio or Xcode.

---

## 6. Testing with Hidden Debug Panel
To simulate a fall during the demo:
1. Ensure you are running a **Debug** build.
2. On the Home screen, **tap the "Falling App" title 4 times** within 1.5 seconds.
3. A "Debug Controls" panel will appear.
4. Tap **"Trigger FALL"** to initiate the emergency sequence.
5. Tap **"Acknowledge OK"** to return to normal monitoring.

---

## 7. Competition Demo Steps (30s)
1. **Show Normal State:** Point out the clean healthcare UI and the "Monitoring" status pulse.
2. **Trigger Fall:** Tap title 4 times -> Trigger FALL.
3. **Emergency Response:** Observe the red Alert screen, hear the siren, and show the Android notification.
4. **Resolution:** Tap "I'M OK" to stop the siren and show the updated "Last Event" timestamp on the Home screen.

---

## 8. Permissions
- `POST_NOTIFICATIONS`: For emergency alerts.
- `FOREGROUND_SERVICE`: To maintain the alarm in the background.
- `VIBRATE`: For haptic feedback.
