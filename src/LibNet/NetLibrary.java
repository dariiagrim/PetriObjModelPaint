package LibNet;

import PetriObj.ArcIn;
import PetriObj.ArcOut;
import PetriObj.ExceptionInvalidNetStructure;
import PetriObj.ExceptionInvalidTimeDelay;
import PetriObj.PetriNet;
import PetriObj.PetriP;
import PetriObj.PetriT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class NetLibrary {

    /**
     * Creates Petri net that describes the dynamics of system of the mass
     * service (with unlimited queue)
     *
     * @param numChannel the quantity of devices
     * @param timeMean   the mean value of service time of unit
     * @param name       the individual name of SMO
     * @return Petri net dynamics of which corresponds to system of mass service with given parameters
     * @throws ExceptionInvalidTimeDelay             if one of net's transitions has no input position.
     * @throws PetriObj.ExceptionInvalidNetStructure
     */
    public static PetriNet CreateNetSMOwithoutQueue(int numChannel, double timeMean, String name) throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
        ArrayList<PetriP> d_P = new ArrayList<>();
        ArrayList<PetriT> d_T = new ArrayList<>();
        ArrayList<ArcIn> d_In = new ArrayList<>();
        ArrayList<ArcOut> d_Out = new ArrayList<>();
        d_P.add(new PetriP("P1", 0));
        d_P.add(new PetriP("P2", numChannel));
        d_P.add(new PetriP("P3", 0));
        d_T.add(new PetriT("T1", timeMean));
        d_T.get(0).setDistribution("exp", d_T.get(0).getTimeServ());
        d_T.get(0).setParamDeviation(0.0);
        d_In.add(new ArcIn(d_P.get(0), d_T.get(0), 1));
        d_In.add(new ArcIn(d_P.get(1), d_T.get(0), 1));
        d_Out.add(new ArcOut(d_T.get(0), d_P.get(1), 1));
        d_Out.add(new ArcOut(d_T.get(0), d_P.get(2), 1));
        PetriNet d_Net = new PetriNet("SMOwithoutQueue" + name, d_P, d_T, d_In, d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
    }

    /**
     * Creates Petri net that describes the dynamics of arrivals of demands for
     * service
     *
     * @param timeMean mean value of interval between arrivals
     * @return Petri net dynamics of which corresponds to generator
     * @throws PetriObj.ExceptionInvalidTimeDelay    if Petri net has invalid structure
     * @throws PetriObj.ExceptionInvalidNetStructure
     */
    public static PetriNet CreateNetGenerator(double timeMean) throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
        ArrayList<PetriP> d_P = new ArrayList<>();
        ArrayList<PetriT> d_T = new ArrayList<>();
        ArrayList<ArcIn> d_In = new ArrayList<>();
        ArrayList<ArcOut> d_Out = new ArrayList<>();
        d_P.add(new PetriP("P1", 1));
        d_P.add(new PetriP("P2", 0));
        d_T.add(new PetriT("T1", timeMean, Double.MAX_VALUE));
        d_T.get(0).setDistribution("exp", d_T.get(0).getTimeServ());
        d_T.get(0).setParamDeviation(0.0);
        d_In.add(new ArcIn(d_P.get(0), d_T.get(0), 1));
        d_Out.add(new ArcOut(d_T.get(0), d_P.get(1), 1));
        d_Out.add(new ArcOut(d_T.get(0), d_P.get(0), 1));
        PetriNet d_Net = new PetriNet("Generator", d_P, d_T, d_In, d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
    }

    /**
     * Creates Petri net that describes the route choice with given
     * probabilities
     *
     * @param p1 the probability of choosing the first route
     * @param p2 the probability of choosing the second route
     * @param p3 the probability of choosing the third route
     * @return Petri net dynamics of which corresponds to fork of routs
     * @throws PetriObj.ExceptionInvalidTimeDelay    if Petri net has invalid structure
     * @throws PetriObj.ExceptionInvalidNetStructure
     */
    public static PetriNet CreateNetFork(double p1, double p2, double p3) throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
        ArrayList<PetriP> d_P = new ArrayList<>();
        ArrayList<PetriT> d_T = new ArrayList<>();
        ArrayList<ArcIn> d_In = new ArrayList<>();
        ArrayList<ArcOut> d_Out = new ArrayList<>();
        d_P.add(new PetriP("P1", 0));
        d_P.add(new PetriP("P2", 0));
        d_P.add(new PetriP("P3", 0));
        d_P.add(new PetriP("P4", 0));
        d_P.add(new PetriP("P5", 0));
        d_T.add(new PetriT("T1", 0.0, Double.MAX_VALUE));
        d_T.get(0).setProbability(p1);
        d_T.add(new PetriT("T2", 0.0, Double.MAX_VALUE));
        d_T.get(1).setProbability(p2);
        d_T.add(new PetriT("T3", 0.0, Double.MAX_VALUE));
        d_T.get(2).setProbability(p3);
        d_T.add(new PetriT("T4", 0.0, Double.MAX_VALUE));
        d_T.get(3).setProbability(1 - p1 - p2 - p3);
        d_In.add(new ArcIn(d_P.get(0), d_T.get(0), 1));
        d_In.add(new ArcIn(d_P.get(0), d_T.get(1), 1));
        d_In.add(new ArcIn(d_P.get(0), d_T.get(2), 1));
        d_In.add(new ArcIn(d_P.get(0), d_T.get(3), 1));
        d_Out.add(new ArcOut(d_T.get(0), d_P.get(1), 1));
        d_Out.add(new ArcOut(d_T.get(1), d_P.get(2), 1));
        d_Out.add(new ArcOut(d_T.get(2), d_P.get(3), 1));
        d_Out.add(new ArcOut(d_T.get(3), d_P.get(4), 1));
        PetriNet d_Net = new PetriNet("Fork", d_P, d_T, d_In, d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
    }

    /**
     * Creates Petri net that describes the route choice with given
     * probabilities
     *
     * @param numOfWay      quantity of possibilities in choice ("ways")
     * @param probabilities set of values probabilities for each "way"
     * @return Petri net dynamics of which corresponds to fork of routs
     * @throws PetriObj.ExceptionInvalidTimeDelay    if Petri net has invalid structure
     * @throws PetriObj.ExceptionInvalidNetStructure
     */
    public static PetriNet CreateNetFork(int numOfWay, double[] probabilities) throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {

        ArrayList<PetriP> d_P = new ArrayList<>();
        ArrayList<PetriT> d_T = new ArrayList<>();
        ArrayList<ArcIn> d_In = new ArrayList<>();
        ArrayList<ArcOut> d_Out = new ArrayList<>();

        d_P.add(new PetriP("P0", 0));
        for (int j = 0; j < numOfWay; j++) {
            d_P.add(new PetriP("P" + (j + 1), 0));
        }

        for (int j = 0; j < numOfWay; j++) {
            d_T.add(new PetriT("вибір маршруту " + (j + 1), 0));
        }
        for (int j = 0; j < numOfWay; j++) {
            d_T.get(j).setProbability(probabilities[j]);
        }

        for (int j = 0; j < numOfWay; j++) {
            d_In.add(new ArcIn(d_P.get(0), d_T.get(j), 1));
        }

        for (int j = 0; j < numOfWay; j++) {
            d_Out.add(new ArcOut(d_T.get(j), d_P.get(j + 1), 1));
        }

        PetriNet d_Net = new PetriNet("Fork ", d_P, d_T, d_In, d_Out);

        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
    }

    /*pblic static PetriNet CreateNetMalware() throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
        ArrayList<PetriP> d_P = new ArrayList<>();
        ArrayList<PetriT> d_T = new ArrayList<>();
        ArrayList<ArcIn> d_In = new ArrayList<>();
        ArrayList<ArcOut> d_Out = new ArrayList<>();
        d_P.add(new PetriP("NewPacket",0));
        d_P.add(new PetriP("Firewall",0));//p1
        d_P.add(new PetriP("P2",0));
        d_P.add(new PetriP("WebServer",0));//p3
        d_P.add(new PetriP("P4",0));
        d_P.add(new PetriP("P5",0));
        d_P.add(new PetriP("FileServer",0));//p6
        d_P.add(new PetriP("P7",0));
        d_P.add(new PetriP("OS",0));//p8
        d_P.add(new PetriP("P9",0));
        d_P.add(new PetriP("VulnOfWall",1));
        d_P.add(new PetriP("VulnOfWebServ",1));
        d_P.add(new PetriP("VulnOfFileServ",1));
        d_P.add(new PetriP("VulnOfOS",1));
        d_P.add(new PetriP("VulnOfAuthorization",1));
        d_P.add(new PetriP("P15",0));
        d_P.add(new PetriP("P16",0));
        d_P.add(new PetriP("Success",0));  //p17
        d_P.add(new PetriP("Alarm",0));
        d_T.add(new PetriT("NetworkAccessControl",1.0));
        d_T.add(new PetriT("Authorization",0.0));
        d_T.get(1).setPriority(1);
        d_T.get(1).setProbability(0.5);
        d_T.add(new PetriT("RunWS",1.0));
        d_T.get(2).setPriority(1);
        d_T.get(2).setProbability(0.5);
        d_T.add(new PetriT("RunFS",1.0));
        d_T.get(3).setPriority(1);
        d_T.get(3).setProbability(0.5);
        d_T.add(new PetriT("RunOS",1.0));
        d_T.get(4).setPriority(1);
        d_T.get(4).setProbability(0.5);
        d_T.add(new PetriT("T1",0.0));
        d_T.add(new PetriT("T2",0.0));
        d_T.add(new PetriT("T3",0.0));
        d_T.add(new PetriT("T4",0.0));
        d_T.get(8).setPriority(1);
        d_T.get(8).setProbability(0.5);
        d_T.add(new PetriT("T5",0.0));
        d_T.get(9).setPriority(1);
        d_T.get(9).setProbability(0.5);
        d_T.add(new PetriT("T6",0.0));
        d_T.get(10).setPriority(1);
        d_T.get(10).setProbability(0.5);
        d_T.add(new PetriT("T7",0.0));
        d_T.get(11).setPriority(1);
        d_T.get(11).setProbability(0.5);
        d_In.add(new ArcIn(d_P.get(0),d_T.get(0),1));
        d_In.add(new ArcIn(d_P.get(1),d_T.get(0),1));
        d_In.add(new ArcIn(d_P.get(2),d_T.get(1),1));
        d_In.add(new ArcIn(d_P.get(3),d_T.get(1),1));
        d_In.add(new ArcIn(d_P.get(3),d_T.get(2),1));
        d_In.add(new ArcIn(d_P.get(4),d_T.get(2),1));
        d_In.add(new ArcIn(d_P.get(5),d_T.get(3),1));
        d_In.add(new ArcIn(d_P.get(6),d_T.get(3),1));
        d_In.add(new ArcIn(d_P.get(8),d_T.get(4),1));
        d_In.add(new ArcIn(d_P.get(7),d_T.get(4),1));
        d_In.add(new ArcIn(d_P.get(10),d_T.get(0),1));
        d_In.get(10).setInf(true);
        d_In.add(new ArcIn(d_P.get(12),d_T.get(3),1));
        d_In.get(11).setInf(true);
        d_In.add(new ArcIn(d_P.get(14),d_T.get(1),1));
        d_In.get(12).setInf(true);
        d_In.add(new ArcIn(d_P.get(11),d_T.get(2),1));
        d_In.get(13).setInf(true);
        d_In.add(new ArcIn(d_P.get(13),d_T.get(4),1));
        d_In.get(14).setInf(true);
        d_In.add(new ArcIn(d_P.get(8),d_T.get(5),1));
        d_In.add(new ArcIn(d_P.get(9),d_T.get(5),1));
        d_In.add(new ArcIn(d_P.get(15),d_T.get(6),1));
        d_In.add(new ArcIn(d_P.get(16),d_T.get(7),1));
        d_In.add(new ArcIn(d_P.get(2),d_T.get(8),1));
        d_In.add(new ArcIn(d_P.get(7),d_T.get(11),1));
        d_In.add(new ArcIn(d_P.get(5),d_T.get(10),1));
        d_In.add(new ArcIn(d_P.get(4),d_T.get(9),1));
        d_Out.add(new ArcOut(d_T.get(0),d_P.get(1),1));
        d_Out.add(new ArcOut(d_T.get(0),d_P.get(2),1));
        d_Out.add(new ArcOut(d_T.get(1),d_P.get(3),1));
        d_Out.add(new ArcOut(d_T.get(2),d_P.get(3),1));
        d_Out.add(new ArcOut(d_T.get(1),d_P.get(4),1));
        d_Out.add(new ArcOut(d_T.get(2),d_P.get(5),1));
        d_Out.add(new ArcOut(d_T.get(3),d_P.get(6),1));
        d_Out.add(new ArcOut(d_T.get(4),d_P.get(8),1));
        d_Out.add(new ArcOut(d_T.get(3),d_P.get(7),1));
        d_Out.add(new ArcOut(d_T.get(4),d_P.get(9),1));
        d_Out.add(new ArcOut(d_T.get(5),d_P.get(15),1));
        d_Out.add(new ArcOut(d_T.get(6),d_P.get(16),1));
        d_Out.add(new ArcOut(d_T.get(7),d_P.get(17),1));
        d_Out.add(new ArcOut(d_T.get(8),d_P.get(18),1));
        d_Out.add(new ArcOut(d_T.get(11),d_P.get(18),1));
        d_Out.add(new ArcOut(d_T.get(10),d_P.get(18),1));
        d_Out.add(new ArcOut(d_T.get(9),d_P.get(18),1));
        PetriNet d_Net = new PetriNet("malware",d_P,d_T,d_In,d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
    }*/
/*pblic static PetriNet CreateNetAdmin() throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
	ArrayList<PetriP> d_P = new ArrayList<>();
	ArrayList<PetriT> d_T = new ArrayList<>();
	ArrayList<ArcIn> d_In = new ArrayList<>();
	ArrayList<ArcOut> d_Out = new ArrayList<>();
	d_P.add(new PetriP("User",1));
	d_P.add(new PetriP("P1",0));
	d_P.add(new PetriP("P2",0));
	d_P.add(new PetriP("Damaged",0));
	d_P.add(new PetriP("Harmless",0));
	d_P.add(new PetriP("Executed",0));
	d_P.add(new PetriP("NewPacket",0));
	d_P.add(new PetriP("Admin",1));
	d_P.add(new PetriP("FileServer",0));
	d_P.add(new PetriP("Alarm",0));
	d_P.add(new PetriP("P10",0));
	d_T.add(new PetriT("DoTest",1.0));
	d_T.add(new PetriT("ControlTime",10.0));
	d_T.add(new PetriT("T3",0.0));
	d_T.add(new PetriT("T4",0.0));
	d_T.add(new PetriT("RepairResources",0.0));
	d_T.add(new PetriT("T2",0.0));
	d_T.add(new PetriT("T3",1.0));
	d_In.add(new ArcIn(d_P.get(1),d_T.get(1),1));
	d_In.add(new ArcIn(d_P.get(2),d_T.get(2),1));
	d_In.add(new ArcIn(d_P.get(2),d_T.get(3),1));
	d_In.add(new ArcIn(d_P.get(5),d_T.get(3),1));
	d_In.add(new ArcIn(d_P.get(7),d_T.get(4),1));
	d_In.add(new ArcIn(d_P.get(3),d_T.get(4),1));
	d_In.add(new ArcIn(d_P.get(9),d_T.get(5),1));
	d_In.add(new ArcIn(d_P.get(10),d_T.get(0),1));
	d_In.add(new ArcIn(d_P.get(0),d_T.get(6),1));
	d_Out.add(new ArcOut(d_T.get(0),d_P.get(1),1));
	d_Out.add(new ArcOut(d_T.get(1),d_P.get(2),1));
	d_Out.add(new ArcOut(d_T.get(2),d_P.get(3),1));
	d_Out.add(new ArcOut(d_T.get(3),d_P.get(4),1));
	d_Out.add(new ArcOut(d_T.get(0),d_P.get(6),1));
	d_Out.add(new ArcOut(d_T.get(4),d_P.get(7),1));
	d_Out.add(new ArcOut(d_T.get(4),d_P.get(8),1));
	d_Out.add(new ArcOut(d_T.get(6),d_P.get(10),1));
	d_Out.add(new ArcOut(d_T.get(6),d_P.get(0),1));
	d_Out.add(new ArcOut(d_T.get(5),d_P.get(10),1));
	PetriNet d_Net = new PetriNet("admin",d_P,d_T,d_In,d_Out);
	PetriP.initNext();
	PetriT.initNext();
	ArcIn.initNext();
	ArcOut.initNext();

	return d_Net;
}*/
    public static PetriNet CreateNetTest3() throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
        ArrayList<PetriP> d_P = new ArrayList<>();
        ArrayList<PetriT> d_T = new ArrayList<>();
        ArrayList<ArcIn> d_In = new ArrayList<>();
        ArrayList<ArcOut> d_Out = new ArrayList<>();
        d_P.add(new PetriP("P1", 100));
        d_P.add(new PetriP("P2", 0));
        d_P.add(new PetriP("P3", 0));
        d_T.add(new PetriT("T1", 0.0));
        d_T.add(new PetriT("T2", 0.0));
        d_In.add(new ArcIn(d_P.get(0), d_T.get(0), 1));
        d_In.add(new ArcIn(d_P.get(1), d_T.get(1), 1));
        d_Out.add(new ArcOut(d_T.get(0), d_P.get(1), 1));
        d_Out.add(new ArcOut(d_T.get(1), d_P.get(2), 1));
        PetriNet d_Net = new PetriNet("test3", d_P, d_T, d_In, d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
    }

    public static PetriNet CreateNetGenerator2(double d) throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
        ArrayList<PetriP> d_P = new ArrayList<>();
        ArrayList<PetriT> d_T = new ArrayList<>();
        ArrayList<ArcIn> d_In = new ArrayList<>();
        ArrayList<ArcOut> d_Out = new ArrayList<>();
        d_P.add(new PetriP("P1", 1));
        d_P.add(new PetriP("P2", 0));
        d_T.add(new PetriT("T1", d));
        d_T.get(0).setDistribution("exp", d_T.get(0).getTimeServ());
        d_T.get(0).setParamDeviation(0.0);
        d_In.add(new ArcIn(d_P.get(0), d_T.get(0), 1));
        d_Out.add(new ArcOut(d_T.get(0), d_P.get(1), 1));
        d_Out.add(new ArcOut(d_T.get(0), d_P.get(0), 1));
        PetriNet d_Net = new PetriNet("Generator", d_P, d_T, d_In, d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
    }

    public static PetriNet CreateNetThread3() throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
        ArrayList<PetriP> d_P = new ArrayList<>();
        ArrayList<PetriT> d_T = new ArrayList<>();
        ArrayList<ArcIn> d_In = new ArrayList<>();
        ArrayList<ArcOut> d_Out = new ArrayList<>();
        d_P.add(new PetriP("bow{", 0));
        d_P.add(new PetriP("P2", 0));
        d_P.add(new PetriP("lockA", 1));
        d_P.add(new PetriP("P4", 0));
        d_P.add(new PetriP("P5", 0));
        d_P.add(new PetriP("P6", 0));
        d_P.add(new PetriP("failure++", 0));
        d_P.add(new PetriP("lockB", 1));
        d_P.add(new PetriP("bowA++", 0));
        d_P.add(new PetriP("P10", 0));
        d_P.add(new PetriP("bowB++", 0));
        d_P.add(new PetriP("P15", 0));
        d_P.add(new PetriP("bowLoop{", 100));
        d_P.add(new PetriP("bow", 0));
        d_P.add(new PetriP("Core", 1));
        d_T.add(new PetriT("imp{", 0.1));
        d_T.add(new PetriT("tryLockA", 0.0));
        d_T.add(new PetriT("0&?", 0.0));
        d_T.add(new PetriT("tryLockB", 0.0));
        d_T.add(new PetriT("bowBack{", 0.1));
        d_T.add(new PetriT("unlockA", 0.0));
        d_T.add(new PetriT("0&1", 0.0));
        d_T.add(new PetriT("failure", 0.0));
        d_T.add(new PetriT("unlockAB", 0.1));
        d_T.add(new PetriT("unlockB", 0.1));
        d_T.add(new PetriT("for{", 0.0));
        d_T.add(new PetriT("for", 0.0));
        d_In.add(new ArcIn(d_P.get(0), d_T.get(0), 1));
        d_In.add(new ArcIn(d_P.get(1), d_T.get(1), 1));
        d_In.add(new ArcIn(d_P.get(1), d_T.get(2), 1));
        d_In.add(new ArcIn(d_P.get(2), d_T.get(1), 1));
        d_In.add(new ArcIn(d_P.get(3), d_T.get(3), 1));
        d_In.add(new ArcIn(d_P.get(7), d_T.get(3), 1));
        d_In.add(new ArcIn(d_P.get(5), d_T.get(4), 1));
        d_In.add(new ArcIn(d_P.get(3), d_T.get(5), 1));
        d_In.add(new ArcIn(d_P.get(4), d_T.get(6), 1));
        d_In.add(new ArcIn(d_P.get(4), d_T.get(7), 1));
        d_In.add(new ArcIn(d_P.get(7), d_T.get(6), 1));
        d_In.add(new ArcIn(d_P.get(9), d_T.get(8), 1));
        d_In.add(new ArcIn(d_P.get(11), d_T.get(9), 1));
        d_In.add(new ArcIn(d_P.get(12), d_T.get(10), 1));
        d_In.add(new ArcIn(d_P.get(13), d_T.get(11), 1));
        d_In.add(new ArcIn(d_P.get(14), d_T.get(10), 1));
        d_Out.add(new ArcOut(d_T.get(0), d_P.get(1), 1));
        d_Out.add(new ArcOut(d_T.get(1), d_P.get(3), 1));
        d_Out.add(new ArcOut(d_T.get(2), d_P.get(4), 1));
        d_Out.add(new ArcOut(d_T.get(3), d_P.get(5), 1));
        d_Out.add(new ArcOut(d_T.get(5), d_P.get(2), 1));
        d_Out.add(new ArcOut(d_T.get(4), d_P.get(9), 1));
        d_Out.add(new ArcOut(d_T.get(7), d_P.get(6), 1));
        d_Out.add(new ArcOut(d_T.get(3), d_P.get(8), 1));
        d_Out.add(new ArcOut(d_T.get(4), d_P.get(10), 1));
        d_Out.add(new ArcOut(d_T.get(8), d_P.get(2), 1));
        d_Out.add(new ArcOut(d_T.get(8), d_P.get(7), 1));
        d_Out.add(new ArcOut(d_T.get(6), d_P.get(11), 1));
        d_Out.add(new ArcOut(d_T.get(9), d_P.get(7), 1));
        d_Out.add(new ArcOut(d_T.get(10), d_P.get(0), 1));
        d_Out.add(new ArcOut(d_T.get(8), d_P.get(13), 1));
        d_Out.add(new ArcOut(d_T.get(11), d_P.get(14), 1));
        d_Out.add(new ArcOut(d_T.get(5), d_P.get(13), 1));
        d_Out.add(new ArcOut(d_T.get(9), d_P.get(13), 1));
        d_Out.add(new ArcOut(d_T.get(7), d_P.get(13), 1));
        d_Out.add(new ArcOut(d_T.get(9), d_P.get(6), 1));
        d_Out.add(new ArcOut(d_T.get(5), d_P.get(6), 1));

        PetriNet d_Net = new PetriNet("friendThread", d_P, d_T, d_In, d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
    }

/*pblic static PetriNet CreateNetConsistency(double forDelay, double readDelay, double modifyDelay, double writeDelay) throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
	ArrayList<PetriP> d_P = new ArrayList<>();
	ArrayList<PetriT> d_T = new ArrayList<>();
	ArrayList<ArcIn> d_In = new ArrayList<>();
	ArrayList<ArcOut> d_Out = new ArrayList<>();
	d_P.add(new PetriP("P1",1000000));
	d_P.add(new PetriP("P2",0));
	d_P.add(new PetriP("P3",0));
	d_P.add(new PetriP("P4",0));
	d_P.add(new PetriP("P5",1));
        d_P.add(new PetriP("readPermission",1));
	d_P.add(new PetriP("writePermission",1));
        d_P.add(new PetriP("Cores",2));
	d_T.add(new PetriT("for",forDelay));
        d_T.get(0).setDistribution("norm", d_T.get(0).getTimeServ());
        d_T.get(0).setParamDeviation(0.1);
	d_T.add(new PetriT("read",readDelay));
	d_T.add(new PetriT("modify",modifyDelay));
	d_T.add(new PetriT("write",writeDelay));
	d_In.add(new ArcIn(d_P.get(0),d_T.get(0),1));
	d_In.add(new ArcIn(d_P.get(4),d_T.get(0),1));
	d_In.add(new ArcIn(d_P.get(1),d_T.get(1),1));
	d_In.add(new ArcIn(d_P.get(2),d_T.get(2),1));
	d_In.add(new ArcIn(d_P.get(3),d_T.get(3),1));
	d_In.add(new ArcIn(d_P.get(5),d_T.get(1),1));
	d_In.add(new ArcIn(d_P.get(6),d_T.get(3),1));
	d_Out.add(new ArcOut(d_T.get(0),d_P.get(1),1));
	d_Out.add(new ArcOut(d_T.get(1),d_P.get(2),1));
	d_Out.add(new ArcOut(d_T.get(2),d_P.get(3),1));
	d_Out.add(new ArcOut(d_T.get(3),d_P.get(4),1));
	d_Out.add(new ArcOut(d_T.get(1),d_P.get(5),1));
	d_Out.add(new ArcOut(d_T.get(3),d_P.get(6),1));
        
        
	PetriNet d_Net = new PetriNet("Consistency",d_P,d_T,d_In,d_Out);
	PetriP.initNext();
	PetriT.initNext();
	ArcIn.initNext();
	ArcOut.initNext();

	return d_Net;
}*/

    public static PetriNet CreateNetFriend() throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
        double delay = 100.0;
        double x = 0.00000001;
        ArrayList<PetriP> d_P = new ArrayList<>();
        ArrayList<PetriT> d_T = new ArrayList<>();
        ArrayList<ArcIn> d_In = new ArrayList<>();
        ArrayList<ArcOut> d_Out = new ArrayList<>();
        Random r = new Random();
        d_P.add(new PetriP("bow[", 0));
        d_P.add(new PetriP("P2", 0));
        d_P.add(new PetriP("lock", 1));
        d_P.add(new PetriP("P4", 0));
        d_P.add(new PetriP("P5", 0));
        d_P.add(new PetriP("P6", 0));
        d_P.add(new PetriP("failure++", 0));
        d_P.add(new PetriP("lockOther", 1));
        d_P.add(new PetriP("bowA++", 0));
        d_P.add(new PetriP("P10", 0));
        d_P.add(new PetriP("bowB++", 0));
        d_P.add(new PetriP("P15", 0));
        d_P.add(new PetriP("bowLoop[", 10));
        d_P.add(new PetriP("bow]", 0));
        d_P.add(new PetriP("Core", 1));
        d_T.add(new PetriT("imp[", delay * x));// was delay
        d_T.get(0).setDistribution("norm", d_T.get(0).getTimeServ() * 0.1);
        d_T.add(new PetriT("tryLockA", delay * x, 1));//priority = 1
        d_T.get(1).setDistribution("norm", d_T.get(1).getTimeServ() * 0.1);
        d_T.add(new PetriT("0&?", 0.0));
        d_T.add(new PetriT("tryLockB", delay * x, 1));//priority = 1
        d_T.get(3).setDistribution("norm", d_T.get(3).getTimeServ() * 0.1);
        d_T.add(new PetriT("bowBack[]", delay)); //delay*x
        d_T.get(4).setDistribution("norm", d_T.get(4).getTimeServ() * 0.1);
        d_T.add(new PetriT("unlockA", 0.0));
        d_T.add(new PetriT("0&1", 0.0, 1)); //priority = 1
        d_T.add(new PetriT("failure", 0.0));
        d_T.add(new PetriT("unlockAB", 0.0));
        d_T.add(new PetriT("unlockB", 0.0));
        d_T.add(new PetriT("for[", delay)); //sleep
        d_T.get(10).setDistribution("norm", d_T.get(10).getTimeServ() * 0.1);
        d_T.add(new PetriT("for]", 0.0 + r.nextDouble()));
        d_In.add(new ArcIn(d_P.get(0), d_T.get(0), 1));
        d_In.add(new ArcIn(d_P.get(1), d_T.get(1), 1));
        d_In.add(new ArcIn(d_P.get(1), d_T.get(2), 1));
        d_In.add(new ArcIn(d_P.get(2), d_T.get(1), 1));
        d_In.add(new ArcIn(d_P.get(3), d_T.get(3), 1));
        d_In.add(new ArcIn(d_P.get(7), d_T.get(3), 1));
        d_In.add(new ArcIn(d_P.get(5), d_T.get(4), 1));
        d_In.add(new ArcIn(d_P.get(3), d_T.get(5), 1));
        d_In.add(new ArcIn(d_P.get(4), d_T.get(6), 1));
        d_In.add(new ArcIn(d_P.get(4), d_T.get(7), 1));
        d_In.add(new ArcIn(d_P.get(7), d_T.get(6), 1));
        d_In.add(new ArcIn(d_P.get(9), d_T.get(8), 1));
        d_In.add(new ArcIn(d_P.get(11), d_T.get(9), 1));
        d_In.add(new ArcIn(d_P.get(12), d_T.get(10), 1));
        d_In.add(new ArcIn(d_P.get(13), d_T.get(11), 1));
        d_In.add(new ArcIn(d_P.get(14), d_T.get(10), 1));
        d_Out.add(new ArcOut(d_T.get(0), d_P.get(1), 1));
        d_Out.add(new ArcOut(d_T.get(1), d_P.get(3), 1));
        d_Out.add(new ArcOut(d_T.get(2), d_P.get(4), 1));
        d_Out.add(new ArcOut(d_T.get(3), d_P.get(5), 1));
        d_Out.add(new ArcOut(d_T.get(5), d_P.get(2), 1));
        d_Out.add(new ArcOut(d_T.get(4), d_P.get(9), 1));
        d_Out.add(new ArcOut(d_T.get(7), d_P.get(6), 1));
        d_Out.add(new ArcOut(d_T.get(3), d_P.get(8), 1));
        d_Out.add(new ArcOut(d_T.get(4), d_P.get(10), 1));
        d_Out.add(new ArcOut(d_T.get(8), d_P.get(2), 1));
        d_Out.add(new ArcOut(d_T.get(8), d_P.get(7), 1));
        d_Out.add(new ArcOut(d_T.get(6), d_P.get(11), 1));
        d_Out.add(new ArcOut(d_T.get(9), d_P.get(7), 1));
        d_Out.add(new ArcOut(d_T.get(10), d_P.get(0), 1));
        d_Out.add(new ArcOut(d_T.get(8), d_P.get(13), 1));
        d_Out.add(new ArcOut(d_T.get(11), d_P.get(14), 1));
        d_Out.add(new ArcOut(d_T.get(5), d_P.get(13), 1));
        d_Out.add(new ArcOut(d_T.get(9), d_P.get(13), 1));
        d_Out.add(new ArcOut(d_T.get(7), d_P.get(13), 1));
        d_Out.add(new ArcOut(d_T.get(9), d_P.get(6), 1));
        d_Out.add(new ArcOut(d_T.get(5), d_P.get(6), 1));
        PetriNet d_Net = new PetriNet("Friend ", d_P, d_T, d_In, d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
    }

/*pblic static PetriNet CreateNetFriendUsingCores() throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
	double delay = 100.0;
        double x=0.8;
        ArrayList<PetriP> d_P = new ArrayList<>();
	ArrayList<PetriT> d_T = new ArrayList<>();
	ArrayList<ArcIn> d_In = new ArrayList<>();
	ArrayList<ArcOut> d_Out = new ArrayList<>();
        Random r = new Random();
	d_P.add(new PetriP("bow[",0));
	d_P.add(new PetriP("P2",0));
	d_P.add(new PetriP("lock",1));
	d_P.add(new PetriP("P4",0));
	d_P.add(new PetriP("P5",0));
	d_P.add(new PetriP("P6",0));
	d_P.add(new PetriP("failure++",0));
	d_P.add(new PetriP("lockOther",1));
	d_P.add(new PetriP("bowA++",0));
	d_P.add(new PetriP("P10",0));
	d_P.add(new PetriP("bowB++",0));
	d_P.add(new PetriP("P15",0));
	d_P.add(new PetriP("bowLoop[",10));
	d_P.add(new PetriP("bow]",0));
	d_P.add(new PetriP("Core",10));
	d_T.add(new PetriT("imp[",delay*x));// was delay
        d_T.get(0).setDistribution("norm", d_T.get(0).getTimeServ()*0.1);
	d_T.add(new PetriT("tryLockA",delay*x,1));//priority = 1
	d_T.get(1).setDistribution("norm", d_T.get(1).getTimeServ()*0.1);
        d_T.add(new PetriT("0&?",0.0));
	d_T.add(new PetriT("tryLockB",delay*x,1));//priority = 1
	d_T.get(3).setDistribution("norm", d_T.get(3).getTimeServ()*0.1);
        d_T.add(new PetriT("bowBack[]",delay)); //delay*x
	d_T.get(4).setDistribution("norm", d_T.get(4).getTimeServ()*0.1);
        d_T.add(new PetriT("unlockA",0.0));
	d_T.add(new PetriT("0&1",0.0,1)); //priority = 1
	d_T.add(new PetriT("failure",0.0));
	d_T.add(new PetriT("unlockAB",0.0));
	d_T.add(new PetriT("unlockB",0.0));
	d_T.add(new PetriT("for[",delay)); //sleep
	d_T.get(10).setDistribution("norm", d_T.get(10).getTimeServ()*0.1);
        d_T.add(new PetriT("for]",0.0+r.nextDouble()));
	d_In.add(new ArcIn(d_P.get(0),d_T.get(0),1));
	d_In.add(new ArcIn(d_P.get(1),d_T.get(1),1));
	d_In.add(new ArcIn(d_P.get(1),d_T.get(2),1));
	d_In.add(new ArcIn(d_P.get(2),d_T.get(1),1));
	d_In.add(new ArcIn(d_P.get(3),d_T.get(3),1));
	d_In.add(new ArcIn(d_P.get(7),d_T.get(3),1));
	d_In.add(new ArcIn(d_P.get(5),d_T.get(4),1));
	d_In.add(new ArcIn(d_P.get(3),d_T.get(5),1));
	d_In.add(new ArcIn(d_P.get(4),d_T.get(6),1));
	d_In.add(new ArcIn(d_P.get(4),d_T.get(7),1));
	d_In.add(new ArcIn(d_P.get(7),d_T.get(6),1));
	d_In.add(new ArcIn(d_P.get(9),d_T.get(8),1));
	d_In.add(new ArcIn(d_P.get(11),d_T.get(9),1));
	d_In.add(new ArcIn(d_P.get(12),d_T.get(10),1));
	d_In.add(new ArcIn(d_P.get(13),d_T.get(11),1));
	d_In.add(new ArcIn(d_P.get(14),d_T.get(10),1));
	d_Out.add(new ArcOut(d_T.get(0),d_P.get(1),1));
	d_Out.add(new ArcOut(d_T.get(1),d_P.get(3),1));
	d_Out.add(new ArcOut(d_T.get(2),d_P.get(4),1));
	d_Out.add(new ArcOut(d_T.get(3),d_P.get(5),1));
	d_Out.add(new ArcOut(d_T.get(5),d_P.get(2),1));
	d_Out.add(new ArcOut(d_T.get(4),d_P.get(9),1));
	d_Out.add(new ArcOut(d_T.get(7),d_P.get(6),1));
	d_Out.add(new ArcOut(d_T.get(3),d_P.get(8),1));
	d_Out.add(new ArcOut(d_T.get(4),d_P.get(10),1));
	d_Out.add(new ArcOut(d_T.get(8),d_P.get(2),1));
	d_Out.add(new ArcOut(d_T.get(8),d_P.get(7),1));
	d_Out.add(new ArcOut(d_T.get(6),d_P.get(11),1));
	d_Out.add(new ArcOut(d_T.get(9),d_P.get(7),1));
	d_Out.add(new ArcOut(d_T.get(10),d_P.get(0),1));
	d_Out.add(new ArcOut(d_T.get(8),d_P.get(13),1));
	d_Out.add(new ArcOut(d_T.get(11),d_P.get(14),1));
	d_Out.add(new ArcOut(d_T.get(5),d_P.get(13),1));
	d_Out.add(new ArcOut(d_T.get(9),d_P.get(13),1));
	d_Out.add(new ArcOut(d_T.get(7),d_P.get(13),1));
	d_Out.add(new ArcOut(d_T.get(9),d_P.get(6),1));
	d_Out.add(new ArcOut(d_T.get(5),d_P.get(6),1));

	PetriNet d_Net = new PetriNet("Friend ",d_P,d_T,d_In,d_Out);
	PetriP.initNext();
	PetriT.initNext();
	ArcIn.initNext();
	ArcOut.initNext();

	return d_Net;
}*/


/*pblic static PetriNet CreateNetNewPool() throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
	ArrayList<PetriP> d_P = new ArrayList<>();
	ArrayList<PetriT> d_T = new ArrayList<>();
	ArrayList<ArcIn> d_In = new ArrayList<>();
	ArrayList<ArcOut> d_Out = new ArrayList<>();
	d_P.add(new PetriP("P1",1));
	d_P.add(new PetriP("P2",0));
	d_P.add(new PetriP("P3",0));
	d_P.add(new PetriP("tasksQueue",0));
	d_P.add(new PetriP("P5",0));
	d_P.add(new PetriP("end",0));
	d_P.add(new PetriP("P7",0));
	d_P.add(new PetriP("numWorkers",0));
	d_T.add(new PetriT("T1",0.1));
	d_T.add(new PetriT("newRunnable",0.1));
	d_T.add(new PetriT("invoke",0.1));
	d_T.add(new PetriT("run",0.2));
	d_T.add(new PetriT("T5",0.1));
	d_In.add(new ArcIn(d_P.get(0),d_T.get(0),1));
	d_In.add(new ArcIn(d_P.get(1),d_T.get(1),1));
	d_In.add(new ArcIn(d_P.get(2),d_T.get(2),1));
	d_In.add(new ArcIn(d_P.get(3),d_T.get(3),1));
	d_In.add(new ArcIn(d_P.get(4),d_T.get(4),5));
	d_In.add(new ArcIn(d_P.get(6),d_T.get(3),1));
	d_In.add(new ArcIn(d_P.get(7),d_T.get(1),1));
	d_Out.add(new ArcOut(d_T.get(0),d_P.get(6),2));
	d_Out.add(new ArcOut(d_T.get(0),d_P.get(7),5));
	d_Out.add(new ArcOut(d_T.get(0),d_P.get(1),1));
	d_Out.add(new ArcOut(d_T.get(1),d_P.get(2),1));
	d_Out.add(new ArcOut(d_T.get(2),d_P.get(3),1));
	d_Out.add(new ArcOut(d_T.get(3),d_P.get(4),1));
	d_Out.add(new ArcOut(d_T.get(4),d_P.get(5),1));
	d_Out.add(new ArcOut(d_T.get(3),d_P.get(6),1));
	d_Out.add(new ArcOut(d_T.get(1),d_P.get(1),1));
	PetriNet d_Net = new PetriNet("NewPool",d_P,d_T,d_In,d_Out);
	PetriP.initNext();
	PetriT.initNext();
	ArcIn.initNext();
	ArcOut.initNext();

	return d_Net;
}

pblic static PetriNet CreateNetThreadStartAndEnd() throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
	ArrayList<PetriP> d_P = new ArrayList<>();
	ArrayList<PetriT> d_T = new ArrayList<>();
	ArrayList<ArcIn> d_In = new ArrayList<>();
	ArrayList<ArcOut> d_Out = new ArrayList<>();
	d_P.add(new PetriP("P1",1));
	d_P.add(new PetriP("P2",0));
	d_P.add(new PetriP("P3",0));
	d_P.add(new PetriP("P4",0));
	d_P.add(new PetriP("P5",0));
	d_P.add(new PetriP("P6",0));
	d_P.add(new PetriP("P7",0));
	d_P.add(new PetriP("P8",0));
	d_P.add(new PetriP("P9",0));
	d_T.add(new PetriT("new",0.1));
	d_T.add(new PetriT("start",0.1));
	d_T.add(new PetriT("runStart",0.1));
	d_T.add(new PetriT("runFinish",0.1));
	d_T.add(new PetriT("end",0.1));
	d_In.add(new ArcIn(d_P.get(0),d_T.get(0),1));
	d_In.add(new ArcIn(d_P.get(1),d_T.get(1),1));
	d_In.add(new ArcIn(d_P.get(3),d_T.get(2),1));
	d_In.add(new ArcIn(d_P.get(5),d_T.get(3),1));
	d_In.add(new ArcIn(d_P.get(6),d_T.get(4),1));
	d_In.add(new ArcIn(d_P.get(7),d_T.get(4),1));
	d_Out.add(new ArcOut(d_T.get(0),d_P.get(1),1));
	d_Out.add(new ArcOut(d_T.get(1),d_P.get(2),1));
	d_Out.add(new ArcOut(d_T.get(1),d_P.get(3),1));
	d_Out.add(new ArcOut(d_T.get(2),d_P.get(4),1));
	d_Out.add(new ArcOut(d_T.get(3),d_P.get(6),1));
	d_Out.add(new ArcOut(d_T.get(4),d_P.get(8),1));
	PetriNet d_Net = new PetriNet("ThreadStartAndEnd",d_P,d_T,d_In,d_Out);
	PetriP.initNext();
	PetriT.initNext();
	ArcIn.initNext();
	ArcOut.initNext();

	return d_Net;
}*/

    public static PetriNet CreateNetTestInfArc() throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
        ArrayList<PetriP> d_P = new ArrayList<>();
        ArrayList<PetriT> d_T = new ArrayList<>();
        ArrayList<ArcIn> d_In = new ArrayList<>();
        ArrayList<ArcOut> d_Out = new ArrayList<>();
        d_P.add(new PetriP("P1", 1));
        d_P.add(new PetriP("P2", 1));
        d_P.add(new PetriP("P3", 0));
        d_P.add(new PetriP("P4", 0));
        d_T.add(new PetriT("T1", 1.0));
        d_In.add(new ArcIn(d_P.get(0), d_T.get(0), 1));
        d_In.add(new ArcIn(d_P.get(2), d_T.get(0), 1));
        d_In.get(1).setInf(true);
        d_In.add(new ArcIn(d_P.get(1), d_T.get(0), 1));
        d_Out.add(new ArcOut(d_T.get(0), d_P.get(1), 1));
        d_Out.add(new ArcOut(d_T.get(0), d_P.get(3), 1));
        PetriNet d_Net = new PetriNet("TestInfArc", d_P, d_T, d_In, d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
    }

    public static PetriNet CreateNetTestStatistics() throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
        ArrayList<PetriP> d_P = new ArrayList<>();
        ArrayList<PetriT> d_T = new ArrayList<>();
        ArrayList<ArcIn> d_In = new ArrayList<>();
        ArrayList<ArcOut> d_Out = new ArrayList<>();
        d_P.add(new PetriP("P1", 100));
        d_P.add(new PetriP("P2", 1));
        d_P.add(new PetriP("P3", 0));
        d_P.add(new PetriP("P4", 0));
        d_P.add(new PetriP("P5", 1));
        d_T.add(new PetriT("T1", 10.0));
        d_T.add(new PetriT("T2", 5.0));
        d_In.add(new ArcIn(d_P.get(0), d_T.get(0), 1));
        d_In.add(new ArcIn(d_P.get(1), d_T.get(0), 1));
        d_In.add(new ArcIn(d_P.get(2), d_T.get(1), 1));
        d_In.add(new ArcIn(d_P.get(4), d_T.get(1), 1));
        d_Out.add(new ArcOut(d_T.get(0), d_P.get(1), 1));
        d_Out.add(new ArcOut(d_T.get(0), d_P.get(2), 1));
        d_Out.add(new ArcOut(d_T.get(1), d_P.get(3), 1));
        d_Out.add(new ArcOut(d_T.get(1), d_P.get(4), 1));
        PetriNet d_Net = new PetriNet("TestStatistics", d_P, d_T, d_In, d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
    }

    public static PetriNet CreateNetTask(double a) throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
        ArrayList<PetriP> d_P = new ArrayList<>();
        ArrayList<PetriT> d_T = new ArrayList<>();
        ArrayList<ArcIn> d_In = new ArrayList<>();
        ArrayList<ArcOut> d_Out = new ArrayList<>();
        d_P.add(new PetriP("P1", 1000));
        d_P.add(new PetriP("P2", 0));
        d_P.add(new PetriP("Resource", 0));
        d_T.add(new PetriT("T1", a));
        d_In.add(new ArcIn(d_P.get(0), d_T.get(0), 1));
        d_Out.add(new ArcOut(d_T.get(0), d_P.get(1), 1));
        for (PetriT tr : d_T) {
            d_In.add(new ArcIn(d_P.get(2), tr, 1));
            d_Out.add(new ArcOut(tr, d_P.get(2), 1));

        }
        PetriNet d_Net = new PetriNet("Task", d_P, d_T, d_In, d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
    }


    public static PetriNet CreateNetGeneratorInf() throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
        ArrayList<PetriP> d_P = new ArrayList<>();
        ArrayList<PetriT> d_T = new ArrayList<>();
        ArrayList<ArcIn> d_In = new ArrayList<>();
        ArrayList<ArcOut> d_Out = new ArrayList<>();
        d_P.add(new PetriP("P1", 1));
        d_P.add(new PetriP("P2", 0));
        d_P.add(new PetriP("P1", 0));
        d_T.add(new PetriT("T1", 2.0));
        d_T.get(0).setDistribution("exp", d_T.get(0).getTimeServ());
        d_T.get(0).setParamDeviation(0.0);
        d_In.add(new ArcIn(d_P.get(0), d_T.get(0), 1));
        d_In.add(new ArcIn(d_P.get(2), d_T.get(0), 1));
        d_In.get(1).setInf(true);
        d_Out.add(new ArcOut(d_T.get(0), d_P.get(1), 1));
        d_Out.add(new ArcOut(d_T.get(0), d_P.get(0), 1));
        PetriNet d_Net = new PetriNet("Generator", d_P, d_T, d_In, d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
    }

    public static PetriNet CreateNetSimple() throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
        ArrayList<PetriP> d_P = new ArrayList<>();
        ArrayList<PetriT> d_T = new ArrayList<>();
        ArrayList<ArcIn> d_In = new ArrayList<>();
        ArrayList<ArcOut> d_Out = new ArrayList<>();
        d_P.add(new PetriP("P1", 100));
        d_P.add(new PetriP("P2", 0));
        d_T.add(new PetriT("T1", 2.0));
        d_In.add(new ArcIn(d_P.get(0), d_T.get(0), 1));
        d_Out.add(new ArcOut(d_T.get(0), d_P.get(1), 1));
        PetriNet d_Net = new PetriNet("Simple", d_P, d_T, d_In, d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
    }

    public static PetriNet CreateNetSMOgroup(int numInGroup, int numChannel, double timeMean, String name) throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
        ArrayList<PetriP> d_P = new ArrayList<>();
        ArrayList<PetriT> d_T = new ArrayList<>();
        ArrayList<ArcIn> d_In = new ArrayList<>();
        ArrayList<ArcOut> d_Out = new ArrayList<>();
        d_P.add(new PetriP("P0", 0));
        for (int j = 0; j < numInGroup; j++) {
            d_P.add(new PetriP("P" + (2 * j + 1), numChannel));
            d_P.add(new PetriP("P" + (2 * j + 2), 0));
            d_T.add(new PetriT("T" + (j), timeMean));
            d_T.get(j).setDistribution("exp", d_T.get(j).getTimeServ());
            d_T.get(j).setParamDeviation(0.0);
            d_In.add(new ArcIn(d_P.get(2 * j), d_T.get(j), 1));
            d_In.add(new ArcIn(d_P.get(2 * j + 1), d_T.get(j), 1));
            d_Out.add(new ArcOut(d_T.get(j), d_P.get(2 * j + 1), 1));
            d_Out.add(new ArcOut(d_T.get(j), d_P.get(2 * j + 2), 1));
        }
        PetriNet d_Net = new PetriNet(name, d_P, d_T, d_In, d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
    }


    public static PetriNet CreateNetLab6Task2() throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
        PetriP toArrivePlace = new PetriP("ToArrive", 1);
        PetriP arrivedPlace = new PetriP("Arrived", 0);
        PetriP device1AvailablePlace = new PetriP("Device1Available", 1);
        PetriP device2AvailablePlace = new PetriP("Device2Available", 1);
        PetriP device3AvailablePlace = new PetriP("Device3Available", 1);
        PetriP device4AvailablePlace = new PetriP("Device4Available", 1);
        PetriP device5AvailablePlace = new PetriP("Device5Available", 1);
        PetriP nearDevice2Place = new PetriP("NearDevice2", 0);
        PetriP nearDevice3Place = new PetriP("NearDevice3", 0);
        PetriP nearDevice4Place = new PetriP("NearDevice4", 0);
        PetriP nearDevice5Place = new PetriP("NearDevice5", 0);
        PetriP allProcessedPlace = new PetriP("AllProcessed", 0);

        PetriT arrivalTransition = new PetriT("Arrival", 1.0);
        arrivalTransition.setMoments(true);
        PetriT device1Transition = new PetriT("Device1", 1.0);
        device1Transition.setDistribution("exp", device1Transition.getTimeServ());
        device1Transition.setParamDeviation(0.0);
        device1Transition.setPriority(9);
        device1Transition.setMoments(true);
        PetriT device2Transition = new PetriT("Device2", 1.0);
        device2Transition.setDistribution("exp", device2Transition.getTimeServ());
        device2Transition.setParamDeviation(0.0);
        device2Transition.setPriority(9);
        device2Transition.setMoments(true);
        PetriT device3Transition = new PetriT("Device3", 1.0);
        device3Transition.setDistribution("exp", device3Transition.getTimeServ());
        device3Transition.setParamDeviation(0.0);
        device3Transition.setPriority(9);
        device3Transition.setMoments(true);
        PetriT device4Transition = new PetriT("Device4", 1.0);
        device4Transition.setDistribution("exp", device4Transition.getTimeServ());
        device4Transition.setParamDeviation(0.0);
        device4Transition.setPriority(9);
        device4Transition.setMoments(true);
        PetriT device5Transition = new PetriT("Device5", 1.0);
        device5Transition.setDistribution("exp", device5Transition.getTimeServ());
        device5Transition.setParamDeviation(0.0);
        device5Transition.setPriority(9);
        device5Transition.setMoments(true);
        PetriT moveToDevice2Transition = new PetriT("MoveToDevice2", 1.0);
        PetriT moveToDevice3Transition = new PetriT("MoveToDevice3", 1.0);
        PetriT moveToDevice4Transition = new PetriT("MoveToDevice4", 1.0);
        PetriT moveToDevice5Transition = new PetriT("MoveToDevice5", 1.0);
        PetriT goBackTransition = new PetriT("GoBack", 5.0);

        ArrayList<ArcIn> d_In = new ArrayList<>(Arrays.asList(
                new ArcIn(toArrivePlace, arrivalTransition, 1),
                new ArcIn(arrivedPlace, device1Transition, 1),
                new ArcIn(arrivedPlace, moveToDevice2Transition, 1),
                new ArcIn(device1AvailablePlace, device1Transition, 1),
                new ArcIn(device2AvailablePlace, device2Transition, 1),
                new ArcIn(device3AvailablePlace, device3Transition, 1),
                new ArcIn(device4AvailablePlace, device4Transition, 1),
                new ArcIn(device5AvailablePlace, device5Transition, 1),
                new ArcIn(nearDevice2Place, device2Transition, 1),
                new ArcIn(nearDevice2Place, moveToDevice3Transition, 1),
                new ArcIn(nearDevice3Place, device3Transition, 1),
                new ArcIn(nearDevice3Place, moveToDevice4Transition, 1),
                new ArcIn(nearDevice4Place, device4Transition, 1),
                new ArcIn(nearDevice4Place, moveToDevice5Transition, 1),
                new ArcIn(nearDevice5Place, device5Transition, 1),
                new ArcIn(nearDevice5Place, goBackTransition, 1)
        ));

        ArrayList<ArcOut> d_Out = new ArrayList<>(Arrays.asList(
                new ArcOut(arrivalTransition, arrivedPlace, 4),
                new ArcOut(arrivalTransition, toArrivePlace, 1),
                new ArcOut(device1Transition, device1AvailablePlace, 1),
                new ArcOut(device1Transition, allProcessedPlace, 1),
                new ArcOut(device2Transition, device2AvailablePlace, 1),
                new ArcOut(device2Transition, allProcessedPlace, 1),
                new ArcOut(device3Transition, device3AvailablePlace, 1),
                new ArcOut(device3Transition, allProcessedPlace, 1),
                new ArcOut(device4Transition, device4AvailablePlace, 1),
                new ArcOut(device4Transition, allProcessedPlace, 1),
                new ArcOut(device5Transition, device5AvailablePlace, 1),
                new ArcOut(device5Transition, allProcessedPlace, 1),
                new ArcOut(moveToDevice2Transition, nearDevice2Place, 1),
                new ArcOut(moveToDevice3Transition, nearDevice3Place, 1),
                new ArcOut(moveToDevice4Transition, nearDevice4Place, 1),
                new ArcOut(moveToDevice5Transition, nearDevice5Place, 1),
                new ArcOut(goBackTransition, arrivedPlace, 1)
        ));

        ArrayList<PetriP> d_P = new ArrayList<>(Arrays.asList(
                toArrivePlace,
                arrivedPlace,
                device1AvailablePlace,
                device2AvailablePlace,
                device3AvailablePlace,
                device4AvailablePlace,
                device5AvailablePlace,
                nearDevice2Place,
                nearDevice3Place,
                nearDevice4Place,
                nearDevice5Place,
                allProcessedPlace
        ));

        ArrayList<PetriT> d_T = new ArrayList<>(Arrays.asList(
                arrivalTransition,
                device1Transition,
                device2Transition,
                device3Transition,
                device4Transition,
                device5Transition,
                moveToDevice2Transition,
                moveToDevice3Transition,
                moveToDevice4Transition,
                moveToDevice5Transition,
                goBackTransition
        ));


        PetriNet d_Net = new PetriNet("Lab6Task2", d_P, d_T, d_In, d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
    }

    public static PetriNet CreateNetLab6Task3() throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
        PetriP toArrivePlace = new PetriP("ToArrive", 1);
        PetriP newOrdersPlace = new PetriP("NewOrders", 0);
        PetriP lostCustomersPlace = new PetriP("LostCustomers", 0);
        PetriP waitingForSupplyPlace = new PetriP("WaitingForSupply", 0);
        PetriP successfulOrdersPlace = new PetriP("SuccessfulOrders", 0);
        PetriP storagePlace = new PetriP("Storage", 72);
        PetriP toCheckPlaceOrderPlace = new PetriP("ToCheckPlaceOrder", 1);
        PetriP readyToDecidePlace = new PetriP("ReadyToDecide", 0);
        PetriP toPlaceSupplyOrderPlace = new PetriP("PlacedSupplyOrder", 0);
        PetriP placedSupplyOrdersPlace = new PetriP("PlacedSupplyOrders", 0);
        PetriP notPlacedSupplyOrdersPlace = new PetriP("NotPlacedSupplyOrders", 0);
        PetriP currentStatePlace = new PetriP("CurrentState", 72);

        PetriT newOrderArrivalTransition = new PetriT("NewOrderArrival", 0.2);
        newOrderArrivalTransition.setDistribution("exp", newOrderArrivalTransition.getTimeServ());
        newOrderArrivalTransition.setParamDeviation(0.0);
        PetriT buyTransition = new PetriT("Buy", 0.0);
        buyTransition.setPriority(5);
        PetriT leaveTransition = new PetriT("Leave", 0.0);
        leaveTransition.setProbability(0.8);
        leaveTransition.setMoments(true);
        PetriT waitForSupplyTransition = new PetriT("WaitForSupply", 0.0);
        waitForSupplyTransition.setProbability(0.2);
        PetriT buyAfterWaitTransition = new PetriT("BuyAfterWait", 0.0);
        PetriT checkTransition = new PetriT("Check", 4.0);
        PetriT placeSupplyOrderTransition = new PetriT("PlaceSupplyOrder", 0);
        PetriT waitForSupplyOrderTransition = new PetriT("WaitForSupplyOrder", 3.0);
        PetriT doNotPlaceSupplyOrderTransition = new PetriT("DoNotPlaceSupplyOrder", 0.0);
        doNotPlaceSupplyOrderTransition.setPriority(5);

        ArrayList<ArcIn> d_In = new ArrayList<>(Arrays.asList(
                new ArcIn(toArrivePlace, newOrderArrivalTransition, 1),
                new ArcIn(newOrdersPlace, buyTransition, 1),
                new ArcIn(newOrdersPlace, waitForSupplyTransition, 1),
                new ArcIn(newOrdersPlace, leaveTransition, 1),
                new ArcIn(waitingForSupplyPlace, buyAfterWaitTransition, 1),
                new ArcIn(storagePlace, buyAfterWaitTransition, 1),
                new ArcIn(storagePlace, buyTransition, 1),
                new ArcIn(currentStatePlace, waitForSupplyTransition, 1),
                new ArcIn(currentStatePlace, buyTransition, 1),
                new ArcIn(currentStatePlace, doNotPlaceSupplyOrderTransition, 19, true),
                new ArcIn(toCheckPlaceOrderPlace, checkTransition, 1),
                new ArcIn(toPlaceSupplyOrderPlace, waitForSupplyOrderTransition, 1),
                new ArcIn(readyToDecidePlace, placeSupplyOrderTransition, 1),
                new ArcIn(readyToDecidePlace, doNotPlaceSupplyOrderTransition, 1)
        ));

        ArrayList<ArcOut> d_Out = new ArrayList<>(Arrays.asList(
                new ArcOut(newOrderArrivalTransition, toArrivePlace, 1),
                new ArcOut(newOrderArrivalTransition, newOrdersPlace, 1),
                new ArcOut(leaveTransition, lostCustomersPlace, 1),
                new ArcOut(buyTransition, successfulOrdersPlace, 1),
                new ArcOut(waitForSupplyTransition, waitingForSupplyPlace, 1),
                new ArcOut(buyAfterWaitTransition, successfulOrdersPlace, 1),
                new ArcOut(checkTransition, toCheckPlaceOrderPlace, 1),
                new ArcOut(checkTransition, readyToDecidePlace, 1),
                new ArcOut(doNotPlaceSupplyOrderTransition, notPlacedSupplyOrdersPlace, 1),
                new ArcOut(placeSupplyOrderTransition, placedSupplyOrdersPlace, 1),
                new ArcOut(placeSupplyOrderTransition, toPlaceSupplyOrderPlace, 1),
                new ArcOut(placeSupplyOrderTransition, currentStatePlace, 72),
                new ArcOut(waitForSupplyOrderTransition, storagePlace, 72)
        ));


        ArrayList<PetriP> d_P = new ArrayList<>(Arrays.asList(
                toArrivePlace,
                newOrdersPlace,
                lostCustomersPlace,
                waitingForSupplyPlace,
                successfulOrdersPlace,
                storagePlace,
                toCheckPlaceOrderPlace,
                readyToDecidePlace,
                toPlaceSupplyOrderPlace,
                placedSupplyOrdersPlace,
                notPlacedSupplyOrdersPlace,
                currentStatePlace
        ));

        ArrayList<PetriT> d_T = new ArrayList<>(Arrays.asList(
                newOrderArrivalTransition,
                buyTransition,
                leaveTransition,
                waitForSupplyTransition,
                buyAfterWaitTransition,
                checkTransition,
                placeSupplyOrderTransition,
                waitForSupplyOrderTransition,
                doNotPlaceSupplyOrderTransition
        ));


        PetriNet d_Net = new PetriNet("Lab6Task3", d_P, d_T, d_In, d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
    }
}