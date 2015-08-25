CREATE VIEW v_uzytkownicy AS SELECT u.id as `id`,u.imie AS `imie`,u.nazwisko AS
`nazwisko`,u.opis AS `opis`, u.aktywny AS `status`,g.nazwa AS `grupa` FROM
grupa_uzytkownik AS ug INNER JOIN grupa AS g ON ug.grupa_id=g.id INNER JOIN
uzytkownik AS u ON ug.uzytkownik_id=u.id;