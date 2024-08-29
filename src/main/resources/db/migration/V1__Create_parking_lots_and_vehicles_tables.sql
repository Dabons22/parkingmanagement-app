-- Create table for parking lots
CREATE TABLE IF NOT EXISTS parking_lots (
    lot_id VARCHAR(50) PRIMARY KEY,
    location VARCHAR(100),
    capacity INT,
    occupied_spaces INT,
    cost_per_minute DOUBLE
);

-- Create table for vehicles
CREATE TABLE IF NOT EXISTS vehicles (
    license_plate VARCHAR(20) PRIMARY KEY,
    type VARCHAR(20),
    owner_name VARCHAR(100)
);
