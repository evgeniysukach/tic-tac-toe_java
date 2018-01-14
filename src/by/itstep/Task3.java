package by.itstep;

//3*) Алгоритм для компа который играет "умно"

import java.util.Scanner;

public class Task3 {

    public static void main(String[] args) {
        char[][] gameField = {{' ', ' ', ' '}, {' ', ' ', ' '}, {' ', ' ', ' '}};
        System.out.println("Выберите, каким будет ваш ход: 1 или 2");
        Scanner scanner = new Scanner(System.in);
        int priority = scanner.nextInt();
        showGameField(gameField);
        int result;
        char symbol = 'X';
        do {

            if (priority == 1)
                makeMove(gameField, symbol);
            else computerMove(gameField, symbol);

            symbol = symbol == 'X' ? '0' : 'X';
            showGameField(gameField);
            result = checkField(gameField);
            if (result != 0)
                break;

            if (priority == 1)
                computerMove(gameField, symbol);
            else makeMove(gameField, symbol);
            symbol = symbol == 'X' ? '0' : 'X';
            showGameField(gameField);
            result = checkField(gameField);

        } while (result == 0);
        switch (result) {
            case 1:
                System.out.println("Победа X");
                break;
            case 2:
                System.out.println("Победа 0");
                break;
            case 3:
                System.out.println("Ничья");
        }
    }

    public static void computerMove(char[][] gameField, char symbol) {
        System.out.println("Ход компьютера ");
        /*Если центральная ячейка свободна, то ходим в неё*/
        if (gameField[1][1] == ' ') {
            gameField[1][1] = symbol;
            return;
        }

        char anotherSymbol = symbol == 'X' ? '0' : 'X';

        boolean flag = ifTwoIdenticalSymbolsAndSpacePutSymbol(gameField, symbol, symbol); //завершающая атака
        if (flag == true)
            return;
        flag = ifTwoIdenticalSymbolsAndSpacePutSymbol(gameField, anotherSymbol, symbol);//защита
        if (flag == true)
            return;

/*Если нам у нас нет хода для мгновенной победы, а также у нас нет хода, чтобы предотварить победу соперника, то ходим рандомно*/
        int n;
        int m;
        int number;
        do {
            number = (int) (Math.random() * 10);
            n = 2 - (number - 1) / 3;
            m = (number - 1) % 3;
        } while (number < 1 || number > 9 || gameField[n][m] != ' ');
        gameField[n][m] = symbol;


    }


