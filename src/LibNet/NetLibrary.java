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
        PetriT goBackTransition = new PetriT("GoBack", 1.0);

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
        PetriT waitForSupplyOrderTransition = new PetriT("WaitForSupplyOrder", 0.5);
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

    public static PetriNet CreateNetLab6Task4() throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
        PetriP toArrivePlace = new PetriP("ToArrive", 1);
        PetriP arrivedPlace = new PetriP("Arrived", 0);
        PetriP requestsToMainServerPlace = new PetriP("RequestsToMainServer", 0);
        PetriP requestsToServerPlace = new PetriP("RequestsToServer", 0);
        PetriP processedMainServerPlace = new PetriP("ProcessedMainServer", 0);
        PetriP processedServerPlace = new PetriP("ProcessedServer", 0);

        PetriT arrivalTransition = new PetriT("Arrival", 0.1);
        arrivalTransition.setDistribution("exp", arrivalTransition.getTimeServ());
        arrivalTransition.setParamDeviation(0.0);
        PetriT toMainServerTransition = new PetriT("ToMainServer", 0.0);
        toMainServerTransition.setProbability(0.7);
        PetriT toServerTransition = new PetriT("ToServer", 0.0);
        toServerTransition.setProbability(0.3);
        PetriT processMainServerTransition = new PetriT("ProcessMainServer", 0.2);
        processMainServerTransition.setDistribution("exp", processMainServerTransition.getTimeServ());
        processMainServerTransition.setParamDeviation(0.0);
        PetriT processServerTransition = new PetriT("ProcessServer", 0.3);
        processServerTransition.setDistribution("exp", processServerTransition.getTimeServ());
        processServerTransition.setParamDeviation(0.0);

        ArrayList<ArcIn> d_In = new ArrayList<>(Arrays.asList(
                new ArcIn(toArrivePlace, arrivalTransition, 1),
                new ArcIn(arrivedPlace, toMainServerTransition, 1),
                new ArcIn(arrivedPlace, toServerTransition, 1),
                new ArcIn(requestsToMainServerPlace, processMainServerTransition, 1),
                new ArcIn(requestsToServerPlace, processServerTransition, 1)
        ));

        ArrayList<ArcOut> d_Out = new ArrayList<>(Arrays.asList(
                new ArcOut(arrivalTransition, arrivedPlace, 1),
                new ArcOut(arrivalTransition, toArrivePlace, 1),
                new ArcOut(toMainServerTransition, requestsToMainServerPlace, 1),
                new ArcOut(toServerTransition, requestsToServerPlace, 1),
                new ArcOut(processMainServerTransition, processedMainServerPlace, 1),
                new ArcOut(processServerTransition, processedServerPlace, 1)
        ));

        ArrayList<PetriP> d_P = new ArrayList<>(Arrays.asList(
                toArrivePlace,
                arrivedPlace,
                requestsToMainServerPlace,
                requestsToServerPlace,
                processedMainServerPlace,
                processedServerPlace
        ));

        ArrayList<PetriT> d_T = new ArrayList<>(Arrays.asList(
                arrivalTransition,
                toMainServerTransition,
                toServerTransition,
                processMainServerTransition,
                processServerTransition
        ));

        PetriNet d_Net = new PetriNet("Lab6Task4", d_P, d_T, d_In, d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
    }

    public static PetriNet CreateNetCoursework() throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
        //shared places
        PetriP passengersUpPlace = new PetriP("PassengersUp", 0);
        PetriP passengersDownPlace = new PetriP("PassengersDown", 0);
        PetriP passengersTo1FloorPlace = new PetriP("PassengersTo1Floor", 0);
        PetriP passengersTo2FloorPlace = new PetriP("PassengersTo2Floor", 0);
        PetriP passengersTo3FloorPlace = new PetriP("PassengersTo3Floor", 0);
        PetriP passengersTo4FloorPlace = new PetriP("PassengersTo4Floor", 0);
        PetriP passengersTo5FloorPlace = new PetriP("PassengersTo5Floor", 0);
        PetriP availablePlacesPlace = new PetriP("AvailablePlaces", 6);
        PetriP directionUpPlace = new PetriP("DirectionUp", 1);
        PetriP directionDownPlace = new PetriP("DirectionDown", 0);


        //1 floor
        PetriP toArrive1FloorPlace = new PetriP("ToArrive1Floor", 1);
        PetriP waiting1FloorPlace = new PetriP("Waiting1Floor", 0);
        PetriP noActiveExit1FloorPlace = new PetriP("NoActiveExit1Floor", 1);
        PetriP noActiveEnter1FloorPlace = new PetriP("NoActiveEnter1Floor", 1);

        PetriT arrive1FloorTransition = new PetriT("Arrive1Floor", 1.0);
        arrive1FloorTransition.setDistribution("exp", arrive1FloorTransition.getTimeServ());
        arrive1FloorTransition.setParamDeviation(0.0);
        PetriT enterToMove1To2Transition = new PetriT("EnterToMove1To2", 0.0);
        enterToMove1To2Transition.setProbability(0.25);
        enterToMove1To2Transition.setPriority(5);
        PetriT enterToMove1To3Transition = new PetriT("EnterToMove1To3", 0.0);
        enterToMove1To3Transition.setProbability(0.25);
        enterToMove1To3Transition.setPriority(5);
        PetriT enterToMove1To4Transition = new PetriT("EnterToMove1To4", 0.0);
        enterToMove1To4Transition.setProbability(0.25);
        enterToMove1To4Transition.setPriority(5);
        PetriT enterToMove1To5Transition = new PetriT("EnterToMove1To5", 0.0);
        enterToMove1To5Transition.setProbability(0.25);
        enterToMove1To5Transition.setPriority(5);
        PetriT switchDirectionUpOn1FloorTransition = new PetriT("SwitchDirectionUpOn1Floor", 0.0);


        //1 -> 2 floor
        PetriP elevatorOn1FloorPlace = new PetriP("ElevatorOn1Floor", 1);

        PetriT move1To2Transition = new PetriT("Move1To2", 0.4);
        move1To2Transition.setPriority(3);

        //2 floor
        PetriP peopleOn2FloorPlace = new PetriP("PeopleOn2Floor", 0);
        PetriP removeFromUpOrDownPassengers2FloorPlace = new PetriP("ToRemoveFromUpOrDownPassengers2Floor", 0);
        PetriP removedFromUpPassengers2FloorPlace = new PetriP("RemovedFromUpPassengers2Floor", 0);
        PetriP removedFromDownPassengers2FloorPlace = new PetriP("RemovedFromDown2FloorPassengers", 0);
        PetriP waitingOn2FloorPlace = new PetriP("WaitingOn2Floor", 0);
        PetriP noActiveExit2FloorPlace = new PetriP("NoActiveExit2Floor", 1);
        PetriP noActiveEnter2FloorPlace = new PetriP("NoActiveEnter2Floor", 1);

        PetriT exitOn2FloorTransition = new PetriT("ExitOn2Floor", 0.4);
        exitOn2FloorTransition.setPriority(10);
        PetriT removeFromUpPassengers2FloorTransition = new PetriT("RemoveFromUpPassengers2Floor", 0.0);
        removeFromUpPassengers2FloorTransition.setPriority(5);
        PetriT removeFromDownPassengers2FloorTransition = new PetriT("RemoveFromDown2FloorPassengers", 0.0);
        removeFromDownPassengers2FloorTransition.setPriority(5);
        PetriT spendTimeOn2FloorTransition = new PetriT("SpendTimeOn2Floor", 120.0);
        spendTimeOn2FloorTransition.setDistribution("unif", spendTimeOn2FloorTransition.getTimeServ());
        spendTimeOn2FloorTransition.setParamDeviation(15.0);
        PetriT enterToMove2To1Transition = new PetriT("EnterToMove2To1", 0.0);
        enterToMove2To1Transition.setProbability(0.7);
        enterToMove2To1Transition.setPriority(5);
        PetriT enterToMove2To3Transition = new PetriT("EnterToMove2To3", 0.0);
        enterToMove2To3Transition.setProbability(0.1);
        enterToMove2To3Transition.setPriority(5);
        PetriT enterToMove2To4Transition = new PetriT("EnterToMove2To4", 0.0);
        enterToMove2To4Transition.setProbability(0.1);
        enterToMove2To4Transition.setPriority(5);
        PetriT enterToMove2To5Transition = new PetriT("EnterToMove2To5", 0.0);
        enterToMove2To5Transition.setProbability(0.1);
        enterToMove2To5Transition.setPriority(5);
        PetriT switchDirectionUpOn2FloorTransition = new PetriT("SwitchDirectionUpOn2Floor", 0.0);
        PetriT switchDirectionDownOn2FloorTransition = new PetriT("SwitchDirectionDownOn2Floor", 0.0);

        //2 -> 3 floor && 2 -> 1
        PetriP elevatorOn2FloorPlace = new PetriP("ElevatorOn2Floor", 0);
        PetriT move2To1Transition = new PetriT("Move2To1", 0.4);
        move2To1Transition.setPriority(3);
        PetriT move2To3Transition = new PetriT("Move2To3", 0.4);
        move2To3Transition.setPriority(3);

        // 1 floor
        PetriT exitOn1FloorTransition = new PetriT("ExitOn1Floor", 0.4);
        exitOn1FloorTransition.setPriority(10);
        PetriP removeFromDownPassengers1FloorPlace = new PetriP("ToRemoveFromDownPassengers1Floor", 0);
        PetriP removedFromDownPassengers1FloorPlace = new PetriP("RemovedFromDownPassengers1Floor", 0);

        PetriT removeFromDownPassengers1FloorTransition = new PetriT("RemoveFromDownPassengers1Floor", 0.0);
        removeFromDownPassengers1FloorTransition.setPriority(5);

        //3 floor
        PetriP peopleOn3FloorPlace = new PetriP("PeopleOn3Floor", 0);
        PetriP removeFromUpOrDownPassengers3FloorPlace = new PetriP("ToRemoveFromUpOrDownPassengers3Floor", 0);
        PetriP removedFromUpPassengers3FloorPlace = new PetriP("RemovedFromUpPassengers3Floor", 0);
        PetriP removedFromDownPassengers3FloorPlace = new PetriP("RemovedFromDown3FloorPassengers", 0);
        PetriP waitingOn3FloorPlace = new PetriP("WaitingOn3Floor", 0);
        PetriP noActiveExit3FloorPlace = new PetriP("NoActiveExit3Floor", 1);
        PetriP noActiveEnter3FloorPlace = new PetriP("NoActiveEnter3Floor", 1);

        PetriT exitOn3FloorTransition = new PetriT("ExitOn3Floor", 0.4);
        exitOn3FloorTransition.setPriority(10);
        PetriT removeFromUpPassengers3FloorTransition = new PetriT("RemoveFromUpPassengers3Floor", 0.0);
        removeFromUpPassengers3FloorTransition.setPriority(5);
        PetriT removeFromDownPassengers3FloorTransition = new PetriT("RemoveFromDown3FloorPassengers", 0.0);
        removeFromDownPassengers3FloorTransition.setPriority(5);
        PetriT spendTimeOn3FloorTransition = new PetriT("SpendTimeOn3Floor", 120.0);
        spendTimeOn3FloorTransition.setDistribution("unif", spendTimeOn3FloorTransition.getTimeServ());
        spendTimeOn3FloorTransition.setParamDeviation(15.0);
        PetriT enterToMove3To1Transition = new PetriT("EnterToMove3To1", 0.0);
        enterToMove3To1Transition.setProbability(0.7);
        enterToMove3To1Transition.setPriority(5);
        PetriT enterToMove3To2Transition = new PetriT("EnterToMove3To2", 0.0);
        enterToMove3To2Transition.setProbability(0.1);
        enterToMove3To2Transition.setPriority(5);
        PetriT enterToMove3To4Transition = new PetriT("EnterToMove3To4", 0.0);
        enterToMove3To4Transition.setProbability(0.1);
        enterToMove3To4Transition.setPriority(5);
        PetriT enterToMove3To5Transition = new PetriT("EnterToMove3To5", 0.0);
        enterToMove3To5Transition.setProbability(0.1);
        enterToMove3To5Transition.setPriority(5);
        PetriT switchDirectionUpOn3FloorTransition = new PetriT("SwitchDirectionUpOn3Floor", 0.0);
        PetriT switchDirectionDownOn3FloorTransition = new PetriT("SwitchDirectionDownOn3Floor", 0.0);

        //3 -> 4 && 3 -> 2
        PetriP elevatorOn3FloorPlace = new PetriP("ElevatorOn3Floor", 0);
        PetriT move3To2Transition = new PetriT("Move3To2", 0.4);
        move3To2Transition.setPriority(3);
        PetriT move3To4Transition = new PetriT("Move3To4", 0.4);
        move3To4Transition.setPriority(3);


        //4 floor
        PetriP peopleOn4FloorPlace = new PetriP("PeopleOn4Floor", 0);
        PetriP removeFromUpOrDownPassengers4FloorPlace = new PetriP("ToRemoveFromUpOrDownPassengers4Floor", 0);
        PetriP removedFromUpPassengers4FloorPlace = new PetriP("RemovedFromUpPassengers4Floor", 0);
        PetriP removedFromDownPassengers4FloorPlace = new PetriP("RemovedFromDown4FloorPassengers", 0);
        PetriP waitingOn4FloorPlace = new PetriP("WaitingOn4Floor", 0);
        PetriP noActiveExit4FloorPlace = new PetriP("NoActiveExit4Floor", 1);
        PetriP noActiveEnter4FloorPlace = new PetriP("NoActiveEnter4Floor", 1);

        PetriT exitOn4FloorTransition = new PetriT("ExitOn4Floor", 0.4);
        exitOn4FloorTransition.setPriority(10);
        PetriT removeFromUpPassengers4FloorTransition = new PetriT("RemoveFromUpPassengers4Floor", 0.0);
        removeFromUpPassengers4FloorTransition.setPriority(5);
        PetriT removeFromDownPassengers4FloorTransition = new PetriT("RemoveFromDown4FloorPassengers", 0.0);
        removeFromDownPassengers4FloorTransition.setPriority(5);
        PetriT spendTimeOn4FloorTransition = new PetriT("SpendTimeOn4Floor", 120.0);
        spendTimeOn4FloorTransition.setDistribution("unif", spendTimeOn4FloorTransition.getTimeServ());
        spendTimeOn4FloorTransition.setParamDeviation(15.0);
        PetriT enterToMove4To1Transition = new PetriT("EnterToMove4To1", 0.0);
        enterToMove4To1Transition.setProbability(0.7);
        enterToMove4To1Transition.setPriority(5);
        PetriT enterToMove4To2Transition = new PetriT("EnterToMove4To2", 0.0);
        enterToMove3To2Transition.setProbability(0.1);
        enterToMove4To2Transition.setPriority(5);
        PetriT enterToMove4To3Transition = new PetriT("EnterToMove4To3", 0.0);
        enterToMove4To3Transition.setProbability(0.1);
        enterToMove4To3Transition.setPriority(5);
        PetriT enterToMove4To5Transition = new PetriT("EnterToMove4To5", 0.0);
        enterToMove4To5Transition.setProbability(0.1);
        enterToMove4To5Transition.setPriority(5);
        PetriT switchDirectionUpOn4FloorTransition = new PetriT("SwitchDirectionUpOn4Floor", 0.0);
        PetriT switchDirectionDownOn4FloorTransition = new PetriT("SwitchDirectionDownOn4Floor", 0.0);

        //4 -> 5 && 4 -> 3
        PetriP elevatorOn4FloorPlace = new PetriP("ElevatorOn4Floor", 0);
        PetriT move4To3Transition = new PetriT("Move4To3", 0.4);
        move4To3Transition.setPriority(3);
        PetriT move4To5Transition = new PetriT("Move4To5", 0.4);
        move4To5Transition.setPriority(3);


        //5 floor
        PetriP peopleOn5FloorPlace = new PetriP("PeopleOn5Floor", 0);
        PetriP removeFromUpPassengers5FloorPlace = new PetriP("ToRemoveFromUpPassengers5Floor", 0);
        PetriP removedFromUpPassengers5FloorPlace = new PetriP("RemovedFromUpPassengers5Floor", 0);
        PetriP waitingOn5FloorPlace = new PetriP("WaitingOn5Floor", 0);
        PetriP noActiveExit5FloorPlace = new PetriP("NoActiveExit5Floor", 1);
        PetriP noActiveEnter5FloorPlace = new PetriP("NoActiveEnter5Floor", 1);

        PetriT exitOn5FloorTransition = new PetriT("ExitOn5Floor", 0.4);
        exitOn5FloorTransition.setPriority(10);
        PetriT removeFromUpPassengers5FloorTransition = new PetriT("RemoveFromUpPassengers5Floor", 0.0);
        removeFromUpPassengers5FloorTransition.setPriority(5);
        PetriT spendTimeOn5FloorTransition = new PetriT("SpendTimeOn5Floor", 120.0);
        spendTimeOn5FloorTransition.setDistribution("unif", spendTimeOn5FloorTransition.getTimeServ());
        spendTimeOn5FloorTransition.setParamDeviation(15.0);
        PetriT enterToMove5To1Transition = new PetriT("EnterToMove5To1", 0.0);
        enterToMove5To1Transition.setProbability(0.7);
        enterToMove5To1Transition.setPriority(5);
        PetriT enterToMove5To2Transition = new PetriT("EnterToMove5To2", 0.0);
        enterToMove5To2Transition.setProbability(0.1);
        enterToMove5To2Transition.setPriority(5);
        PetriT enterToMove5To3Transition = new PetriT("EnterToMove5To3", 0.0);
        enterToMove5To3Transition.setProbability(0.1);
        enterToMove5To3Transition.setPriority(5);
        PetriT enterToMove5To4Transition = new PetriT("EnterToMove5To4", 0.0);
        enterToMove5To4Transition.setProbability(0.1);
        enterToMove5To4Transition.setPriority(5);
        PetriT switchDirectionDownOn5FloorTransition = new PetriT("SwitchDirectionDownOn5Floor", 0.0);

        // 5 -> 4
        PetriP elevatorOn5FloorPlace = new PetriP("ElevatorOn5Floor", 0);
        PetriT move5To4Transition = new PetriT("Move5To4", 0.4);
        move5To4Transition.setPriority(3);

        ArrayList<ArcIn> d_In = new ArrayList<>(Arrays.asList(
                //1 floor
                new ArcIn(toArrive1FloorPlace, arrive1FloorTransition, 1),
                new ArcIn(waiting1FloorPlace, enterToMove1To2Transition, 1),
                new ArcIn(waiting1FloorPlace, enterToMove1To3Transition, 1),
                new ArcIn(waiting1FloorPlace, enterToMove1To4Transition, 1),
                new ArcIn(waiting1FloorPlace, enterToMove1To5Transition, 1),
                new ArcIn(availablePlacesPlace, enterToMove1To2Transition, 1),
                new ArcIn(availablePlacesPlace, enterToMove1To3Transition, 1),
                new ArcIn(availablePlacesPlace, enterToMove1To4Transition, 1),
                new ArcIn(availablePlacesPlace, enterToMove1To5Transition, 1),
                new ArcIn(elevatorOn1FloorPlace, enterToMove1To2Transition, 1, true),
                new ArcIn(elevatorOn1FloorPlace, enterToMove1To3Transition, 1, true),
                new ArcIn(elevatorOn1FloorPlace, enterToMove1To4Transition, 1, true),
                new ArcIn(elevatorOn1FloorPlace, enterToMove1To5Transition, 1, true),
                new ArcIn(noActiveExit1FloorPlace, enterToMove1To2Transition, 1, true),
                new ArcIn(noActiveExit1FloorPlace, enterToMove1To3Transition, 1, true),
                new ArcIn(noActiveExit1FloorPlace, enterToMove1To4Transition, 1, true),
                new ArcIn(noActiveExit1FloorPlace, enterToMove1To5Transition, 1, true),
                new ArcIn(noActiveEnter1FloorPlace, enterToMove1To2Transition, 1),
                new ArcIn(noActiveEnter1FloorPlace, enterToMove1To3Transition, 1),
                new ArcIn(noActiveEnter1FloorPlace, enterToMove1To4Transition, 1),
                new ArcIn(noActiveEnter1FloorPlace, enterToMove1To5Transition, 1),
                new ArcIn(elevatorOn1FloorPlace, switchDirectionUpOn1FloorTransition, 1, true),
                new ArcIn(directionDownPlace, switchDirectionUpOn1FloorTransition, 1),
                new ArcIn(noActiveEnter1FloorPlace, switchDirectionUpOn1FloorTransition, 1, true),
                new ArcIn(noActiveExit1FloorPlace, switchDirectionUpOn1FloorTransition, 1, true),
                //1 -> 2 floor
                new ArcIn(elevatorOn1FloorPlace, move1To2Transition, 1),
                new ArcIn(passengersUpPlace, move1To2Transition, 1, true),
                new ArcIn(noActiveExit1FloorPlace, move1To2Transition, 1, true),
                new ArcIn(noActiveEnter1FloorPlace, move1To2Transition, 1, true),
                new ArcIn(directionUpPlace, move1To2Transition, 1, true),
                // 2 floor
                new ArcIn(elevatorOn2FloorPlace, exitOn2FloorTransition, 1, true),
                new ArcIn(passengersTo2FloorPlace, exitOn2FloorTransition, 1),
                new ArcIn(noActiveExit2FloorPlace, exitOn2FloorTransition, 1),
                new ArcIn(peopleOn2FloorPlace, spendTimeOn2FloorTransition, 1),
                new ArcIn(removeFromUpOrDownPassengers2FloorPlace, removeFromUpPassengers2FloorTransition, 1),
                new ArcIn(passengersUpPlace, removeFromUpPassengers2FloorTransition, 1),
                new ArcIn(removeFromUpOrDownPassengers2FloorPlace, removeFromDownPassengers2FloorTransition, 1),
                new ArcIn(passengersDownPlace, removeFromDownPassengers2FloorTransition, 1),
                new ArcIn(directionUpPlace, removeFromUpPassengers2FloorTransition, 1, true),
                new ArcIn(directionDownPlace, removeFromDownPassengers2FloorTransition, 1, true),
                new ArcIn(waitingOn2FloorPlace, enterToMove2To1Transition, 1),
                new ArcIn(waitingOn2FloorPlace, enterToMove2To3Transition, 1),
                new ArcIn(waitingOn2FloorPlace, enterToMove2To4Transition, 1),
                new ArcIn(waitingOn2FloorPlace, enterToMove2To5Transition, 1),
                new ArcIn(availablePlacesPlace, enterToMove2To1Transition, 1),
                new ArcIn(availablePlacesPlace, enterToMove2To3Transition, 1),
                new ArcIn(availablePlacesPlace, enterToMove2To4Transition, 1),
                new ArcIn(availablePlacesPlace, enterToMove2To5Transition, 1),
                new ArcIn(elevatorOn2FloorPlace, enterToMove2To1Transition, 1, true),
                new ArcIn(elevatorOn2FloorPlace, enterToMove2To3Transition, 1, true),
                new ArcIn(elevatorOn2FloorPlace, enterToMove2To4Transition, 1, true),
                new ArcIn(elevatorOn2FloorPlace, enterToMove2To5Transition, 1, true),
                new ArcIn(noActiveExit2FloorPlace, enterToMove2To1Transition, 1, true),
                new ArcIn(noActiveExit2FloorPlace, enterToMove2To3Transition, 1, true),
                new ArcIn(noActiveExit2FloorPlace, enterToMove2To4Transition, 1, true),
                new ArcIn(noActiveExit2FloorPlace, enterToMove2To5Transition, 1, true),
                new ArcIn(noActiveEnter2FloorPlace, enterToMove2To1Transition, 1),
                new ArcIn(noActiveEnter2FloorPlace, enterToMove2To3Transition, 1),
                new ArcIn(noActiveEnter2FloorPlace, enterToMove2To4Transition, 1),
                new ArcIn(noActiveEnter2FloorPlace, enterToMove2To5Transition, 1),
                new ArcIn(elevatorOn2FloorPlace, switchDirectionUpOn2FloorTransition, 1, true),
                new ArcIn(directionDownPlace, switchDirectionUpOn2FloorTransition, 1),
                new ArcIn(noActiveEnter2FloorPlace, switchDirectionUpOn2FloorTransition, 1, true),
                new ArcIn(noActiveExit2FloorPlace, switchDirectionUpOn2FloorTransition, 1, true),
                new ArcIn(elevatorOn2FloorPlace, switchDirectionDownOn2FloorTransition, 1, true),
                new ArcIn(directionUpPlace, switchDirectionDownOn2FloorTransition, 1),
                new ArcIn(noActiveEnter2FloorPlace, switchDirectionDownOn2FloorTransition, 1, true),
                new ArcIn(noActiveExit2FloorPlace, switchDirectionDownOn2FloorTransition, 1, true),
                // 2 -> 1 floor
                new ArcIn(elevatorOn2FloorPlace, move2To1Transition, 1),
                new ArcIn(passengersDownPlace, move2To1Transition, 1, true),
                new ArcIn(noActiveExit2FloorPlace, move2To1Transition, 1, true),
                new ArcIn(noActiveEnter2FloorPlace, move2To1Transition, 1, true),
                new ArcIn(directionDownPlace, move2To1Transition, 1, true),
                // 1 floor
                new ArcIn(elevatorOn1FloorPlace, exitOn1FloorTransition, 1, true),
                new ArcIn(passengersTo1FloorPlace, exitOn1FloorTransition, 1),
                new ArcIn(noActiveExit1FloorPlace, exitOn1FloorTransition, 1),
                new ArcIn(removeFromDownPassengers1FloorPlace, removeFromDownPassengers1FloorTransition, 1),
                new ArcIn(passengersDownPlace, removeFromDownPassengers1FloorTransition, 1),
                // 2 -> 3 floor
                new ArcIn(elevatorOn2FloorPlace, move2To3Transition, 1),
                new ArcIn(passengersUpPlace, move2To3Transition, 1, true),
                new ArcIn(noActiveExit2FloorPlace, move2To3Transition, 1, true),
                new ArcIn(directionUpPlace, move2To3Transition, 1, true),
                //3 floor
                new ArcIn(elevatorOn3FloorPlace, exitOn3FloorTransition, 1, true),
                new ArcIn(passengersTo3FloorPlace, exitOn3FloorTransition, 1),
                new ArcIn(noActiveExit3FloorPlace, exitOn3FloorTransition, 1),
                new ArcIn(peopleOn3FloorPlace, spendTimeOn3FloorTransition, 1),
                new ArcIn(removeFromUpOrDownPassengers3FloorPlace, removeFromUpPassengers3FloorTransition, 1),
                new ArcIn(passengersUpPlace, removeFromUpPassengers3FloorTransition, 1),
                new ArcIn(removeFromUpOrDownPassengers3FloorPlace, removeFromDownPassengers3FloorTransition, 1),
                new ArcIn(passengersDownPlace, removeFromDownPassengers3FloorTransition, 1),
                new ArcIn(directionUpPlace, removeFromUpPassengers3FloorTransition, 1, true),
                new ArcIn(directionDownPlace, removeFromDownPassengers3FloorTransition, 1, true),
                new ArcIn(waitingOn3FloorPlace, enterToMove3To1Transition, 1),
                new ArcIn(waitingOn3FloorPlace, enterToMove3To2Transition, 1),
                new ArcIn(waitingOn3FloorPlace, enterToMove3To4Transition, 1),
                new ArcIn(waitingOn3FloorPlace, enterToMove3To5Transition, 1),
                new ArcIn(availablePlacesPlace, enterToMove3To1Transition, 1),
                new ArcIn(availablePlacesPlace, enterToMove3To2Transition, 1),
                new ArcIn(availablePlacesPlace, enterToMove3To4Transition, 1),
                new ArcIn(availablePlacesPlace, enterToMove3To5Transition, 1),
                new ArcIn(elevatorOn3FloorPlace, enterToMove3To1Transition, 1, true),
                new ArcIn(elevatorOn3FloorPlace, enterToMove3To2Transition, 1, true),
                new ArcIn(elevatorOn3FloorPlace, enterToMove3To4Transition, 1, true),
                new ArcIn(elevatorOn3FloorPlace, enterToMove3To5Transition, 1, true),
                new ArcIn(noActiveExit3FloorPlace, enterToMove3To1Transition, 1, true),
                new ArcIn(noActiveExit3FloorPlace, enterToMove3To2Transition, 1, true),
                new ArcIn(noActiveExit3FloorPlace, enterToMove3To4Transition, 1, true),
                new ArcIn(noActiveExit3FloorPlace, enterToMove3To5Transition, 1, true),
                new ArcIn(noActiveEnter3FloorPlace, enterToMove3To1Transition, 1),
                new ArcIn(noActiveEnter3FloorPlace, enterToMove3To2Transition, 1),
                new ArcIn(noActiveEnter3FloorPlace, enterToMove3To4Transition, 1),
                new ArcIn(noActiveEnter3FloorPlace, enterToMove3To5Transition, 1),
                new ArcIn(elevatorOn3FloorPlace, switchDirectionUpOn3FloorTransition, 1, true),
                new ArcIn(directionDownPlace, switchDirectionUpOn3FloorTransition, 1),
                new ArcIn(noActiveEnter3FloorPlace, switchDirectionUpOn3FloorTransition, 1, true),
                new ArcIn(noActiveExit3FloorPlace, switchDirectionUpOn3FloorTransition, 1, true),
                new ArcIn(elevatorOn3FloorPlace, switchDirectionDownOn3FloorTransition, 1, true),
                new ArcIn(directionUpPlace, switchDirectionDownOn3FloorTransition, 1),
                new ArcIn(noActiveEnter3FloorPlace, switchDirectionDownOn3FloorTransition, 1, true),
                new ArcIn(noActiveExit3FloorPlace, switchDirectionDownOn3FloorTransition, 1, true),
                // 3 -> 2 floor
                new ArcIn(elevatorOn3FloorPlace, move3To2Transition, 1),
                new ArcIn(passengersDownPlace, move3To2Transition, 1, true),
                new ArcIn(noActiveExit3FloorPlace, move3To2Transition, 1, true),
                new ArcIn(noActiveEnter3FloorPlace, move3To2Transition, 1, true),
                new ArcIn(directionDownPlace, move3To2Transition, 1, true),
                // 3 -> 4 floor
                new ArcIn(elevatorOn3FloorPlace, move3To4Transition, 1),
                new ArcIn(passengersUpPlace, move3To4Transition, 1, true),
                new ArcIn(noActiveExit3FloorPlace, move3To4Transition, 1, true),
                new ArcIn(noActiveEnter3FloorPlace, move3To4Transition, 1, true),
                new ArcIn(directionUpPlace, move3To4Transition, 1, true),

                //4 floor
                new ArcIn(elevatorOn4FloorPlace, exitOn4FloorTransition, 1, true),
                new ArcIn(passengersTo4FloorPlace, exitOn4FloorTransition, 1),
                new ArcIn(noActiveExit4FloorPlace, exitOn4FloorTransition, 1),
                new ArcIn(peopleOn4FloorPlace, spendTimeOn4FloorTransition, 1),
                new ArcIn(removeFromUpOrDownPassengers4FloorPlace, removeFromUpPassengers4FloorTransition, 1),
                new ArcIn(passengersUpPlace, removeFromUpPassengers4FloorTransition, 1),
                new ArcIn(removeFromUpOrDownPassengers4FloorPlace, removeFromDownPassengers4FloorTransition, 1),
                new ArcIn(passengersDownPlace, removeFromDownPassengers4FloorTransition, 1),
                new ArcIn(directionUpPlace, removeFromUpPassengers4FloorTransition, 1, true),
                new ArcIn(directionDownPlace, removeFromDownPassengers4FloorTransition, 1, true),
                new ArcIn(waitingOn4FloorPlace, enterToMove4To1Transition, 1),
                new ArcIn(waitingOn4FloorPlace, enterToMove4To2Transition, 1),
                new ArcIn(waitingOn4FloorPlace, enterToMove4To3Transition, 1),
                new ArcIn(waitingOn4FloorPlace, enterToMove4To5Transition, 1),
                new ArcIn(availablePlacesPlace, enterToMove4To1Transition, 1),
                new ArcIn(availablePlacesPlace, enterToMove4To2Transition, 1),
                new ArcIn(availablePlacesPlace, enterToMove4To3Transition, 1),
                new ArcIn(availablePlacesPlace, enterToMove4To5Transition, 1),
                new ArcIn(elevatorOn4FloorPlace, enterToMove4To1Transition, 1, true),
                new ArcIn(elevatorOn4FloorPlace, enterToMove4To2Transition, 1, true),
                new ArcIn(elevatorOn4FloorPlace, enterToMove4To3Transition, 1, true),
                new ArcIn(elevatorOn4FloorPlace, enterToMove3To5Transition, 1, true),
                new ArcIn(noActiveExit4FloorPlace, enterToMove4To1Transition, 1, true),
                new ArcIn(noActiveExit4FloorPlace, enterToMove4To2Transition, 1, true),
                new ArcIn(noActiveExit4FloorPlace, enterToMove4To3Transition, 1, true),
                new ArcIn(noActiveExit4FloorPlace, enterToMove3To5Transition, 1, true),
                new ArcIn(noActiveEnter4FloorPlace, enterToMove4To1Transition, 1),
                new ArcIn(noActiveEnter4FloorPlace, enterToMove4To2Transition, 1),
                new ArcIn(noActiveEnter4FloorPlace, enterToMove4To3Transition, 1),
                new ArcIn(noActiveEnter4FloorPlace, enterToMove4To5Transition, 1),
                new ArcIn(elevatorOn4FloorPlace, switchDirectionUpOn4FloorTransition, 1, true),
                new ArcIn(directionDownPlace, switchDirectionUpOn4FloorTransition, 1),
                new ArcIn(noActiveEnter4FloorPlace, switchDirectionUpOn4FloorTransition, 1, true),
                new ArcIn(noActiveExit4FloorPlace, switchDirectionUpOn4FloorTransition, 1, true),
                new ArcIn(elevatorOn4FloorPlace, switchDirectionDownOn4FloorTransition, 1, true),
                new ArcIn(directionUpPlace, switchDirectionDownOn4FloorTransition, 1),
                new ArcIn(noActiveEnter4FloorPlace, switchDirectionDownOn4FloorTransition, 1, true),
                new ArcIn(noActiveExit4FloorPlace, switchDirectionDownOn4FloorTransition, 1, true),
                // 4 -> 3 floor
                new ArcIn(elevatorOn4FloorPlace, move4To3Transition, 1),
                new ArcIn(passengersDownPlace, move4To3Transition, 1, true),
                new ArcIn(noActiveExit4FloorPlace, move4To3Transition, 1, true),
                new ArcIn(noActiveEnter4FloorPlace, move4To3Transition, 1, true),
                new ArcIn(directionDownPlace, move4To3Transition, 1, true),
                // 4 -> 5 floor
                new ArcIn(elevatorOn4FloorPlace, move4To5Transition, 1),
                new ArcIn(passengersUpPlace, move4To5Transition, 1, true),
                new ArcIn(noActiveExit4FloorPlace, move4To5Transition, 1, true),
                new ArcIn(directionUpPlace, move4To5Transition, 1, true),

                //5 floor
                new ArcIn(elevatorOn5FloorPlace, exitOn5FloorTransition, 1, true),
                new ArcIn(passengersTo5FloorPlace, exitOn5FloorTransition, 1),
                new ArcIn(noActiveExit5FloorPlace, exitOn5FloorTransition, 1),
                new ArcIn(peopleOn5FloorPlace, spendTimeOn5FloorTransition, 1),
                new ArcIn(removeFromUpPassengers5FloorPlace, removeFromUpPassengers5FloorTransition, 1),
                new ArcIn(passengersUpPlace, removeFromUpPassengers5FloorTransition, 1),
                new ArcIn(directionUpPlace, removeFromUpPassengers5FloorTransition, 1, true),
                new ArcIn(waitingOn5FloorPlace, enterToMove5To1Transition, 1),
                new ArcIn(waitingOn5FloorPlace, enterToMove5To2Transition, 1),
                new ArcIn(waitingOn5FloorPlace, enterToMove5To3Transition, 1),
                new ArcIn(waitingOn5FloorPlace, enterToMove5To4Transition, 1),
                new ArcIn(availablePlacesPlace, enterToMove5To1Transition, 1),
                new ArcIn(availablePlacesPlace, enterToMove5To2Transition, 1),
                new ArcIn(availablePlacesPlace, enterToMove5To3Transition, 1),
                new ArcIn(availablePlacesPlace, enterToMove5To4Transition, 1),
                new ArcIn(elevatorOn5FloorPlace, enterToMove5To1Transition, 1, true),
                new ArcIn(elevatorOn5FloorPlace, enterToMove5To2Transition, 1, true),
                new ArcIn(elevatorOn5FloorPlace, enterToMove5To3Transition, 1, true),
                new ArcIn(elevatorOn5FloorPlace, enterToMove5To4Transition, 1, true),
                new ArcIn(noActiveExit5FloorPlace, enterToMove5To1Transition, 1, true),
                new ArcIn(noActiveExit5FloorPlace, enterToMove5To2Transition, 1, true),
                new ArcIn(noActiveExit5FloorPlace, enterToMove5To3Transition, 1, true),
                new ArcIn(noActiveExit5FloorPlace, enterToMove5To4Transition, 1, true),
                new ArcIn(noActiveEnter5FloorPlace, enterToMove5To1Transition, 1),
                new ArcIn(noActiveEnter5FloorPlace, enterToMove5To2Transition, 1),
                new ArcIn(noActiveEnter5FloorPlace, enterToMove5To3Transition, 1),
                new ArcIn(noActiveEnter5FloorPlace, enterToMove5To4Transition, 1),
                new ArcIn(elevatorOn5FloorPlace, switchDirectionDownOn5FloorTransition, 1, true),
                new ArcIn(directionUpPlace, switchDirectionDownOn5FloorTransition, 1),
                new ArcIn(noActiveEnter5FloorPlace, switchDirectionDownOn5FloorTransition, 1, true),
                new ArcIn(noActiveExit5FloorPlace, switchDirectionDownOn5FloorTransition, 1, true),
                // 5 -> 4 floor
                new ArcIn(elevatorOn5FloorPlace, move5To4Transition, 1),
                new ArcIn(passengersDownPlace, move5To4Transition, 1, true),
                new ArcIn(noActiveExit5FloorPlace, move5To4Transition, 1, true),
                new ArcIn(noActiveEnter5FloorPlace, move5To4Transition, 1, true),
                new ArcIn(directionDownPlace, move5To4Transition, 1, true)
        ));

        ArrayList<ArcOut> d_Out = new ArrayList<>(Arrays.asList(
                //1 floor
                new ArcOut(arrive1FloorTransition, toArrive1FloorPlace, 1),
                new ArcOut(arrive1FloorTransition, waiting1FloorPlace, 1),
                new ArcOut(enterToMove1To2Transition, passengersUpPlace, 1),
                new ArcOut(enterToMove1To3Transition, passengersUpPlace, 1),
                new ArcOut(enterToMove1To4Transition, passengersUpPlace, 1),
                new ArcOut(enterToMove1To5Transition, passengersUpPlace, 1),
                new ArcOut(enterToMove1To2Transition, passengersTo2FloorPlace, 1),
                new ArcOut(enterToMove1To3Transition, passengersTo3FloorPlace, 1),
                new ArcOut(enterToMove1To4Transition, passengersTo4FloorPlace, 1),
                new ArcOut(enterToMove1To5Transition, passengersTo5FloorPlace, 1),
                new ArcOut(enterToMove1To2Transition, noActiveEnter1FloorPlace, 1),
                new ArcOut(enterToMove1To3Transition, noActiveEnter1FloorPlace, 1),
                new ArcOut(enterToMove1To4Transition, noActiveEnter1FloorPlace, 1),
                new ArcOut(enterToMove1To5Transition, noActiveEnter1FloorPlace, 1),
                new ArcOut(switchDirectionUpOn1FloorTransition, directionUpPlace, 1),
                //1 ->2 floor
                new ArcOut(move1To2Transition, elevatorOn2FloorPlace, 1),
                //2 floor
                new ArcOut(exitOn2FloorTransition, peopleOn2FloorPlace, 1),
                new ArcOut(exitOn2FloorTransition, removeFromUpOrDownPassengers2FloorPlace, 1),
                new ArcOut(exitOn2FloorTransition, availablePlacesPlace, 1),
                new ArcOut(removeFromDownPassengers2FloorTransition, removedFromDownPassengers2FloorPlace, 1),
                new ArcOut(removeFromDownPassengers2FloorTransition, noActiveExit2FloorPlace, 1),
                new ArcOut(removeFromUpPassengers2FloorTransition, removedFromUpPassengers2FloorPlace, 1),
                new ArcOut(removeFromUpPassengers2FloorTransition, noActiveExit2FloorPlace, 1),
                new ArcOut(spendTimeOn2FloorTransition, waitingOn2FloorPlace, 1),
                new ArcOut(enterToMove2To1Transition, passengersTo1FloorPlace, 1),
                new ArcOut(enterToMove2To3Transition, passengersTo3FloorPlace, 1),
                new ArcOut(enterToMove2To4Transition, passengersTo4FloorPlace, 1),
                new ArcOut(enterToMove2To5Transition, passengersTo5FloorPlace, 1),
                new ArcOut(enterToMove2To1Transition, passengersDownPlace, 1),
                new ArcOut(enterToMove2To3Transition, passengersUpPlace, 1),
                new ArcOut(enterToMove2To4Transition, passengersUpPlace, 1),
                new ArcOut(enterToMove2To5Transition, passengersUpPlace, 1),
                new ArcOut(enterToMove2To1Transition, noActiveEnter2FloorPlace, 1),
                new ArcOut(enterToMove2To3Transition, noActiveEnter2FloorPlace, 1),
                new ArcOut(enterToMove2To4Transition, noActiveEnter2FloorPlace, 1),
                new ArcOut(enterToMove2To5Transition, noActiveEnter2FloorPlace, 1),
                new ArcOut(switchDirectionUpOn2FloorTransition, directionUpPlace, 1),
                new ArcOut(switchDirectionDownOn2FloorTransition, directionDownPlace, 1),
                //2 ->1 floor
                new ArcOut(move2To1Transition, elevatorOn1FloorPlace, 1),
                //1 floor
                new ArcOut(exitOn1FloorTransition, removeFromDownPassengers1FloorPlace, 1),
                new ArcOut(exitOn1FloorTransition, availablePlacesPlace, 1),
                new ArcOut(removeFromDownPassengers1FloorTransition, removedFromDownPassengers1FloorPlace, 1),
                new ArcOut(removeFromDownPassengers1FloorTransition, noActiveExit1FloorPlace, 1),
                //2 ->3 floor
                new ArcOut(move2To3Transition, elevatorOn3FloorPlace, 1),
                //3 floor
                new ArcOut(exitOn3FloorTransition, peopleOn3FloorPlace, 1),
                new ArcOut(exitOn3FloorTransition, removeFromUpOrDownPassengers3FloorPlace, 1),
                new ArcOut(exitOn3FloorTransition, availablePlacesPlace, 1),
                new ArcOut(removeFromDownPassengers3FloorTransition, removedFromDownPassengers3FloorPlace, 1),
                new ArcOut(removeFromDownPassengers3FloorTransition, noActiveExit3FloorPlace, 1),
                new ArcOut(removeFromUpPassengers3FloorTransition, removedFromUpPassengers3FloorPlace, 1),
                new ArcOut(removeFromUpPassengers3FloorTransition, noActiveExit3FloorPlace, 1),
                new ArcOut(spendTimeOn3FloorTransition, waitingOn3FloorPlace, 1),
                new ArcOut(enterToMove3To1Transition, passengersTo1FloorPlace, 1),
                new ArcOut(enterToMove3To2Transition, passengersTo2FloorPlace, 1),
                new ArcOut(enterToMove3To4Transition, passengersTo4FloorPlace, 1),
                new ArcOut(enterToMove3To5Transition, passengersTo5FloorPlace, 1),
                new ArcOut(enterToMove3To1Transition, passengersDownPlace, 1),
                new ArcOut(enterToMove3To2Transition, passengersDownPlace, 1),
                new ArcOut(enterToMove3To4Transition, passengersUpPlace, 1),
                new ArcOut(enterToMove3To5Transition, passengersUpPlace, 1),
                new ArcOut(enterToMove3To1Transition, noActiveEnter3FloorPlace, 1),
                new ArcOut(enterToMove3To2Transition, noActiveEnter3FloorPlace, 1),
                new ArcOut(enterToMove3To4Transition, noActiveEnter3FloorPlace, 1),
                new ArcOut(enterToMove3To5Transition, noActiveEnter3FloorPlace, 1),
                new ArcOut(switchDirectionUpOn3FloorTransition, directionUpPlace, 1),
                new ArcOut(switchDirectionDownOn3FloorTransition, directionDownPlace, 1),
                //3 ->2 floor
                new ArcOut(move3To2Transition, elevatorOn2FloorPlace, 1),
                //3 ->4 floor
                new ArcOut(move3To4Transition, elevatorOn4FloorPlace, 1),
                //4 floor
                new ArcOut(exitOn4FloorTransition, peopleOn4FloorPlace, 1),
                new ArcOut(exitOn4FloorTransition, removeFromUpOrDownPassengers4FloorPlace, 1),
                new ArcOut(exitOn4FloorTransition, availablePlacesPlace, 1),
                new ArcOut(removeFromDownPassengers4FloorTransition, removedFromDownPassengers4FloorPlace, 1),
                new ArcOut(removeFromUpPassengers4FloorTransition, removedFromUpPassengers4FloorPlace, 1),
                new ArcOut(removeFromDownPassengers4FloorTransition, noActiveExit4FloorPlace, 1),
                new ArcOut(removeFromUpPassengers4FloorTransition, noActiveExit4FloorPlace, 1),
                new ArcOut(spendTimeOn4FloorTransition, waitingOn4FloorPlace, 1),
                new ArcOut(enterToMove4To1Transition, passengersTo1FloorPlace, 1),
                new ArcOut(enterToMove4To2Transition, passengersTo2FloorPlace, 1),
                new ArcOut(enterToMove4To3Transition, passengersTo3FloorPlace, 1),
                new ArcOut(enterToMove4To5Transition, passengersTo5FloorPlace, 1),
                new ArcOut(enterToMove4To1Transition, passengersDownPlace, 1),
                new ArcOut(enterToMove4To2Transition, passengersDownPlace, 1),
                new ArcOut(enterToMove4To3Transition, passengersDownPlace, 1),
                new ArcOut(enterToMove4To5Transition, passengersUpPlace, 1),
                new ArcOut(enterToMove4To1Transition, noActiveEnter4FloorPlace, 1),
                new ArcOut(enterToMove4To2Transition, noActiveEnter4FloorPlace, 1),
                new ArcOut(enterToMove4To3Transition, noActiveEnter4FloorPlace, 1),
                new ArcOut(enterToMove4To5Transition, noActiveEnter4FloorPlace, 1),
                new ArcOut(switchDirectionUpOn4FloorTransition, directionUpPlace, 1),
                new ArcOut(switchDirectionDownOn4FloorTransition, directionDownPlace, 1),
                //4 ->3 floor
                new ArcOut(move4To3Transition, elevatorOn3FloorPlace, 1),
                //4 ->5 floor
                new ArcOut(move4To5Transition, elevatorOn5FloorPlace, 1),

                //5 floor
                new ArcOut(exitOn5FloorTransition, peopleOn5FloorPlace, 1),
                new ArcOut(exitOn5FloorTransition, removeFromUpPassengers5FloorPlace, 1),
                new ArcOut(exitOn5FloorTransition, availablePlacesPlace, 1),
                new ArcOut(removeFromUpPassengers5FloorTransition, removedFromUpPassengers5FloorPlace, 1),
                new ArcOut(removeFromUpPassengers5FloorTransition, noActiveExit5FloorPlace, 1),
                new ArcOut(spendTimeOn5FloorTransition, waitingOn5FloorPlace, 1),
                new ArcOut(enterToMove5To1Transition, passengersTo1FloorPlace, 1),
                new ArcOut(enterToMove5To2Transition, passengersTo2FloorPlace, 1),
                new ArcOut(enterToMove5To3Transition, passengersTo3FloorPlace, 1),
                new ArcOut(enterToMove5To4Transition, passengersTo4FloorPlace, 1),
                new ArcOut(enterToMove5To1Transition, passengersDownPlace, 1),
                new ArcOut(enterToMove5To2Transition, passengersDownPlace, 1),
                new ArcOut(enterToMove5To3Transition, passengersDownPlace, 1),
                new ArcOut(enterToMove5To4Transition, passengersDownPlace, 1),
                new ArcOut(enterToMove5To1Transition, noActiveEnter5FloorPlace, 1),
                new ArcOut(enterToMove5To2Transition, noActiveEnter5FloorPlace, 1),
                new ArcOut(enterToMove5To3Transition, noActiveEnter5FloorPlace, 1),
                new ArcOut(enterToMove5To4Transition, noActiveEnter5FloorPlace, 1),
                new ArcOut(switchDirectionDownOn5FloorTransition, directionDownPlace, 1),
                //5 ->4 floor
                new ArcOut(move5To4Transition, elevatorOn4FloorPlace, 1)
        ));

        ArrayList<PetriP> d_P = new ArrayList<>(Arrays.asList(
                passengersUpPlace,
                passengersDownPlace,
                passengersTo1FloorPlace,
                passengersTo2FloorPlace,
                passengersTo3FloorPlace,
                passengersTo4FloorPlace,
                passengersTo5FloorPlace,
                availablePlacesPlace,
                directionUpPlace,
                directionDownPlace,
                toArrive1FloorPlace,
                waiting1FloorPlace,
                noActiveExit1FloorPlace,
                noActiveEnter1FloorPlace,
                elevatorOn1FloorPlace,
                peopleOn2FloorPlace,
                removeFromUpOrDownPassengers2FloorPlace,
                removedFromUpPassengers2FloorPlace,
                removedFromDownPassengers2FloorPlace,
                waitingOn2FloorPlace,
                noActiveExit2FloorPlace,
                noActiveEnter2FloorPlace,
                elevatorOn2FloorPlace,
                removeFromDownPassengers1FloorPlace,
                removedFromDownPassengers1FloorPlace,
                peopleOn3FloorPlace,
                removeFromUpOrDownPassengers3FloorPlace,
                removedFromUpPassengers3FloorPlace,
                removedFromDownPassengers3FloorPlace,
                waitingOn3FloorPlace,
                noActiveExit3FloorPlace,
                noActiveEnter3FloorPlace,
                elevatorOn3FloorPlace,
                peopleOn4FloorPlace,
                removeFromUpOrDownPassengers4FloorPlace,
                removedFromUpPassengers4FloorPlace,
                removedFromDownPassengers4FloorPlace,
                waitingOn4FloorPlace,
                noActiveExit4FloorPlace,
                noActiveEnter4FloorPlace,
                elevatorOn4FloorPlace,
                peopleOn5FloorPlace,
                removeFromUpPassengers5FloorPlace,
                removedFromUpPassengers5FloorPlace,
                waitingOn5FloorPlace,
                noActiveExit5FloorPlace,
                noActiveEnter5FloorPlace,
                elevatorOn5FloorPlace
        ));

        ArrayList<PetriT> d_T = new ArrayList<>(Arrays.asList(
                arrive1FloorTransition,
                enterToMove1To2Transition,
                enterToMove1To3Transition,
                enterToMove1To4Transition,
                enterToMove1To5Transition,
                move1To2Transition,
                exitOn2FloorTransition,
                removeFromUpPassengers2FloorTransition,
                removeFromDownPassengers2FloorTransition,
                spendTimeOn2FloorTransition,
                enterToMove2To1Transition,
                enterToMove2To3Transition,
                enterToMove2To4Transition,
                enterToMove2To5Transition,
                move2To1Transition,
                move2To3Transition,
                exitOn1FloorTransition,
                removeFromDownPassengers1FloorTransition,
                exitOn3FloorTransition,
                removeFromUpPassengers3FloorTransition,
                removeFromDownPassengers3FloorTransition,
                spendTimeOn3FloorTransition,
                enterToMove3To1Transition,
                enterToMove3To2Transition,
                enterToMove3To4Transition,
                enterToMove3To5Transition,
                move3To2Transition,
                move3To4Transition,
                exitOn4FloorTransition,
                removeFromUpPassengers4FloorTransition,
                removeFromDownPassengers4FloorTransition,
                spendTimeOn4FloorTransition,
                enterToMove4To1Transition,
                enterToMove4To2Transition,
                enterToMove4To3Transition,
                enterToMove4To5Transition,
                move4To3Transition,
                move4To5Transition,
                exitOn5FloorTransition,
                removeFromUpPassengers5FloorTransition,
                spendTimeOn5FloorTransition,
                enterToMove5To1Transition,
                enterToMove5To2Transition,
                enterToMove5To3Transition,
                enterToMove5To4Transition,
                move5To4Transition,

                switchDirectionUpOn1FloorTransition,
                switchDirectionUpOn2FloorTransition,
                switchDirectionDownOn2FloorTransition,
                switchDirectionUpOn3FloorTransition,
                switchDirectionDownOn3FloorTransition,
                switchDirectionUpOn4FloorTransition,
                switchDirectionDownOn4FloorTransition,
                switchDirectionDownOn5FloorTransition
        ));

        PetriNet d_Net = new PetriNet("coursework", d_P, d_T, d_In, d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
    }


    public static PetriNet CreateNetFloorObject(
            PetriP passengersUpPlace,
            PetriP passengersDownPlace,
            PetriP passengersTo1FloorPlace,
            PetriP passengersTo2FloorPlace,
            PetriP passengersTo3FloorPlace,
            PetriP passengersTo4FloorPlace,
            PetriP passengersTo5FloorPlace,
            PetriP availablePlacesPlace,
            PetriP directionUpPlace,
            PetriP directionDownPlace,
            PetriP elevatorOnPreviousFloorPlace,
            PetriP elevatorOnNextFloorPlace,
            PetriP elevatorOnFloorPlace,
            int floorNumber
    ) throws ExceptionInvalidNetStructure, ExceptionInvalidTimeDelay {
        ArrayList<PetriP> d_P = new ArrayList<>(Arrays.asList(
                passengersUpPlace,
                passengersDownPlace,
                passengersTo1FloorPlace,
                passengersTo2FloorPlace,
                passengersTo3FloorPlace,
                passengersTo4FloorPlace,
                passengersTo5FloorPlace,
                availablePlacesPlace,
                directionUpPlace,
                directionDownPlace,
                elevatorOnFloorPlace
        ));

        if (elevatorOnPreviousFloorPlace != null) {
            d_P.add(elevatorOnPreviousFloorPlace);
        }

        if (elevatorOnNextFloorPlace != null) {
            d_P.add(elevatorOnNextFloorPlace);
        }

        ArrayList<PetriT> d_T = new ArrayList<>();
        ArrayList<ArcIn> d_In = new ArrayList<>();
        ArrayList<ArcOut> d_Out = new ArrayList<>();

        PetriP peopleOnFloorPlace = new PetriP(String.format("PeopleOn%dFloor", floorNumber), 0);
        d_P.add(peopleOnFloorPlace);

        PetriP removeFromUpOrDownPassengersFloorPlace = new PetriP(String.format("ToRemoveFromUpOrDownPassengers%dFloor", floorNumber), 0);
        d_P.add(removeFromUpOrDownPassengersFloorPlace);

        PetriP removedFromUpPassengersFloorPlace = new PetriP(String.format("RemovedFromUpPassengers%dFloor", floorNumber), 0);
        d_P.add(removedFromUpPassengersFloorPlace);

        PetriP removedFromDownPassengersFloorPlace = new PetriP(String.format("RemovedFromDown%dFloorPassengers", floorNumber), 0);
        d_P.add(removedFromDownPassengersFloorPlace);

        PetriP waitingOnFloorPlace = new PetriP(String.format("WaitingOn%dFloor", floorNumber), 0);
        d_P.add(waitingOnFloorPlace);

        PetriP noActiveExitFloorPlace = new PetriP(String.format("NoActiveExit%dFloor", floorNumber), 1);
        d_P.add(noActiveExitFloorPlace);

        PetriP noActiveEnterFloorPlace = new PetriP(String.format("NoActiveEnter%dFloor", floorNumber), 1);
        d_P.add(noActiveEnterFloorPlace);

        PetriT exitOnFloorTransition = new PetriT(String.format("ExitOn%dFloor", floorNumber), 0.4);
        exitOnFloorTransition.setPriority(10);
        d_T.add(exitOnFloorTransition);

        PetriT removeFromUpPassengersFloorTransition = new PetriT(String.format("RemoveFromUpPassengers%dFloor", floorNumber), 0.0);
        removeFromUpPassengersFloorTransition.setPriority(5);
        d_T.add(removeFromUpPassengersFloorTransition);

        PetriT removeFromDownPassengersFloorTransition = new PetriT(String.format("RemoveFromDown%dFloorPassengers", floorNumber), 0.0);
        removeFromDownPassengersFloorTransition.setPriority(5);
        d_T.add(removeFromDownPassengersFloorTransition);

        PetriT spendTimeOnFloorTransition = null;
        if (floorNumber != 1) {
            spendTimeOnFloorTransition = new PetriT(String.format("SpendTimeOn%dFloor", floorNumber), 120.0);
            spendTimeOnFloorTransition.setDistribution("unif", spendTimeOnFloorTransition.getTimeServ());
            spendTimeOnFloorTransition.setParamDeviation(15.0);
            d_T.add(spendTimeOnFloorTransition);
        }


        ArrayList<EnterToMoveTransition> enterToMoveTransitions = getEnterToMoveTransitions(floorNumber);
        for (EnterToMoveTransition t : enterToMoveTransitions) {
            d_T.add(t.transition);
        }

        ArrayList<SwitchDirectionTransition> switchDirectionTransitions = getSwitchDirectionTransitions(floorNumber);
        for (SwitchDirectionTransition t : switchDirectionTransitions) {
            d_T.add(t.transition);
        }

        ArrayList<MoveTransition> moveTransitions = getMoveTransitions(floorNumber);
        for (MoveTransition t : moveTransitions) {
            d_T.add(t.transition);
        }

        PetriP currentFloorPassengersPlace = getCurrentFloorPassengersPlace(
                passengersTo1FloorPlace,
                passengersTo2FloorPlace,
                passengersTo3FloorPlace,
                passengersTo4FloorPlace,
                passengersTo5FloorPlace,
                floorNumber
        );

        d_In.add(new ArcIn(elevatorOnFloorPlace, exitOnFloorTransition, 1, true));
        d_In.add(new ArcIn(currentFloorPassengersPlace, exitOnFloorTransition, 1, true));
        d_In.add(new ArcIn(noActiveExitFloorPlace, exitOnFloorTransition, 1));
        d_In.add(new ArcIn(noActiveExitFloorPlace, exitOnFloorTransition, 1));
        if (spendTimeOnFloorTransition != null) {
            d_In.add(new ArcIn(peopleOnFloorPlace, spendTimeOnFloorTransition, 1));
        }

        d_In.add(new ArcIn(removeFromUpOrDownPassengersFloorPlace, removeFromUpPassengersFloorTransition, 1));
        d_In.add(new ArcIn(passengersUpPlace, removeFromUpPassengersFloorTransition, 1));
        d_In.add(new ArcIn(removeFromUpOrDownPassengersFloorPlace, removeFromDownPassengersFloorTransition, 1));
        d_In.add(new ArcIn(passengersDownPlace, removeFromDownPassengersFloorTransition, 1));
        d_In.add(new ArcIn(removeFromUpOrDownPassengersFloorPlace, removeFromDownPassengersFloorTransition, 1));
        d_In.add(new ArcIn(passengersDownPlace, removeFromDownPassengersFloorTransition, 1));
        d_In.add(new ArcIn(directionUpPlace, removeFromUpPassengersFloorTransition, 1, true));
        d_In.add(new ArcIn(directionDownPlace, removeFromDownPassengersFloorTransition, 1, true));

        for (EnterToMoveTransition t : enterToMoveTransitions) {
            d_In.add(new ArcIn(waitingOnFloorPlace, t.transition, 1));
            d_In.add(new ArcIn(availablePlacesPlace, t.transition, 1));
            d_In.add(new ArcIn(elevatorOnFloorPlace, t.transition, 1, true));
            d_In.add(new ArcIn(noActiveExitFloorPlace, t.transition, 1, true));
            d_In.add(new ArcIn(noActiveEnterFloorPlace, t.transition, 1));
        }

        for (SwitchDirectionTransition t : switchDirectionTransitions) {
            d_In.add(new ArcIn(elevatorOnFloorPlace, t.transition, 1, true));
            d_In.add(new ArcIn(noActiveEnterFloorPlace, t.transition, 1, true));
            d_In.add(new ArcIn(noActiveExitFloorPlace, t.transition, 1, true));
            if (t.isUp) {
                d_In.add(new ArcIn(directionDownPlace, t.transition, 1));
            } else {
                d_In.add(new ArcIn(directionUpPlace, t.transition, 1));
            }
        }

        for (MoveTransition t : moveTransitions) {
            d_In.add(new ArcIn(elevatorOnFloorPlace, t.transition, 1));
            d_In.add(new ArcIn(noActiveEnterFloorPlace, t.transition, 1, true));
            d_In.add(new ArcIn(noActiveExitFloorPlace, t.transition, 1, true));
            if (t.isUp) {
                d_In.add(new ArcIn(directionUpPlace, t.transition, 1, true));
                d_In.add(new ArcIn(passengersUpPlace, t.transition, 1, true));
            } else {
                d_In.add(new ArcIn(passengersDownPlace, t.transition, 1, true));
                d_In.add(new ArcIn(directionDownPlace, t.transition, 1, true));
            }
        }

        d_Out.add(new ArcOut(exitOnFloorTransition, peopleOnFloorPlace, 1));
        d_Out.add(new ArcOut(exitOnFloorTransition, removeFromUpOrDownPassengersFloorPlace, 1));
        d_Out.add(new ArcOut(exitOnFloorTransition, availablePlacesPlace, 1));
        d_Out.add(new ArcOut(removeFromDownPassengersFloorTransition, removedFromDownPassengersFloorPlace, 1));
        d_Out.add(new ArcOut(removeFromDownPassengersFloorTransition, noActiveExitFloorPlace, 1));
        d_Out.add(new ArcOut(removeFromUpPassengersFloorTransition, removedFromUpPassengersFloorPlace, 1));
        d_Out.add(new ArcOut(removeFromUpPassengersFloorTransition, noActiveExitFloorPlace, 1));
        if (spendTimeOnFloorTransition != null) {
            d_Out.add(new ArcOut(spendTimeOnFloorTransition, waitingOnFloorPlace, 1));
        }

        for (EnterToMoveTransition t : enterToMoveTransitions) {
            switch (t.resultingFloor) {
                case 2:
                    d_Out.add(new ArcOut(t.transition, passengersTo2FloorPlace, 1));
                case 3:
                    d_Out.add(new ArcOut(t.transition, passengersTo3FloorPlace, 1));
                case 4:
                    d_Out.add(new ArcOut(t.transition, passengersTo4FloorPlace, 1));
                case 5:
                    d_Out.add(new ArcOut(t.transition, passengersTo5FloorPlace, 1));
                default:
                    d_Out.add(new ArcOut(t.transition, passengersTo1FloorPlace, 1));
            }
            if (t.isUp) {
                d_Out.add(new ArcOut(t.transition, passengersUpPlace, 1));
            } else {
                d_Out.add(new ArcOut(t.transition, passengersDownPlace, 1));
            }
            d_Out.add(new ArcOut(t.transition, noActiveEnterFloorPlace, 1));
        }

        for (SwitchDirectionTransition t : switchDirectionTransitions) {
            if (t.isUp) {
                d_Out.add(new ArcOut(t.transition, directionUpPlace, 1));
            } else {
                d_Out.add(new ArcOut(t.transition, directionDownPlace, 1));
            }
        }

        for (MoveTransition t : moveTransitions) {
            if (t.isUp) {
                d_Out.add(new ArcOut(t.transition, elevatorOnNextFloorPlace, 1));
            } else {
                d_Out.add(new ArcOut(t.transition, elevatorOnPreviousFloorPlace, 1));
            }
        }

        PetriNet d_Net = new PetriNet(String.format("floor%d", floorNumber), d_P, d_T, d_In, d_Out);
        PetriP.initNext();
        PetriT.initNext();
        ArcIn.initNext();
        ArcOut.initNext();

        return d_Net;
    }


    private static ArrayList<EnterToMoveTransition> getEnterToMoveTransitions(int floorNumber) {
        ArrayList<EnterToMoveTransition> enterToMoveTransitions = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            if (i == floorNumber) {
                continue;
            }

            double probability = 0.1;

            if (i == 1) {
                probability = 0.7;
            }

            if (floorNumber == 1) {
                probability = 0.25;
            }


            PetriT transition = new PetriT(String.format("EnterToMove%dTo%d", floorNumber, i), 0.0);
            transition.setProbability(probability);
            transition.setPriority(5);

            EnterToMoveTransition enterToMoveTransition = new EnterToMoveTransition(transition, floorNumber < i, i);
            enterToMoveTransitions.add(enterToMoveTransition);
        }

        return enterToMoveTransitions;
    }

    private static ArrayList<SwitchDirectionTransition> getSwitchDirectionTransitions(int floorNumber) {
        ArrayList<SwitchDirectionTransition> switchDirectionTransitions = new ArrayList<>();

        if (floorNumber != 1) {
            PetriT upTransition = new PetriT(String.format("SwitchDirectionUpOn%dFloor", floorNumber), 0.0);
            SwitchDirectionTransition switchDirectionUpOnFloorTransition = new SwitchDirectionTransition(upTransition, true);
            switchDirectionTransitions.add(switchDirectionUpOnFloorTransition);
        }

        if (floorNumber != 5) {
            PetriT upTransition = new PetriT(String.format("SwitchDirectionUpOn%dFloor", floorNumber), 0.0);
            SwitchDirectionTransition switchDirectionUpOnFloorTransition = new SwitchDirectionTransition(upTransition, true);
            switchDirectionTransitions.add(switchDirectionUpOnFloorTransition);
        }

        return switchDirectionTransitions;
    }

    private static ArrayList<MoveTransition> getMoveTransitions(int floorNumber) {
        ArrayList<MoveTransition> moveTransitions = new ArrayList<>();

        PetriT move2To1Transition = new PetriT("Move2To1", 0.4);
        move2To1Transition.setPriority(3);
        PetriT move2To3Transition = new PetriT("Move2To3", 0.4);
        move2To3Transition.setPriority(3);

        if (floorNumber != 1) {
            PetriT previousTransition = new PetriT(String.format("Move%dTo%d", floorNumber, floorNumber - 1), 0.0);
            previousTransition.setPriority(3);
            MoveTransition moveToPreviousTransition = new MoveTransition(previousTransition, false);
            moveTransitions.add(moveToPreviousTransition);
        }

        if (floorNumber != 5) {
            PetriT nextTransition = new PetriT(String.format("Move%dTo%d", floorNumber, floorNumber + 1), 0.0);
            nextTransition.setPriority(3);
            MoveTransition moveToNextTransition = new MoveTransition(nextTransition, true);
            moveTransitions.add(moveToNextTransition);
        }

        return moveTransitions;
    }


    private static PetriP getCurrentFloorPassengersPlace(
            PetriP passengersTo1FloorPlace,
            PetriP passengersTo2FloorPlace,
            PetriP passengersTo3FloorPlace,
            PetriP passengersTo4FloorPlace,
            PetriP passengersTo5FloorPlace,
            int floorNumber) {
        switch (floorNumber) {
            case 2:
                return passengersTo2FloorPlace;
            case 3:
                return passengersTo3FloorPlace;
            case 4:
                return passengersTo4FloorPlace;
            case 5:
                return passengersTo5FloorPlace;
            default:
                return passengersTo1FloorPlace;
        }
    }

}