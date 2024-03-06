# Event Buddy

## Projektni tim
(svi članovi tima moraju biti iz iste seminarske grupe)

Ime i prezime | E-mail adresa (FOI) | JMBAG | Github korisničko ime | Seminarska grupa
------------  | ------------------- | ----- | --------------------- | ----------------
Dominik Josipović | djosipovi21@foi.hr | - | djosipovi21 | -
Karlo Mikec | kmikec20@foi.hr | - | kmikec20 | -
Sebastijan Vinko | svinko21@foi.hr | -  | svinko21 | -

## Opis domene
Projekt pokriva domenu obavještavanja korisnika aplikacije o budućim događajima (koncerti, seminari, radionice...) te mogućnosti prijave, rezervacije gostiju koji će gostovati na određenom događaju koji odaberu. Aplikacija radi tako da organizator događaja objavi događaj u aplikaciji tako da popuni obrazac u koji upisuje osnovne informacije o događaju (naziv, kratak opis, mjesto, vrijeme). Organizator će moći uređivati napravljen događaj, moći će ga otkazati i premjestiti datum ili mjesto održavanja. U svojem pogledu aplikacije, organizator će moći maknuti korisnika s kreiranog događaja te će moći i određenom korisniku zabraniti pretplatu na događaj. Korisnici će moći pregledavati događaje koji su kreirani od strane organizatora, pretplatiti se na događaj i napustiti događaj te vidjeti broj sudionika pretplaćenih na događaj.

## Specifikacija projekta

Oznaka | Naziv | Kratki opis | Odgovorni član tima
------ | ----- | ----------- | -------------------
F01 | Prijava i registracija | Korisnik se registrira u sustav na način da unosi svoje ime i prezime, adresu te dostupno korisničko ime, lozinku i ponavlja izabranu lozinku radi sigurnosti korisničkog računa. Korisnik se prijavljuje u aplikaciju na način da unosi svoje korisničko ime i lozinku. | Karlo Mikec
F02 | Uređivanje računa | Korisnik na svojem profilu u aplikaciji može promijeniti lozinku. Lozinka se mijenja na način da se upisuje stara lozinka te se upisuje nova lozinka kao što je to slučaj kod registracije. Korisnik također može uređivati i svoje osobne podatke kao što su ime, prezime | Karlo Mikec
F03 | Slanje zahtjeva | Korisnik može zatražiti od administrativne strane ulogu organizatora. Korisnik sa svojeg profila odabire opciju slanja zahtjeva te ispunjava obrazac zašto bi on trebao postati organizator. Zahtjev se zatim šalje moderatoru/administratoru za daljnji pregled. | Dominik Josipović
F04 | Pregledavanje događaja | Korisnik može pregledavati današnje i buduće događaje u aplikaciji koji su kreirani od strane organizatora. | Dominik Josipović
F05 | Kreiranje događaja | Organizator će imati opciju kreiranja događaja u sekciji "Vlastiti događaji" prilikom čega mu se otvara forma u kojoj navodi naziv događaja, lokaciju, vrijeme početka | Sebastijan Vinko
F06 | Uređivanje događaja | Prilikom odabira vlastitog događaja kojeg je organizator kreirao, prikazuju mu se detalji događaja koje može izmjenjivati. Podatke koje organizator može mijenjati su naziv događaja, datum, mjesto i vrijeme odvijanja, opis i otkazati događaj. | Sebastijan Vinko
F07 | Pregled detalja događaja | Korisnik vidi detalje događaja prilikom odabira željenog događaja te u prikazu vidi naziv događaja, opis, mjesto, vrijeme i datum održavanja događaja, objave organizatora te broj prijavljenih sudionika događaja i može se pretplatiti na događaj. | Karlo Mikec
F08 | Pretplata na događaj | Korisnik kod pregleda detalja odabranog događaja ima opciju pretplatiti se na taj odabrani događaj pritiskom na gumb "Pretplati se". Također korisnik može napustiti događaj pritiskom na gumb "Napusti događaj". | Dominik Josipović
F09 | Pregled svojih događaja | Korisnik može pregledavati svoje nadolazeće i prošle događaje. Organizatori će moći vidjeti vlastite kreirane događaje kao i one na koje su se pretplatili, moći će vidjeti i prošle događaje koji su završili, ali ih neće moći uređivati.| Dominik Josipović
F10 | Pregledavanje sudionika | Korisnik će moći kod pregleda detalja odabranog događaja vidjeti broj sudionika te klikom na broj sudionika moći će vidjeti listu sudionika koji su pretplaćeni na događaj. | Karlo Mikec
F11 | Upravljanje sudionicima | Organizator će kod prikazivanja sudionika na vlastitom događaju imati opciju uklanjanja sudionika sa događaja ili stavljanja zabrane pristupa korisniku na taj događaj | Sebastijan Vinko
F12 | Slanje notifikacija | Korisnici kojima je odobren zahtjev za organizatora dobivaju notifikaciju za odobrenje. Također korisnici dobivaju informacije o sakrivanju događaja i gubljenja uloge organizatora | Sebastijan Vinko

## Tehnologije i oprema

Projekt se fokusira na razvoj mobilne aplikacije za informiranje o budućim događajima, koja će omogućiti korisnicima pravovremene informacije o nadolazećim događajima. Aplikacija će biti razvijena koristeći najnovije tehnologije i alate kako bi se osigurala izvrsna korisnička iskustva.

Programski jezik Kotlin: Kotlin će biti glavni programski jezik korišten za razvoj aplikacije. Kotlin je moderni programski jezik koji je specifično dizajniran za Android platformu, pružajući visoku produktivnost i performanse.

Android Studio: Android Studio će biti glavno razvojno okruženje koje će se koristiti za izradu aplikacije. Android Studio pruža bogat skup alata i resursa za razvoj Android aplikacija, uključujući emulator za testiranje i optimizaciju performansi aplikacije.

Baza podataka: Aplikacija će koristiti Microsoft SQL Server, dok će model baze podataka biti izrađen u alatu MySQL Workbench.

Git i GitHub: Za verzioniranje programskog koda koristit ćemo Git kao sustav za kontrolu verzija, a GitHub će nam biti platforma za rad na programskom rješenju i projektu u cjelini. Ovo će omogućiti timu da učinkovito surađuje na razvoju aplikacije, prati promjene i održava povijest razvoja.

## .gitignore
