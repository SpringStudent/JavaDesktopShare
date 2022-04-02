package io.github.springstudent;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

/**
 * @author zhouning
 * @date 2022/03/31 17:21
 */
public class OutputHandler extends Thread {

    private BufferedReader br;

    private Process process;

    private CommandTask commandTask;

    private volatile boolean running;

    public OutputHandler(InputStream inputStream, Process process, CommandTask commandTask) {
        this.br = new BufferedReader(new InputStreamReader(inputStream));
        this.process = process;
        this.commandTask = commandTask;
        this.running = true;
    }

    @Override
    public void run() {
        String msg;
        try {
            while (running && (msg = br.readLine()) != null) {
                if (!commandTask.isRunning() && Pattern.matches(CommandTask.FFMPEG_CMOUTPUT_REGEX, msg)) {
                    commandTask.setRunning(true);
                }
                System.out.println(msg);
            }
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            commandTask.setRunning(false);
        }
    }

    @Override
    public void destroy() {
        this.running = false;
    }

}
