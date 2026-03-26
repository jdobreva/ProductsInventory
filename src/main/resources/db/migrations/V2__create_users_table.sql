CREATE TABLE public.user_entity (
    id BIGINT GENERATED ALWAYS AS IDENTITY NOT NULL,
    "password" VARCHAR(255) NOT NULL,
    username VARCHAR(50) NOT NULL,
    "role" VARCHAR(50) NOT NULL,
    CONSTRAINT user_entity_pk PRIMARY KEY (id)
);

-- Set owner
ALTER TABLE public.user_entity OWNER TO "productsUser";

-- Grant permissions
GRANT SELECT, INSERT, UPDATE, DELETE, REFERENCES, TRIGGER ON TABLE public.user_entity TO "productsUser";

