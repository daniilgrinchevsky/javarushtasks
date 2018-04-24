package com.javarush.task.task30.task3008.client;

import com.javarush.task.task30.task3008.ConsoleHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BotClient extends Client {
    public class BotSocketThread extends SocketThread {
        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);
            if(message.contains(": ")) {
                String[] str = message.split(": ");
                if (str[1].equals("дата"))
                    sendTextMessage("Информация для " + str[0] + ": " + new SimpleDateFormat("d.MM.YYYY").format(Calendar.getInstance().getTime()));
                else if (str[1].equals("день"))
                    sendTextMessage("Информация для " + str[0] + ": " + new SimpleDateFormat("d").format(Calendar.getInstance().getTime()));
                else if (str[1].equals("месяц"))
                    sendTextMessage("Информация для " + str[0] + ": " + new SimpleDateFormat("MMMM").format(Calendar.getInstance().getTime()));
                else if (str[1].equals("год"))
                    sendTextMessage("Информация для " + str[0] + ": " + new SimpleDateFormat("YYYY").format(Calendar.getInstance().getTime()));
                else if (str[1].equals("время"))
                    sendTextMessage("Информация для " + str[0] + ": " + new SimpleDateFormat("H:mm:ss").format(Calendar.getInstance().getTime()));
                else if (str[1].equals("час"))
                    sendTextMessage("Информация для " + str[0] + ": " + new SimpleDateFormat("H").format(Calendar.getInstance().getTime()));
                else if (str[1].equals("минуты"))
                    sendTextMessage("Информация для " + str[0] + ": " + new SimpleDateFormat("m").format(Calendar.getInstance().getTime()));
                else if (str[1].equals("секунды"))
                    sendTextMessage("Информация для " + str[0] + ": " + new SimpleDateFormat("s").format(Calendar.getInstance().getTime()));
            }
        }
    }

    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    @Override
    protected boolean shouldSendTextFromConsole() {
        return false;
    }

    @Override
    protected String getUserName() {
        return "date_bot_" + String.valueOf((int)(Math.random()*100));
    }

    public static void main(String[] args) {
        BotClient botClient = new BotClient();
        botClient.run();
    }
}
