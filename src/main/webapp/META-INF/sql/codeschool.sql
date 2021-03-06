CREATE DATABASE `codeschool` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE TABLE users_groups(
     id INT AUTO_INCREMENT,
     name VARCHAR(256) UNIQUE,
     PRIMARY KEY (id)
) ENGINE=INNODB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

INSERT INTO users_groups (id, name) VALUES
    (1, 'ADMIN Group'),
    (2, 'Group A'),
    (3, 'Group B'),
    (4, 'Group C');

CREATE TABLE users(
      id INT AUTO_INCREMENT,
      username VARCHAR(256),
      email VARCHAR(256) UNIQUE,
      password VARCHAR(256) NOT NULL,
      group_id INT NOT NULL,
      is_admin BOOLEAN NOT NULL,
      PRIMARY KEY (id),
      FOREIGN KEY (group_id) REFERENCES users_groups(id)
) ENGINE=INNODB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

INSERT INTO users (username, email, password, group_id, is_admin) VALUES
    ('ADMIN', 'admin@gmail.com', '$2a$10$1FchTNOoF6gS9v.s7GkNmujcqscQkMvSkWgi3..vEv9Z9l3X49BNq', 1, true),
    ('Marcin Cieszkowski', 'marcin.cieszkowski@gmail.com', '$2a$10$J/GMBBC.lNrYU3W4pUbDQe2LlTWajV45.Io04tlk8H.lq0su7k.yi', 2, false),
    ('Janusz Kowalski', 'jan.kowalski@wp.pl', '$2a$10$J/GMBBC.lNrYU3W4pUbDQe2LlTWajV45.Io04tlk8H.lq0su7k.yi', 2, false),
    ('Anna Nowak', 'anna.nowak@o2.pl', '$2a$10$J/GMBBC.lNrYU3W4pUbDQe2LlTWajV45.Io04tlk8H.lq0su7k.yi', 3, false);

CREATE TABLE exercises(
      id INT AUTO_INCREMENT,
      title VARCHAR(256),
      description TEXT,
      PRIMARY KEY (id)
) ENGINE=INNODB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

INSERT INTO exercises (title, description) VALUES
    ('Geometry - calculate the area of the triangle.', 'Calculate the area of​an equilateral triangle whose height is 4 cm.'),
    ('Vectors', 'Calculate the distance of the center of the segment AB from the beginning of the coordinate system: A (-7.7), B (11.1)'),
    ('Political systems', 'Characterize the political system of the monarchy and republic.');

CREATE TABLE solutions(
      id INT AUTO_INCREMENT,
      user_id INT NOT NULL,
      exercise_id INT NOT NULL,
      created DATETIME,
      updated DATETIME DEFAULT NULL,
      description TEXT,
      PRIMARY KEY (id),
      FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
      FOREIGN KEY (exercise_id) REFERENCES exercises(id) ON DELETE CASCADE
) ENGINE=INNODB CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

INSERT INTO solutions (user_id, exercise_id, created, updated, description) VALUES
    (2, 1, '2020-06-05', NOW() - INTERVAL 3 HOUR,
     'First we calculate a from the formula:
    h=aV3/2

    4=aV3/2
    a=8V3/3

    P=a^2*V3/4

    P=(8V3/3)^2*V3/4

    P=64/3*V3/4
    P=16V3/3 cm^2'
    ),
    (2, 2, '2020-06-01', NOW() - INTERVAL 2 HOUR, 'I don''t even know how to start, I give up'),
    (3, 1, '2020-05-20', NOW() - INTERVAL 2 DAY, 'I don''t know how to solve this exercise'),
    (3, 2, '2020-06-03', NOW() - INTERVAL 2 DAY,
    '    A(-7;7)
    B(11,1)
    *we use the formula for the middle of the episode : S=[ ( x1+x2)/2 ; (y1+y2)/2]

    S=[ (-7+11)/2 ; (7+1)/2 ]

    S=(4/2 ; 8/2 ]

    S=-(2;4 )

    The origin of the coordinate system is a point P=(0;0)
    When calculating the distance between the middle of the segment and the beginning of the coordinate
    system, we use the formula:

    AB^2=(x2-x1)^2 + (y2-y1)^2

    calculated and given points S(2;4) i P(0;0) substitute into the formula as above and count:

    AB^2=(0-2)^2 + (0-4)^2= (-2)^2 +(-4)^2= 4+16=20

    AB=V20=V(4*5)
    AB=2V5'
    ),
    (4, 1, '2020-04-28', NOW() - INTERVAL 1 DAY,
     '    h=a√3/2
    h=4cm
    4=a√3/2 /·2
    8=a√3/:√3
    a=8:√3
    a=8/√3  ·√3/√3
    a=8√3/3
    P=a²√3/4
    P={ (8√3/3)² ·√3} /4
    P={64·3/9) ·√3} :4= 16√3/3
    P=16√3/3 cm²'
    ),
    (1, 3, '2020-06-01', NOW() - INTERVAL 10 HOUR,
     'Monarchy is a political system based on the authority of one person. It can be emperor,
king, sultan, caliph etc. In today''s world there are practically no absolute monarchs,
although there are countries where the monarch''s power is very large (Saudi Arabia, Brunei).
The most common form of monarchy is constitutional or parliamentary monarchy. In this system,
the monarch is the head of state, but has rather representative functions and is a symbol of political unity and independence.
His place in the system of power is determined by the constitution. Examples of such countries can be: Great Britain, Spain, Sweden.

Republic - a political system in which authorities come from democratic elections. There is a clear separation of powers.
The head of state is usually the president. The legislative power is one or two bicameral parliament.
The executive branch is headed by the prime minister or president, and the judiciary is independent.
This type of political system occurs, among others. in Poland.'),
    (3, 3, '2020-05-04', NOW() - INTERVAL 5 HOUR,
     'Monarchy is a political system based on the authority of one person. It can be emperor, king, sultan, caliph etc.
In today''s world there are practically no absolute monarchs, although there are countries where the monarch''s power is very large (Saudi Arabia, Brunei).
The most common form of monarchy is constitutional or parliamentary monarchy. In this system, the monarch is the head of state, but has rather representative functions and is a symbol of political unity and independence.
His place in the system of power is determined by the constitution. Examples of such countries can be: Great Britain, Spain, Sweden.

Monarchy (Latin monarchy, from the Greek word μοναρχία, monarchy "monarchy", from μόναρχος "the sole ruler", from μόνος monos "one" and ἀρχός archós "the beginning" [1]) - a political system or form of government where not chosen the republican head of state is one person, called the monarch. At present, the monarch usually holds power for life (except Andorra, Malaysia and the United Arab Emirates). His function is often hereditary (except for elective monarchies) and usually his position is indelible. Currently, in many countries with such a political system, the monarch has only representative functions. His policy control is scarce or nonexistent.
Republic - ???');

SET GLOBAL innodb_default_row_format = 'DYNAMIC';