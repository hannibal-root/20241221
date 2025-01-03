import java.util.Random;
import java.util.Scanner;

public class Lottery {
    public static void main(String[] args) {
        // kérjük be a felhasználótó a tippjeit (5 db szám az [1;90] intervallumról
        // ismétlődés nélkül). Hibás input esetén kérjük újra a számot.

        int[] guessed = new int[5]; // 5 elemű, int értékeket tartalmazó tömb (lehetséges indexek: 0-4)
        Scanner sc = new Scanner(System.in); // a konzolról szeretnék adatokat beolvasni -> Scanner
        // mivel a felhasználó "rossz" értéket is beírhat, így nem biztos az 5x futás, ezért inkább while-t használok
        int actPos = 0; // ez mutatja, hogy aktuális melyik helyre
        while(actPos < guessed.length){ // a whiletól is azt várom, hogy végigmenjünk a tömbön, de nem feltételenül 5 lépésben
            System.out.print("Kerem az " + (actPos + 1)+". szamot: ");
            int number = sc.nextInt(); // beolvasok egy szamot

            // mikor jo a szam? a) ha 1 és 90 között van és még nincs a tömbben
            if(1 <= number  && number <= 90){
                // továbbnézem, hogy nincs-e már a tömbben -> eldöntés tétele
               /* boolean inArray = false; // feltételezem, hogy a number nincs a tömbben
                for(int i = 0; i < actPos; i++){
                    if(guessed[i] == number){
                        inArray = true;
                        break; // felesleges a belső fort tovább futtatni, ezért kiugrom belőle
                    }
                }*/
                // az inArray hamis, akkor tároljuk a számot és megyünk a következőre
                boolean inArray = inArray(guessed, number); // !!! most már a metódust hívjuk meg
                if(!inArray){
                    guessed[actPos] = number;
                    actPos++;
                }
            }

        }

        // generáljunk 5 db számot egy tömbbe az 1;90 intervallumról
        // változó igény: 5 elemű tömb, Random változót a véletlenszám generáláshoz
        Random rnd = new Random();//rnd.nextInt(alsó határ, felső határ + 1) -> véletlen szám generálás
        int[] winningNumbers = new int[5]; // ebbe "generálom" a nyerőszámokat (szimuláció)
        // a tömb összes elemét fel kell tölteni ismétlődés nélkül az 1;90 intervallumról
        int actWinningPos = 0;
        while(actWinningPos < winningNumbers.length){
            int number = rnd.nextInt(1,91); // generálok egy számot [1;90]-ről
           /* boolean inArray = false; // feltételezem, hogy még nincs benne
            for(int i = 0; i < actWinningPos; i++){
                if(winningNumbers[i] == number){
                    inArray = true;
                    break;
                }
            }*/
            boolean inArray = inArray(winningNumbers, number);
            if(!inArray){
                winningNumbers[actWinningPos] = number;
                actWinningPos++;
            }
        }

        // winningNumbers-ben benne vannak a nyerőszámok, a guessed-ben a tipppek ...
        // felhasználva az inArray metódust, új metódus implementálása nélkül döntsük el, hogy
        // hány azonos elem van a két tömbben !!!
        // eldöntés: logikai érték, amely megmondja, hogy adott elem a tömbben van-e (pl. az inArray)
        // visszavezetés: 2 db tömb, ha az egyiket egy ciklussal feldolgozom elemenként, akkor
        // tulajdonképpen n db diszkrét értéket kapok (ami az eldöntés bemenete lehet), a másik tömb
        // pedig az eldöntés alapja lehet
        // összeépítés: az eldöntés alapjául szolgáló tételhez egy megszámlálást kell hozzáépíteni
        int commonCnt = 0;
        for(int i = 0; i < guessed.length; i++){
            int actualGuess = guessed[i]; // n-szer kapok egy elemi számot
            if(inArray(winningNumbers, actualGuess)){ // igazat ad vissza, ha benne van
                commonCnt++;
            }
        }
        System.out.println("A kozos elemek szama: " + commonCnt);

    }

    // metódus: névvel, visszatérési értékkel és paraméterekkel rendelkező programegység, amelyet
    // meghívva tudjuk futtatni a benne lévő utasításokat

    // inArray metódus implementálása: feladata, hogy eldöntse, hogy egy adott szám egy tömbben benne van-e vagy sem
    //   NÉV/AZONOSÍTÓ: inArray
    //   VISSZATÉRÉSI TÍPUS: boolean (mivel arra vagyok kíváncsi, hogy benne van-e, IGAZ: benne van, HAMIS: nincs benne)
    //   PARAMÉTEREK: szám típus tömb (amiben lefolytatom a vizsgálatot), a szám, amire kíváncsi vagyok, hogy benne van-e
    static boolean inArray(int[] array, int number){
        // eldöntés tételét kell alkalmazni
        boolean inArray = false; // felteszem, hogy nincs benne
        for(int i = 0; i < array.length; i++){ // szisztematikusan végignézem a tömböt
            // ha az aktuális elem megegyezik a number paraméterrel, akkor megváloztatom a döntésem
            if(array[i] == number){
                inArray = true;
                break;
            }
        }
        // a hívás végén visszaadom a logikia értéket
        return inArray;
    }
}