    public static void makeMove(char[][] gameField, char symbol/*Х или 0*/) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ваш ход ");
        int n;
        int m;
        int number;
        do {
            number = scanner.nextInt();
            n = 2 - (number - 1) / 3;
            m = (number - 1) % 3;
        } while (number < 1 || number > 9 || gameField[n][m] != ' ');
        gameField[n][m] = symbol;
    }


    public static void showGameField(char[][] gameField) {
        System.out.println(gameField[0][0] + "|" + gameField[0][1] + "|" + gameField[0][2]);
        System.out.println("-----");
        System.out.println(gameField[1][0] + "|" + gameField[1][1] + "|" + gameField[1][2]);
        System.out.println("-----");
        System.out.println(gameField[2][0] + "|" + gameField[2][1] + "|" + gameField[2][2]);
    }

    public static int checkField(char[][] gameField) {
        for (int i = 0; i < 3; i++) {
            if (gameField[i][0] == gameField[i][1]
                    && gameField[i][0] == gameField[i][2]) {
                if (gameField[i][0] == 'X')
                    return 1;
                if (gameField[i][0] == '0')
                    return 2;
            }
            if (gameField[0][i] == gameField[1][i]
                    && gameField[0][i] == gameField[2][i]) {
                if (gameField[0][i] == 'X')
                    return 1;
                if (gameField[0][i] == '0')
                    return 2;
            }
        }
        if (gameField[0][0] == gameField[1][1] && gameField[0][0] == gameField[2][2]) {
            if (gameField[0][0] == 'X')
                return 1;
            if (gameField[0][0] == '0')
                return 2;
        }
        if (gameField[2][0] == gameField[1][1] && gameField[2][0] == gameField[0][2]) {
            if (gameField[2][0] == 'X')
                return 1;
            if (gameField[2][0] == '0')
                return 2;
        }
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (gameField[i][j] == ' ')
                    return 0; //Продолжаем

        return 3; //Ничья
    }


    /*Метод, который ставит putSymbol, если видит что в одной из строк есть два одинаковых символа checkSymbol и пробел.
    Если находит такую строку, то возвращает true (для прекращения работы вышестоящего метода)
    Может применяться и для атаки, и для защиты
     */

    public static boolean ifTwoIdenticalSymbolsAndSpacePutSymbol(char gameField[][], char checkSymbol, char putSymbol) {
        char anotherSymbol = checkSymbol == 'X' ? '0' : 'X';
//проверяем строки
        for (int i = 0; i < 3; i++) {
            int count = 0;
            int position = 0;
            boolean check = false;
            for (int j = 0; j < 3; j++) {

                if (gameField[i][j] == anotherSymbol)
                    break;
                if (gameField[i][j] == checkSymbol)
                    count++;
                if (gameField[i][j] == ' ') {
                    position = j;
                    check = true;
                }


            }
            if ((count == 2) && check) {
                gameField[i][position] = putSymbol;
                System.out.println("Словили строку");
                return true;
            }
//проверяем столбцы
            count = 0;
            position = 0;
            check = false;


            for (int j = 0; j < 3; j++) {
                if (gameField[j][i] == anotherSymbol)
                    break;
                if (gameField[j][i] == checkSymbol)
                    count++;
                if (gameField[j][i] == ' ') {
                    position = j;
                    check = true;
                }


            }
            if ((count == 2) && check) {
                gameField[position][i] = putSymbol;
                System.out.println("Словили строку");
                return true;
            }

        }

        //проверяем диагонали
        //диагональ {0,0; 1,1; 2,2}
        int count = 0;
        int position = 0;
        boolean check = false;

        for (int i = 0; i < 3; i++) {
            if (gameField[i][i] == anotherSymbol)
                break;
            if (gameField[i][i] == checkSymbol)
                count++;
            if (gameField[i][i] == ' ') {
                position = i;
                check = true;
            }


        }
        if ((count == 2) && check) {
            gameField[position][position] = putSymbol;
            System.out.println("Словили строку");
            return true;


        }

        //диагональ {0,2; 1,1; 2,0}
        count = 0;
        check = false;
        int j = 2; //вспомогательная переменная для определения номера столбца
        int positionN = 0; //запоминает номер строки, в котором находится пустая ячейка
        int positionM = 0;//запоминает номер столбца, в котором находится пустая ячейка

        for (int i = 0; i < 3; i++) {
            if (gameField[i][j] == anotherSymbol)
                break;
            if (gameField[i][j] == checkSymbol)
                count++;
            if (gameField[i][j] == ' ') {
                positionN = i;
                positionM = j;
                check = true;
            }
            j--;


        }
        if ((count == 2) && check) {
            gameField[positionN][positionM] = putSymbol;
            System.out.println("Словили строку");
            return true;


        }
        return false;
    }


}



 /*Финишная атака. Если в одном из направлений, две ячейки  - это symbol,
        а третья - пробел, тогда ставим symbol в третью ячейку*/

      /*  char anotherSymbol = symbol == 'X' ? '0' : 'X';
//проверяем строки
        for (int i = 0; i < 3; i++) {
            int count = 0;
            int position = 0;
            boolean check = false;
            for (int j = 0; j < 3; j++) {

                if (gameField[i][j] == anotherSymbol)
                    break;
                if (gameField[i][j] == symbol)
                    count++;
                if (gameField[i][j] == ' ') {
                    position = j;
                    check = true;
                }


            }
            if ((count == 2) && check) {
                gameField[i][position] = symbol;
                return;
            }
//проверяем столбцы

            for (int j = 0; j < 3; j++) {
                if (gameField[j][i] == anotherSymbol)
                    break;
                if (gameField[j][i] == symbol)
                    count++;
                if (gameField[j][i] == ' ') {
                    position = j;
                    check = true;
                }


            }
            if ((count == 2) && check) {
                gameField[position][i] = symbol;
                return;
            }

        }

        //проверяем диагонали
        //диагональ {0,0; 1,1; 2,2}
        int count = 0;
        int position = 0;
        boolean check = false;

        for (int i = 0; i < 3; i++) {
            if (gameField[i][i] == anotherSymbol)
                break;
            if (gameField[i][i] == symbol)
                count++;
            if (gameField[i][i] == ' ') {
                position = i;
                check = true;
            }


        }
        if ((count == 2) && check) {
            gameField[position][position] = symbol;
            return;


        }

        //диагональ {0,2; 1,1; 2,0} - проверяем вручную каждую ячейку.
        count = 0;
        check = false;
        int j = 2; //вспомогательная переменная для определения номера столбца
        int positionN = 0; //запоминает номер строки, в котором находится пустая ячейка
        int positionM = 0;//запоминает номер столбца, в котором находится пустая ячейка

        for (int i = 0; i < 3; i++) {
            if (gameField[i][j] == anotherSymbol)
                break;
            if (gameField[i][j] == symbol)
                count++;
            if (gameField[i][j] == ' ') {
                positionN = i;
                positionM = j;
                check = true;
            }
            j--;


        }
        if ((count == 2) && check) {
            gameField[positionN][positionM] = symbol;
            return;


        }





       */