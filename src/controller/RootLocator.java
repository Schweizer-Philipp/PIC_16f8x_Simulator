package controller;

import java.net.URL;

public class RootLocator {

    private static final String TEST_TPIC_SIM = "test/TPicSim";

    public String getRoot(int number) {



        URL url = getClass().getProtectionDomain().getCodeSource().getLocation();
        String urlFinal = url.getFile().split("out")[0] + TEST_TPIC_SIM + String.valueOf(number) + ".LST";
        return urlFinal;
    }
}
