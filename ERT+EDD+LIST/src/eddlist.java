import java.util.Random;
import java.util.Scanner;

public class eddlist {

	public static void main(String[] args) {
		Scanner scanInput = new Scanner(System.in);
		int NJ;
		int NW;
		double[] goalFunction = new double[2];

		System.out.println("Give me the number of jobs: ");
		NJ = scanInput.nextInt();
		System.out.println("Give me the number of worstations: ");
		NW = scanInput.nextInt();
		scanInput.close();

		int[] s = new int[NJ];
		Jobs[] job = new Jobs[NJ];
		for (int i = 0; i < NJ; i++) {
			Random r = new Random();
			job[i] = new Jobs();
			job[i].setId(i + 1);
			for (int j = 0; j < NW; j++) {
				job[i].setProcT(r.nextInt(100) + 1);
			}
		}
		createSchedule(s, NJ);

		Workstations[] res = new Workstations[NW];
		for (int i = 0; i < NW; i++) {
			Random r = new Random();
			res[i] = new Workstations();
			res[i].setId(i + 1);
			for (int j = 0; j < NW; j++) {
				res[i].setTransT(r.nextInt(20) + 10);
			}
			for (int j = 0; j < NJ; j++) {
				for (int k = 0; k < NJ; k++) {
					if (j ==k)
						res[i].setSetT(0);
					else
						res[i].setSetT(r.nextInt(100) + 10);
				}
			}
		}
		
		//ad-hoc szimulacio
		System.out.println("\nAd-hoc sorrend:");
		Simulation_FS(NW, job, res, NW, s, 0);
		print_Res_Gantt(job, NJ, NW, s);
		Simulation_P(NW, job, res, s);
		Evaluate(NJ, job, goalFunction);
		printGoals(goalFunction);

		/*randomJobGenerator(NJ, Array);
		Massage(Array);
		randomWorkstationGenerator(NW, Array2);
		Massage2(Array2);

		EDD_LIST(NJ, Array, NW, Array2, s, Schedule);
		Simulation_P(NW, Array, Array2, Schedule);
		Evaluate(NJ, Array, goalFunction);
		System.out.println("\nEDD+LIST:");
		printGoals(goalFunction);
		System.out.println();
		Massage3(s);
		System.out.println();
		Massage2(Array2);
		System.out.println();
		Massage(Array);
		*/
		
	}

	public static void Massage(Jobs[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.println(array[i].toString());
		}
	}

	public static void Massage2(Workstations[] array) {
		for (int i = 0; i < array.length ; i++) {
			System.out.println(array[i].toString());
		}
	}

	public static void Massage3(Scheduler[] array) {
		for (int i = 0; i < array.length; i++) {
			if(array[i].getJobID() != 0) {
				System.out.println("Job:\tWorkstation:");
				System.out.println("" + array[i].getJobID()+"\t"+ array[i].getWorkstationID()+"\n");
			}
		}
	}

	public static void randomJobGenerator(int NJ, Jobs[] array) {
		Random r = new Random();
		for (int i = 0; i < NJ; i++) {
			int op = r.nextInt(100) + 1;
			array[i].setId(i + 1);
			array[i].setProcT(op);
			array[i].setD((NJ - i) * r.nextInt(10) + 1 + op);
			array[i].setType(0);
		}
	}
	
	/*public static void randomWorkstationGenerator(int NW, Workstations[] array) {
		Random r = new Random();
		for (int i = 0; i < NW; i++) {
			int op = r.nextInt(10) + 1;
			array[i].setL(0);
			array[i].setStart(op);
			array[i].setEnd(r.nextInt(150) + 50 + op);
			array[i].setC(0);
			array[i].setType(0);
		}
	}*/

	public static void EDD(int NJ, Jobs[] array, int[] s) {

		createSchedule(s, NJ);

		for (int i = 0; i < (NJ - 1); i++) {
			int index = i;

			for (int j = (i + 1); j < NJ; j++) {
				if (array[s[index]].getD() > array[s[j]].getD())
					index = j;
			}

			if (index != i) {
				int temp = s[index];
				s[index] = s[i];
				s[i] = temp;
			}

		}
	}

