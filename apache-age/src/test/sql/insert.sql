TRUNCATE TABLE solar_system, waypoint, waypoint_jump_gate;

INSERT INTO solar_system (symbol, x, y) VALUES ('X1-ZA40', 34, 40),
                                                ('X1-TQ4', 24, 5),
                                               ('X1-KS23', 38, 3),
                                               ('X1-JP80', 8, 18),
                                               ('X1-N82', 44, 54)
;

INSERT INTO waypoint (symbol, system_symbol, type, x, y) VALUES
     ('X1-ZA40-15970B', 'X1-ZA40', 'PLANET', 10, 0),
       ('X1-ZA40-28549E', 'X1-ZA40', 'JUMP_GATE', 2, 75),

       ('X1-TQ4-38810X', 'X1-TQ4', 'PLANET', 10, 0),
       ('X1-TQ4-43213E', 'X1-TQ4', 'ORBITAL_STATION', 10, 0),
       ('X1-TQ4-76314C', 'X1-TQ4', 'JUMP_GATE', 2, 75),

       ('X1-KS23-39184B', 'X1-KS23', 'JUMP_GATE', 42, 48),
       ('X1-JP80-14117Z', 'X1-JP80', 'JUMP_GATE', -62, -60),
       ('X1-N82-72562D', 'X1-N82', 'JUMP_GATE', -2, -66)

;

INSERT INTO waypoint_jump_gate(symbol, system_symbol) VALUES
        ('X1-ZA40-28549E', 'X1-TQ4'),
        ('X1-TQ4-76314C', 'X1-ZA40'),

        ('X1-TQ4-76314C', 'X1-KS23'),
        ('X1-KS23-39184B', 'X1-TQ4'),

        ('X1-KS23-39184B', 'X1-JP80'),
        ('X1-JP80-14117Z', 'X1-KS23'),

        ('X1-KS23-39184B', 'X1-N82'),
        ('X1-N82-72562D', 'X1-KS23');



