-- V1__init_translation_schema.sql

-- TAGS TABLE
CREATE TABLE tags (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      name VARCHAR(64) NOT NULL UNIQUE
);

-- TRANSLATIONS TABLE
CREATE TABLE translations (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              namespace VARCHAR(255) NOT NULL,
                              `key` VARCHAR(255) NOT NULL
);

-- TRANSLATION_KEYS TABLE
CREATE TABLE translation_keys (
                                  id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                  namespace VARCHAR(64) NOT NULL DEFAULT 'default',
                                  t_key VARCHAR(255) NOT NULL,
                                  created_at TIMESTAMP,
                                  updated_at TIMESTAMP,
                                  CONSTRAINT uq_namespace_key UNIQUE (namespace, t_key)
);

-- TRANSLATION_VALUES TABLE
CREATE TABLE translation_values (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    locale VARCHAR(8) NOT NULL,
                                    content TEXT NOT NULL,
                                    version INT NOT NULL DEFAULT 1,
                                    updated_at TIMESTAMP,
                                    key_id BIGINT NOT NULL,
                                    translation_id BIGINT,

                                    CONSTRAINT uq_key_locale UNIQUE (key_id, locale),
                                    CONSTRAINT fk_translation_values_key FOREIGN KEY (key_id)
                                        REFERENCES translation_keys (id) ON DELETE CASCADE,
                                    CONSTRAINT fk_translation_values_translation FOREIGN KEY (translation_id)
                                        REFERENCES translations (id) ON DELETE CASCADE
);

-- TRANSLATION_TAGS (join table for Translation <-> Tag)
CREATE TABLE translation_tags (
                                  translation_id BIGINT NOT NULL,
                                  tag_id BIGINT NOT NULL,
                                  PRIMARY KEY (translation_id, tag_id),
                                  CONSTRAINT fk_translation_tags_translation FOREIGN KEY (translation_id)
                                      REFERENCES translations (id) ON DELETE CASCADE,
                                  CONSTRAINT fk_translation_tags_tag FOREIGN KEY (tag_id)
                                      REFERENCES tags (id) ON DELETE CASCADE
);

-- TRANSLATION_KEY_TAGS (join table for TranslationKey <-> Tag)
CREATE TABLE translation_key_tags (
                                      key_id BIGINT NOT NULL,
                                      tag_id BIGINT NOT NULL,
                                      PRIMARY KEY (key_id, tag_id),
                                      CONSTRAINT fk_translation_key_tags_key FOREIGN KEY (key_id)
                                          REFERENCES translation_keys (id) ON DELETE CASCADE,
                                      CONSTRAINT fk_translation_key_tags_tag FOREIGN KEY (tag_id)
                                          REFERENCES tags (id) ON DELETE CASCADE
);