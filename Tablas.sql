INSERT into jugadores (id, nombre, apellidos, elo, sexo, federacion, titulo, bYear) VALUES (-1, 'Descanso','',0, 'Male', '', '',2000)


CREATE TABLE jugadores (
   id BIGINT PRIMARY KEY,
   nombre VARCHAR(100) NOT NULL,
   apellidos VARCHAR(100) NOT NULL,
   elo INT DEFAULT 0,
   sexo ENUM('Male', 'Female') NOT NULL,
   federacion VARCHAR(100) NOT NULL,
   titulo VARCHAR(50) DEFAULT 'None',
   bYear YEAR
);

CREATE TABLE torneos (
     id VARCHAR(255) PRIMARY KEY,
     nombre VARCHAR(100) NOT NULL,
     fecha_inicio DATE NOT NULL,
     fecha_fin DATE NOT NULL,
     lugar VARCHAR(100) NOT NULL,
     rondas INT NOT NULL,
     ritmo VARCHAR(50) NOT NULL
);

CREATE TABLE partidas (
      id_torneo VARCHAR(255) NOT NULL,
      id_jugador_blancas BIGINT NOT NULL,
      id_jugador_negras BIGINT NOT NULL,
      ronda INT NOT NULL,
      resultado VARCHAR(10) NOT NULL,
      mesa INT NOT NULL,
      pgn TEXT,

      PRIMARY KEY (id_torneo, id_jugador_blancas, id_jugador_negras, ronda),

      CONSTRAINT fk_torneo FOREIGN KEY (id_torneo) REFERENCES torneos(id) ON DELETE CASCADE,
      CONSTRAINT fk_jugador_blancas FOREIGN KEY (id_jugador_blancas) REFERENCES jugadores(id) ON DELETE CASCADE,
      CONSTRAINT fk_jugador_negras FOREIGN KEY (id_jugador_negras) REFERENCES jugadores(id) ON DELETE CASCADE
);

CREATE TABLE jugador_torneo (
    id_torneo VARCHAR(255) NOT NULL,
    id_jugador BIGINT NOT NULL,
    ranking_inicial INT NOT NULL,
    ranking_final INT,
    puntos VARCHAR(10),
    PRIMARY KEY (id_torneo, id_jugador),

    CONSTRAINT fk_torneo_jt FOREIGN KEY (id_torneo) REFERENCES torneos(id) ON DELETE CASCADE,
    CONSTRAINT fk_jugador_jt FOREIGN KEY (id_jugador) REFERENCES jugadores(id) ON DELETE CASCADE
);

CREATE TABLE arbitro (
     codigo VARCHAR(20) PRIMARY KEY,
     nombre VARCHAR(50) NOT NULL,
     apellidos VARCHAR(100) NOT NULL,
     titulo VARCHAR(20),
     federacion VARCHAR(50)
);

CREATE TABLE arbitro_torneo (
    id_arbitro VARCHAR(20) NOT NULL,
    id_torneo VARCHAR(255) NOT NULL,
    PRIMARY KEY (id_arbitro, id_torneo),
    CONSTRAINT fk_arbitro_torneo_ar FOREIGN KEY (id_arbitro) REFERENCES arbitro(codigo) ON DELETE CASCADE,
    CONSTRAINT fk_arbitro_torneo_tor FOREIGN KEY (id_torneo) REFERENCES torneos(id) ON DELETE CASCADE
);