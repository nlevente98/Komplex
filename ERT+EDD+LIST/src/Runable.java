import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

public class Runable {

	public static void main(String[] args) {
		int NJ = 6;
		int NR = 4;
		int NG = 5;
		String str = "C:\\Users\\Leventeke\\Desktop\\";
		// String str2 = str + "NJ_" + NJ + "_2022-01-29.txt";
		// String str3 = str + "NR_" + NR + "_2022-01-29.txt";
		String test = str + "test.txt";
		String test2 = str + "test2.txt";
		Goals[] goals = new Goals[NG];
		for (int i = 0; i < NG; i++) {
			goals[i] = new Goals();
		}
		goals[0].setName("EDD+List");
		goals[1].setName("SPT+List");
		goals[2].setName("LPT+List");
		goals[3].setName("ERD+ List");
		goals[4].setName("EDD+List+REDD");

		int[] Js = new int[NJ];
		int[] Ws = new int[NR];
		int[] types = null;
		Scheduler[] s = new Scheduler[NJ];
		Scheduler[] s2 = new Scheduler[NJ];
		Jobs[] job = new Jobs[NJ];
		Jobs[] job2 = new Jobs[NJ];
		Jobs[] job3 = new Jobs[NJ];
		Jobs[] job4 = new Jobs[NJ];
		Jobs[] job5 = new Jobs[NJ];
		Jobs[] job6 = new Jobs[NJ];
		for (int i = 0; i < NJ; i++) {
			s[i] = new Scheduler();
			s2[i] = new Scheduler();
			job[i] = new Jobs();
		}

		Resources[] res = new Resources[NR];
		Resources[] res2 = new Resources[NR];
		Resources[] res3 = new Resources[NR];
		Resources[] res4 = new Resources[NR];
		Resources[] res5 = new Resources[NR];
		Resources[] res6 = new Resources[NR];
		for (int i = 0; i < NR; i++) {
			res[i] = new Resources();
			res[i].setId(i + 1);
			res[i].setL(0);
			res[i].setNumber(1);
		}

		try {
			ReadJobs(NJ, job, test);
			ReadResources(NR, res, test2);
		} catch (Exception ex) {
			System.out.println("Error while reading files:\n" + ex.getMessage());
		}

		/*
		 * randomJobGenerator(NJ, job); randomWorkstationGenerator(NR, res); String str4
		 * = str + "NJ_" + NJ + "_" + LocalDate.now() + ".txt"; WriteJobsToTxt(NJ, job,
		 * str4); String str5 = str + "NR_" + NR + "_" + LocalDate.now() + ".txt";
		 * WriteResourcesToTxt(NR, res, str5);
		 */

		SaveJobs(NJ, job, job2);
		SaveResources(NR, res, res2);
		SaveJobs(NJ, job, job3);
		SaveResources(NR, res, res3);
		SaveJobs(NJ, job, job4);
		SaveResources(NR, res, res4);
		SaveJobs(NJ, job, job5);
		SaveResources(NR, res, res5);
		SaveJobs(NJ, job, job6);
		SaveResources(NR, res, res6);

		EDDList(NJ, job, NR, res, s2, Js, Ws, 1, goals, types);
		SPTList(NJ, job2, NR, res2, s, Js, Ws, 2, goals, types);
		LPTList(NJ, job3, NR, res3, s, Js, Ws, 3, goals, types);
		ERDList(NJ, job4, NR, res4, s, Js, Ws, 4, goals, types);
		RList(NJ, job5, NR, res5, s2, Js, Ws, 5, goals, types);

	}

	public static void Message(Jobs[] job) {
		for (int i = 0; i < job.length; i++) {
			System.out.println(job[i].toString());
		}
	}

	public static void Message2(Resources[] res) {
		for (int i = 0; i < res.length; i++) {
			TimeWindows[] time = res[i].getTime();
			System.out.println(res[i].toString());
			for (int j = 0; j < res[i].getTw(); j++) {
				System.out.println((j + 1) + ".\nStart:" + time[j].getStart() + "\tEnd:" + time[j].getEnd() + "\n");
			}
		}
	}

