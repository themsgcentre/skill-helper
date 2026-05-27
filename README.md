# Projekt Setup

## Voraussetzungen

Benötigte Software:

- Docker Desktop
- Java / Gradle
- Gradle
- IntelliJ IDEA empfohlen

## Datenbank starten

Der Microsoft SQL Server wird über Docker bereitgestellt.

Im Projektordner (SkillHelper) folgenden Befehl ausführen:

```bash
docker compose up -d
```

Falls das Setup nicht funktioniert, bitte eine Mail an: hannahmoog01@gmail.com

## Testabdeckung

Der Domain Layer und der Repository Layer sind vollständig getestet.

Die vollständige Kernlogik des Backends kann theoretisch über Unit Tests abgedeckt werden.

## Backend ausführen

Die Migrationen funktionieren aktuell nur mit einer älteren Spring-Boot-Version, da Flyway mit der neusten Version nicht kompatibel ist.

IntelliJ verwendet beim normalen Run teilweise nicht die Gradle-Konfiguration, sondern eine neuere Spring-Boot-Version. Deshalb sollte das Backend über Gradle gestartet werden.

Falls kein `bootRun`-Profil vorhanden ist:

1. Neue Run Configuration erstellen.
2. Als Gradle Command `bootRun` eintragen.
3. Die Konfiguration starten.

Hinweis: Das `bootRun`-Profil bleibt dauerhaft auf „running“. Nach einigen Sekunden ist das Backend online, sofern keine Fehlermeldung erscheint.

## API-Dokumentation

Aktuell ist kein Frontend implementiert.

Die API-Dokumentation ist über Swagger erreichbar:

`http://localhost:8080/swagger-ui/index.html`

## Nutzung der API

Vorgegebene öffentliche Skills werden über die Migration eingefügt und können zum Testen verwendet werden.

Bitte beachten: Die meisten Endpoints benötigen Authentifizierung.

Empfohlene Reihenfolge:

1. User über den User Controller erstellen.
2. Login über den Auth Controller durchführen.
3. Den erhaltenen Token für authentifizierte Requests verwenden (bzw. sobald 1 und 2 durchgeführt wurden, sollten alle anderen Endpoints problemlos funktionieren)
