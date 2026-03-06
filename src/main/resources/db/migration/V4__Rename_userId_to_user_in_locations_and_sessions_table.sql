ALTER TABLE locations
    RENAME COLUMN user_id TO user;
ALTER TABLE sessions
    RENAME COLUMN user_id TO user;