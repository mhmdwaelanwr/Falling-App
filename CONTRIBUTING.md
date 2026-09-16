# Contributing to Falling App

Falling App is being developed in stages. Keep `main` as the clean, buildable demo baseline and use focused branches/PRs for deeper work.

## Before Starting

Read:

- `README.md`
- `ROADMAP.md`
- `docs/COMPETITION_SHOWCASE.md`
- `SECURITY.md`

## Local Validation

```bash
./gradlew :composeApp:compileDebugKotlinAndroid
./gradlew :composeApp:testDebugUnitTest
```

For iOS, validate on macOS/Xcode. For Desktop, use the Gradle tasks appropriate to the JVM target when changing desktop-specific behavior.

## Repository Hygiene

Do not commit generated Gradle/Kotlin output, IDE metadata, signing material, `.env` files, `local.properties`, real personal/emergency data, or credentials.

## Scope

Prefer one clear concern per PR. Keep debug simulation obvious, update tests when state behavior changes, and do not describe planned detector/emergency integrations as production-ready until implemented and validated.
