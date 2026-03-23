INSERT INTO [dbo].[User]
VALUES('skillapp', 'test-password', NULL, NULL);

INSERT INTO [dbo].[Skill] ([Name], [Description], [StressLevel], [Author], [Visibility], [ImageSrc])
VALUES
    (
    N'STOP-Skill',
    N'Der STOP-Skill hilft dir, impulsive Reaktionen zu unterbrechen. Erst stoppen, dann bewusst atmen, die Situation beobachten und erst danach gezielt handeln. Besonders hilfreich bei starker innerer Anspannung oder akuten Konflikten.',
    78,
    N'skillapp',
    2,
    NULL
    ),
    (
    N'Eiswürfel halten',
    N'Das Halten von Eiswürfeln kann helfen, intensive innere Spannung kurzfristig zu regulieren. Der starke Kältereiz lenkt die Aufmerksamkeit auf den Körper und kann dabei unterstützen, destruktive Impulse zu unterbrechen.',
    91,
    N'skillapp',
    2,
    NULL
    ),
    (
    N'Kaltes Wasser ins Gesicht',
    N'Ein starker Kältereiz im Gesicht kann helfen, das Stressniveau rasch zu senken und den Fokus zurück in den Moment zu holen. Gut geeignet bei Überforderung, innerer Unruhe oder dem Gefühl, die Kontrolle zu verlieren.',
    84,
    N'skillapp',
    2,
    NULL
    ),
    (
    N'Atemfokus 4-6',
    N'Bei diesem Skill atmest du 4 Sekunden ein und 6 Sekunden aus. Die verlängerte Ausatmung kann beruhigend auf das Nervensystem wirken und eignet sich besonders in Situationen mittlerer Anspannung.',
    42,
    N'skillapp',
    2,
    NULL
    ),
    (
    N'5-4-3-2-1 Orientierung',
    N'Benenne 5 Dinge, die du siehst, 4, die du fühlst, 3, die du hörst, 2, die du riechst und 1, die du schmeckst. Diese Übung hilft dir, dich wieder im Hier und Jetzt zu verankern.',
    56,
    N'skillapp',
    2,
    NULL
    ),
    (
    N'Spannungsskala prüfen',
    N'Beurteile deine aktuelle Anspannung auf einer Skala von 0 bis 100. Dieser Skill stärkt die Selbstwahrnehmung und hilft dir, frühzeitig passende Gegenmaßnahmen auszuwählen, bevor die Spannung zu hoch wird.',
    28,
    N'skillapp',
    2,
    NULL
    ),
    (
    N'Power-Walk',
    N'Ein kurzer, intensiver Spaziergang oder zügiges Gehen kann überschüssige Anspannung abbauen und dich körperlich regulieren. Besonders hilfreich, wenn du das Gefühl hast, innerlich festzustecken.',
    63,
    N'skillapp',
    2,
    NULL
    ),
    (
    N'Gummiband-Skill',
    N'Ein leichtes Schnipsen mit einem Gummiband am Handgelenk kann als kurzer Reiz dienen, um Grübelschleifen oder impulsive Handlungen zu unterbrechen. Sollte bewusst und achtsam eingesetzt werden.',
    69,
    N'skillapp',
    2,
    NULL
    ),
    (
    N'Duftanker nutzen',
    N'Ein intensiver Duft, etwa ätherisches Öl oder ein vertrautes Parfum, kann helfen, den Fokus zu verändern und Sicherheit zu vermitteln. Sinnesreize sind oft besonders wirksam bei innerer Anspannung.',
    35,
    N'skillapp',
    2,
    NULL
    ),
    (
    N'Muskelanspannung und Loslassen',
    N'Spanne nacheinander verschiedene Muskelgruppen für einige Sekunden an und lass dann bewusst los. Diese Übung unterstützt die körperliche Entspannung und verbessert die Wahrnehmung von Anspannung.',
    31,
    N'skillapp',
    2,
    NULL
    ),
    (
    N'Sicherer Ort',
    N'Stelle dir einen inneren sicheren Ort vor, an dem du dich geschützt, ruhig und stabil fühlst. Dieser Imaginationsskill kann helfen, sich emotional zu beruhigen und Distanz zur aktuellen Belastung zu gewinnen.',
    40,
    N'skillapp',
    2,
    NULL
    ),
    (
    N'Radikale Akzeptanz',
    N'Dieser Skill unterstützt dich dabei, belastende Realität zunächst anzuerkennen, ohne sie sofort verändern zu müssen. Akzeptanz bedeutet nicht Zustimmung, sondern den Kampf gegen das Unveränderliche zu beenden.',
    52,
    N'skillapp',
    2,
    NULL
    ),
    (
    N'Ablenkung mit Kopfrechnen',
    N'Intensives Kopfrechnen oder Rückwärtszählen in ungewöhnlichen Schritten kann helfen, die Aufmerksamkeit von überwältigenden Gefühlen wegzulenken. Sinnvoll bei hoher Anspannung und kreisenden Gedanken.',
    74,
    N'skillapp',
    2,
    NULL
    ),
    (
    N'Notfallbox verwenden',
    N'Eine Notfallbox enthält hilfreiche Gegenstände wie Duft, Texturen, Fotos, Skillkarten oder beruhigende Erinnerungen. Sie kann in Krisen schnell Orientierung geben und den Zugriff auf bewährte Skills erleichtern.',
    58,
    N'skillapp',
    2,
    NULL
    ),
    (
    N'Selbstberuhigende Worte',
    N'Formuliere kurze, beruhigende Sätze wie: "Ich halte diesen Moment aus" oder "Die Anspannung geht vorbei". Solche inneren Botschaften können stabilisieren und gegen extreme innere Bewertungen helfen.',
    22,
    N'skillapp',
    2,
    NULL
    );