	public static void SPT(int NJ, Jobs[] array, int[] s) {

		createSchedule(s, NJ);

		for (int i = 0; i < (NJ - 1); i++) {
			int index = i;

			for (int j = (i + 1); j < NJ; j++) {
				if (array[s[index]].getProcT() > array[s[j]].getProcT())
					index = j;
			}

			if (index != i) {
				int temp = s[index];
				s[index] = s[i];
				s[i] = temp;
			}

		}
	}

	public static void createSchedule(int[] s, int NJ) {
		for (int i = 0; i < NJ; i++) {
			s[i] = i;
		}
	}

	public static void Evaluate(int NJ, Jobs[] array, double[] goal) {

		double Emax = 0;
		double Lmax = 0;
		for (int i = 0; i < NJ; i++) {
			if(array[i].getEndT() == 0) {
				array[i].setL(0);
				array[i].setD(0);
			}
			array[i].setL(array[i].getEndT() - array[i].getD());
			if (i == 0) {
				Lmax = array[i].getL();
				Emax = Math.max(0, -(array[i].getL()));
			} else {
				if (Lmax < array[i].getL()) {
					Lmax = array[i].getL();
				}
				if (Emax < array[i].getL()) {
					Emax = array[i].getL();
				}
			}

		}

		goal[0] = Emax;
		goal[1] = Lmax;
	}
	
	public static void Simulation_FS(int NJ, Jobs[] job, Workstations[] res, int NW, int[] s, long t0) {
		 int i; //az inditas sorrendjenek indexe
		 int r; //munkahely indexe, technologiai sorrendet koveti a gepek azonositoja

		  for( i=0; i<NJ; i++ ){
		    for( r=0; r<NW; r++ ){
		         if ( i==0 ){ //kezdo munka
		              if ( r==0 ){ //kezdo gepen
		                job[s[i]].setStartT((int) t0);
		              }
		              else{ //nem kezdo gepen
		            	int ad = job[r-1].getEndT() + res[r-1].getTransT();
		                job[s[i]].setStartT(ad);
		              }
		              	int ad2 = job[s[i]].getStartT() + res[r].getSetT() + job[s[i]].getProcT();
		                job[s[i]].setEndT(ad2);
		         }
		         else{ //nem kezdo munka
		              if ( r==0 ){ //kezdo gepen
		                job[s[i]].setStartT(job[s[i-1]].getEndT());
		              }
		              else{ //nem kezdo gepen
		                job[s[i]].setStartT(Math.max( job[r-1].getEndT() + res[r-1].getTransT(), job[r].getEndT()));
		              }
		              	int ad2 = job[s[i]].getStartT() + res[r].getSetT() + job[r].getProcT();
		                job[s[i]].setEndT(ad2);
		         }
		   }
		 }
	}
	
	public static void print_Res_Gantt( Jobs[] job, int NJ, int NW, int[] s){
		System.out.println("\n Eroforras-orientalt Gantt adatok:");
		for ( int r = 0; r<NW; r++ ){
			  System.out.println("\n" + r + ". munkahely:");
			  System.out.println("\n # \t munka \t indul \t muv.\t bef.");
			  
			  for (int i=0; i<NJ; i++ ){
				  System.out.println("\n " + i +"\t" + s[i] + "\t" + job[s[i]].getStartT() + "\t" + job[s[i]].getProcT() + "\t" + job[s[i]].getEndT());
			  }
		}
	}

	public static void Simulation(int NJ, Jobs[] array, int[] s) {
		for (int i = 0; i < NJ; i++) {
			array[s[i]].setEndT((int) array[s[i]].getStartT() + array[s[i]].getProcT());
		}
	}

	public static void Simulation_P(int NW, Jobs[] array, Workstations[] array2, int[] s) {
		int NJ = 0;
		for (int i = 0; i < NW; i++) {
			NJ = NJ + array2[i].getL();
			Simulation(NJ, array, s);
		}
	}

	public static void printGoals(double[] goal) {
		System.out.println("Goal functions:");
		System.out.println("Emax: " + goal[0]);
		System.out.println("Lmax: " + goal[1]);
	}
	

