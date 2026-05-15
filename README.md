# Projekt Setup

## Voraussetzungen

Für das Backend wird ein laufender Microsoft SQL Server benötigt.

Benötigte Software:

- Microsoft SQL Server
- Microsoft SQL Server Management Studio
- Java / Gradle
- IntelliJ IDEA empfohlen

## Datenbank einrichten

1. Microsoft SQL Server installieren und starten.
2. Microsoft SQL Server Management Studio installieren.
3. Mit `localhost` verbinden.
   - SQL Server Authentication muss aktiviert sein.
   - Alternativ ist auch Windows Authentication möglich.
4. Unter **Security > Logins** einen neuen Login anlegen.
   - Benutzername und Passwort müssen den Werten aus der `application.yaml` entsprechen.
5. Zwei Datenbanken erstellen:
   - `SkillDb`
   - `SkillDb.Test`

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
3. Den erhaltenen Token für authentifizierte Requests verwenden.