DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS events CASCADE;
DROP TABLE IF EXISTS requests CASCADE;
DROP TABLE IF EXISTS compilations CASCADE;
DROP TABLE IF EXISTS compilation_event CASCADE;
DROP TABLE IF EXISTS subscriptions CASCADE;
DROP TABLE IF EXISTS subscription_initiator CASCADE;

CREATE TABLE users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE,
    email VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE categories (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE events (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    annotation VARCHAR(2000) NOT NULL,
    category_id BIGINT NOT NULL REFERENCES categories ON DELETE RESTRICT,
    description VARCHAR(7000) NOT NULL,
    event_date TIMESTAMP NOT NULL,
    location_lat DOUBLE PRECISION NOT NULL,
    location_lon DOUBLE PRECISION NOT NULL,
    paid BOOLEAN NOT NULL,
    participant_limit INTEGER NOT NULL,
    request_moderation BOOLEAN NOT NULL,
    title VARCHAR(120) NOT NULL,
    created_on TIMESTAMP NOT NULL,
    initiator_id BIGINT NOT NULL REFERENCES users ON DELETE CASCADE,
    published_on TIMESTAMP,
    state SMALLINT NOT NULL
);

CREATE INDEX events_event_date_idx ON events (event_date);

CREATE TABLE requests (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    created TIMESTAMP NOT NULL,
    event_id BIGINT NOT NULL REFERENCES events ON DELETE CASCADE,
    requester_id BIGINT NOT NULL REFERENCES users ON DELETE CASCADE,
    status SMALLINT NOT NULL
);

CREATE TABLE compilations (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    pinned BOOLEAN NOT NULL,
    title VARCHAR(256) NOT NULL UNIQUE
);

CREATE TABLE compilation_event (
    compilation_id BIGINT NOT NULL REFERENCES compilations ON DELETE CASCADE,
    event_id BIGINT NOT NULL REFERENCES events ON DELETE CASCADE,
    PRIMARY KEY (compilation_id, event_id)
);

CREATE TABLE subscriptions (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    subscriber_id BIGINT NOT NULL REFERENCES users ON DELETE CASCADE,
    name VARCHAR(64) NOT NULL,
    UNIQUE (subscriber_id, name)
);

CREATE TABLE subscription_initiator (
    subscription_id BIGINT NOT NULL REFERENCES subscriptions ON DELETE CASCADE,
    initiator_id BIGINT NOT NULL REFERENCES users ON DELETE CASCADE,
    PRIMARY KEY (subscription_id, initiator_id)
);