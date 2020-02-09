# Praca inżynierska - TuneMeUp
Aplikacja wspomagająca naukę gry na gitarze

## Funkcjonalności

### Stroik gitarowy
Stroik posiada dwa tryby. Pierwszy z nich to tryb <strong>manualny</strong>, w którym użytkownik przełącza struny. 
Drugi tryb to tryb strojenia <strong>automatycznego</strong>. 
W tym trybie algorytm sam określa, która struna jest aktualnie strojona.
<div>
<img src="https://raw.githubusercontent.com/RowickaM/praca-inz/master/ekrany/og%C3%B3lne/1%20(1).jpg" width="150px"/>
<img src="https://raw.githubusercontent.com/RowickaM/praca-inz/master/ekrany/og%C3%B3lne/1%20(2).jpg" width="150px"/>
<img src="https://raw.githubusercontent.com/RowickaM/praca-inz/master/ekrany/og%C3%B3lne/1%20(3).jpg" width="150px"/>
<img src="https://raw.githubusercontent.com/RowickaM/praca-inz/master/ekrany/og%C3%B3lne/1%20(4).jpg" width="150px"/>
</div>

### Metronom
Metronom pozawala na zmiane <strong>metrum</strong> oraz <strong>tempa</strong>.
<div>
<img src="https://github.com/RowickaM/praca-inz/blob/master/ekrany/og%C3%B3lne/2%20(1).jpg" width="150px"/>
<img src="https://github.com/RowickaM/praca-inz/blob/master/ekrany/og%C3%B3lne/2%20(3).jpg" width="150px"/>
<img src="https://github.com/RowickaM/praca-inz/blob/master/ekrany/og%C3%B3lne/2%20(4).jpg" width="150px"/>
</div>

### Nauka akordów
Użytkownicy zalogowani mają możliwość uczenia sie akordów. Do wyboru są dwa tryby. 
<br /><strong>Tryb nauki</strong> pozwala na naukę akordu poprzez wyświetlenie schematu akordu oraz pokazanie imitacji strun, zaznaczając zielonym kolorem poprawnie zagrane struny.
Natomiast <strong>tryb rankingu</strong> ustala ile punktów należy przyznać użytkownikowi za daną rundę (na rundę skłądają się 4 próby). 
<br />Algorytm podczas przyznawania punktów bierze pod uwagę:
* czas zagrania danego akordu w danej próbie

| czas [s] | liczba punktów |
| --- | --- |
| < 8 | 200 |
| 8 - 9 | 150 |
| 10 - 19 | 140 |
| 20 - 29 | 130 |
| 30 - 39 | 120 |
| 40 - 49 | 110 |
| 50 - 59 | 100 |
| 60 - 120 | 50 |

* czy został wyświetlony schemat akordu, za niewyświetlanie schematu użytkownik dostaje dodatkowe 100 punktów

* które jest to podejście danego użytkownika do danego akordu w trybie rankingu

| podejście | mnożnik |
| --- | --- |
| 0 | 5 |
| 1 | 4 |
| 2 | 3 |
| >= 3 | 0 |

<div>
<img src="https://github.com/RowickaM/praca-inz/blob/master/ekrany/zalogowany/5.jpg" width="150px"/>
<img src="https://github.com/RowickaM/praca-inz/blob/master/ekrany/zalogowany/6%20(1).jpg" width="150px"/>
<img src="https://github.com/RowickaM/praca-inz/blob/master/ekrany/zalogowany/6%20(2).jpg" width="150px"/>
</div>

#### Tryb nauki
<div>
<img src="https://github.com/RowickaM/praca-inz/blob/master/ekrany/zalogowany/9%20(1).jpg" width="150px"/>
<img src="https://github.com/RowickaM/praca-inz/blob/master/ekrany/zalogowany/9%20(3).jpg" width="150px"/>
<img src="https://github.com/RowickaM/praca-inz/blob/master/ekrany/zalogowany/9%20(2).jpg" width="150px"/>
</div>

#### Tryb rankingu
<div>
<img src="https://github.com/RowickaM/praca-inz/blob/master/ekrany/zalogowany/8.jpg" width="150px"/>
<img src="https://github.com/RowickaM/praca-inz/blob/master/ekrany/zalogowany/8%20(2).jpg" width="150px"/>
<img src="https://github.com/RowickaM/praca-inz/blob/master/ekrany/zalogowany/8%20(3).jpg" width="150px"/>
<img src="https://github.com/RowickaM/praca-inz/blob/master/ekrany/zalogowany/8%20(5).jpg" width="150px"/>
</div>

### Profil użytkownika
Użytkownik po zalogowaniu się ma możliwość zmiany <strong>ikony</strong> spośród dostępnych oraz zmiane swojej <strong>nazwy</strong>.
<div>
<img src="https://github.com/RowickaM/praca-inz/blob/master/ekrany/zalogowany/11%20(1).jpg" width="150px"/>
<img src="https://github.com/RowickaM/praca-inz/blob/master/ekrany/zalogowany/12.jpg" width="150px"/>
</div>

### Ranking użytkowników
Zalogowani użytkownicy mają dostęp do wyświetlania rankingu zalogowanych użytkowników, gdzie punkty są zdobywane podczas nauki akordów w trybie rankingu
Ranking posiada <strong>podział na odpowiednie grupy</strong> oraz jest dostępny ranking z <strong>podsumowaniem wszystkich grup</strong>. 
<div>
<img src="https://github.com/RowickaM/praca-inz/blob/master/ekrany/zalogowany/10%20(1).jpg" width="150px"/>
<img src="https://github.com/RowickaM/praca-inz/blob/master/ekrany/zalogowany/10%20(2).jpg" width="150px"/>
<img src="https://github.com/RowickaM/praca-inz/blob/master/ekrany/zalogowany/10%20(3).jpg" width="150px"/>
</div>

## Szczegółowy opis implementacji
Szczegółowy opis implementacji został umieszczony w pracy inżynierskiej. Praca jest dostępna pod [tym adresem](https://github.com/RowickaM/praca-inz) w pliku Rowicka.pdf.
