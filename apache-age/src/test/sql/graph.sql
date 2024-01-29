select * from waypoint_jump_gate;

WITH RECURSIVE dest AS (
    SELECT symbol, system_symbol, 1 AS level
    FROM waypoint_jump_gate
    WHERE system_symbol = 'X1-TQ4'
    UNION ALL
    SELECT e.symbol, e.system_symbol, dest.level + 1 AS level
    FROM waypoint_jump_gate e
             JOIN dest ON e.symbol = dest.symbol
)

SELECT * FROM dest;
