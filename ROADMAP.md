# Falling App Roadmap

This repository is treated as the current source of truth. Older local/Drive snapshots are historical backups unless a specific missing asset or idea is intentionally recovered from them.

## Phase 0 — Clean Repository Baseline ✅

- current Kotlin Multiplatform implementation preserved
- Android/iOS/Desktop targets kept intact
- generated and sensitive local files ignored
- Android compile/test CI added
- repository-health and secret-pattern checks added
- competition/demo documentation preserved
- safety boundary kept explicit

## Phase 1 — Stabilize the Existing Prototype

- validate the complete `FALL` → alert → acknowledgement flow
- expand unit tests around `FallStore` and edge cases
- verify countdown lifecycle and cancellation behavior
- test foreground-service restart/background scenarios on Android
- verify notification/vibration/audio behavior across supported Android versions
- make debug simulation clearly isolated from release behavior
- review accessibility and large-touch-target behavior

## Phase 2 — External Detector Integration

Choose one real event transport and complete it end to end instead of adding several partial integrations.

Possible directions:

- Bluetooth/BLE
- MQTT
- local sensor/model bridge
- network/service event source

For the selected transport:

- define connection/reconnection state
- define malformed/stale event behavior
- timestamp and deduplicate events
- expose detector connection health to the UI
- add deterministic integration tests where practical

## Phase 3 — Emergency Workflow Reliability

- persistence across process/device lifecycle events
- explicit alert delivery/failure states
- configurable countdown/escalation policy
- contact model and user-controlled emergency actions
- retry/failure handling
- audit-friendly local event history
- privacy and data-retention design

The app must not claim guaranteed emergency delivery before these paths are implemented and validated.

## Phase 4 — Platform Parity

- validate iOS build and runtime on macOS/Xcode
- define iOS notification/audio/background behavior
- review Desktop target purpose and either polish or explicitly keep it as a development/demo target
- align common state behavior across platforms

## Phase 5 — Demo and Project Polish

- screenshots and short demo video/GIF
- architecture diagram
- reproducible competition/demo instructions
- hardware/integration diagram once a real detector is selected
- final README gallery
- GitHub About/Topics cleanup

## Phase 6 — Real-World Readiness

Only if the project is intended to move beyond an engineering demo:

- threat/privacy review
- production telemetry/crash reporting decision
- reliability testing under poor connectivity and device power constraints
- release signing/pipelines
- user testing
- legal/regulatory/safety assessment appropriate to the intended use

## Development Rule

Keep `main` buildable and demonstrable. Do deeper work in focused branches/PRs, and do not present planned detection/emergency capabilities as production-ready until they actually exist.
