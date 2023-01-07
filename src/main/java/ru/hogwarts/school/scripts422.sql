
create table cars (
    id INTEGER PRIMARY KEY,
    brand TEXT,
    model TEXT,
    price INTEGER
);

create table people (
    id INTEGER PRIMARY KEY,
    name TEXT,
    age INTEGER,
    drivers_license BOOLEAN,
    car_id INTEGER REFERENCES cars(id)
)
