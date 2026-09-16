# Security and Safety Policy

Falling App is currently an engineering prototype / competition project. It must not be treated as a guaranteed emergency-response system or certified medical device.

## Reporting a Security Issue

Do not open a public issue for vulnerabilities that could expose secrets, personal data, or unsafe emergency behavior. Report the problem privately to the repository owner with enough detail to reproduce it safely.

## Repository Rules

- Never commit API keys, passwords, tokens, signing keys, certificates, or service credentials.
- Keep `local.properties`, `.env` files, Android/iOS signing material, and private service configuration outside Git.
- Do not commit real emergency contacts, phone numbers, health information, location history, or other personal data.
- Keep debug fall simulation clearly separated from production behavior.
- Review any future Bluetooth, MQTT, backend, analytics, location, or notification integration before adding it.

Automated repository-health checks catch some common mistakes, but they are not a substitute for code and security review.

## Safety Boundary

The current project demonstrates software response to a fall event. Real-world use requires validation of the detector, transport reliability, alert persistence, delivery/failure handling, device/background behavior, privacy, and any applicable safety/regulatory requirements.
