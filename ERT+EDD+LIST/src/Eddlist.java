import java.util.StringTokenizer;
import java.io.*;

public class Eddlist {

	public static void main(String[] args) throws IOException {
		String str = "C:\\Users\\Dell\\OneDrive\\Dokumentumok\\Test.txt";
		String str2 = "C:\\Users\\Dell\\OneDrive\\Dokumentumok\\Test2.txt";
		int NJ=7;
		int NW=3;
		double[] goalFunction = new double[2];
		double[] goalFunction2 = new double[2];
		double[] goalFunction3 = new double[2];

		int[] Js = new int[NJ];
		int[] Ws = new int[NW];
		Scheduler[] s = new Scheduler[NJ];
		Scheduler[] s2 = new Scheduler[NJ];
		Scheduler[] s3 = new Scheduler[NJ];
		Jobs[] job = new Jobs[NJ];
		Jobs[] job2 = new Jobs[NJ];
		Jobs[] job3 = new Jobs[NJ];
		for (int i = 0; i < NJ; i++) {
			s[i] = new Scheduler();
			s2[i] = new Scheduler();
			s3[i] = new Scheduler();
			job[i] = new Jobs();
			job2[i] = new Jobs();
			job3[i] = new Jobs();
		}

		Resources[] res = new Resources[NW];
		Resources[] res2 = new Resources[NW];
		Resources[] res3 = new Resources[NW];
		for (int i = 0; i < NW; i++) {
			res[i] = new Resources();
			res2[i] = new Resources();
			res3[i] = new Resources();
			res[i].setId(i + 1);
			res[i].setL(200);
		}

		ReadJobs(NJ, job, str);
		ReadResources(NW, res, str2);

		SaveJobs(NJ, job, job2);
		SaveWorkstations(NW, res, res2);
		SaveJobs(NJ, job, job3);
		SaveWorkstations(NW, res, res3);

		// EDD+List
		List(NJ, job, NW, res, s, Js, 1, Ws);
		Simulation_P(NW, job, res, Js);
		Evaluate(NJ, job, goalFunction);

		System.out.println("\nEDD+List:");
		System.out.println(printGoals(goalFunction));
		System.out.println();
		Massage3(s);
		System.out.println();
		Massage2(res);
		System.out.println();
		Massage(job);

		// SPT+List
		System.out.println();
		List(NJ, job2, NW, res2, s2, Js, 2, Ws);
		Simulation_P(NW, job2, res2, Js);
		Evaluate(NJ, job2, goalFunction2);

		System.out.println("\nSPT+List:");
		System.out.println(printGoals(goalFunction2));
		System.out.println();
		Massage3(s2);
		System.out.println();
		Massage2(res2);
		System.out.println();
		Massage(job2);

		// LPT+List
		System.out.println();
		List(NJ, job3, NW, res3, s3, Js, 3, Ws);
		Simulation_P(NW, job3, res3, Js);
		Evaluate(NJ, job3, goalFunction3);

		System.out.println("\nLPT+List:");
		System.out.println(printGoals(goalFunction3));
		System.out.println();
		Massage3(s3);
		System.out.println();
		Massage2(res3);
		System.out.println();
		Massage(job3);

		ScheduleRes(Js, job2, NJ, s2);
		System.out.println();
		Massage3(s2);
		int need = CountNumbersAppers(1, NJ, NW, s2);
		System.out.println("1.res: " + need);
		System.out.println("Best algorith: ");
		BestAlgorithm(goalFunction, goalFunction2, goalFunction3);

	}

	public static void Massage(Jobs[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.println(array[i].toString());
		}
	}

	public static void Massage2(Resources[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.println(array[i].toString());
		}
	}

	public static void Massage3(Scheduler[] array) {
		System.out.println("Jobs:\tWorkstations:");
		for (int i = 0; i < array.length; i++) {
			if (array[i].getJobID() != 0) {
				System.out.println(array[i].getJobID() + "\t" + array[i].getWorkstationID());
			}
		}
	}

	public static void SPT(int NJ, Jobs[] job, int[] s) {

		createSchedule(s, NJ);
		for (int i = 0; i < NJ - 1; i++) {
			int index = i;

			for (int j = i + 1; j < NJ; j++) {
				if (job[s[index]].getProcT() > job[s[j]].getProcT())
					index = j;
			}

			if (index != i) {
				int temp = s[index];
				s[index] = s[i];
				s[i] = temp;
			}
		}
	}

	public static void EDD(int NJ, Jobs[] job, int[] s) {

		createSchedule(s, NJ);

		for (int i = 0; i < (NJ - 1); i++) {
			int index = i;

			for (int j = (i + 1); j < NJ; j++) {
				if (job[s[index]].getD() > job[s[j]].getD())
					index = j;
			}

			if (index != i) {
				int temp = s[index];
				s[index] = s[i];
				s[i] = temp;
			}

		}
	}