	/*public static void EDD_LIST(int NJ, Jobs[] array, int NW, Workstations[] array2, Scheduler[] sch, int[] s) {

		if (NW == 1) {
			EDDOneWorkstation(NJ, array, NW, array2, sch, s);
		} else {
			createSchedule(s, NJ);
			EDD(NJ, array, s);
			
			int[][] sorted = new int[2][NW];
			int rSel = 0;
			
			for (int i = 0; i < NJ; i++) {
				sorted = SortingWorkstations(NW, array2);
				for (int j = 0; j < NW; j++) {
					if(array2[j].getId() == sorted [0][0])
						rSel = j;
				}
				if(array2[rSel].getEnd()-array2[rSel].getStart() >= array[s[i]].getOperationT() && array2[rSel].getType() == array[s[i]].getType()) {
					System.out.println(array[s[i]].getId() +" job assigned to " + array2[rSel].getId() + " workstation!\n");
					array[s[i]].setStartT((int) array2[rSel].getStart());
					sch[i].setWorkstationID(array2[rSel].getId());
					sch[i].setJobID(array[s[i]].getId());
					array2[rSel].setL(array2[rSel].getL() + 1);
					array2[rSel].setC(array2[rSel].getC() + array[s[i]].getOperationT());
					array2[rSel].setStart(array2[rSel].getStart() + array[s[i]].getOperationT());
				}else{
					for (int j = 1; j < NW; j++) {
						for (int j2 = 0; j2 < NW; j2++) {
							if(array2[j2].getId() == sorted [0][j])
								if(j2 != rSel)
									rSel = j2;
							if(array2[rSel].getEnd()-array2[rSel].getStart() >= array[s[i]].getOperationT() && array2[rSel].getType() == array[s[i]].getType()) {
								System.out.println(array[s[i]].getId() +" job assigned to " + array2[rSel].getId() + " workstation!\n");
								array[s[i]].setStartT((int) array2[rSel].getStart());
								sch[i].setWorkstationID(array2[rSel].getId());
								sch[i].setJobID(array[s[i]].getId());
								array2[rSel].setL(array2[rSel].getL() + 1);
								array2[rSel].setC(array2[rSel].getC() + array[s[i]].getOperationT());
								array2[rSel].setStart(array2[rSel].getStart() + array[s[i]].getOperationT());
							}else {
								System.out.println(array[s[i]].getId() +" job can't assign to " + array2[rSel].getId() +" workstation!");
								if(j<NW)
									j++;
							}
						}
						
					}
				}	
				
			}
		}
	}
	

	public static void EDDOneWorkstation(int NJ, Jobs[] array, int NW, Workstations[] array2, Scheduler[] sch, int[] s) {

		createSchedule(s, NJ);
		EDD(NJ, array, s);
		int rSel = 0;
		
		for (int i = 0; i < NJ; i++) {
			
			if((int) (array2[rSel].getEnd()-array2[rSel].getC()) >= array[s[i]].getOperationT()) {
				
				array[s[i]].setStartT((int) array2[rSel].getC());
				sch[i].setWorkstationID(array2[rSel].getId());
				sch[i].setJobID(array[s[i]].getId());
				array2[rSel].setL(array2[rSel].getL() + 1);
				array2[rSel].setC(array2[rSel].getC() + array[s[i]].getOperationT());
				array2[rSel].setStart(array2[rSel].getStart() + array[s[i]].getOperationT());
				
			}else {
				System.out.println("\nThis job can't be done in 1 Workstation: " + array[s[i]].getId() + "\nTry more workstations!");
			}
		}
	}*/
	
	
	public static int[][] SortingWorkstations(int NW, Workstations[] work) {
		int[][] array = new int[2][NW];
		for (int i = 0; i < NW; i++) {
			array[0][i] = (int) work[i].getId();
			array[1][i] = (int) work[i].getC();
		}
		
		
		for(int i = 0 ; i < array.length; i ++){
            for(int j = 1 ; j < array[i].length ; j ++){
                if(i != 0){
                    if(array[i][j-1] > array[i][j] ){
                        for(int k = j;k>0;k--){
                            if(array[i][k-1] > array[i][k] ){
                                 int temp1 = array[i][k-1];
                                 array[i][k-1] =  array[i][k];
                                 array[i][k] = temp1;
                                 int temp2  = array[i-1][k-1];
                                 array[i-1][k-1] =  array[i-1][k];
                                 array[i-1][k] = temp2;
                            }
                        }
                    }
                }
            }
        }
		return array;
	}
}