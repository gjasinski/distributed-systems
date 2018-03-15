import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Chat {
    private final InputStreamReader inputStreamReader = new InputStreamReader(System.in);
    private final BufferedReader consoleReader = new BufferedReader(inputStreamReader);
    private final String host;
    private final int port;
    private TCPMessageReceiver tcpMessageReceiver;
    private UDPMessageReceiver udpMessageReceiver;
    private Socket socket;
    private String name;
    private PrintWriter writer;
    private DatagramSocket datagramSocket;
    private volatile boolean terminate = false;

    Chat(String host, int port) throws IOException {
        this.host = host;
        this.port = port;
        initConnections();
    }

    private void initConnections() throws IOException {
        socket = new Socket(host, port);
        writer = new PrintWriter(socket.getOutputStream(), true);
        datagramSocket = new DatagramSocket(socket.getLocalSocketAddress());
    }

    void runChat() throws IOException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        name = getName();

        tcpMessageReceiver = new TCPMessageReceiver(socket);
        udpMessageReceiver = new UDPMessageReceiver(datagramSocket);
        executorService.execute(tcpMessageReceiver);
        executorService.execute(udpMessageReceiver);

        writer.println(name);
        while (!terminate) {
            String input = consoleReader.readLine();
            switch (input) {
                case "A":
                    writer.println(getHitmanArt());
                    break;
                case "U":
                    sendUdpMessage();
                    break;
                case "exit":
                    terminate();
                    break;
                default:
                    writer.println(name + ": " + input);
            }
        }
        executorService.shutdownNow();
    }

    private void sendUdpMessage() throws IOException {
        String input = consoleReader.readLine();
        switch (input) {
            case "A":
                String hitmanArt = name + ":\n" + getHitmanArt();
                datagramSocket.send(new DatagramPacket(hitmanArt.getBytes(), hitmanArt.length(),
                        new InetSocketAddress(host, port)));
                break;
            case "exit":
                terminate();
                break;
            default:
                input = name + ": " + input;
                datagramSocket.send(new DatagramPacket(input.getBytes(), input.length(),
                        new InetSocketAddress(host, port)));
        }
    }

    private String getName() throws IOException {
        System.out.println("Type your name: ");
        return consoleReader.readLine();
    }

    private void terminate() {
        terminate = true;
        tcpMessageReceiver.terminate();
        udpMessageReceiver.terminate();
    }

    private String getHitmanArt() {
        return "                        .-\"\"\"\"-.\n" +
                "                       / j      \\\n" +
                "                      :.d;       ;\n" +
                "                      $$P        :\n" +
                "           .m._       $$         :\n" +
                "          dSMMSSSss.__$$b.    __ :\n" +
                "         :MMSMMSSSMMMSS$$$b  $$P ;\n" +
                "         SMMMSMMSMMMSSS$$$$     :b\n" +
                "        dSMMMSMMMMMMSSMM$$$b.dP SSb.\n" +
                "       dSMMMMMMMMMMSSMMPT$$=-. /TSSSS.\n" +
                "      :SMMMSMMMMMMMSMMP  `$b_.'  MMMMSS.\n" +
                "      SMMMMMSMMMMMMMMM \\  .'\\    :SMMMSSS.\n" +
                "     dSMSSMMMSMMMMMMMM  \\/\\_/; .'SSMMMMSSSm\n" +
                "    dSMMMMSMMSMMMMMMMM    :.;'\" :SSMMMMSSMM;\n" +
                "  .MMSSSSSMSSMMMMMMMM;    :.;   MMSMMMMSMMM;\n" +
                " dMSSMMSSSSSSSMMMMMMM;    ;.;   MMMMMMMSMMM\n" +
                ":MMMSSSSMMMSSP^TMMMMM     ;.;   MMMMMMMMMMM\n" +
                "MMMSMMMMSSSSP   `MMMM     ;.;   :MMMMMMMMM;\n" +
                "\"TMMMMMMMMMM      TM;    :`.:    MMMMMMMMM;\n" +
                "   )MMMMMMM;     _/\\\\    :`.:    :MMMMMMMM\n" +
                "  d$SS$$$MMMb.  |._\\\\\\   :`.:     MMMMMMMM\n" +
                "  T$$S$$$$$$$$$$m;O\\\\\\\\\"-;`.:_.-  MMMMMMM;\n" +
                " :$$$$$$$$$$$$$$$b_l./\\\\ ;`.:    mMMSSMMM;\n" +
                " :$$$$$$$$$$$$$$$$$$$./\\\\;`.:  .$$MSMMMMMM\n" +
                "  $$$$$$$$$$$$$$$$$$$$. \\\\`.:.$$$$SMSSSMMM;\n" +
                "  $$$$$$$$$$$$$$$$$$$$$. \\\\.:$$$$$SSMMMMMMM\n" +
                "  :$$$$$$$$$$$$$$$$$$$$$.//.:$$$$SSSSSSSMM;\n" +
                "  :$$$$$$$$$$$$$$$$$$$$$$.`.:$$SSSSSSSMMMP\n" +
                "   $$$$$$$$$;\"^$J \"^$$$$;.`.$$P  `SSSMMMM\n" +
                "   :$$$$$$$$$       :$$$;.`.P'..   TMMM$$b\n" +
                "   :$$$$$$$$$;      $$$$;.`/ c^'   d$$$$$S;\n" +
                "   $$$$$S$$$$;      '^^^:_d$g:___.$$$$$$SSS\n" +
                "   $$$$SS$$$$;            $$$$$$$$$$$$$$SSS;\n" +
                "  :$$$SSSS$$$$            : $$$$$$$$$$$$$SSS\n" +
                "  :$P\"TSSSS$$$            ; $$$$$$$$$$$$$SSS;\n" +
                "  j    `SSSSS$           :  :$$$$$$$$$$$$$SS$\n" +
                " :       \"^S^'           :   $$$$$$$$$$$$$S$;\n" +
                " ;.____.-;\"               \"--^$$$$$$$$$$$$$P\n" +
                " '-....-\"  hitman              \"\"^^T$$$$P\"\n" +
                "\n";
    }
}
