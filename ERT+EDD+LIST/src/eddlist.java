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

		int[] Schedule = new int[NJ];
		Scheduler[] s = new Scheduler[NJ];
		for (int i = 0; i < NJ; i++) {
			s[i] = new Scheduler();
		}

		Jobs[] Array = new Jobs[NJ];
		for (int i = 0; i < NJ; i++) {
			Array[i] = new Jobs();
		}

		Workstations[] Array2 = new Workstations[NW];
		for (int i = 0; i < NW; i++) {
			Array2[i] = new Workstations();
			Array2[i].setId(i + 1);
			Array2[i].setL(200);
		}

		randomJobGenerator(NJ, Array);
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
			array[i].setOperationT(op);
			array[i].setD((NJ - i) * r.nextInt(10) + 1 + op);
		}
	}
	
	public static void randomWorkstationGenerator(int NW, Workstations[] array) {
		Random r = new Random();
		for (int i = 0; i < NW; i++) {
			int op = r.nextInt(10) + 1;
			array[i].setL(0);
			array[i].setStart(op);
			array[i].setEnd(r.nextInt(200) + 50 + op);
			array[i].setC(0);
		}
	}

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
				if (array[s[index]].getOperationT() > array[s[j]].getOperationT())
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

	public static void Simulation(int NJ, Jobs[] array, int[] s) {
		for (int i = 0; i < NJ; i++) {
			array[s[i]].setEndT((int) array[s[i]].getStartT() + array[s[i]].getOperationT());
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

	public static void EDD_LIST(int NJ, Jobs[] array, int NW, Workstations[] array2, Scheduler[] sch, int[] s) {

		if (NW == 1) {
			onlyOneWorkstation(NJ, array, NW, array2, sch, s);
		} else {
			createSchedule(s, NJ);
			EDD(NJ, array, s);
			
			
			for (int i = 0; i < NJ; i++) {
				
				for (int r = 1; r < NW; r++) {
					int rSel = 0;
					if (array2[rSel].getC() > array2[r].getC()) {
						rSel = r;
					}
					if(array2[rSel].getEnd()-array2[rSel].getStart() >= array[s[i]].getOperationT()) {	
						System.out.println(array[s[i]].getId() +" job assigned to " + array2[rSel].getId() + " workstation!\n");
						array[s[i]].setStartT((int) array2[rSel].getStart());
						sch[i].setWorkstationID(array2[rSel].getId());
						sch[i].setJobID(array[s[i]].getId());
						array2[rSel].setL(array2[rSel].getL() + 1);
						array2[rSel].setC(array2[rSel].getC() + array[s[i]].getOperationT());
						array2[rSel].setStart(array2[rSel].getStart() + array[s[i]].getOperationT());
					}else{
						System.out.println(array[s[i]].getId() +" job can't assign to " + array2[rSel].getId() +" workstation!");
						if(array2[r].getEnd()-array2[r].getStart() >= array[s[i]].getOperationT() ) {
							System.out.println(array[s[i]].getId() +" job assigned to " + array2[r].getId() + " workstation!\n");
							array[s[i]].setStartT((int) array2[r].getStart());
							sch[i].setWorkstationID(array2[r].getId());
							sch[i].setJobID(array[s[i]].getId());
							array2[r].setL(array2[r].getL() + 1);
							array2[r].setC(array2[r].getC() + array[s[i]].getOperationT());
							array2[r].setStart(array2[r].getStart() + array[s[i]].getOperationT());
						}else {
							System.out.println(array[s[i]].getId() +" job has too much operation time to schedule it!");
						}
					}
					
				}
			}
		}
	}

	public static void onlyOneWorkstation(int NJ, Jobs[] array, int NW, Workstations[] array2, Scheduler[] sch, int[] s) {

		createSchedule(s, NJ);
		EDD(NJ, array, s);

		int rSel = 0;
		
		for (int i = 0; i < NJ; i++) {
			
			int processTime = (int) (array2[rSel].getEnd()-array2[rSel].getC());
			if(processTime >= array[s[i]].getOperationT()) {
				
				array[s[i]].setStartT((int) array2[rSel].getC());
				sch[i].setWorkstationID(array2[rSel].getId());
				sch[i].setJobID(array[s[i]].getId());
				array2[rSel].setL(array2[rSel].getL() + 1);
				array2[rSel].setC(array2[rSel].getC() + array[s[i]].getOperationT());
				
				
			}else {
				System.out.println("\nThis job can't be done in 1 Workstation: " + array[s[i]].getId() + "\nTry more workstations!");
			}
			
			
		}

	}
}