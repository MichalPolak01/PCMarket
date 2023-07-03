# PCMarket - Aplikacja sklepu internetowego na urządzenia android

### Opis aplikacji:

Aplikacja pozwala na korzystanie z internetowego sklepu komputerowego zarówno jako klient, jak i osoba administrująca sklepem. 

Po uruchomieniu aplikacji wyświetla się SplashScreen. Następnie użytkownik musi się zalogować na istniejące konto lub założyć nowe. Do gromadzenia kont oraz wszystkich niezbędnych informacji wykorzystana jest baza danych. Przechowuje ona również uprawnienia konta, dzięki czemu użytkownik i pracownik otrzymują dostęp do dedykowanych im Activity. Każde nowo założone konto jest domyślnie z uprawnieniami użytkownika. Zakres uprawnień może zostać zmieniony za sprawą administratora w panelu administracyjnym. 

Po zalogowaniu u góry ekranu wyświetla się panel nawigacyjny. Składa się z przycisków (zdjęć) odpowiedzialnych za przenoszenie do nowych activity, które dają dostęp do zarządzania kontem, filtrowania produktów oraz przejścia do koszyka. 
W menu zarządzania kontem można zmienić swoje dane osobowe i kontaktowe oraz się wylogować. W Activity filtrowania produktów można wybrać kategorię produktu, która nas interesuje i posegregować produkty według ceny.
Po przejściu do koszyka można zobaczyć listę znajdujących się w nim produktów oraz je zamówić.

Poniżej menu za pomocą RecyclerView skonstruowano kafelki z krótkimi prezentacjami dostępnych w sklepie produktów (zdjęcie, marka, nazwa produktu, cena, przycisk dodający produkt do koszyka) na podstawie informacji pobranych z bazy danych. Po kliknięciu w kafelek następuje przejście do activity zawierającego więcej szczegółów o produkcie. Jest tam dostępna opcja dodania produktu do koszyka oraz przejścia do wcześniejszego activity lub do koszyka. 

Przy opcji przeglądania sklepu jako administrator górne menu nieco się różni  - pojawiają się cztery przyciski odpowiedzialne za dodanie nowego produktu do bazy towarów, filtrowania produktów, przeglądanie złożonych klientów zamówień oraz panel administratora.
W activity dodania nowego produktu znajduje się formularz, za pomocą którego łatwo można dodać nowy produkt do bazy towarów. 
Następnie w menu filtrowania można wyszukać produkty danej kategorii oraz wyświetlić niedostępny towar.
W activity gdzie znajduje się lista złożonych zamówień można wyświetlić zamówienie klienta oraz potwierdzić wysłanie towaru.
Panel administracyjny służy do wylogowania się, edycji danych osobowych i adresowych oraz przyznania uprawnień administracyjnych za pomocą unikatowego adresu e-mail.
 
Ponownie na głównym ekranie poniżej przycisków nawigacyjnych znajdują się produkty wyświetlone za pomocą RecyclerView. Różnica pomiędzy widokiem dla użytkownika polega na tym, że zamiast opcji kupna pojawia się ilość dostępnych sztuk produktu, możliwość dodania/usunięcia sztuki produktu oraz możliwość całkowitego wycofania produktu ze sprzedaży.
Dodatkowo w menu głównym aplikacji wykorzystano funkcjonalność Swipe to Refresh.
