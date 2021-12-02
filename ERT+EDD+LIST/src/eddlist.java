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

		Workstations[] Array2 = new Workstations[NJ];
		for (int i = 0; i < NW; i++) {
			Array2[i] = new Workstations();
			Array2[i].setId(i + 1);
			Array2[i].setL(200);
		}

		randomDataGenerator(NJ, Array);
		Massage(Array);

		EDD(NJ, Array, Schedule);
		Simulation(NJ, Array, Schedule, 0);
		Evaluate(NJ, Array, goalFunction);
		System.out.println("\nEDD:");
		printGoals(goalFunction);

		SPT(NJ, Array, Schedule);
		Simulation(NJ, Array, Schedule, 0);
		Evaluate(NJ, Array, goalFunction);
		System.out.println("\nSPT:");
		printGoals(goalFunction);

		EDD_LIST(NJ, Array, NW, Array2, s, Schedule);
		Simulation_P(NW, Array, Array2, Schedule, 0);
		Evaluate(NJ, Array, goalFunction);
		System.out.println("\nEDD+LIST:");
		printGoals(goalFunction);
		System.out.println();
		Massage3(s);
		System.out.println();
		Massage2(Array2, NW);
		System.out.println();
		Massage(Array);
		System.out.println(Schedule[0]+"\t"+ Schedule[1]+"\t"+ Schedule[2]);
		
	}

	public static void Massage(Jobs[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.println(array[i].toString());
		}
	}

	public static void Massage2(Workstations[] array2, int NW) {
		for (int i = 0; i < NW; i++) {
			System.out.println(array2[i].toString());
		}
	}

	public static void Massage3(Scheduler[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.println(array[i].toString());
		}
	}

	public static void randomDataGenerator(int NJ, Jobs[] array) {
		Random r = new Random();
		for (int i = 0; i < NJ; i++) {
			int op = r.nextInt(100) + 1;
			array[i].setId(i + 1);
			array[i].setOperationT(op);
			array[i].setD((NJ - i) * r.nextInt(10) + 1 + op);
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

	public static void Simulation(int NJ, Jobs[] array, int[] s, long t0) {

		for (int i = 0; i < NJ; i++) {
			if (i == 0) {
				array[s[i]].setStartT((int) t0);
			} else {
				array[s[i]].setStartT((int) array[s[i - 1]].getEndT());
			}

			array[s[i]].setEndT((int) array[s[i]].getStartT() + array[s[i]].getOperationT());

		}
	}

	public static void Simulation_P(int NW, Jobs[] array, Workstations[] array2, int[] s, long t0) {
		for (int i = 0; i < NW; i++) {
			Simulation(array2[i].getL(), array, s, t0);
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

			for (int r = 0; r < NW; r++) {
				array2[r].setL(0);
				array2[r].setC(0);
			}

			for (int i = 0; i < NJ; i++) {
				int rSel = 0;

				for (int r = 1; r < NW; r++) {
					if (array2[rSel].getC() > array2[r].getC())
						rSel = r;
				}

				sch[i].setWorkstationID(array2[rSel].getId());
				sch[i].setJobID(array[s[i]].getId());
				array2[rSel].setL(array2[rSel].getL() + 1);
				array2[rSel].setC(array2[rSel].getC() + array[s[i]].getOperationT());
			}

		}

	}

	public static void onlyOneWorkstation(int NJ, Jobs[] array, int NW, Workstations[] array2, Scheduler[] sch,
			int[] s) {

		createSchedule(s, NJ);

		EDD(NJ, array, s);

		int rSel = 0;
		array2[rSel].setL(0);
		array2[rSel].setC(0);

		for (int i = 0; i < NJ; i++) {

			sch[i].setWorkstationID(array2[rSel].getId());
			sch[i].setJobID(array[s[i]].getId());
			array2[rSel].setL(array2[rSel].getL() + 1);
			array2[rSel].setC(array2[rSel].getC() + array[s[i]].getOperationT());
			if( s[i] == 0) {
				array[s[i]].setStartT(0);
				array[s[i]].setEndT(array[s[i]].getOperationT());
			}else {
				array[s[i]].setStartT((int) array2[rSel].getC());
				array[s[i]].setEndT((int) array2[rSel].getC() + array[s[i]].getOperationT());
			}
			
		}

	}
}