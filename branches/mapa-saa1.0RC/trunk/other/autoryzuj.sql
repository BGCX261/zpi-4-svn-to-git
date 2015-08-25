/*
autoryzuje aktywnego użytkownika
należącego do aktywnej grupy
z uprawnieniami do aktywnych czytników
nie będącego w blackliście dla danego czytnika
bądź będącego przypisanym w whiteliście
*/
SELECT
    uzy.imie        status,
    uzy.nazwisko    uzytkownik,
    odc.hash        HASH,
    czy.id          czytnik
FROM        odcisk_palca        odc
INNER JOIN  uzytkownik          uzy ON  uzy.id = odc.uzytkownik_id
                                        AND uzy.aktywny = 1
                                        AND uzy.usuniety = 0
                                        AND odc.aktywny = 1
LEFT JOIN   grupa_uzytkownik    gu  ON  gu.uzytkownik_id = uzy.id
LEFT JOIN   grupa               gru ON  gru.id = gu.grupa_id
                                        AND gru.aktywna = 1
LEFT JOIN   grupa_czytnik       gc  ON  gc.grupa_id = gu.grupa_id
INNER JOIN  czytnik             czy ON  czy.id = gc.czytnik_id
    AND czy.aktywny = 1
    AND czy.id NOT IN (
        SELECT czytnik_id cid
        FROM czytnik_uzytkownik
        WHERE
            uzytkownik_id = uzy.id
            AND blacklist = 1
    )
    OR czy.id IN (
        SELECT czytnik_id cid
        FROM czytnik_uzytkownik
        WHERE
            uzytkownik_id = uzy.id
            AND blacklist = 0
    )
WHERE
    AND czy.id = 2