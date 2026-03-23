# Projekt Setup

- Microsoft SQL Server ist notwendig -> Installation und Server muss online sein

1. MS Server Management Studio installieren
2. mit 'localhost' verbinden (SQL Server Auth muss aktiv sein, Login geht aber hier auch über Windows)
3. unter Reiter Security -> Logins: Login hinzufügen mit Name und Passwort aus application.yaml
4. SkillDb und SkillDb.Test erstellen

Sollte das Setup nicht funktionieren, bitte Mail an hannahmoog01@gmail.com

# Test Abdeckung
- Domain Layer und Repository Layer sind vollständig getestet, Komplette Logik kann theoretisch über Unit Tests abgedeckt werden

# Ausführen Backend
- Migrationen funktionieren nur auf einer älteren SpringBoot Version
- IntelliJ nutzt im Standard Run in der Regel trotz Gradle die neuste
- Falls 'bootRun' Profil nicht verfügbar: Neue Konfigerstellen mit Command 'bootRun' (dieses Profil bleibt permanent auf 'laden', nach ein paar Sekunden ist das Backend online sofern keine FEhlermeldung kommt)

# Api Doc
- zum aktuellen Stand ist kein Frontend implementiert
- API Swagger: http://localhost:8080/swagger-ui/index.html
- Vorgegebene (public) Skills sind in der Migration zum Testen
- bitte beachten dass die meisten Endpoints authentfiziert sein müssen, es sollte also zuerst ein User (user controller) erstellt werden und dann ein Login (auth controller) durchgeführt werden