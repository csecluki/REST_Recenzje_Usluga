# REST_Recenzje_Usluga

Usługa REST na serwerze Glassfish. Przechowuje zapisane (na sztywno w kodzie) filmy oraz słowa kluczowe. Klient usługi może zażądać informacji o filmie wraz z liczbą pozytywnych i negatywnych jego recenzji, które również może wysłać. Wysłana recenzja jest "badana" pod kątem ilości wystąpień pozytywnych i negatywnych słów kluczowych. Gdy liczba słów pozytywnych przewyższa liczbę negatywnych, usługa zwiększa liczbę pozytywnych recenzji danego filmu oraz wysyła odpowiedni komunikat. Gdy recenzja nie zawiera żadnego słowa kluczowego albo w recenzji występuje tyle samo słów pozytywnych, co negatywnych - zwraca "NEUTRAL".

Klient do tej usługi zanjduje się tu: https://github.com/csecluki/REST_Recenzje_Klient.