	public static void Message3(Scheduler[] sch) {
		System.out.println("Jobs:\tWorkstations:");
		for (int i = 0; i < sch.length; i++) {
			if (sch[i].getJobID() != 0) {
				System.out.println(sch[i].getJobID() + "\t" + sch[i].getResourceID());
			}
		}
	}

	public static void randomJobGenerator(int NJ, Jobs[] job) {
		Random r = new Random();
		for (int i = 0; i < NJ; i++) {
			job[i].setId(i + 1);
			job[i].setStartT(r.nextInt(10) + 0);
			job[i].setProcT(r.nextInt(20) + 1);
			job[i].setType(r.nextInt(2) + 1);
			if (job[i].getType() == 1)
				job[i].setSetUp(2);
			else
				job[i].setSetUp(3);
			job[i].setD(((NJ - i) * r.nextInt(100) + 20));
		}
	}

	public static void randomWorkstationGenerator(int NR, Resources[] res) {
		Random r = new Random();
		for (int i = 0; i < NR; i++) {
			res[i].setL(0);
			res[i].setC(0);
			res[i].setStartT(r.nextInt(10) + 0);
			res[i].setEndT(r.nextInt(400) + 200);
			res[i].setType(r.nextInt(2) + 1);
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

	public static void ERD(int NJ, Jobs[] job, int[] s) {
		createSchedule(s, NJ);

		for (int i = 0; i < (NJ - 1); i++) {
			int index = i;

			for (int j = (i + 1); j < NJ; j++) {
				if (job[s[index]].getStartT() > job[s[j]].getStartT())
					index = j;
				else if (job[s[index]].getStartT() == job[s[j]].getStartT()) {
					if (job[s[index]].getD() > job[s[j]].getD())
						index = j;
				}
			}

			if (index != i) {
				int temp = s[index];
				s[index] = s[i];
				s[i] = temp;
			}

		}
	}

	public static void AList(int NR, Resources[] res, int[] s, Jobs job) {

		createSchedule(s, NR);

		for (int i = 0; i < (NR - 1); i++) {
			int index = i;

			for (int j = (i + 1); j < NR; j++) {
				if (res[s[index]].getStartT() > res[s[j]].getStartT())
					index = j;
			}

			if (index != i) {
				int temp = s[index];
				s[index] = s[i];
				s[i] = temp;
			}

		}

		for (int i = 0; i < NR; i++) {
			if (res[s[i]].getId() != 0) {
				if (res[s[i]].getEndT() - res[s[i]].getStartT() < job.getProcT()) {
					if (res[s[i]].getTw() > 1 && res[s[i]].getTw() >= res[s[i]].getNumber()) {
						TimeWindows[] time = res[s[i]].getTime();
						res[s[i]].setStartT(time[res[s[i]].getNumber()].getStart());
						res[s[i]].setEndT(time[res[s[i]].getNumber()].getEnd());
						res[s[i]].setNumber(res[s[i]].getNumber() + 1);
					}
				}
			}
		}
	}

	public static void createSchedule(int[] s, int NJ) {
		for (int i = 0; i < NJ; i++) {
			s[i] = i;
		}
	}

	public static void Evaluate(int NJ, Jobs[] job, Goals[] goals, int number) {

		number = number - 1;
		double Emax = 0;
		double Tmax = 0;
		double Esum = 0;
		double Tsum = 0;

		for (int i = 0; i < NJ; i++) {
			if (job[i].getEndT() == 0) {
				job[i].setL(0);
				job[i].setD(0);
			}
			job[i].setL(job[i].getEndT() - job[i].getD());
			if (i == 0) {
				Tmax = job[i].getL();
				Emax = job[i].getL();
			} else {
				if (Tmax < job[i].getL()) {
					Tmax = job[i].getL();
				}
				if (Emax > job[i].getL()) {
					Emax = job[i].getL();
				}
			}
			if (job[i].getL() > 0) {
				Tsum += job[i].getL();
			} else {
				Esum += -job[i].getL();
			}

		}

		goals[number].setTmax(Math.max(0, Tmax));
		goals[number].setEmax(Math.max(0, -Emax));
		goals[number].setTsum(Tsum);
		goals[number].setEsum(Esum);
	}

	public static int EvaluateInt(int NJ, Jobs[] job) {

		double Tmax = 0;

		for (int i = 0; i < NJ; i++) {
			if (job[i].getEndT() == 0) {
				job[i].setL(0);
				job[i].setD(0);
			}
			job[i].setL(job[i].getEndT() - job[i].getD());
			if (i == 0) {
				Tmax = Math.max(0, job[i].getL());
			} else if (Tmax > job[i].getL()) {
				Tmax = job[i].getL();
			}
		}

		return (int) Tmax;
	}

	public static void Simulation(int NJ, Jobs[] job, int[] s) {
		for (int i = 0; i < NJ; i++) {
			job[s[i]].setEndT(job[s[i]].getStartT() + job[s[i]].getProcT());
		}
	}

	public static void Simulation_P(int NR, Jobs[] job, Resources[] res, int[] s) {
		int NJ = 0;
		for (int i = 0; i < NR; i++) {
			NJ = NJ + res[i].getL();
			Simulation(NJ, job, s);
		}
	}

	public static String printGoals(Goals[] goals, int number) {
		number = number - 1;
		String str = "\n" + goals[number].getName() + ":\nGoal functions:\nTmax: " + goals[number].getTmax()
				+ "\nEmax: " + goals[number].getEmax() + "\nTsum: " + goals[number].getTsum() + "\nEsum: "
				+ goals[number].getEsum() + "\n";
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

	public static void List(int NJ, Jobs[] job, int NR, Resources[] res, int[] s, int number, int[] Ws) {

		try {
			createSchedule(s, NJ);
			createSchedule(Ws, NR);
			if (number == 1) {
				EDD(NJ, job, s);
			} else if (number == 2)
				SPT(NJ, job, s);
			else if (number == 3)
				LPT(NJ, job, s);
			else if (number == 4)
				ERD(NJ, job, s);

			for (int i = 0; i < NJ; i++) {
				if (job[s[i]].getId() != 0) {
					AList(NR, res, Ws, job[s[i]]);
					for (int j = 0; j < NR; j++) {
						if (job[s[i]].getStartT() < res[Ws[j]].getStartT()) {
							if (job[s[i]].getStartT() + job[s[i]].getSetUp() <= res[Ws[j]].getStartT()) {
								job[s[i]].setStartT(res[Ws[j]].getStartT());
							} else {
								res[Ws[j]].setStartT(job[s[i]].getStartT() + job[s[i]].getSetUp());
								job[s[i]].setStartT(res[Ws[j]].getStartT());
							}
						} else {
							if (res[Ws[j]].getStartT() + job[s[i]].getSetUp() <= job[s[i]].getStartT()) {
								res[Ws[j]].setStartT(job[s[i]].getStartT());
							} else {
								job[s[i]].setStartT(res[Ws[j]].getStartT() + job[s[i]].getSetUp());
								res[Ws[j]].setStartT(job[s[i]].getStartT());
							}
						}
						if (res[Ws[j]].getEndT() - res[Ws[j]].getStartT() >= job[s[i]].getProcT()) {
							res[Ws[j]].setC(res[Ws[j]].getC() + job[s[i]].getProcT());
							res[Ws[j]].setStartT(res[Ws[j]].getStartT() + job[s[i]].getProcT());
							res[Ws[j]].setL(res[Ws[j]].getL() + 1);
							if (res[Ws[j]].getSch() == null)
								res[Ws[j]].setSch("" + (job[s[i]].getId()));
							else
								res[Ws[j]].setSch(res[Ws[j]].getSch() + " " + (job[s[i]].getId()));
							j = NR - 1;
						}
					}
				}
			}
			System.out.println("Simulation done!\n");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void SaveJobs(int NJ, Jobs[] job, Jobs[] jobs) {
		for (int i = 0; i < NJ; i++) {
			jobs[i] = new Jobs();
			jobs[i].setId(job[i].getId());
			jobs[i].setStartT(job[i].getStartT());
			jobs[i].setProcT(job[i].getProcT());
			jobs[i].setD(job[i].getD());
			jobs[i].setType(job[i].getType());
			jobs[i].setSetUp(job[i].getSetUp());
		}
	}

	public static void SaveResources(int NR, Resources[] res, Resources[] ress) {
		for (int i = 0; i < NR; i++) {
			ress[i] = new Resources();
			ress[i].setId(res[i].getId());
			ress[i].setTime(res[i].getTime());
			ress[i].setTw(res[i].getTw());
			ress[i].setNumber(res[i].getNumber());
			ress[i].setStartT(res[i].getStartT());
			ress[i].setEndT(res[i].getEndT());
			ress[i].setType(res[i].getType());
			ress[i].setL(res[i].getL());
			ress[i].setC(res[i].getC());
		}
	}

	public static void SaveSchedule(int NJ, int NR, Resources[] res, Scheduler[] sch) {
		try {
			ArrayList<Scheduler> list = new ArrayList<Scheduler>();
			for (int i = 0; i < NR; i++) {
				System.out.println(res[i].getSch());
				if(res[i].getSch() != null) {
					String str = res[i].getSch();
					String[] numbersArray = str.split(" ");
					for (int j = 0; j < numbersArray.length; j++) {
						Scheduler s = new Scheduler();
						s.setJobID(Integer.parseInt(numbersArray[j]));
						s.setResourceID(i + 1);
						if (s.getJobID() != 0)
							list.add(s);
					}
				}
			}
			for (int i = 0; i < NJ; i++) {
				sch[i].setJobID(list.get(i).getJobID());
				sch[i].setResourceID(list.get(i).getResourceID());
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
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
				int setUp = Integer.parseInt(stn.nextToken());

				job[count].setProcT(ProcT);
				job[count].setStartT(StartT);
				job[count].setD(d);
				job[count].setType(Type);
				job[count].setSetUp(setUp);
				job[count].setId(count + 1);
				count++;

			}

			for (int i = 0; i < NJ; i++) {
				System.out.println(job[i].toString());
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public static void WriteJobsToTxt(int NJ, Jobs[] job, String string) throws IOException {
		try (BufferedWriter bf = new BufferedWriter(new FileWriter(string))) {
			bf.write("ProcT	StartT	d	Type	SetUp");
			for (int i = 0; i < NJ; i++) {
				String str = "\n" + job[i].getProcT() + "\t" + job[i].getStartT() + "\t" + job[i].getD() + "\t"
						+ job[i].getType() + "\t" + job[i].getSetUp();
				bf.append(str);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public static void WriteResourcesToTxt(int NR, Resources[] res, String string) throws IOException {
		try (BufferedWriter bf = new BufferedWriter(new FileWriter(string))) {
			bf.write("start	end	Type");
			for (int i = 0; i < NR; i++) {
				String str = "\n" + res[i].getStartT() + "\t" + res[i].getEndT() + "\t" + res[i].getType();
				bf.append(str);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	public static void ReadResources(int NR, Resources[] res, String string) throws IOException {
		FileReader file = new FileReader(string);
		try (BufferedReader bf = new BufferedReader(file)) {
			String st = bf.readLine();

			while ((st = bf.readLine()) != null) {

				StringTokenizer stn = new StringTokenizer(st);
				int id = Integer.parseInt(stn.nextToken());
				int type = Integer.parseInt(stn.nextToken());
				int tw = Integer.parseInt(stn.nextToken());
				TimeWindows[] time = new TimeWindows[tw];
				for (int i = 0; i < tw; i++) {
					time[i] = new TimeWindows();
					int start = Integer.parseInt(stn.nextToken());
					int end = Integer.parseInt(stn.nextToken());
					time[i].setStart(start);
					time[i].setEnd(end);
				}

				id = id - 1;
				res[id].setId(id + 1);
				res[id].setTime(time);
				res[id].setStartT(time[0].getStart());
				res[id].setEndT(time[0].getEnd());
				res[id].setTw(tw);
				res[id].setType(type);
				res[id].setC(0);
				res[id].setL(0);

			}

			for (int i = 0; i < NR; i++) {
				System.out.println(res[i].getId() + ".Resource readed successfully!");
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
		for (int i = 0; i < (sch.length - 1); i++) {
			int index = i;
			for (int j = (i + 1); j < sch.length; j++) {
				if (sch[index].getResourceID() > sch[j].getResourceID())
					index = j;
				else if (sch[index].getResourceID() == sch[j].getResourceID()
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

	public static void BestAlgorithm(Goals[] goals, int NG) {
		for (int i = 0; i < (NG - 1); i++) {
			int index = i;
			if (goals[index].getTmax() >= 0) {
				for (int j = (i + 1); j < NG; j++) {
					if (goals[index].getTmax() > goals[j].getTmax() && goals[j].getTmax() >= 0)
						index = j;
					else if (goals[index].getEmax() >= 2 * goals[j].getEmax() && goals[j].getTmax() >= 0)
						index = j;
					else if (goals[index].getTmax() == goals[j].getTmax() && goals[index].getEmax() > goals[j].getEmax()
							&& goals[j].getTmax() >= 0)
						index = j;
				}
			} else if (NG == 2) {
				index = NG - 1;
			}

			if (index != i) {
				Goals temp = goals[index];
				goals[index] = goals[i];
				goals[i] = temp;
			}

		}
	}

	public static int CountNumbersAppers(int needed, Scheduler[] sch) {
		int count = 0;
		for (int j = 0; j < sch.length; j++) {
			if (sch[j].getResourceID() == needed) {
				count++;
			}
		}
		return count;
	}

	public static Jobs[] JobsAreDone(int NJ, Jobs[] job) {
		ArrayList<Jobs> list = new ArrayList<Jobs>();
		for (int i = 0; i < NJ; i++) {
			if (job[i].getEndT() != 0 && job[i].getD() != 0 && job[i].getL() != 0) {
				list.add(job[i]);
			}
		}
		Jobs[] jobs = new Jobs[list.size()];
		for (int i = 0; i < list.size(); i++) {
			jobs[i] = list.get(i);
		}
		return jobs;
	}

	public static Scheduler[] ScheduleDoneJobs(int NJ, Scheduler[] s) {
		ArrayList<Scheduler> list = new ArrayList<Scheduler>();
		for (int i = 0; i < NJ; i++) {
			if (s[i].getResourceID() != 0) {
				list.add(s[i]);
			}
		}
		Scheduler[] sch = new Scheduler[list.size()];
		for (int i = 0; i < list.size(); i++) {
			sch[i] = list.get(i);
		}
		return sch;
	}

	public static void ReverseList(int NJ, Jobs[] job, int number, Scheduler[] sch, int num) {
		int next = 0;
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < NJ; i++) {
			if (sch[i].getResourceID() == num + 1)
				list.add(sch[i].getJobID() - 1);
		}
		int[] Js = new int[list.size()];
		for (int i = 0; i < Js.length; i++) {
			Js[i] = list.get(i);
		}
		try {
			for (int i = (Js.length - 1); i >= 0; i--) {
				if (i != 0) {
					if (job[Js[i]].getD() - job[Js[i]].getProcT() - next >= job[Js[i - 1]].getD()
							&& job[Js[i]].getD() - job[Js[i]].getProcT() - next >= 0) {
						job[Js[i]].setStartT(job[Js[i]].getD() - job[Js[i]].getProcT() - next + number);
						next = 0;
					} else if (job[Js[i]].getD() - job[Js[i]].getProcT() - next >= 0) {
						job[Js[i]].setStartT(job[Js[i]].getD() - job[Js[i]].getProcT() - next + number);
						next += (job[Js[i - 1]].getD() - (job[Js[i]].getD() - job[Js[i]].getProcT()));
					}
				} else {
					if (job[Js[i]].getD() - job[Js[i]].getProcT() - next >= 0 + job[Js[i]].getSetUp()) {
						job[Js[i]].setStartT(job[Js[i]].getD() - job[Js[i]].getProcT() - next + number);
					} else {
						job[Js[i]].setStartT(0 + job[Js[i]].getSetUp() + number);
					}
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}

	public static void EDDList(int NJ, Jobs[] job, int NR, Resources[] res, Scheduler[] s2, int[] Js, int[] Ws,
			int number, Goals[] goals, int[] types) {
		types = ListTypes(job, NJ);
		int type = 0;
		for (int j = 0; j < types.length; j++) {
			System.out.println("Types: " + types[j]);
			type = types[j];
			Jobs[] jobs = SelectJobsByType(type, job, NJ);
			Resources[] ress = SelectResByType(type, res, NR);

			List(NJ, jobs, NR, ress, Js, number, Ws);
			for (int i = 0; i < jobs.length; i++) {
				if (jobs[i].getId() != 0)
					job[i] = jobs[i];
			}
			for (int i = 0; i < ress.length; i++) {
				if (ress[i].getId() != 0) {
					res[i] = ress[i];
				}
			}
		}
		SaveSchedule(NJ, NR, res, s2);
		AfterScheduler(NJ, job, s2);
		Simulation_P(NR, job, res, Js);
		Evaluate(NJ, job, goals, 1);
		System.out.println(printGoals(goals, 1));
		Message(job);
		Message3(s2);
	}

	public static void SPTList(int NJ, Jobs[] job, int NR, Resources[] res, Scheduler[] s2, int[] Js, int[] Ws,
			int number, Goals[] goals, int[] types) {
		types = ListTypes(job, NJ);
		int type = 0;
		for (int j = 0; j < types.length; j++) {
			System.out.println("Types: " + types[j]);
			type = types[j];
			Jobs[] jobs = SelectJobsByType(type, job, NJ);
			Resources[] ress = SelectResByType(type, res, NR);

			List(NJ, jobs, NR, ress, Js, number, Ws);
			for (int i = 0; i < jobs.length; i++) {
				if (jobs[i].getId() != 0)
					job[i] = jobs[i];
			}
			for (int i = 0; i < ress.length; i++) {
				if (ress[i].getId() != 0) {
					res[i] = ress[i];
				}
			}
		}
		SaveSchedule(NJ, NR, res, s2);
		AfterScheduler(NJ, job, s2);
		Simulation_P(NR, job, res, Js);
		Evaluate(NJ, job, goals, number);
		System.out.println(printGoals(goals, number));
		Message(job);
		Message3(s2);
	}

	public static void LPTList(int NJ, Jobs[] job, int NR, Resources[] res, Scheduler[] s2, int[] Js, int[] Ws,
			int number, Goals[] goals, int[] types) {
		types = ListTypes(job, NJ);
		int type = 0;
		for (int j = 0; j < types.length; j++) {
			System.out.println("Types: " + types[j]);
			type = types[j];
			Jobs[] jobs = SelectJobsByType(type, job, NJ);
			Resources[] ress = SelectResByType(type, res, NR);

			List(NJ, jobs, NR, ress, Js, number, Ws);
			for (int i = 0; i < jobs.length; i++) {
				if (jobs[i].getId() != 0)
					job[i] = jobs[i];
			}
			for (int i = 0; i < ress.length; i++) {
				if (ress[i].getId() != 0) {
					res[i] = ress[i];
				}
			}
		}
		SaveSchedule(NJ, NR, res, s2);
		AfterScheduler(NJ, job, s2);
		Simulation_P(NR, job, res, Js);
		Evaluate(NJ, job, goals, number);
		System.out.println(printGoals(goals, number));
		Message(job);
		Message3(s2);
	}

	public static void ERDList(int NJ, Jobs[] job, int NR, Resources[] res, Scheduler[] s2, int[] Js, int[] Ws,
			int number, Goals[] goals, int[] types) {
		types = ListTypes(job, NJ);
		int type = 0;
		for (int j = 0; j < types.length; j++) {
			System.out.println("Types: " + types[j]);
			type = types[j];
			Jobs[] jobs = SelectJobsByType(type, job, NJ);
			Resources[] ress = SelectResByType(type, res, NR);

			List(NJ, jobs, NR, ress, Js, number, Ws);
			for (int i = 0; i < jobs.length; i++) {
				if (jobs[i].getId() != 0)
					job[i] = jobs[i];
			}
			for (int i = 0; i < ress.length; i++) {
				if (ress[i].getId() != 0) {
					res[i] = ress[i];
				}
			}
		}
		SaveSchedule(NJ, NR, res, s2);
		AfterScheduler(NJ, job, s2);
		Simulation_P(NR, job, res, Js);
		Evaluate(NJ, job, goals, number);
		System.out.println(printGoals(goals, number));
		Message(job);
		Message3(s2);
	}

	public static void RList(int NJ, Jobs[] job, int NR, Resources[] res, Scheduler[] s2, int[] Js, int[] Ws,
			int number, Goals[] goals, int[] types) {
		BestAlgorithm(goals, 4);
		String name = goals[0].getName();
		System.out.println("Best choice: " + name);
		switch (name) {
		case "EDD+List":
			EDDList(NJ, job, NR, res, s2, Js, Ws, 1, goals, types);
			break;
		case "SPT+List":
			SPTList(NJ, job, NR, res, s2, Js, Ws, 1, goals, types);
			break;
		case "LPT+List":
			LPTList(NJ, job, NR, res, s2, Js, Ws, 1, goals, types);
			break;
		case "ERD+List":
			ERDList(NJ, job, NR, res, s2, Js, Ws, 1, goals, types);
			break;
		}
		SaveSchedule(NJ, NR, res, s2);
		AfterScheduler(NJ, job, s2);
		for (int i = 0; i < NR; i++) {
			ReverseList(NJ, job, 3, s2, i);
		}
		Simulation_P(NR, job, res, Js);
		Evaluate(NJ, job, goals, number);
		System.out.println(printGoals(goals, number));
		Message(job);
		Message3(s2);
	}

	public static Jobs[] SelectJobsByType(int type, Jobs[] job, int NJ) {
		ArrayList<Jobs> list = new ArrayList<Jobs>();
		for (int i = 0; i < NJ; i++) {
			if (job[i].getType() != 0 && job[i].getType() == type) {
				list.add(job[i]);
			}
		}

		Jobs[] jobs = new Jobs[NJ];
		for (int i = 0; i < NJ; i++) {
			jobs[i] = new Jobs();
		}
		do {
			Jobs job2 = list.get(0);
			jobs[job2.getId() - 1] = job2;
			list.remove(0);
		} while (list.isEmpty() == false);

		return jobs;
	}

	public static Resources[] SelectResByType(int type, Resources[] res, int NR) {
		ArrayList<Resources> list = new ArrayList<Resources>();
		for (int i = 0; i < NR; i++) {
			if (res[i].getType() != 0 && res[i].getType() == type) {
				list.add(res[i]);
			}
		}

		Resources[] ress = new Resources[NR];
		for (int i = 0; i < NR; i++) {
			ress[i] = new Resources();
		}
		do {
			Resources res2 = list.get(0);
			ress[res2.getId() - 1] = res2;
			list.remove(0);
		} while (list.isEmpty() == false);

		return ress;
	}

	public static int[] ListTypes(Jobs[] job, int NJ) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < NJ; i++) {
			if (job[i].getType() != 0 && list.contains(job[i].getType())) {
			} else {
				list.add(job[i].getType());
			}
		}

		int[] types = new int[list.size()];
		for (int i = 0; i < list.size(); i++) {
			types[i] = list.get(i);
		}
		return types;
	}

	public static void AfterScheduler(int NJ, Jobs[] job, Scheduler[] s) {

		for (int i = 0; i < NJ - 1; i++) {
			int index = i;

			for (int j = i + 1; j < NJ; j++) {
				if (job[s[index].getJobID() - 1].getStartT() > job[s[j].getJobID() - 1].getStartT())
					index = j;
			}

			if (index != i) {
				Scheduler temp = s[index];
				s[index] = s[i];
				s[i] = temp;
			}
		}
	}

}