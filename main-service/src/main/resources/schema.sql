drop table IF EXISTS users,
locations,
categories,
events,
compilation_events,
compilation,
requests;

create TABLE
  IF NOT EXISTS users (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(512) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
  );

create TABLE
  IF NOT EXISTS locations (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    lat FLOAT NOT NULL,
    lon FLOAT NOT NULL,
    CONSTRAINT pk_locations PRIMARY KEY (id),
    CONSTRAINT UQ_LOCATION_LAT UNIQUE (lat),
    CONSTRAINT UQ_LOCATION_LON UNIQUE (lon)
  );

create TABLE
  IF NOT EXISTS categories (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(100) NOT NULL UNIQUE,
    CONSTRAINT pk_categories PRIMARY KEY (id),
    CONSTRAINT UQ_CATEGORY_NAME UNIQUE (name)
  );

create TABLE
  IF NOT EXISTS events (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    annotation VARCHAR(2000),
    category_id BIGINT,
    confirmed_requests INT,
    created_on TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    description VARCHAR(7000),
    event_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    initiator_id BIGINT,
    location_id BIGINT,
    paid BOOLEAN,
    participant_limit INT,
    published_on TIMESTAMP WITHOUT TIME ZONE,
    request_moderation BOOLEAN,
    state VARCHAR,
    title VARCHAR(120),
    CONSTRAINT pk_events PRIMARY KEY (id),
    CONSTRAINT fk_events_initiator FOREIGN KEY (initiator_id) REFERENCES users (id),
    CONSTRAINT fk_events_location FOREIGN KEY (location_id) REFERENCES locations (id),
    CONSTRAINT fk_events_category FOREIGN KEY (category_id) REFERENCES categories (id)
  );

create TABLE
  IF NOT EXISTS compilations (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    title VARCHAR(255) NOT NULL,
    pinned BOOLEAN NOT NULL,
    CONSTRAINT pk_compilation PRIMARY KEY (id),
    CONSTRAINT UQ_COMPILATION_TITLE UNIQUE(title)
  );

create TABLE
  compilation_events (
    compilation_id BIGINT,
    event_id BIGINT,
    CONSTRAINT pk_compilations_event PRIMARY KEY (compilation_id, event_id),
    CONSTRAINT fk_compilation_events_compilation FOREIGN KEY (compilation_id) REFERENCES compilations (id),
    CONSTRAINT fk_compilation_events_event FOREIGN KEY (event_id) REFERENCES events (id)
  );

create TABLE
  IF NOT EXISTS requests (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    event_id BIGINT,
    requester_id BIGINT,
    status VARCHAR(255) NOT NULL,
    CONSTRAINT pk_erequests PRIMARY KEY (id),
    CONSTRAINT fk_requests_requester FOREIGN KEY (requester_id) REFERENCES users (id),
    CONSTRAINT fk_requests_event FOREIGN KEY (event_id) REFERENCES events (id)
  );