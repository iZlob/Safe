import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        //инициализируем игру, получаем секретную комбинацию сейфа
        Game game = new Game();
        String secret = game.getSecret();
        //инициализируем меню игры и выводим его в консоль
        String[] menu = new String[] {"\n1. Хочу сам хакнуть этот код",
                                      "2. Пусть всю грязную работу сделает бот"};

        for (int i = 0; i < menu.length; i++) {
            System.out.println(menu[i]);
        }
        //инициализируем поток ввода и выбираем действие
        Scanner input = new Scanner(System.in);
        System.out.print("\nЧто будем делать? :  ");
        int action = input.nextInt();
        boolean isPlay = true;//инициализируем состояние игры

        while (isPlay) {
            int step = 1;//инициализируем шаги
            switch (action) {
                case 1: {//игра с пользователем
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

                    Player player = new Player();

                    while (game.getDigitsInPlace() < 4) {//игра
                        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

                        System.out.print("Step #: " + step + "\nYour guessed: ");
                        player.setPlayerVariant(input.next());
                        game.CheckVariant(player.getPlayerVariant());
                        System.out.println(String.format("Result: Guessed = %d; In placed = %d",
                                                            game.getCorrectDigits(), game.getDigitsInPlace()));
                        System.out.println("===========================\n");

                        step++;
                    }

                    System.out.println("Поздравляю! Вы это сделали!");
                    isPlay = false;
                    break;
                }
                case 2: {//игра с ботом
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

                    Bot bot = new Bot();

                    System.out.println("Secret = " + secret + "\n");

                    try(SqLite db = new SqLite()) {//создаем базу данных для сохранения результатов и записываем в него все результаты каждого шага
                        try {
                            db.ExecuteWithoutResult("DROP TABLE IF EXISTS BotResult");
                            db.ExecuteWithoutResult(
                                    "CREATE TABLE IF NOT EXISTS BotResult (" +
                                            "ID INT NOT NULL, " +
                                            "STEP INT, " +
                                            "VARIANT VARCHAR(5), " +
                                            "GUESSED INT, " +
                                            "INPLACE INT)");

                            while (game.getDigitsInPlace() < 4) {//игра
                                System.out.println("Step #: " + step);
                                String botVariant = bot.offerVariant();
                                System.out.println("bot variant: " + botVariant);
                                game.CheckVariant(bot.getPossibleVariant());
                                bot.hackSecret(game.getCorrectDigits(), game.getDigitsInPlace());
                                System.out.println(String.format("Result: Guessed = %d; In placed - %d", game.getCorrectDigits(), game.getDigitsInPlace()));
                                System.out.println("===========================\n");
                                //записываем результаты прохода в БД
                                db.ExecuteWithoutResult(String.format("INSERT INTO BotResult (ID, STEP, VARIANT, GUESSED, INPLACE) VALUES" +
                                                                      "(%d, %d, '%s', %d, %d);",
                                                                       step, step, botVariant, game.getCorrectDigits(), game.getDigitsInPlace()));

                                step++;
                            }
                            //эта чсать выполняет вывод результатов игры из БД в консоль
                            String query = "SELECT * FROM BotResult";
                            Statement statement = db._dbConnection.createStatement();
                            ResultSet result = statement.executeQuery(query);

                            System.out.println("Hacking results from DB:");
                            while(result.next()){
                                    System.out.println(String.format("%d: Step #%d Bot variant: %s guessed - %d, in placed - %d;",
                                            result.getInt(1), result.getInt(2), result.getString(3),
                                            result.getInt(4), result.getInt(5)));
                            }

                        }catch (Exception ex){
                            System.out.println(ex.getMessage());
                        }
                    }

                    isPlay = false;
                    break;
                }
                default: {//если ввели не верное действие предлагаем еще выбрать
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                    System.out.println("Вы ввели не верное действие!\n Попробуете еще раз!\n");

                    for (int i = 0; i < menu.length; i++) {
                        System.out.println(menu[i]);
                    }

                    System.out.print("\nНу так что будем делать? :  ");
                    action = input.nextInt();
                    break;
                }
            }
        }
        input.close();//закрываем поток ввода
    }
}