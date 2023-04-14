-- liquibase formatted sql

-- changeset AlekseiLitvin:1681183419487-1
CREATE SEQUENCE IF NOT EXISTS hibernate_sequence AS bigint START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1;

-- changeset AlekseiLitvin:1681183419487-2
CREATE TABLE app_user
(
    id         BIGINT NOT NULL,
    email      VARCHAR(255),
    first_name VARCHAR(255),
    last_name  VARCHAR(255),
    phone      VARCHAR(255),
    CONSTRAINT app_user_pkey PRIMARY KEY (id)
);

-- changeset AlekseiLitvin:1681183419487-3
CREATE TABLE post
(
    id            BIGINT NOT NULL,
    creation_time TIMESTAMP WITHOUT TIME ZONE,
    media_url     VARCHAR(255),
    message       VARCHAR(255),
    app_user_id   BIGINT,
    CONSTRAINT post_pkey PRIMARY KEY (id),
    CONSTRAINT post_app_user_id_fkey FOREIGN KEY (app_user_id) REFERENCES app_user (id)
);

-- changeset AlekseiLitvin:1681183419487-4
CREATE TABLE comment
(
    id          BIGINT NOT NULL,
    text        VARCHAR(255),
    app_user_id BIGINT,
    post_id     BIGINT,
    CONSTRAINT comment_pkey PRIMARY KEY (id),
    CONSTRAINT comment_app_user_id_fkey FOREIGN KEY (app_user_id) REFERENCES app_user (id),
    CONSTRAINT comment_post_id_fkey FOREIGN KEY (post_id) REFERENCES post (id)
);

-- changeset AlekseiLitvin:1681183419487-5
CREATE TABLE post_like
(
    id            BIGINT NOT NULL,
    creation_time TIMESTAMP WITHOUT TIME ZONE,
    app_user_id   BIGINT,
    post_id       BIGINT,
    CONSTRAINT post_like_pkey PRIMARY KEY (id),
    CONSTRAINT post_like_app_user_id_fkey FOREIGN KEY (app_user_id) REFERENCES app_user (id),
    CONSTRAINT post_like_post_id_fkey FOREIGN KEY (post_id) REFERENCES post (id)
);
