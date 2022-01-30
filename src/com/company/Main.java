package com.company;

/*
  Программа, которая работает с шифром Цезаря

За основу криптографического алфавита возмем все буквы русского алфавита и знаки
пунктуации (. , ”” : - ! ? ПРОБЕЛ). Если попадутся символы,
которые не входят в мой криптографический алфавит, я не просто, но пропускаю их.

У программы 2 режима:
    У пользователя программы есть возможность выбрать один из двух методов криптоанализа
1. Шифрование / расшифровка. Программа зашифровывает и расшифровывать текст, используя заданный криптографический ключ.
Программа получает путь к текстовому файлу с исходным текстом и на его основе создает файл с зашифрованным текстом.
    Если пользователь выбирает brute force, программа должна самостоятельно, путем перебора, подобрать ключ и расшифровать текст
2. Криптоанализ. Программа взламывает зашифрованный текст, переданный в виде текстового файла.
    Если пользователь выбирает метод статистического анализа, ему нужно предложить загрузить еще один дополнительный файл с текстом,
желательно — того же автора и той же стилистики.
    На основе загруженного файла программа должна составить статистику вхождения символов
и после этого попытаться использовать полученную статистику для криптоанализа зашифрованного текста.
*/

// BrutForse
// Другой способ проверки выборки: Длина слова не больше 25 (после пробела)
// Другой способ проверки выборки: вероятность гласных и согласных под ряд
// Другой способ проверки выборки: Если сомневаемся спрашиваем у пользователя читабельно ли

// Аналитическая хуйня
// ключ не искать, каждый символ нужно отгадывать с вероятностью
// считаем, что есть пару абзацев, короче нормальный текст
// соединяем 2 отсортированных мапов, в результате слияния получим мапу а-п, в-о, ...

// Общая инфа занятия с 24.01.2022
//      Спринг нужен для 95% работы
//      Спринг core
//      Спринг boot


import java.util.*;

public class Main {
    static String s_Crypto = "";
    static int shift;
    static String s_original = "";

    public static void main(String[] args) {
        Scanner vv = new Scanner(System.in);

        System.out.println("Эта программа очень крутая на самом деле, " +
                "тут можно не только расшифровать текст, но и зашифровать.");
        System.out.println("\t Если хочешь зашифровать текст нажми 'y', " +
                "продолжить по умолчанию - любой другой символ");
        // Проверка на запуск метода шифрования
        if (vv.next().equals("y")) {
            System.out.println("Выбери смещение символов по Шифру Цезаря. " +
                    "Знак минус означает смещение влево");
            System.out.println("\t\tТолько выбирай сердцем (от -40 до 40)");
            shift = vv.nextInt();
            // Если пользователь ввел 3 раза подряд неправильное число, то программа закроется
            if (Three_try.three_try()) {
                System.out.println("Если хочешь зашифровать свой собственный текст нажмите 'y'");
                // Проверка на желание пользователя загрузить собственный текст
                if (vv.next().equals("y")) {
                    System.out.println("\t Введи путь до твоего текстового файла");
                    System.out.println("\t\t Вот пример: C:\\Users\\User\\Desktop\\name.txt");
                    // дается 2 попытки ввести правильно путь,
                    // если они исчерпаны, то путь выбирается по умолчанию
                    TryCatch_openFile.tryCatch_openFile();
                    System.out.println("Файл зашифрован и находится в корне программы " +
                            "под названием \"cipher_text.txt\"");
                }
            }
            s_original = ReadFile.readFile("original_text.txt");
            //  Зашифруем текст файла
            s_Crypto = ToCrypto_noCase.toCrypto_noCase(s_original);
            WriteFile.writeFile("cipher_text.txt");
        }
//      Расшифровка текста начинается тут
        String cryptoLine = ReadFile.readFile("cipher_text.txt");

        System.out.println("Каким способом ты хочешь расшифровать текст?");
        System.out.println("\tЕсли Brute force (поиск грубой силой) набери \"1\"");
        System.out.println("\tЕсли Криптоанализ на основе статистических данных набери \"2\"");

        String one_or_two = vv.next();
        if (one_or_two.equals("1")) {
//              1. brute force
            System.out.println("Выбран метод Brute force (поиск грубой силой)");
            BrutForse.brutForse(cryptoLine);
        } else if (one_or_two.equals("2")) {
//              2. Криптоанализ на основе статистических данных
            System.out.println("Выбран метод Криптоанализ на основе статистических данных");
            System.out.println("По умолчанию стоит самый простой стиль дешифровки \n");
            CryptoAnalysis cryptoAnalysis = new CryptoAnalysis();
            cryptoAnalysis.cryptoAnalysis(cryptoLine);
        } else {
            // Если пользователь ввел 3 раза подряд неправильное число, то программа просто закроется
            Three_try2.three_try2(cryptoLine);
        }
    }
}