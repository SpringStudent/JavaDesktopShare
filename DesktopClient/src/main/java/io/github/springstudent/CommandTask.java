package io.github.springstudent;

import java.io.IOException;

/**
 * @author zhouning
 * @date 2022/03/31 17:19
 */
public class CommandTask {

    public static final String FFMPEG_DESKTOP_CMD = "%s -f gdigrab -i desktop -vcodec mpeg1video  -preset:v ultrafast -tune:v zerolatency -q 5 -r 25 -f mpegts %s";
    public static final String FFMPEG_CMOUTPUT_REGEX="frame=.*fps=.*speed=.*";
    public static final String TEXTFIELD_REGEX="http://.*/receive?\\?id=.*";

    /**
     * 命令行
     */
    private String command;
    /**
     * 命令行运行主进程
     */
    private Process process;
    /**
     * 命令行消息输出子线程
     */
    private OutputHandler outputHandler;
    /**
     * 是否在运行
     */
    private boolean running;
    /**
     * cmd监听器
     */
    private CmdListener cmdListener;

    /**
     * 监听cmd任务
     */
    public interface CmdListener {
        void notify(CommandTask commandTask);
    }

    public void setCmdListener(CmdListener cmdListener) {
        this.cmdListener = cmdListener;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
        cmdListener.notify(this);
    }

    public CommandTask(String cmd) {
        this.command = cmd;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public OutputHandler getOutputHandler() {
        return outputHandler;
    }

    public void setOutputHandler(OutputHandler outputHandler) {
        this.outputHandler = outputHandler;
    }

    public CommandTask start() throws IOException {
        Process process = Runtime.getRuntime().exec(command);
        OutputHandler outputHandler = new OutputHandler(process.getErrorStream(), process, this);
        outputHandler.start();
        this.setProcess(process);
        this.setOutputHandler(outputHandler);
        return this;
    }

    public void stop() {
        Process process = this.getProcess();
        if (process != null && process.isAlive()) {
            process.destroy();
        }
        OutputHandler outHandler = this.getOutputHandler();
        if (outHandler != null && outHandler.isAlive()) {
            outHandler.destroy();
        }
    }


}
