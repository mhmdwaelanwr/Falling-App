# Falling App

<p align="center">
  <strong>Cross-platform fall-alert and emergency-response interface built with Kotlin Multiplatform.</strong>
</p>

<p align="center">
  <img alt="Kotlin Multiplatform" src="https://img.shields.io/badge/Kotlin-Multiplatform-7F52FF?style=flat-square&logo=kotlin&logoColor=white">
  <img alt="Compose Multiplatform" src="https://img.shields.io/badge/Compose-Multiplatform-4285F4?style=flat-square&logo=jetpackcompose&logoColor=white">
  <img alt="Android" src="https://img.shields.io/badge/Android-Foreground%20Service-3DDC84?style=flat-square&logo=android&logoColor=white">
  <img alt="IEEE CASS Competition 2026" src="https://img.shields.io/badge/IEEE%20CASS%202026-University%20Track-00629B?style=flat-square">
</p>

Falling App is an experimental safety interface for consuming fall-detection events and turning them into a persistent alert workflow. The project focuses on shared cross-platform state and UI, Android background alert behavior, and a manual acknowledgement model that prevents a later `NO_FALL` event from silently clearing an active emergency.

## Competition context

Falling App was developed as part of **Team MSC AOU's participation in the University Track of the IEEE CASS Competition 2026**.

The competition project focused on the software response layer of a fall-detection concept: receive a `FALL` / `NO_FALL` event from an external detector and turn it into a clear emergency workflow with persistent alerts, countdown behavior, notifications, vibration, and explicit user acknowledgement.

- **Competition:** IEEE CASS Competition 2026
- **Track:** University Track
- **Team:** MSC AOU
- **Project:** Falling App
- **Participation certificate date:** January 27, 2026

For a competition-oriented walkthrough, problem statement, demo flow, and engineering scope, see **[Competition Showcase](docs/COMPETITION_SHOWCASE.md)**.

> **Project status:** prototype / engineering demo. It is not a certified medical device or a replacement for professional emergency systems.

## What it demonstrates

- Shared application state and UI with **Kotlin Multiplatform** and **Compose Multiplatform**.
- A simple `FALL` / `NO_FALL` event contract that can be connected to external detection hardware or software.
- Persistent Android alert handling through a foreground service.
- Audible alerts, notifications, vibration, and an emergency countdown workflow.
- Manual acknowledgement before returning from an active alert to the normal state.
- Built-in debug simulation so the complete flow can be demonstrated without dedicated hardware.

## Alert model

```text
External detector
      ↓
FALL / NO_FALL event
      ↓
   FallStore
      ↓
NORMAL ── FALL ──> ALERT
                    │
                    └── manual acknowledgement ──> NORMAL
```

Once the application enters `ALERT`, receiving `NO_FALL` does not automatically clear the alert. The user must explicitly acknowledge that they are safe.

## Competition demo scenario

The application includes a debug simulation path so the emergency workflow can be demonstrated even when external detection hardware is unavailable.

A typical demo shows:

1. The application in the normal monitoring state.
2. A simulated `FALL` event entering the shared state layer.
3. Transition to the emergency alert state.
4. Persistent Android alert behavior through the foreground service.
5. Audible, visual, vibration, notification, and countdown feedback.
6. A later `NO_FALL` signal being intentionally prevented from silently dismissing the active emergency.
7. Explicit user acknowledgement returning the application to the normal state.

This keeps the software flow reproducible for demonstrations, judging, development, and testing without claiming that the repository contains validated fall-detection hardware or a medical-grade detection model.

## Architecture

The project separates shared state and UI from platform-specific behavior:

```text
composeApp/commonMain
├── domain state and models
└── Compose Multiplatform UI

composeApp/androidMain
├── foreground alert service
├── notifications
└── media / siren integration

iosApp
└── iOS application wrapper
```

Key components include:

- `FallStore.kt` — state machine for the normal and alert states.
- `HomeScreen.kt` — monitoring / safe-state interface.
- `AlertScreen.kt` — active emergency interface and countdown.
- `AlertService.kt` — Android foreground service for persistent alert behavior.

## Running the project

### Android

1. Open the project in Android Studio.
2. Install the required Android SDK components.
3. Sync Gradle.
4. Run the `composeApp` module on an emulator or physical Android device.

### iOS

Open the iOS project from the `iosApp` directory in Xcode and run it on a simulator or supported device.

## Android permissions

The Android application uses permissions for:

- posting notifications,
- running the foreground alert service,
- vibration / haptic feedback.

Review the platform configuration before using the project outside a development environment.

## Testing without hardware

Debug builds include a hidden fall-event simulation path that triggers the same application alert flow used by an incoming fall event.

This makes it possible to test the state transition, foreground service, siren, notification, countdown, and manual acknowledgement flow without connecting an external detector.

## Integration direction

The shared state layer is intentionally small so other event sources can feed it. A detector only needs to translate its output into the project event contract:

```kotlin
enum class FallEvent {
    FALL,
    NO_FALL
}
```

Potential transports include Bluetooth, MQTT, local sensor processing, or another application/service layer.

## Scope and safety note

This repository is an engineering prototype and competition project. Real-world fall detection and emergency response require validated hardware or detection models, platform-specific reliability work, privacy review, production emergency infrastructure, and appropriate regulatory / safety assessment.

The project should not be presented as a certified medical device or as a replacement for professional emergency services.
