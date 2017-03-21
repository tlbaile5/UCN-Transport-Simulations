import java.util.Random;
import java.io.*;
import java.util.*;
/*
Class for writing Regionfile and Connexfile for UCN_Transport_Simulator
@author Thomas Bailey

to change the output change to boolean values in the next few lines.  
options currently are Specific scattering, random scattering, and
no scattering along with all layers deuterium, alternating layers 
of vacuum and deuterium and random layers of vacuum and deuterium.
*/

public class Printing {


//constant for the number of layers
public static final int SCATTERING_LAYERS = 50;

//constant for the maximum scattering
public static final int MAX_SCATTER = 10;

//boolean for scattering at a specific value
public static final boolean SPECIFIC_SCATTER = false;

public static final int SCATTER_VALUE = 50;

//boolean for random scattering.  Note this and the above can't both be true
public static final boolean RANDOM_SCATTER = true;

//boolean for alternating layers
public static final boolean ALTERNATING_LAYERS = true;

//boolean for random gas/substance layers.  Note this and the above can not both be true
public static final boolean RANDOM_LAYERS = false;

    public static void main(String[] args) throws FileNotFoundException {
        
        File Connex = new File("Connexfile");       //File output for the Connexfile
        PrintStream Con = new PrintStream(Connex);
        
        File Region = new File("Regionfile");           //file output for the Regionfile
        PrintStream Reg = new PrintStream(Region);
        
        Random r = new Random();  //Random number generater for scattering constant outputs integers from 0 to MAX_SCATTER (declared later)
        
        Random bin = new Random(); //random number generator for random scattering layers. Values 0 or 1 (declared later)
        
        Reg.println("Reg#  RType   BP(x,y,z)          Dim[m]        Orient         Grad B [T/m]  Spec   Loss    Depol   WPot[neV]  BPot[neV]  Scat   Absorb[1/s]   Det  PM  SM  LM  DM"); //Region file header lines
        Reg.println();
        Reg.println("0    2      0.0,0.0,0.0         0.36,0.40000  0.0,0.0,0.0    0.0,0.0,0.0   0.900  5.0e-4  1.0e-6  335.0      18.5       0.0    0.287         1    1   0   0   0");
        Reg.println("1    2      0.0,-0.08,0.4       0.18,0.01     0.0,0.0,0.0    0.0,0.0,0.0   0.900  5.0e-4  1.0e-6  335.0      18.5       0.0    0.287         0    1   0   0   0");
        Reg.println("2    2      *                   0.18,0.89     0.0,0.0,0.0    0.0,0.0,0.0   0.300  1.0e-3  1.0e-6  335.0      18.5       0.0    0.287         2    1   0   0   0");
        
        Con.println("connex: The first region should be region 0, and a special-handling code must be used to specify how to treat its cut-plane.");  // Connex file header lines
        Con.println("-----------------------------------------------------------------------------------------------------------------------------");
        Con.println("Connects through its own cut-plane to:     AND also to:     AND also to:     AND also to:     AND also to:     AND also to:");
        Con.println("0," + (SCATTERING_LAYERS + 3) + "        0                                               1/");
        Con.println("1,"+ (SCATTERING_LAYERS + 2) + "        0                                               2/");
        Con.println("2           1                                               3                   " + (SCATTERING_LAYERS + 3) + "/");        
        
        for(int i = 3 ; i < SCATTERING_LAYERS + 6; i++) {
            if (i < (SCATTERING_LAYERS + 3)) {
                Con.println(i + "           " + (i-1) + "                                               " + (i+1) + "/");
                if (SPECIFIC_SCATTER) {
                    if (ALTERNATING_LAYERS) {
                        if (i%2 == 0) {
                            Reg.println(i + "    2      *                   0.18,0.0001   0.0,0.0,0.0    0.0,0.0,0.0   0.900  5.0e-4  1.0e-6  335.0      000        " + SCATTER_VALUE + "    0000.0        3    1   0   0   0");
                        } else {
                            Reg.println(i + "    2      *                   0.18,0.0001   0.0,0.0,0.0    0.0,0.0,0.0   0.900  5.0e-4  1.0e-6  335.0      102        " + SCATTER_VALUE + "    0022.2        3    1   0   0   0");
                        }
                    } else if (RANDOM_LAYERS) {
                        if (bin.nextInt(2) == 0) {
                            Reg.println(i + "    2      *                   0.18,0.0001   0.0,0.0,0.0    0.0,0.0,0.0   0.900  5.0e-4  1.0e-6  335.0      000        " + SCATTER_VALUE + "    0000.0        3    1   0   0   0");
                        } else {
                            Reg.println(i + "    2      *                   0.18,0.0001   0.0,0.0,0.0    0.0,0.0,0.0   0.900  5.0e-4  1.0e-6  335.0      102        " + SCATTER_VALUE + "    0022.2        3    1   0   0   0");
                        }
                    } else {
                        Reg.println(i + "    2      *                   0.18,0.0001   0.0,0.0,0.0    0.0,0.0,0.0   0.900  5.0e-4  1.0e-6  335.0      102        " + SCATTER_VALUE + "    0022.2        3    1   0   0   0");
                    }
                }
                else if (RANDOM_SCATTER) {
                    int j = r.nextInt(MAX_SCATTER)+1;
                    if (ALTERNATING_LAYERS) {
                        if (i%2 == 0) {
                            Reg.println(i + "    2      *                   0.18,0.0001   0.0,0.0,0.0    0.0,0.0,0.0   0.900  5.0e-4  1.0e-6  335.0      000        " + j + "     0000.0        3    1   0   0   0");
                        } else {
                            Reg.println(i + "    2      *                   0.18,0.0001   0.0,0.0,0.0    0.0,0.0,0.0   0.900  5.0e-4  1.0e-6  335.0      102        " + j + "     0022.2        3    1   0   0   0");
                        }
                    } else if (RANDOM_LAYERS) {
                        if (bin.nextInt(2) == 0) {
                            Reg.println(i + "    2      *                   0.18,0.0001   0.0,0.0,0.0    0.0,0.0,0.0   0.900  5.0e-4  1.0e-6  335.0      000        " + j + "     0000.0        3    1   0   0   0");
                        } else {
                            Reg.println(i + "    2      *                   0.18,0.0001   0.0,0.0,0.0    0.0,0.0,0.0   0.900  5.0e-4  1.0e-6  335.0      102        " + j + "     0022.2        3    1   0   0   0");
                        }
                    } else {
                        Reg.println(i + "    2      *                   0.18,0.0001   0.0,0.0,0.0    0.0,0.0,0.0   0.900  5.0e-4  1.0e-6  335.0      102        " + j + "     0022.2        3    1   0   0   0");
                    }
                }
                else {
                    if (ALTERNATING_LAYERS) {
                        if (i%2 == 0) {
                            Reg.println(i + "    2      *                   0.18,0.0001   0.0,0.0,0.0    0.0,0.0,0.0   0.900  5.0e-4  1.0e-6  335.0      000        0.0    0000.0        3    1   0   0   0");
                        } else {
                            Reg.println(i + "    2      *                   0.18,0.0001   0.0,0.0,0.0    0.0,0.0,0.0   0.900  5.0e-4  1.0e-6  335.0      102        0.0    0022.2        3    1   0   0   0");
                        }
                    } else if (RANDOM_LAYERS) {
                        if (bin.nextInt(2) == 0) {
                            Reg.println(i + "    2      *                   0.18,0.0001   0.0,0.0,0.0    0.0,0.0,0.0   0.900  5.0e-4  1.0e-6  335.0      000        0.0    0000.0        3    1   0   0   0");
                        } else {
                            Reg.println(i + "    2      *                   0.18,0.0001   0.0,0.0,0.0    0.0,0.0,0.0   0.900  5.0e-4  1.0e-6  335.0      102        0.0    0022.2        3    1   0   0   0");
                        }
                    } else {
                        Reg.println(i + "    2      *                   0.18,0.0001   0.0,0.0,0.0    0.0,0.0,0.0   0.900  5.0e-4  1.0e-6  335.0      102        0.0    0022.2        3    1   0   0   0");
                    }
                }         
            }
            if (i == (SCATTERING_LAYERS + 3)) {
                Con.println(i + "           2                                                " + (i+1) + "/");
                Reg.println(i + "    2      *                   0.18,0.10     0.0,0.0,0.0    0.0,0.0,0.0   0.900  5.0e-4  1.0e-6  335.0      0.0        0.0    0             0    0   0   0   0");     
            }
            if (i == (SCATTERING_LAYERS + 4)) {
                Con.println((i) + "           " + (i-1) + "/");
                Reg.println(i + "    2      *                   0.18,0.10     0.0,0.0,0.0    0.0,0.0,0.0   0.900  5.0e-4  1.0e-6  335.0      0.0        0.0    9999.0        4    1   0   0   0");
            }    
            if (i == (SCATTERING_LAYERS + 5)) {
                Reg.println(i + "    2      >0.0,0.01,1.1       0.14,0.0500   0.0,-90.0,0.0  0.0,0.0,0.0   0.900  5.0e-4  1.0e-6  335.0      18.5       0.0    0.287         0    1   0   0   0");
                Reg.println((i+1) + "    2      *                   0.14,0.0500   0.0,-90.0,0.0  0.0,0.0,0.0   0.900  5.0e-4  1.0e-6  335.0      18.5       0.0    9999.0        5    1   0   0   0");
                Reg.println("/");
            }
        }
    }
}
