-- Create table for parking records
CREATE TABLE IF NOT EXISTS parking_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    license_plate VARCHAR(20),
    lot_id VARCHAR(50),
    check_in_time TIMESTAMP,
    check_out_time TIMESTAMP
);
