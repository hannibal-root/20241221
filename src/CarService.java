import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class CarService {
    // a rekordokhoz tartozó adatokat független tömbökben
    // tároljuk, az egy szervízhet tartozó adatokat úgy fogjuk
    // tudni visszakeresni, hogy kiolvassuk minden tömb ugyanazon
    // indexe alatt lévő értéket
    static int[] ids; // ebbe jönnek az id-k
    static String[] owners; // tulajdonosok
    static String[] cars; // autók
    static String[] errorCodes; // hibakódok
    static double[] prices; // javítás ára


    public static void main(String[] args) {
        loadCSV("db/car_repair_db.csv"); // beolvassuk a csv tartalmát

        // készítsük el a cli menürendszerét (1-es pont a teljes lista, 2-es javítás hozzáadása,
        // 3 - javítás törlése, 4 - javítás módosítása, 5 - javítás id alapján, 6 - kilépés)
        // minden menüpontnak készítsünk egy metódus, amelyet meghívunk abban az esetben
        // ha a felhasználó ezt választotta, majd alakítsuk ki a cli felületét
        Scanner sc = new Scanner(System.in);
        while(true){ // vételen ciklus, amiből ha szükségessé válik, kiugrunk
            printMenu(); // kiírom a menüt
            // bekérem a felhasználó választását
            System.out.print("Kerem a valasztott menut: ");
            int menuId = sc.nextInt();

            switch (menuId){
                case 1: printData(); break;
                case 2:
                    System.out.print("Kerem ajda meg az id-t: ");
                    int iId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Kerem ajda meg az uj tulajt: ");
                    String iOwner = sc.nextLine();
                    System.out.print("Kerem ajda meg az uj autot: ");
                    String iCar = sc.nextLine();
                    System.out.print("Kerem ajda meg az uj hibakodot: ");
                    String iErrorCode = sc.nextLine();
                    System.out.print("Kerem ajda meg az uj arat: ");
                    double iPirce = sc.nextDouble();
                    insertRecord(iId, iOwner, iCar, iErrorCode, iPirce);
                    break;
                case 3: // javítás törlése
                    System.out.print("Kerem ajda meg az id-t: ");
                    int dId = sc.nextInt();
                    deleteRecord(dId);
                    break;
                case 4: // a rekord módosítása (elkérjük az id-t, amely alapján módosítunk, a többi
                    // adatot pedig mint frisített adat kérjük el (owner, car, errorCode, price)
                    System.out.print("Kerem ajda meg az id-t: ");
                    int uId = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Kerem ajda meg az uj tulajt: ");
                    String uOwner = sc.nextLine();
                    System.out.print("Kerem ajda meg az uj autot: ");
                    String uCar = sc.nextLine();
                    System.out.print("Kerem ajda meg az uj hibakodot: ");
                    String uErrorCode = sc.nextLine();
                    System.out.print("Kerem ajda meg az uj arat: ");
                    double uPirce = sc.nextDouble();
                    updateRecord(uId, uOwner, uCar, uErrorCode, uPirce);
                    break;
                case 5:
                    // ha ezt a menüt választotta, akkor helyben beolvasunk egy id-t
                    System.out.print("Kerem ajda meg az id-t: ");
                    int id = sc.nextInt();
                    printRecord(id);
                    break;
                case 6: System.exit(0); // azonnal kilépünk a programból
                    break;
                default: System.out.println("nem letezik a valasztott menupont");
                    break; // kiírhatjuk, de az utolsó ág végén nem szokott break lenni
            }
        }

    }

    static void insertRecord(int id, String owner, String car, String errorCode, double price){
        int recPos = findById(id);
        if(recPos != -1){
            System.out.println("A hozzadas nem valosíthato meg, rekord letezik!");
        }
        else{
            // Arrays.copyOf -> másolat készítése egy tömbről
            ids = Arrays.copyOf(ids, ids.length + 1); // az ids eredeti tartalma + 1 üres hely
            ids[ids.length - 1] = id;

            owners = Arrays.copyOf(owners, owners.length + 1);
            owners[owners.length - 1] = owner;

            cars = Arrays.copyOf(cars, cars.length + 1);
            cars[cars.length - 1] = car;

            errorCodes = Arrays.copyOf(errorCodes, errorCodes.length + 1);
            errorCodes[errorCodes.length - 1] = errorCode;

            prices = Arrays.copyOf(prices, prices.length + 1);
            prices[prices.length - 1] = price;

        }

    }

    static void deleteRecord(int id){
        int recPos = findById(id);
        if(recPos == -1){
            System.out.println("A rekord nem talalhato!");
        }
        else{
            // a törlést úgy képzeljük el, hogy lemásoljuk egy segédtömbbe az elemeket, úgy, hogy a
            // törölt elemet kihagyjuk ... majd a segédtömböket átveszik az eredeti tömbök
            int[] idsHelper = new int[ids.length - 1];
            int helpI = 0;
            for(int i = 0; i < ids.length; i++){
                if(i != recPos){
                    idsHelper[helpI] = ids[i];
                    helpI++;
                }
            }
            ids = idsHelper; // az ids új tartalma legyen a segédváltozó

            String[] ownersHelper = new String[owners.length - 1];
            helpI = 0;
            for(int i = 0; i < owners.length; i++){
                if(i != recPos){
                    ownersHelper[helpI] = owners[i];
                    helpI++;
                }
            }
            owners = ownersHelper;



            String[] carsHelper = new String[cars.length - 1];
            helpI = 0;
            for(int i = 0; i < cars.length; i++){
                if(i != recPos){
                    carsHelper[helpI] = cars[i];
                    helpI++;
                }
            }
            cars = carsHelper;

            String[] errorCodesHelper = new String[errorCodes.length - 1];
            helpI = 0;
            for(int i = 0; i < errorCodes.length; i++){
                if(i != recPos){
                    errorCodesHelper[helpI] = errorCodes[i];
                    helpI++;
                }
            }
            errorCodes = errorCodesHelper;

            double[] pricesHelper = new double[prices.length - 1];
            helpI = 0;
            for(int i = 0; i < prices.length; i++){
                if(i != recPos){
                    pricesHelper[helpI] = prices[i];
                    helpI++;
                }
            }
            prices = pricesHelper;
        }
    }

    static void updateRecord(int id, String owner, String car, String errorCode, double price){
        int recPos = findById(id); //megkeresem a helyet

        // ha nincs ilyen hely, akkor hibajelzés, különben módosítom a rekordot
        if(recPos == -1){
            System.out.println("Ilyen azonositoval nem talalhato rekord!");
        }
        else{ // az id kivételével minden frissítek
            owners[recPos] = owner;
            cars[recPos] = car;
            errorCodes[recPos] = errorCode;
            prices[recPos] = price;
        }
    }

    static void printRecord(int recordId){
        // id alapján szeretnénk kikeresni, hogy melyik sorszámú (indexű) rekorddal kell dolgozni
        int recordPos = findById(recordId);

        if(recordPos == -1){
            System.out.println("Nincs ilyen id-vel rekord az adatbazisban");
        }
        else{
            System.out.println("Id: " + ids[recordPos]);
            System.out.println("Tulaj: " + owners[recordPos]);
            System.out.println("Auto: " + cars[recordPos]);
            System.out.println("Hibakod: " + errorCodes[recordPos]);
            System.out.println("Ar: " + prices[recordPos]);
        }
    }

    // keresés: határozzuk meg, hogy az id az ids tömb melyik pozícióján fordul elő
    //  ==> keresésre új metódust szervezünk
    static int findById(int recordId){
        int pos = -1; // felteszem, hogy nincs benne az id a tömbben

        for(int i = 0; i < ids.length; i++){
            if(ids[i] == recordId){ // ha az akt elem a recordId-vel azonos, akkor megtaláltam a pozíciót
                pos = i;
                break;
            }
        }

        return pos;
    }

    static void printData(){ // kiíraj az összes adatot (soronként egy rekordot)
        System.out.println("id, tulaj, autó, hibakód, ár");
        for(int i = 0; i < ids.length; i++){
            System.out.print(ids[i]);
            System.out.print(";");
            System.out.print(owners[i]);
            System.out.print(";");
            System.out.print(cars[i]);
            System.out.print(";");
            System.out.print(errorCodes[i]);
            System.out.print(";");
            System.out.print(prices[i]);
            System.out.print(";");

            System.out.println();
        }
    }

    static void printMenu(){
        System.out.println("Valaszthato menupontok: ");
        System.out.println("1 - teljes lista");
        System.out.println("2 - javítás rögzítése");
        System.out.println("3 - javítás törlése");
        System.out.println("4 - javítás módosítása");
        System.out.println("5 - javítás kereséses id alapján");
        System.out.println("6 - kilépés");
    }



    // C
    // R: read/retrive -> az adatokat a forrásállományból be kell tölteni
    static void loadCSV(String path) { // path-ról beolvassa a struktúra szempontjából adott csv-t
        // iteráljuk végig a fájl sorait, hogy tudjuk hány adat van benne -> tömb statikussága miatt kell


        try{
            // A fájlt mint adatfolyamot megnyitom FileReader-en keresztül
            FileReader fr = new FileReader(path);
            // Mivel a FileReader byte alapú kiolvasást tesz lehetővé, ezért a BufferedReader segítségével ezt sor alapúvá tesszük
            BufferedReader br = new BufferedReader(fr);
            // számoljuk meg, hogy hány sor van a fájlban -> egy ciklussal addig megyünk előre a fájlban amíg az
            // a beolvasott sor null nem lesz
            int rowCnt = 0;
            while(true){ // a ciklus a végtelenségig fut -> a ciklusból break-el fogunk kilépni
                if(br.readLine() == null){ // ha az olvasott sor null, akkor már nincs sor
                    break; // kiugrom
                }
                rowCnt++;
            }

            // a fájlban pontosan rowCnt mennyiségű feldolgozandó sor van
            br = new BufferedReader(new FileReader(path)); // visszaállok a fájl elejére
            // F: próbáljuk meg a fájlok sorait elhelyezni a tömbökben
            // a) inicializáljuk a tömböket rowCnt mérettel
            ids = new int[rowCnt];
            owners = new String[rowCnt];
            cars = new String[rowCnt];
            errorCodes = new String[rowCnt];
            prices = new double[rowCnt];

            // b) beolvassuk a sorokat, daraboljuk és parseolás után a megfelleő tömb megf. indexéret helyezzük
            int actPos = 0; // melyik helyre kell berakni a kiolvasott adatokat
            while(true){
                String line = br.readLine();
                if(line == null){ // elfogytak a sorok
                    break;
                }
                String[] fields = line.split(","); // felvágom a sorokat az elválasztó karakter mentén, így egy tömbbe megkapom az elemei adatokat
                ids[actPos] = Integer.parseInt(fields[0]);
                owners[actPos] = fields[1];
                cars[actPos] = fields[2];
                errorCodes[actPos] = fields[3];
                prices[actPos] = Double.parseDouble(fields[4]);

                actPos++; // a következő elem elhelyezése a tömbökben
            }


        }
        catch (FileNotFoundException e) { // ez a catch akkor fut le, ha a try-on belül FileNotFoundException hiba keletkezik
            System.out.println("A " + path + " utvonalon nem talalhato fajl!");
        }
        catch (IOException e){
            System.out.println("Hiba a fajl kezelese soran!");
        }

    }
    // U
    // D:
}