	public static void LPT(int NJ, Jobs[] job, int[] s) {

		createSchedule(s, NJ);

		for (int i = 0; i < (NJ - 1); i++) {
			int index = i;

			for (int j = (i + 1); j < NJ; j++) {
				if (job[s[index]].getProcT() < job[s[j]].getProcT())
					index = j;
			}

			if (index != i) {
				int temp = s[index];
				s[index] = s[i];
				s[i] = temp;
			}

		}
	}

	public static void WSS(int NW, Resources[] res, int[] s) {

		createSchedule(s, NW);

		for (int i = 0; i < (NW - 1); i++) {
			int index = i;

			for (int j = (i + 1); j < NW; j++) {
				if (res[s[index]].getStart() > res[s[j]].getStart())
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
			if (array[i].getEndT() == 0) {
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
				if (Emax > array[i].getL()) {
					Emax = array[i].getL();
				}
			}
		}

		goal[0] = Lmax;
		if (Emax == 0)
			goal[1] = Emax;
		else
			goal[1] = Math.max(0, -Emax);
	}

	public static void Simulation(int NJ, Jobs[] array, int[] s) {
		for (int i = 0; i < NJ; i++) {
			array[s[i]].setEndT(array[s[i]].getStartT() + array[s[i]].getProcT());
		}
	}

	public static void Simulation_P(int NW, Jobs[] array, Resources[] array2, int[] s) {
		int NJ = 0;
		for (int i = 0; i < NW; i++) {
			NJ = NJ + array2[i].getL();
			Simulation(NJ, array, s);
		}
	}

	public static String printGoals(double[] goal) {
		String str = "Goal functions:\nLmax:" + goal[0] + "\nEmax" + goal[1];
		return str;
	}

	public static String printJobs(Jobs[] job) {
		String str = "";
		for (int i = 0; i < job.length; i++) {
			str += job[i].toString() + "\n";
		}
		return str;
	}

	public static String printResources(Resources[] res) {
		String str = "";
		for (int i = 0; i < res.length; i++) {
			str += res[i].toString() + "\n";
		}
		return str;
	}

	public static void List(int NJ, Jobs[] job, int NW, Resources[] res, Scheduler[] sch, int[] s, int number,
			int[] Ws) {

		try {
			if (NW != 1) {
				createSchedule(s, NJ);
				createSchedule(Ws, NW);
				if (number == 1)
					EDD(NJ, job, s);
				else if (number == 2)
					SPT(NJ, job, s);
				else if (number == 3)
					LPT(NJ, job, s);

				for (int i = 0; i < NJ; i++) {
					WSS(NW, res, Ws);
					for (int j = 0; j < NW; j++) {
						if (res[Ws[j]].getEnd() - res[Ws[j]].getStart() >= job[s[i]].getProcT()
								&& res[Ws[j]].getType() == job[s[i]].getType()) {
							System.out.println(
									job[s[i]].getId() + " job assigned to " + res[Ws[j]].getId() + " workstation!\n");
							if (job[s[i]].getStartT() <= res[Ws[j]].getStart())
								job[s[i]].setStartT(res[Ws[j]].getStart());
							else
								res[Ws[j]].setStart(job[s[i]].getStartT());

							sch[i].setWorkstationID(res[Ws[j]].getId());
							sch[i].setJobID(job[s[i]].getId());
							res[Ws[j]].setL(res[Ws[j]].getL() + 1);
							res[Ws[j]].setC(res[Ws[j]].getC() + job[s[i]].getProcT());
							res[Ws[j]].setStart(res[Ws[j]].getStart() + job[s[i]].getProcT());
							j = NW - 1;

						} else {
							System.out.println(job[s[i]].getId() + " job can't be done in " + res[Ws[j]].getId()
									+ " workstation!\n");
							if (j == NW - 1) {
								System.out.println(
										"This job can't be done in any of these workstation! Try out with more!\n");
								sch[i].setJobID(job[s[i]].getId());
								sch[i].setWorkstationID(0);
							}
						}
					}
				}
				System.out.println("Simulation done!\n");
			} else {
				OneWorkstation(NJ, job, NW, res, sch, s, number);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void OneWorkstation(int NJ, Jobs[] job, int NW, Resources[] res, Scheduler[] sch, int[] s,
			int number) {

		try {
			int rSel = 0;
			int more = 0;
			createSchedule(s, NJ);
			if (number == 1)
				EDD(NJ, job, s);
			else if (number == 2)
				SPT(NJ, job, s);
			else if (number == 3)
				LPT(NJ, job, s);

			for (int i = 0; i < NJ; i++) {
				if (res[rSel].getEnd() - res[rSel].getStart() >= job[s[i]].getProcT()
						&& res[rSel].getType() == job[s[i]].getType()) {
					System.out.println(job[s[i]].getId() + " job assigned to " + res[rSel].getId() + " workstation!\n");
					if (job[s[i]].getStartT() <= res[rSel].getStart())
						job[s[i]].setStartT(res[rSel].getStart());
					else
						res[rSel].setStart(job[s[i]].getStartT());

					sch[i].setWorkstationID(res[rSel].getId());
					sch[i].setJobID(job[s[i]].getId());
					res[rSel].setL(res[rSel].getL() + 1);
					res[rSel].setC(res[rSel].getC() + job[s[i]].getProcT());
					res[rSel].setStart(res[rSel].getStart() + job[s[i]].getProcT());

				} else {
					System.out.println(
							job[s[i]].getId() + " job can't be done in " + res[rSel].getId() + " workstation!\n");
					sch[i].setWorkstationID(0);
					sch[i].setJobID(job[s[i]].getId());
					more++;
				}
			}
			System.out.println("Simulation done!");
			if (more != 0)
				System.out.println("This job can't be done in any of these workstation! Try out with more!\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void SaveJobs(int NJ, Jobs[] job, Jobs[] jobs) {
		for (int i = 0; i < NJ; i++) {
			jobs[i].setId(job[i].getId());
			jobs[i].setStartT(job[i].getStartT());
			jobs[i].setProcT(job[i].getProcT());
			jobs[i].setD(job[i].getD());
			jobs[i].setType(job[i].getType());
		}
	}

	public static void SaveWorkstations(int NW, Resources[] res, Resources[] works) {
		for (int i = 0; i < NW; i++) {
			works[i].setId(res[i].getId());
			works[i].setStart(res[i].getStart());
			works[i].setEnd(res[i].getEnd());
			works[i].setL(res[i].getL());
			works[i].setC(res[i].getC());
			works[i].setType(res[i].getType());
		}
	}

	public static void ReadJobs(int NJ, Jobs[] job, String string) throws IOException {
		FileReader file = new FileReader(string);
		try (BufferedReader bf = new BufferedReader(file)) {
			String st = bf.readLine();
			int count = 0;

			while ((st = bf.readLine()) != null) {

				StringTokenizer stn = new StringTokenizer(st);
				int ProcT = Integer.parseInt(stn.nextToken());
				int StartT = Integer.parseInt(stn.nextToken());
				int d = Integer.parseInt(stn.nextToken());
				int Type = Integer.parseInt(stn.nextToken());

				job[count].setId(count + 1);
				job[count].setProcT(ProcT);
				job[count].setStartT(StartT);
				job[count].setD(d);
				job[count].setType(Type);
				count++;

			}

			for (int i = 0; i < NJ; i++) {
				System.out.println(job[i].toString());
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public static void ReadResources(int NW, Resources[] res, String string) throws IOException {
		FileReader file = new FileReader(string);
		try (BufferedReader bf = new BufferedReader(file)) {
			String st = bf.readLine();
			int count2 = 0;

			while ((st = bf.readLine()) != null) {

				StringTokenizer stn = new StringTokenizer(st);
				int start = Integer.parseInt(stn.nextToken());
				int end = Integer.parseInt(stn.nextToken());
				int Type = Integer.parseInt(stn.nextToken());

				res[count2].setId(count2 + 1);
				res[count2].setStart(start);
				res[count2].setEnd(end);
				res[count2].setType(Type);
				res[count2].setC(0);
				res[count2].setL(0);
				count2++;

			}

			for (int i = 0; i < NW; i++) {
				System.out.println(res[i].toString());
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public static int SaveLastEndTime(int NJ, int[] Js, Jobs[] job) {
		int number = 0;
		for (int i = NJ - 1; i > 0; i--) {
			if (job[Js[i]].getEndT() != 0) {
				number = job[Js[NJ - 1]].getEndT();
				i = 1;
			}
		}
		return number;
	}

	public static void ScheduleRes(int[] s, Jobs[] job, int NJ, Scheduler[] sch) {
		for (int i = 0; i < (NJ - 1); i++) {
			int index = i;
			for (int j = (i + 1); j < NJ; j++) {
				if (sch[index].getWorkstationID() > sch[j].getWorkstationID())
					index = j;
				else if (sch[index].getWorkstationID() == sch[j].getWorkstationID()
						&& job[(sch[index].getJobID() - 1)].getStartT() > job[(sch[j].getJobID() - 1)].getStartT()) {
					index = j;
				}
			}
			if (index != i) {
				Scheduler temp = sch[index];
				sch[index] = sch[i];
				sch[i] = temp;
			}
		}
	}

	public static int BestAlgorithm(double[] g, double[] g2, double[] g3) {
		int lmax = 0;
		int best = 1;
		int[] goals = { (int) g[0], (int) g2[0], (int) g3[0] };
		for (int i = 0; i < goals.length; i++) {
			if (i == 0) {
				lmax = goals[0];
			} else if (lmax > goals[i]) {
				lmax = goals[i];
				best = i + 1;
			}
		}
		switch (best) {
		case 1:
			System.out.println("EDD+List with Lmax: " + lmax);
			break;
		case 2:
			System.out.println("SPT+List with Lmax: " + lmax);
			break;
		case 3:
			System.out.println("LPT+List with Lmax: " + lmax);
			break;
		}
		return best;
	}

	public static int CountNumbersAppers(int needed, int NJ, int NW, Scheduler[] sch) {
		int count = 0;
		for (int j = 0; j < NJ; j++) {
			if (sch[j].getWorkstationID() == needed) {
				count++;
			}
		}
		return count;
	}
	
}