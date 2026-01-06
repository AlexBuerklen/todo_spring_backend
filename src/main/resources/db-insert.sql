INSERT INTO category (category) VALUES
    ('Einkauf'),
    ('Haushalt'),
    ('Arbeit'),
    ('Organisation'),
    ('Privat'),
    ('Gesundheit')
ON CONFLICT (category) DO NOTHING;

INSERT INTO todo (title, category_id, due, description) VALUES
    ('Milch kaufen', (SELECT id FROM category WHERE category = 'Einkauf'), '2025-12-17', '2L Milch'),
    ('Wohnung saugen', (SELECT id FROM category WHERE category = 'Haushalt'), '2025-12-16', 'Wohnzimmer + Flur'),
    ('Sprint-Review vorbereiten', (SELECT id FROM category WHERE category = 'Arbeit'), '2025-12-18', 'Agenda schreiben und Demo-Notes sammeln'),
    ('Post checken', (SELECT id FROM category WHERE category = 'Organisation'), '2025-12-16', 'Briefe/Termine prüfen und abheften'),
    ('Workout', (SELECT id FROM category WHERE category = 'Gesundheit'), '2025-12-16', '30 Minuten Cardio + Dehnen'),
    ('Geburtstagsgeschenk bestellen', (SELECT id FROM category WHERE category = 'Privat'), '2025-12-20', 'Ideen vergleichen und rechtzeitig bestellen'),
    ('Wäsche waschen', (SELECT id FROM category WHERE category = 'Haushalt'), '2025-12-17', 'Dunkle Wäsche, 40 Grad'),
    ('Arzttermin vereinbaren', (SELECT id FROM category WHERE category = 'Gesundheit'), '2025-12-17', 'Praxis anrufen und Termin für Januar fixieren'),
    ('Feature X implementieren', (SELECT id FROM category WHERE category = 'Arbeit'), '2025-12-19', 'Controller + Service + Tests'),
    ('Kühlschrank aufräumen', (SELECT id FROM category WHERE category = 'Haushalt'), '2025-12-16', 'Ablaufdaten prüfen, Reste verwerten'),
    ('Finanzen aktualisieren', (SELECT id FROM category WHERE category = 'Organisation'), '2025-12-17', 'Ausgaben kategorisieren und Budget checken'),
    ('Buch zurückbringen', (SELECT id FROM category WHERE category = 'Privat'), '2025-12-18', 'Bibliothek: Frist einhalten');

