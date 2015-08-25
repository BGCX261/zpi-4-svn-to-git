var czytniki = [];
var pauza = 3000;
var maxLogsize = 5;

function czytnik(kontener, id, posx, posy) {
  this.kontener = kontener;
  this.domNode = null;
  this.id = id;
  this.posx = posx;
  this.posy = posy;
  this.color = "#0"
    + Math.floor((Math.random())*10)
    + Math.floor((Math.random())*10);
  this.logs = [];

  // metoda dodaje log na początek listy
  this.addLog = function(log) {
    if(maxLogsize >= this.logs.length) {
      this.logs.unshift(log);
    }
  }
  
  // utwórz element na makiecie
  this.domNode = $("<div/>")
    .attr("id", this.id)
    .addClass("czytnik")
    .css("left", this.posx + "px")
    .css("top", this.posy + "px")
    .css("background-color", this.color);

  // dodaj trigger celem pokazania logów
  $(this.domNode).mouseover(function() {
    $this = $(this);
    var logs = czytniki[$($this).attr("id")].logs;
    var box = $("<div/>")
      .addClass("infoBox");
    $(box).appendTo($this);

    $("<div />").text($($this)
      .attr("id"))
      .appendTo(box);
    $.each(logs, function(i, item) {
      $("<div />")
        .text(
          item.czas +
          " " +
          item.uzytkownik
        )
        .addClass(item.error ? "error":"")
        .appendTo(box);
    });
  });

  // dodaj trigger celem schowania logów
  $(this.domNode).mouseout(function() {
    $this = $(this);
    $($this).children().remove();
  });

  // podepnij czytnik pod DOM
  $(this.domNode).appendTo(this.kontener);

  // utwrzór infobox na makiecie
  this.infoBox = $("<div/>");
  $(this.infoBox).appendTo(this.domNode);
}

$(document).ready(function() {
  if(makieta == undefined) {
    var makieta = $("#building");
    $("#err").remove();

    //zaczytaj plik konfiguracyjny z czytnikiami, utwórz je i nanieś na DOM
    $.getJSON("media/czytniki.json?" + new Date().getTime(),
      function(json) {
        $.each(json.czytniki, function(i, item) {
          czytniki[item.id] = new czytnik(makieta, item.id, item.x, item.y);
        });
    });
    main();
  }
});

function redrawAll() {
  $.getJSON("logs.php?" + new Date().getTime(), function(json) {
    for(czytnikKey in czytniki) {
      var czytnik = czytniki[czytnikKey];
      czytnik.logs = [];
    }
    for(logKey in json.logi) {
      var log = json.logi[logKey];
      czytniki["czyt"+log.czytnik].addLog(log);
    }
  });
}

function main() {
  redrawAll();
  setInterval("redrawAll()", pauza);
}
