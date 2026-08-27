# IEEE CASS Competition 2026 — Competition Showcase

Falling App was developed as part of **Team MSC AOU's participation in the University Track of the IEEE CASS Competition 2026**.

The project presents a software-side emergency-response workflow for a fall-detection concept. It is designed to receive a simple `FALL` / `NO_FALL` event from an external detector and turn that signal into a clear, persistent user alert.

> This repository documents an engineering prototype and competition project. It is not a certified medical device and must not be treated as a production emergency system.

## Competition context

- **Competition:** IEEE CASS Competition 2026
- **Track:** University Track
- **Team represented:** MSC AOU
- **Project:** Falling App
- **Certificate date:** January 27, 2026
- **Project type:** Cross-platform fall-alert and emergency-response prototype

## Problem statement

A fall-detection system is only useful if a detected event can be communicated to the user reliably and turned into a clear emergency workflow. The application focuses on this response layer rather than claiming to provide a medically validated fall-detection model.

The prototype therefore separates two responsibilities:

1. **Detection source** — hardware, sensor logic, another application, or a future detection model produces a `FALL` or `NO_FALL` event.
2. **Response application** — Falling App consumes that event and manages the alert state, countdown, sound, vibration, notification, and acknowledgement workflow.

## Competition demo flow

A complete demonstration can be performed without external hardware:

1. Launch the Android debug build.
2. Trigger the built-in hidden fall simulation gesture.
3. The application enters the emergency alert state.
4. Android starts the persistent foreground alert behavior.
5. The user receives audible, visual, vibration, and notification feedback.
6. The emergency countdown is displayed.
7. A later `NO_FALL` event does not silently dismiss the emergency.
8. The user must explicitly acknowledge that they are safe before the app returns to the normal state.

This simulation path makes the software workflow reproducible for demonstrations, judging, development, and testing even when dedicated sensor hardware is unavailable.

## Engineering highlights

- Kotlin Multiplatform shared state and UI.
- Compose Multiplatform interface.
- Explicit `NORMAL` and `ALERT` state handling.
- Android foreground service for persistent alerts.
- Audible siren, notifications, vibration, and emergency countdown.
- Manual acknowledgement requirement for active emergencies.
- Debug simulation path for hardware-independent demonstrations.
- Small event contract that can later be connected to Bluetooth, MQTT, local sensor processing, or another service.

## Architecture concept

```text
Fall-detection source
        |
        | FALL / NO_FALL
        v
     FallStore
        |
        +---- NORMAL
        |
        +---- ALERT
               |
               +-- foreground service
               +-- siren
               +-- notification
               +-- vibration
               +-- countdown
               +-- manual acknowledgement
```

## Scope and limitations

The competition prototype demonstrates the application and emergency-response layer. It does **not** claim to provide:

- medically validated fall detection;
- certified emergency-response reliability;
- production-ready remote dispatch;
- validated sensor hardware;
- regulatory approval.

A real deployment would require validated detection hardware or models, reliability testing, privacy review, platform-specific background-execution testing, and appropriate medical/safety assessment.

## Repository

Main project documentation is available in the [README](../README.md).
