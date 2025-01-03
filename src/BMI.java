import java.util.Scanner;
/*
BMI kalkulátor: súly és magasság ismeretében határozzuk
meg a BMI értéket és a hozzá tartozó osztályt.
	1) Bekérem a billentyűzetről a súlyt és eltárolom.
	2) Bekérem a billentyűzetről a magasságot és eltárolom.
	3) BMI érték számítása és kiírása
	4) Testsúlyosztály megjelenítése.


 */
public class BMI {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 1) Bekérjük a súlyt
        System.out.print("Weight (kg): ");
        int weight = scanner.nextInt();

        // 2) Bekérjük a magasságot
        System.out.print("Height (cm): ");
        int height = scanner.nextInt();

        // 3) kiszámoljuk a bmi értéket és kiírja
        double heightM = (double)height / 100; // cm -> m
        double bmi = weight / (heightM * heightM);
        System.out.println("BMI: " + bmi);

        // 4) testsúlyosztály meghatározása
        if (bmi < 16) System.out.println("sulyos sovanysag");
        else if (bmi < 17) System.out.println("mersekelt sovanysag");
        else if (bmi < 18.5) System.out.println("enyhes sovanysag");
        else if (bmi < 25) System.out.println("normalis testsuly");
        else if (bmi < 30) System.out.println("tulsulyos");
        else if (bmi < 35) System.out.println("I. foku elhizas");
        else if (bmi < 40) System.out.println("II. foku elhizas");
        else System.out.println("III. foku elhizas");
    }
}
