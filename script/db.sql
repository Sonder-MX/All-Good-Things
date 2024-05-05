CREATE TABLE IF NOT EXISTS users (
    id bigserial PRIMARY KEY NOT NULL,
    username text DEFAULT ''::text,
    email text NOT NULL,
    password text NOT NULL,
    is_active boolean NOT NULL DEFAULT true,
    created timestamp with time zone DEFAULT CURRENT_TIMESTAMP
);
ALTER TABLE users OWNER TO xxx;

CREATE TABLE IF NOT EXISTS analyze_data (
    id bigserial PRIMARY KEY NOT NULL,
    type text NOT NULL,
    data text NOT NULL,
    created timestamp with time zone DEFAULT CURRENT_TIMESTAMP
);
ALTER TABLE analyze_data OWNER TO xxx;

CREATE TABLE IF NOT EXISTS api_log (
    id bigserial PRIMARY KEY NOT NULL,
    api_path text NOT NULL,
    http_method text NOT NULL,
    start_time timestamp with time zone DEFAULT CURRENT_TIMESTAMP,
    spend_time bigint,
    status integer,
    remote_addr text NOT NULL
);
ALTER TABLE api_log OWNER TO xxx;
