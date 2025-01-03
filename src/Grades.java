import java.util.Scanner;

/*
Kérjük be egy osztály létszámát, majd a dolgozaton
elért eredményeket (1-5-ig). Határozzuk meg, hogy
	- volt-e olyan, aki egyest kapott?
	- mi volt az osztályátlag?
	- mi volt a legjobb érdemjegy, amit szereztek?
	- hányan szereztek 3-as érdemjegyet?

 */
public class Grades {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 1) osztálylétszám meghatározása
        System.out.print("Number of students: ");
        int studCnt = sc.nextInt();

        // 3) volt-e olyan aki egyest kapott? -> eldöntés
        boolean failedStud = false; // veszek egy logikai változót megf. kezdőértékkel
        // 4) mi volt az osztályátlag? -> összegzés + megszámlálás (~ megsz. most nem kell, mert tudom a létszámot)
        int gradesSum = 0; // összegzés: vegyünk fel egy segédváltozót a művletre nézve a neutrális elemmel
        // 5) hányan szereztek 3-as érdemjegyet? -> megszámlálás (határozzuk meg a T tulj. elemeket)
        int grade3Cnt = 0; // vegyünk egy segédváltozót 0 kezdőértékkel
        // 6) mi volt a legjobb érdemjegy, amit szereztek? -> maximum kiválasztás (tömbök esetén felt. hogy az első a legnagyobb elem, itt ezt nem tudom megtenni)
        // választok egy olyna kicsi számot, ami biztosan kisebb a legelső bekérés eredménéytől is
        int maxGrade = 0; // az első beolvasás biztosan felülírja, hiszen az érdemjegyek 1-5 között vannak

        // 2) annyi bekérést végzek, mint amennyi tanuló van az osztályban
        for(int i = 0; i < studCnt; i++){
            System.out.print("Grade of student: ");
            int grade = sc.nextInt();

            // eldöntés: ha olyan körülménnyel találkozom, megváltoztatom a log. változó értékét
            if(grade == 1){
                failedStud = true;
            }
            // összegzés: a megfelelő operátorral az összegzett változóhoz adjuk az akt. értéket
            gradesSum += grade;

            // megszámlálás: minden olyan esetben, amikor T tulj. elem következik a segédváltozó ért. növeljük
            if(grade == 3){
                grade3Cnt++;
            }

            // szélsőérték keresés: ha a követekző érték és a lokálisan helyes szélsőértéknek vélt érték
            // viszonya nem megfelelő, akkor a korábban tárolt értéket felülírjuk az aktuálissal
            if(maxGrade < grade){
                maxGrade = grade;
            }
        }

        if(failedStud){
            System.out.println("Volt aki egyest kapott!");
        }
        else{
            System.out.println("Senki sem kapott egyest!");
        }

        // kiírom az átlagot
        double avg = (double)gradesSum / studCnt;
        System.out.println("Atlag: " + avg);

        System.out.println("3-as érdemjegyet " + grade3Cnt + " tanuló kapott");

        System.out.println("Legjobb érdemjegy: " + maxGrade);
    }
}
