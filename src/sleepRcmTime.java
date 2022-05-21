public class sleepRcmTime {
    public static int[] sleepRcmTime(float h, int d, int L){
        // Function to get recommended sleeping hour. Value returned in a list of integer {hour, min}
        // h is default sleeping time in hour. Ex. if 8hour and 30min sleeping per day -> h=8.5
        // d is current day in integer from 1 to 366. Ex. Jan 1st -> d=1, Dec 31 in normal year -> d=365
        // L is current latitude of the place in degree from -90 to 90. Ex. latitude of Beijing is N. 60 -> L=40, latitude of Sydney is S. 30 -> L=-30
        // Ref for day-length calculation https://physics.stackexchange.com/questions/28563/hours-of-light-per-day-based-on-latitude-longitude-formula
        // Ref for Declination Angle of the sun https://www.pveducation.org/pvcdrom/properties-of-sunlight/declination-angle
        // Ref for common relationship between sleeping time and day-length https://doi.org/10.1038/s41746-021-00435-2
        float meanDayLength = 0;
        for (int i=1; i<366; i++){
            meanDayLength += DayLength(i, L);
        }
        meanDayLength = meanDayLength/365;
        float todayDayLength = DayLength(d, L);
        float sleepTime = (float) (h - 3.6*(todayDayLength - meanDayLength)/60);
        int hour = (int) Math.floor(sleepTime);
        int min = (int) (5*Math.round(((sleepTime - Math.floor(sleepTime)) * 60)/5));
        if (min==60){
            min = 0;
            hour += 1;
        }
        return new int[]{hour, min};
    }

    // Get Day length
    public static float DayLength(int d, int L){
        float sunDec = (float) (-23.4 * Math.cos(Math.toRadians(360*(d+10)/365)));
        sunDec = (float) Math.toRadians(sunDec);
        float l = (float) Math.toRadians(L);
        float ha = (float) Math.acos(Math.cos(Math.toRadians(90.833)) / (Math.cos(L) * Math.cos(sunDec)) - Math.tan(l) * Math.tan(sunDec));
        return (float) (2*Math.toDegrees(ha)/15);
    }

    public static void main(String[] args){
        // Example default sleeping time 8 hours, recommended sleeping time on Jan 10th in Latitude N60.
        int[] values = sleepRcmTime(8,10 , 60);
        System.out.println("Recommended sleeping hour is "+values[0]+":"+values[1]);
    }
}
