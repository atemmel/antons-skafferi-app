package se.grupp1.antonsskafferi.lib;

public class StringFormatter {
    public static String formatTime(String in) {
        if(in.length() == 5) return in;

        int index = in.indexOf(':');

        //Lägg till en 0 i början
        if(index < 2) {
            in = '0' + in;
            ++index;
        }

        //Lägg till en 0 i slutet
        if(in.length() < 5) {
            String end = in.substring(index + 1);
            String begin = in.substring(0, index + 1);
            in = begin + '0' + end;
        }

        return in;
    }
}
