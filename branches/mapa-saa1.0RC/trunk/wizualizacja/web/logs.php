<?php
  header("Content-Type: application/json; charset=utf-8");

  $host = "156.17.247.193";
  $user = "test";
  $pass = "test";
  $db = "test";

  $mysql = new mysqli($host, $user, $pass , $db);
  $mysql->set_charset("utf8");
  $stmt = $mysql->prepare('
    SELECT
      la.id AS id
      ,la.czytnik_id AS czytnik
      ,COALESCE(CONCAT(CONCAT(u.imie, " "), u.nazwisko), "nieznany") AS uzytkownik
      ,la.czas AS czas
      ,CHAR_LENGTH(la.opis) > 20 AS error
    FROM
      log_aktywnosci la
    LEFT JOIN
      uzytkownik u ON u.id = la.uzytkownik_id
    WHERE la.czas > ?
  ');
  $stmt->bind_param('s', date('Y-m-d'));
  $stmt->execute();
  $stmt->bind_result($id, $czytnik, $uzytkownik, $czas, $error);

  $logs = array();
  $item = '';
  while($stmt->fetch())
  {
    $item = '';
    $item .= "\n    \"{$id}\":\n";
    $item .= '    {';
    $item .= "\n      \"czytnik\": \"{$czytnik}\",\n";
    $item .= "      \"uzytkownik\": \"{$uzytkownik}\",\n";
    $item .= "      \"czas\": \"{$czas}\",\n";
    $item .= "      \"error\": ";
    $item .= $error==1 ? 'true' : 'false' . "\n";
    $item .= "\n" . '    }';
    array_push($logs, $item);
  }

  $json =  '{';
  $json .= "\n  \"logi\":\n";
  $json .= '  {';
  $json .= implode($logs, ',');
  $json .= "\n" . '  }';
  $json .= "\n" . '}';
  
  echo $json;

?>
